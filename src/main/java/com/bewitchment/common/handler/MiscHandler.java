package com.bewitchment.common.handler;

import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.common.entity.misc.EntityYewBroom;
import com.bewitchment.common.entity.misc.ModEntityPotion;
import com.bewitchment.common.entity.misc.ModEntityTippedArrow;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"ConstantConditions", "unused"})
public class MiscHandler {
	@SubscribeEvent
	public void attemptBrew(PotionBrewEvent.Pre event) {
		for (int i = 0; i < event.getLength() - 1; i++) {
			ItemStack stack = event.getItem(i);
			if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("bewitchment_brew")) {
				Item item = event.getItem(3).getItem();
				event.setItem(i, TileEntityWitchesCauldron.createPotion(PotionUtils.getEffectsFromStack(stack), item == Items.GUNPOWDER ? 1 : item == Items.DRAGON_BREATH ? 2 : 0));
				event.getItem(3).shrink(1);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void joinWorld(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote) {
			if (event.getEntity() instanceof EntityPotion && !(event.getEntity() instanceof ModEntityPotion)) {
				ModEntityPotion entity = new ModEntityPotion(event.getWorld());
				entity.deserializeNBT(event.getEntity().serializeNBT());
				event.getWorld().spawnEntity(entity);
				event.setCanceled(true);
			}
			else if (event.getEntity() instanceof EntityTippedArrow && !(event.getEntity() instanceof ModEntityTippedArrow)) {
				Entity shooter = ((EntityTippedArrow) event.getEntity()).shootingEntity;
				ModEntityTippedArrow entity = shooter instanceof EntityLivingBase ? new ModEntityTippedArrow(event.getWorld(), (EntityLivingBase) shooter) : new ModEntityTippedArrow(event.getWorld());
				entity.deserializeNBT(event.getEntity().serializeNBT());
				event.getWorld().spawnEntity(entity);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void dismount(EntityMountEvent event) {
		if (!event.getWorldObj().isRemote && event.getEntityBeingMounted() instanceof EntityYewBroom && event.isDismounting()) ((EntityYewBroom) event.getEntityBeingMounted()).dismount();
	}
	
	@SubscribeEvent
	public void explode(ExplosionEvent.Detonate event) {
		for (int i = event.getAffectedBlocks().size() - 1; i >= 0; i--) if (isNextToJuniperDoor(event.getWorld(), event.getAffectedBlocks().get(i))) event.getAffectedBlocks().remove(i);
	}
	
	@SubscribeEvent
	public void breakBlock(BlockEvent.BreakEvent event) {
		if (isNextToJuniperDoor(event.getWorld(), event.getPos())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed event) {
		if (isNextToJuniperDoor(event.getEntityPlayer().world, event.getPos())) event.setNewSpeed(0);
	}
	
	private boolean isNextToJuniperDoor(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() != ModObjects.juniper_door.door && world.getBlockState(pos.up()).getBlock() == ModObjects.juniper_door.door;
	}
}