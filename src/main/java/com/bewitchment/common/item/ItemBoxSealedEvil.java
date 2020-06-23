package com.bewitchment.common.item;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.common.item.util.ModItemBauble;
import com.bewitchment.registry.ModEntities;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemBoxSealedEvil extends Item {
	public ItemBoxSealedEvil() {
		super();
		Util.registerItem(this, "box_of_sealed_evil");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		Random rand = itemRand;
		if (!player.isSneaking() && !worldIn.isRemote) {
			switch (rand.nextInt(24)) {
				case 0:
					LootTable table = worldIn.getLootTableManager().getLootTableFromLocation(LootTableList.CHESTS_DESERT_PYRAMID);
					LootContext ctx = new LootContext.Builder((WorldServer) worldIn).withLuck(player.getLuck()).build();
					List<ItemStack> stacks = table.generateLootForPools(rand, ctx);
					for (ItemStack stack : stacks) Util.giveItem(player, stack);
					break;
				case 1:
					ItemStack bauble = getRandomBauble(rand);
					Util.giveItem(player, bauble);
					break;
				case 2:
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 24000, 1));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 24000, 0));
					break;
				case 3:
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 24000, 1));
					player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 24000, 0));
					break;
				case 4:
					List<Block> ores = ForgeRegistries.BLOCKS.getValuesCollection().stream().filter(b -> b instanceof BlockOre).collect(Collectors.toList());
					for (Block ore : ores)
						InventoryHelper.spawnItemStack(worldIn, player.posX, player.posY + 0.5, player.posZ, new ItemStack(ore));
					break;
				case 5:
					Util.giveItem(player, new ItemStack(Items.NETHER_STAR));
					break;
				case 6:
					int amount = rand.nextInt(10) + 10;
					for (int i = 0; i < amount; i++) {
						Entity bat = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft", "bat")).newInstance(worldIn);
						bat.setPosition(player.posX + rand.nextGaussian(), player.posY + 1, player.posZ + rand.nextGaussian());
						worldIn.spawnEntity(bat);
					}
					for (int i = 0; i < amount - 10; i++) {
						Entity raven = ModEntities.raven.newInstance(worldIn);
						raven.setPosition(player.posX + rand.nextGaussian(), player.posY + 1, player.posZ + rand.nextGaussian());
						worldIn.spawnEntity(raven);
					}
					break;
				case 7:
					Util.giveItem(player, new ItemStack(ModObjects.bottle_of_blood));
					Util.giveItem(player, new ItemStack(ModObjects.heart));
					Util.giveItem(player, Util.getRandomContract(rand));
					Util.giveItem(player, new ItemStack(Items.SKULL, 1, rand.nextInt(6)));
					break;
				case 8:
					final String[] demons = {"leonard", "baphomet", "lilith", "herne"};
					String demon = demons[rand.nextInt(demons.length)];
					String mat = rand.nextBoolean() ? "nether_brick" : "scorned_brick";
					Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Bewitchment.MODID, mat + "_" + demon + "_statue"));
					if (item != null) Util.giveItem(player, new ItemStack(item));
					else Util.giveItem(player, new ItemStack(ModObjects.nether_brick_leonard_statue));
					break;
				case 9:
					Util.giveItem(player, new ItemStack(ModObjects.stew_of_the_grotesque));
					Util.giveItem(player, new ItemStack(Items.CHICKEN, rand.nextInt(64) + 1));
					Util.giveItem(player, new ItemStack(Items.MUTTON, rand.nextInt(64) + 1));
					break;
				case 10:
					Util.giveItem(player, new ItemStack(ModObjects.embergrass));
					Util.giveItem(player, new ItemStack(ModObjects.blue_ink_cap));
					Util.giveItem(player, new ItemStack(ModObjects.spanish_moss));
					String[] type = {"allium", "azure_bluet", "blue_orchid", "dandelion", "oxeye_daisy", "poppy", "tulip_orange", "tulip_pink", "tulip_red", "tulip_white"};
					Block flower = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_" + type[rand.nextInt(10)]));
					if (flower != null) Util.giveItem(player, new ItemStack(flower));
					else Util.giveItem(player, new ItemStack(ModObjects.flower_siphoning_poppy));
					Util.giveItem(player, new ItemStack(ModObjects.dragons_blood_sapling));
					break;
				case 11:
					Util.giveItem(player, new ItemStack(Items.GHAST_TEAR, rand.nextInt(2) + 1));
					Util.giveItem(player, new ItemStack(Items.BLAZE_ROD, rand.nextInt(2) + 1));
					Util.giveItem(player, new ItemStack(ModObjects.hellhound_horn, rand.nextInt(2) + 1));
					Util.giveItem(player, new ItemStack(ModObjects.demon_heart, rand.nextInt(2) + 1));
					break;
				case 12:
					int random0 = rand.nextInt(3) + 1;
					for (int i = 0; i < random0; i++) {
						ModEntityMob temp = (ModEntityMob) ModEntities.feuerwurm.newInstance(worldIn);
						temp.getDataManager().set(ModEntityMob.SPECTRAL, true);
						temp.lifeTimeTicks = 1200;
						temp.setAttackTarget(player);
						temp.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(temp);
					}
					break;
				case 13:
					int random1 = rand.nextInt(3) + 1;
					for (int i = 0; i < random1; i++) {
						ModEntityMob temp = (ModEntityMob) ModEntities.hellhound.newInstance(worldIn);
						temp.setAttackTarget(player);
						temp.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(temp);
					}
					break;
				case 14:
					WorldInfo info = worldIn.getWorldInfo();
					int weatherTime = (300 + (new Random()).nextInt(600)) * 20;
					info.setCleanWeatherTime(0);
					info.setRainTime(weatherTime);
					info.setThunderTime(weatherTime);
					info.setRaining(true);
					info.setThundering(true);
					break;
				case 15:
					worldIn.setWorldTime(worldIn.getWorldTime() + (41600 - (worldIn.getWorldTime() % 24000)) % 24000);
					break;
				case 16:
					int random2 = rand.nextInt(3) + 1;
					for (int i = 0; i < random2; i++) {
						ModEntityMob temp = (ModEntityMob) ModEntities.shadow_person.newInstance(worldIn);
						temp.setAttackTarget(player);
						temp.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(temp);
					}
					break;
				case 17:
					int random3 = rand.nextInt(3) + 1;
					for (int i = 0; i < random3; i++) {
						ModEntityMob temp = (ModEntityMob) ModEntities.ghost.newInstance(worldIn);
						temp.setAttackTarget(player);
						temp.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(temp);
					}
					break;
				case 18:
					int random4 = rand.nextInt(2) + 1;
					for (int i = 0; i < random4; i++) {
						ModEntityMob temp = (ModEntityMob) ModEntities.druden.newInstance(worldIn);
						temp.setAttackTarget(player);
						temp.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(temp);
					}
					break;
				case 19:
					player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1200));
					player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 24000));
					break;
				case 20:
					player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 800));
					break;
				case 21:
					player.addPotionEffect(new PotionEffect(ModPotions.hellfire, 24000));
					//add insanity here
					break;
				case 22:
					worldIn.addWeatherEffect(new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true));
					break;
				default:
					for (BlockPos pos1 : BlockPos.getAllInBoxMutable(player.getPosition().add(-1, 0, -1), player.getPosition().add(1, 2, 1))) {
						if (worldIn.getBlockState(pos1).getBlock().isReplaceable(worldIn, pos1)) {
							worldIn.setBlockState(pos1, Blocks.WEB.getDefaultState());
						}
					}
					int random5 = rand.nextInt(4) + 1;
					for (int i = 0; i < random5; i++) {
						Entity spider = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft", "cave_spider")).newInstance(worldIn);
						spider.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(spider);
					}
					for (int i = 0; i < random5; i++) {
						Entity silver_fish = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft", "silverfish")).newInstance(worldIn);
						silver_fish.setPosition(player.posX + rand.nextGaussian() * 2, player.posY + 0.5, player.posZ + rand.nextGaussian() * 2);
						worldIn.spawnEntity(silver_fish);
					}
					break;
			}
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
		}
		if (worldIn.isRemote) worldIn.playSound(player, player.getPosition(), SoundEvents.ENTITY_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 5, 1);
		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	private ItemStack getRandomBauble(Random random) {
		List<Item> baubles = ForgeRegistries.ITEMS.getValuesCollection().stream().filter(i -> i instanceof ModItemBauble).collect(Collectors.toList());
		if (!baubles.isEmpty()) return new ItemStack(baubles.get(random.nextInt(baubles.size())));
		return new ItemStack(ModObjects.girdle_of_the_dryads);
	}
}
