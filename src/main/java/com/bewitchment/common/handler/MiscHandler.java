package com.bewitchment.common.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.event.WitchesCauldronEvent;
import com.bewitchment.common.block.BlockBrazier;
import com.bewitchment.common.block.tile.entity.TileEntityBrazier;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class MiscHandler {
	@SubscribeEvent
	public void applyBrewingBuffs(WitchesCauldronEvent.CreatePotionEvent evt){
		if (BewitchmentAPI.hasAlchemist(evt.getUser())){
			evt.setBoosted(true);
			evt.setBottles(evt.getBottles() + 1);
		}
	}

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
	
	@SubscribeEvent
	public void wakeUp(PlayerWakeUpEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		List<Potion> potions = new ArrayList<>();
		int length = 600, strength = 0;
		for (BlockPos temp : BlockPos.getAllInBoxMutable(player.getPosition().add(-2, -2, -2), player.getPosition().add(2, 2, 2))) {
			if (world.getBlockState(temp).getBlock() instanceof BlockBrazier && world.getBlockState(temp).getValue(BlockBrazier.LIT)) {
				TileEntityBrazier te = (TileEntityBrazier) world.getTileEntity(temp);
				if (te.incense != null) {
					if (length == 600 && te.incense.getRegistryName().equals(new ResourceLocation(Bewitchment.MODID, "intensity"))) {
						strength++;
					}
					else if (strength == 0 && te.incense.getRegistryName().equals(new ResourceLocation(Bewitchment.MODID, "concentration"))) {
						length = 1200;
					}
					else {
						potions.addAll(te.incense.effects);
					}
				}
			}
		}
		strength = Math.min(strength, 2);
		for (Potion potion : potions) {
			player.addPotionEffect(new PotionEffect(potion, 20 * length, strength));
		}
	}
	
	public void takeBlood(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (event.getEntityLiving() instanceof EntityAnimal || event.getEntityLiving() instanceof EntityPlayer || event.getEntityLiving() instanceof EntityVillager) {
				if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModObjects.athame && player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.GLASS_BOTTLE) {
					Util.replaceAndConsumeItem(player, EnumHand.OFF_HAND, new ItemStack(ModObjects.bottle_of_blood));
				}
			}
		}
	}
}