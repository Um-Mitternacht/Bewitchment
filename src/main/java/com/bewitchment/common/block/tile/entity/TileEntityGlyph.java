package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class TileEntityGlyph extends TileEntityAltarStorage implements ITickable {
	private final ItemStackHandler inventory = new ItemStackHandler(Byte.MAX_VALUE);
	
	public Ritual ritual;
	public UUID casterId;
	public EntityPlayer caster;
	public BlockPos effectivePos;
	public int effectiveDim, time;
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("ritual", ritual == null ? "" : ritual.getRegistryName().toString());
		tag.setLong("effectivePos", effectivePos == null ? 0 : effectivePos.toLong());
		tag.setInteger("effectiveDim", effectiveDim);
		tag.setString("casterId", casterId == null ? "" : casterId.toString());
		tag.setInteger("time", time);
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		ritual = tag.getString("ritual").isEmpty() ? null : BewitchmentAPI.REGISTRY_RITUAL.getValue(new ResourceLocation(tag.getString("ritual")));
		effectivePos = BlockPos.fromLong(tag.getLong("effectivePos"));
		effectiveDim = tag.getInteger("effectiveDim");
		casterId = tag.getString("casterId").isEmpty() ? null : UUID.fromString(tag.getString("casterId"));
		time = tag.getInteger("time");
		super.readFromNBT(tag);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		boolean flag = super.shouldRefresh(world, pos, oldState, newState);
		if (flag) stopRitual(false);
		return flag;
	}
	
	@Override
	public void onLoad() {
		world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 5, 0);
	}
	
	@Override
	public void update() {
		if (ritual != null && caster != null) {
			if (world.getTotalWorldTime() % 20 == 0) {
				if (!MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, caster, ritual.runningPower)) stopRitual(false);
				else time++;
			}
			if (world.isRemote) ritual.onClientUpdate(world, effectivePos, caster);
			ritual.onUpdate(world, effectivePos, caster);
			if (time >= ritual.time) stopRitual(true);
		}
	}
	
	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				int last = getLastNonEmptySlot(inventory);
				if (last > -1) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.extractItem(last, inventory.getStackInSlot(last).getCount(), false));
			}
			else {
				if (ritual != null) stopRitual(false);
				else {
					ItemStack stack = player.getHeldItem(hand);
					if (stack.getItem() == ModObjects.waystone && stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
						if (ritual != null && ritual.canBePerformedRemotely) {
							effectivePos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
							effectiveDim = stack.getTagCompound().getInteger("dimension");
							stack.damageItem(1, player);
						}
					}
					else if (!stack.isEmpty()) inventory.insertItem(getFirstEmptySlot(inventory), stack.splitStack(1), false);
					else {
						List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(3));
						ritual = BewitchmentAPI.REGISTRY_RITUAL.getValuesCollection().stream().filter(r -> r.matches(world, pos, inventory, livings)).findFirst().orElse(null);
						if (ritual != null) {
							if (ritual.isValid(world, pos, player)) {
								if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, ritual.startingPower)) {
									player.getCapability(ExtendedPlayer.CAPABILITY, null).ritualsCast++;
									ExtendedPlayer.syncToClient(player);
									casterId = player.getGameProfile().getId();
									caster = Util.findPlayer(casterId);
									effectivePos = pos;
									effectiveDim = world.provider.getDimension();
									time = 0;
									ritual.onStarted(world, pos, player);
									world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
									player.sendStatusMessage(new TextComponentTranslation("ritual." + ritual.getRegistryName().toString().replace(":", ".")), true);
									world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
									if (ritual.sacrificePredicate != null) for (EntityLivingBase living : livings) if (ritual.sacrificePredicate.test(living) && living.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE)) break;
								}
								else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
							}
							else player.sendStatusMessage(new TextComponentTranslation("ritual.invalid"), true);
						}
						else player.sendStatusMessage(new TextComponentTranslation("ritual.null"), true);
					}
				}
			}
		}
		return true;
	}
	
	public void stopRitual(boolean finished) {
		if (ritual != null && caster != null) {
			if (finished) ritual.onFinished(world, pos, caster);
			else ritual.onHalted(world, pos, caster);
		}
		ritual = null;
		casterId = null;
		caster = null;
		effectivePos = pos;
		effectiveDim = world.provider.getDimension();
		time = 0;
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
	}
}