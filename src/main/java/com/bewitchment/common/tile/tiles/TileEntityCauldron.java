package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.api.state.StateProperties;
import com.bewitchment.common.content.cauldron.behaviours.DefaultBehaviours;
import com.bewitchment.common.content.cauldron.behaviours.ICauldronBehaviour;
import com.bewitchment.common.content.cauldron.teleportCapability.CapabilityCauldronTeleport;
import com.bewitchment.common.core.helper.ColorHelper;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.tile.ModTileEntity;
import com.bewitchment.common.tile.util.CauldronFluidTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TileEntityCauldron extends ModTileEntity implements ITickable {

	public static final int DEFAULT_COLOR = 0x42499b;

	private NonNullList<ItemStack> ingredients = NonNullList.create();
	private AxisAlignedBB collectionZone;
	private CauldronFluidTank tank;

	private IMagicPowerConsumer powerManager = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	private DefaultBehaviours defaultBehaviours = new DefaultBehaviours();
	private LinkedList<ICauldronBehaviour> behaviors = new LinkedList<>();
	private ICauldronBehaviour currentBehaviour;

	private String name;

	private int targetColorRGB = DEFAULT_COLOR;
	private int effectiveClientSideColor = DEFAULT_COLOR;

	public TileEntityCauldron() {
		this.collectionZone = new AxisAlignedBB(0, 0, 0, 1, 0.65D, 1);
		this.tank = new CauldronFluidTank(this);
		this.defaultBehaviours.init(this);
	}

	public static void giveItemToPlayer(EntityPlayer player, ItemStack toGive) {
		if (!player.inventory.addItemStackToInventory(toGive)) {
			player.dropItem(toGive, false);
		} else if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && playerIn.getHeldItem(hand).isEmpty()) {
			worldIn.setBlockState(pos, state.cycleProperty(StateProperties.HANDLE_DOWN), 3);
		}
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!playerIn.world.isRemote && (this.ingredients.size() == 0) && heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			FluidUtil.interactWithFluidHandler(playerIn, hand, this.tank);
			if (playerIn.isCreative() && this.tank.isFull()) {
				this.markDirty();
				this.syncToClient();
			}
		}

		if (heldItem.getItem() == Items.NAME_TAG) {
			String oldname = this.name;
			this.name = heldItem.getDisplayName();
			CapabilityCauldronTeleport ctp = this.world.getCapability(CapabilityCauldronTeleport.CAPABILITY, null);
			if (ctp.put(this.world, pos)) {
				this.markDirty();
				this.syncToClient();
				if (!playerIn.isCreative()) {
					heldItem.shrink(1);
				}
			} else {
				this.name = oldname;
			}
		}

		this.currentBehaviour.playerInteract(playerIn, hand);

		return true;
	}

	@Override
	public void update() {
		if (this.world.isRemote) {
			if (this.effectiveClientSideColor != this.targetColorRGB) {
				this.effectiveClientSideColor = ColorHelper.blendColor(this.effectiveClientSideColor, this.targetColorRGB, 0.92f);
			}
		} else {
			this.behaviors.forEach(d -> d.update(d == this.currentBehaviour));
			if (this.behaviors.stream().allMatch(d -> !d.shouldInputsBeBlocked())) {
				ItemStack next = this.gatherNextItemFromTop();
				if (!next.isEmpty()) {
					this.ingredients.add(next);
					this.setTankLock(false);
					this.behaviors.forEach(d -> d.statusChanged(d == this.currentBehaviour));
					if (this.targetColorRGB != this.currentBehaviour.getColor()) {
						this.setColor(this.currentBehaviour.getColor());
					}
					this.markDirty();
					this.syncToClient();
				}
			}
		}
	}

	public NonNullList<ItemStack> getInputs() {
		return this.ingredients;
	}

	public void addBehaviour(ICauldronBehaviour b) {
		this.behaviors.add(b);
	}

	private ItemStack gatherNextItemFromTop() {
		List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, this.collectionZone.offset(this.getPos()));
		if (list.isEmpty()) {
			return ItemStack.EMPTY;
		}
		EntityItem selectedEntityItem = list.get(0);
		if (this.currentBehaviour.canAccept(selectedEntityItem)) {
			ItemStack next = selectedEntityItem.getItem().splitStack(1);
			if (selectedEntityItem.getItem().isEmpty()) {
				selectedEntityItem.setDead();
			}
			ItemStack container = next.getItem().getContainerItem(next);
			if (!container.isEmpty()) {
				EntityItem res = new EntityItem(this.world, this.pos.getX() + 0.5, this.pos.getY() + 0.9, this.pos.getZ() + 0.5, container);
				res.addTag("cauldron_drop");
				this.world.spawnEntity(res);
			}
			this.world.playSound(null, this.pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1f, (float) ((0.2f * Math.random()) + 1));
			return next;
		}
		return ItemStack.EMPTY;
	}

	public int getColorRGB() {
		return this.effectiveClientSideColor;
	}

	public boolean hasItemsInside() {
		return !this.ingredients.isEmpty();
	}

	public Optional<FluidStack> getFluid() {
		return this.tank.isEmpty() ? Optional.empty() : Optional.ofNullable(this.tank.getFluid());
	}

	public void setColor(int color) {
		this.targetColorRGB = color;
		this.syncToClient();
		this.markDirty();
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || (capability == IMagicPowerConsumer.CAPABILITY);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.tank);
		}
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(this.powerManager);
		}
		return super.getCapability(capability, facing);
	}

	public void handleParticles() {
		this.behaviors.forEach(d -> d.handleParticles(d == this.currentBehaviour));
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setInteger("color", this.targetColorRGB);
		tag.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("ingredients", ItemStackHelper.saveAllItems(new NBTTagCompound(), this.ingredients));
		if (this.name != null) {
			tag.setString("name", this.name);
		}
		this.behaviors.forEach(d -> d.saveToNBT(tag));
		tag.setString("behaviour", this.currentBehaviour.getID());
		tag.setTag("mp", this.powerManager.writeToNbt());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		this.targetColorRGB = tag.getInteger("color");
		this.tank.readFromNBT(tag.getCompoundTag("tank"));
		this.ingredients.clear();
		if (tag.hasKey("name")) {
			this.name = tag.getString("name");
		} else {
			this.name = null;
		}
		ItemStackHelper.loadAllItems(tag.getCompoundTag("ingredients"), this.ingredients);
		this.behaviors.forEach(d -> d.loadFromNBT(tag));
		String id = tag.getString("behaviour");
		this.currentBehaviour = this.behaviors.stream().filter(d -> d.getID().equals(id)).findFirst().orElse(this.defaultBehaviours.IDLE);
		this.powerManager.readFromNbt(tag.getCompoundTag("mp"));
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
		tag.setInteger("color", this.targetColorRGB);
		tag.setBoolean("hasItemsInside", this.ingredients.size() > 0);
		if (this.name != null) {
			tag.setString("name", this.name);
		}
		this.behaviors.forEach(d -> d.saveToSyncNBT(tag));
		tag.setString("behaviour", this.currentBehaviour.getID());
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		this.tank.readFromNBT(tag.getCompoundTag("tank"));
		this.targetColorRGB = tag.getInteger("color");
		if (tag.getBoolean("hasItemsInside")) {
			this.ingredients.clear();
			this.ingredients.add(ItemStack.EMPTY); // Makes the list not empty
		}
		if (tag.hasKey("name")) {
			this.name = tag.getString("name");
		} else {
			this.name = null;
		}
		String id = tag.getString("behaviour");
		this.currentBehaviour = this.behaviors.stream().filter(d -> d.getID().equals(id)).findFirst().orElse(this.defaultBehaviours.IDLE);
		this.behaviors.forEach(d -> d.loadFromSyncNBT(tag));
	}

	public void clearItemInputs() {
		this.ingredients.clear();
		this.behaviors.forEach(d -> d.statusChanged(d == this.currentBehaviour));
		this.markDirty();
		this.syncToClient();
	}

	public void setTankLock(boolean canTransfer) {
		this.tank.setCanDrain(canTransfer);
		this.tank.setCanFill(canTransfer);
		this.markDirty();
	}

	public void setBehaviour(ICauldronBehaviour behaviour) {
		if (behaviour == null) {
			Log.w("null behaviour decorator for cauldron!");
			Log.askForReport();
			this.setBehaviour(this.defaultBehaviours.IDLE);
		} else if (behaviour != this.currentBehaviour) {
			if (this.currentBehaviour != null) {
				this.currentBehaviour.onDeactivation();
			}
			this.currentBehaviour = behaviour;
			this.setColor(this.currentBehaviour.getColor());
			this.markDirty();
			this.syncToClient();
		}
	}

	public void onLiquidChange() {
		this.behaviors.forEach(d -> d.statusChanged(d == this.currentBehaviour));
		this.markDirty();
		this.syncToClient();
	}

	public void clearTanks() {
		this.tank.setFluid(null);
		this.setTankLock(true);
		this.syncToClient();
	}

	public DefaultBehaviours getDefaultBehaviours() {
		return this.defaultBehaviours;
	}

	public ICauldronBehaviour getCurrentBehaviour() {
		return this.currentBehaviour;
	}
}