package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class TileEntityGlyph extends TileEntityAltarStorage implements ITickable {
	private final ItemStackHandler inventory = new ItemStackHandler(Byte.MAX_VALUE);
	
	public Ritual ritual;
	public UUID caster;
	public BlockPos effectivePos;
	public int effectiveDim, time;
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("ritual", ritual == null ? "" : ritual.getRegistryName().toString());
		tag.setLong("effectivePos", effectivePos.toLong());
		tag.setInteger("effectiveDim", effectiveDim);
		tag.setString("caster", caster == null ? "" : caster.toString());
		tag.setInteger("time", time);
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		ritual = tag.getString("ritual").isEmpty() ? null : GameRegistry.findRegistry(Ritual.class).getValue(new ResourceLocation(tag.getString("ritual")));
		effectivePos = BlockPos.fromLong(tag.getLong("effectivePos"));
		effectiveDim = tag.getInteger("effectiveDim");
		caster = tag.getString("caster").isEmpty() ? null : UUID.fromString(tag.getString("caster"));
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
	public void update() {
		if (ritual != null) {
			if (caster != null) {
				if (world.isRemote) ritual.onClientUpdate(world, effectivePos, world.getPlayerEntityByUUID(caster));
				ritual.onUpdate(world, effectivePos, world.getPlayerEntityByUUID(caster));
				if (world.getTotalWorldTime() % 20 == 0) {
					if (MagicPower.attemptDrain(world.getTileEntity(altarPos), null, ritual.runningPower)) time++;
					else stopRitual(false);
					if (time >= ritual.time) stopRitual(true);
				}
			}
		}
	}
	
	public void startRitual(EntityPlayer player) {
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(3));
		List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(3));
		ritual = GameRegistry.findRegistry(Ritual.class).getValuesCollection().stream().filter(r -> r.matches(world, pos, items, livings)).findFirst().orElse(null);
		if (ritual != null) {
			if (ritual.isValid(world, pos, player)) {
				if (MagicPower.attemptDrain(world.getTileEntity(altarPos), null, ritual.startingPower)) {
					caster = player.getPersistentID();
					effectivePos = pos;
					effectiveDim = world.provider.getDimension();
					time = ritual.time;
					ritual.onStarted(world, pos, player);
					player.sendStatusMessage(new TextComponentTranslation(ritual.getRegistryName().toString().replace(":", ".")), true);
					for (EntityItem item : items) {
						world.playSound(null, item.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
						if (world.isRemote) world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, item.getPosition().getX(), item.getPosition().getY(), item.getPosition().getZ(), 0, 0, 0);
						inventory.insertItem(getFirstEmptySlot(inventory), item.getItem().splitStack(item.getItem().getCount()), false);
					}
					if (ritual.sacrificePredicate != null) for (EntityLivingBase living : livings) if (ritual.sacrificePredicate.test(living) && living.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE)) break;
				}
				else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
			}
			else player.sendStatusMessage(new TextComponentTranslation("ritual.invalid"), true);
		}
		else player.sendStatusMessage(new TextComponentTranslation("ritual.null"), true);
	}
	
	public void stopRitual(boolean finished) {
		if (ritual != null) {
			if (finished) ritual.onFinished(world, pos, world.getPlayerEntityByUUID(caster));
			else ritual.onHalted(world, pos, world.getPlayerEntityByUUID(caster));
		}
		ritual = null;
		caster = null;
		effectivePos = pos;
		effectiveDim = world.provider.getDimension();
		time = 0;
	}
}