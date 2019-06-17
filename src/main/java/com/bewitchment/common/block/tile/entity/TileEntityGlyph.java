package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class TileEntityGlyph extends TileEntityAltarStorage implements ITickable {
	private final ItemStackHandler inventory = new ItemStackHandler(Byte.MAX_VALUE);
	
	public Ritual ritual;
	public UUID caster;
	public BlockPos effectivePos;
	public int effectiveDim, time;
	
	private List<EntityItem> items = new ArrayList<>();
	private NBTTagList tempList = new NBTTagList();
	
	public TileEntityGlyph() {
		super();
		for (int i = 0; i < tempList.tagCount(); i++) {
			EntityItem item = new EntityItem(world);
			item.deserializeNBT(tempList.getCompoundTagAt(i));
			items.add(item);
		}
	}
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("ritual", ritual == null ? "" : ritual.getRegistryName().toString());
		tag.setLong("effectivePos", effectivePos == null ? 0 : effectivePos.toLong());
		tag.setInteger("effectiveDim", effectiveDim);
		tag.setString("caster", caster == null ? "" : caster.toString());
		tag.setInteger("time", time);
		NBTTagList itemList = new NBTTagList();
		for (EntityItem item : items) itemList.appendTag(item.serializeNBT());
		tag.setTag("itemList", itemList);
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		ritual = tag.getString("ritual").isEmpty() ? null : BewitchmentAPI.REGISTRY_RITUAL.getValue(new ResourceLocation(tag.getString("ritual")));
		effectivePos = BlockPos.fromLong(tag.getLong("effectivePos"));
		effectiveDim = tag.getInteger("effectiveDim");
		caster = tag.getString("caster").isEmpty() ? null : UUID.fromString(tag.getString("caster"));
		time = tag.getInteger("time");
		tempList = tag.getTagList("itemList", Constants.NBT.TAG_COMPOUND);
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
		if (!world.isRemote) {
			if (!items.isEmpty()) {
				if (time <= 20 && time % 20 == 0) {
					EntityItem item = items.get(0);
					world.playSound(null, item.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
					Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.SMOKE_NORMAL, item.getPosition()), effectiveDim);
					inventory.insertItem(getFirstEmptySlot(inventory), item.getItem().splitStack(1), false);
					items.remove(item);
					if (items.isEmpty()) {
						EntityPlayer player = Util.findPlayer(caster);
						if (player != null) {
							List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(3));
							ritual = BewitchmentAPI.REGISTRY_RITUAL.getValuesCollection().stream().filter(r -> r.matches(world, pos, inventory, livings)).findFirst().orElse(null);
							if (ritual != null) {
								if (ritual.isValid(world, pos, player)) {
									if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, ritual.startingPower)) {
										player.getCapability(ExtendedPlayer.CAPABILITY, null).ritualsCast++;
										ExtendedPlayer.syncToClient(player);
										effectivePos = pos;
										effectiveDim = world.provider.getDimension();
										ritual.onStarted(world, pos, player);
										player.sendStatusMessage(new TextComponentTranslation("ritual." + ritual.getRegistryName().toString().replace(":", ".")), true);
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
				time++;
			}
			if (ritual != null && caster != null) {
				if (time % 20 == 0 && !MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, Util.findPlayer(caster), ritual.runningPower)) stopRitual(false);
				ritual.onUpdate(world, effectivePos, Util.findPlayer(caster));
				if (time >= ritual.time) stopRitual(true);
				time++;
			}
		}
	}
	
	public void consumeItems(EntityPlayer player) {
		if (!world.isRemote && items.isEmpty()) {
			caster = player.getGameProfile().getId();
			items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(3));
			for (EntityItem item : items) item.setInfinitePickupDelay();
			time = -(20 * (items.size() + 1));
		}
	}
	
	public void stopRitual(boolean finished) {
		if (ritual != null) {
			if (finished) ritual.onFinished(world, pos);
			else ritual.onHalted(world, pos);
		}
		ritual = null;
		caster = null;
		effectivePos = pos;
		effectiveDim = world.provider.getDimension();
		time = 0;
		for (EntityItem item : items) item.setDefaultPickupDelay();
		items.clear();
	}
}