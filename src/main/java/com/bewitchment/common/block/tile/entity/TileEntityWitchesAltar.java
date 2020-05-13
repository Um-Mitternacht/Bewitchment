package com.bewitchment.common.block.tile.entity;

import com.bewitchment.ModConfig;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.tool.ItemBastardsGrimoire;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.api.treedata.ITreePart;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems", "StatementWithEmptyBody"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public final MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	private final Map<IBlockState, Integer> map = new HashMap<>();
	private final BlockPos.MutableBlockPos checking = new BlockPos.MutableBlockPos();
	public int color, gain;
	private int counter, maxPower;
	
	private static AltarUpgrade getAltarUpgrade(World world, BlockPos pos) {
		for (Predicate<BlockWorldState> predicate : BewitchmentAPI.ALTAR_UPGRADES.keySet()) if (predicate.test(new BlockWorldState(world, pos, true))) return BewitchmentAPI.ALTAR_UPGRADES.get(predicate);
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		return super.writeToNBT(tag);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		boolean flag = super.shouldRefresh(world, pos, oldState, newState) || !newState.getValue(BlockWitchesAltar.TYPE).equals(oldState.getValue(BlockWitchesAltar.TYPE));
		if (!world.isRemote && flag) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 0.75, pos.getZ(), new ItemStack(Blocks.CARPET, 1, color - 1));
		return flag;
	}
	
	@Override
	public ItemStackHandler[] getInventories() {
		ItemStackHandler fin = new ItemStackHandler();
		fin.insertItem(0, new ItemStack(Blocks.CARPET, 1, color - 1), false);
		return new ItemStackHandler[]{fin};
	}
	
	@Override
	public void onLoad() {
		forceScan();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY ? MagicPower.CAPABILITY.cast(magicPower) : super.getCapability(capability, face);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (world.getTotalWorldTime() % 100 == 0) {
				for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(25))) {
					for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
						ItemStack stack = player.inventory.getStackInSlot(i);
						if ((stack.getItem() instanceof ItemGrimoireMagia || stack.getItem() instanceof ItemBastardsGrimoire) && stack.hasTagCompound()) {
							MagicPower temp = new MagicPower();
							temp.amount = stack.getTagCompound().getInteger("amount");
							temp.maxAmount = stack.getTagCompound().getInteger("maxAmount");
							if (MagicPower.transfer(magicPower, temp, 50, 0.5f)) {
								stack.getTagCompound().setInteger("amount", temp.amount);
								player.inventory.setInventorySlotContents(i, stack);
								player.inventory.markDirty();
								break;
							}
						}
					}
				}
			}
			if (magicPower.amount > magicPower.maxAmount) magicPower.amount = magicPower.maxAmount;
			if (world.getTotalWorldTime() % 20 == 0) magicPower.fill(gain * 8);
			scan(ModConfig.misc.altarScansPerTick);
		}
	}
	
	public void forceScan() {
		syncToClient();
		counter = 0;
		maxPower = 0;
		map.clear();
		scan(Short.MAX_VALUE);
	}
	
	protected void scan(int times) {
		for (int i = 0; i < times; i++) {
			counter = ++counter % Short.MAX_VALUE;
			int x = counter & 31;
			int y = (counter >> 5) & 31;
			int z = (counter >> 10) & 31;
			checking.setPos(pos.getX() + x - 16, pos.getY() + y - 16, pos.getZ() + z - 16);
			registerToMap(world.getBlockState(checking));
			if (counter == Short.MAX_VALUE - 1) {
				boolean foundStone = false, foundCup = false, foundPentacle = false, foundSword = false, foundWand = false;
				gain = 1;
				double multiplier = 1;
				for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
					if (world.getBlockState(pos0).getBlock() instanceof BlockWitchesAltar) {
						if (world.getBlockState(pos0.up()).getBlock() == ModObjects.blessed_stone) foundStone = true;
						if (world.getTileEntity(pos0.up()) instanceof TileEntityPlacedItem) {
							ItemStack stack = ((TileEntityPlacedItem) world.getTileEntity(pos0.up())).getInventories()[0].getStackInSlot(0);
							if (stack.getItem() instanceof ItemGrimoireMagia && stack.hasTagCompound()) {
								MagicPower temp = new MagicPower();
								temp.amount = stack.getTagCompound().getInteger("amount");
								temp.maxAmount = stack.getTagCompound().getInteger("maxAmount");
								if (MagicPower.transfer(magicPower, temp, 100, 0.25f)) stack.getTagCompound().setInteger("amount", temp.amount);
							}
						}
						if (!foundStone) {
							AltarUpgrade upgrade = getAltarUpgrade(world, pos0.up());
							if (upgrade != null) {
								AltarUpgrade.Type type = upgrade.type;
								if (type == AltarUpgrade.Type.CUP && !foundCup) {
									gain += upgrade.upgrade1;
									multiplier *= upgrade.upgrade2;
									foundCup = true;
								}
								if (type == AltarUpgrade.Type.PENTACLE && !foundPentacle) {
									gain += upgrade.upgrade1;
									foundPentacle = true;
								}
								if (type == AltarUpgrade.Type.SWORD && !foundSword) {
									multiplier *= upgrade.upgrade2;
									foundSword = true;
								}
								if (type == AltarUpgrade.Type.WAND && !foundWand) {
									maxPower += 64 * upgrade.upgrade2;
									foundWand = true;
								}
							}
						}
					}
				}
				if (foundStone) {
					gain = Short.MAX_VALUE;
					maxPower = Short.MAX_VALUE;
					multiplier = 1;
				}
				if (gain < 0) gain = 0;
				magicPower.maxAmount = (int) (maxPower * multiplier);
				maxPower = 0;
				counter = 0;
				map.clear();
			}
		}
	}
	
	protected IBlockState convert(IBlockState state) {
		if (Loader.isModLoaded("dynamictrees")) {
			if (state.getBlock() instanceof ITreePart) return state;
		}
		if (state.getBlock() instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
		else if (state.getBlock() instanceof BlockRotatedPillar) state = state.getBlock().getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
		else if (state.getBlock() instanceof BlockLeaves) state = state.withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, false);
		else if (!(state.getBlock() instanceof BlockFlower)) state = state.getBlock().getDefaultState();
		return state;
	}
	
	protected boolean isNatural(IBlockState state) {
		if (Loader.isModLoaded("dynamictrees")) {
		}
		return (!(state.getBlock() instanceof BlockGrass)) && (state.getBlock() instanceof IGrowable || state.getBlock() instanceof IPlantable || state.getBlock() instanceof BlockMelon || state.getBlock() instanceof BlockPumpkin || state.getBlock() instanceof BlockLeaves || (state.getBlock() instanceof BlockRotatedPillar && state.getMaterial() == Material.WOOD));
	}
	
	protected void registerToMap(IBlockState state) {
		if (isNatural(state)) {
			IBlockState state0 = convert(state);
			int current = map.getOrDefault(state0, 0);
			if (map.keySet().isEmpty() || current < 4 * map.keySet().size()) {
				map.put(state0, ++current);
				maxPower++;
			}
		}
	}
}