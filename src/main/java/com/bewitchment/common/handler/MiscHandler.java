package com.bewitchment.common.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.common.entity.misc.ModEntityPotion;
import com.bewitchment.common.entity.misc.ModEntityTippedArrow;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class MiscHandler {
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt) {
		if (evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)) {
			evt.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryTable(new ResourceLocation(Bewitchment.MODID, "chests/nether_materials"), 5, 0, new LootCondition[0], "bewitchment_nether_materials_entry")}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_nether_materials_pool"));
		}
		if (evt.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) || evt.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) || evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE) || evt.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH) || evt.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE) || evt.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID) || evt.getName().equals(LootTableList.CHESTS_IGLOO_CHEST) || evt.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY) || evt.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)) { //Not sure if this is messy or better looking than the alternative.
			evt.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryTable(new ResourceLocation(Bewitchment.MODID, "chests/materials"), 5, 0, new LootCondition[0], "bewitchment_materials_entry")}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_materials_pool"));
		}
	}
	
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
	
//	@SubscribeEvent
//	public void dismount(EntityMountEvent event) {
//		if (!event.getWorldObj().isRemote && event.getEntityBeingMounted() instanceof EntityYewBroom && event.isDismounting()) ((EntityYewBroom) event.getEntityBeingMounted()).dismount();
//	}
	
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

	@SubscribeEvent
	public void onCollectFire(PlayerInteractEvent.RightClickBlock event) {
		Block block = event.getWorld().getBlockState(event.getPos().offset(Objects.requireNonNull(event.getFace()))).getBlock();
		EntityPlayer player = event.getEntityPlayer();
		if (block == ModObjects.hellfire) {
			if (!player.isSneaking() && player.getHeldItem(event.getHand()).getItem() == Items.GLASS_BOTTLE) {
				if (!event.getWorld().isRemote) {
					Util.replaceAndConsumeItem(player, event.getHand(), new ItemStack(ModObjects.bottled_hellfire));
					event.getWorld().setBlockToAir(event.getPos().offset(event.getFace()));
					event.getWorld().playSound(null, event.getPos().offset(event.getFace()), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1.75f);
				}
			}
		}
	}

	@SubscribeEvent
	public void extinguishFire(PlayerInteractEvent.LeftClickBlock event) {
		BlockPos pos = event.getPos().offset(event.getFace());
		if (event.getWorld().getBlockState(pos).getBlock() == ModObjects.hellfire) {
			event.getWorld().playEvent(event.getEntityPlayer(), 1009, pos, 0);
			event.getWorld().setBlockToAir(pos);
		}
	}
	
	private boolean isNextToJuniperDoor(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() != ModObjects.juniper_door.door && world.getBlockState(pos.up()).getBlock() == ModObjects.juniper_door.door;
	}
}