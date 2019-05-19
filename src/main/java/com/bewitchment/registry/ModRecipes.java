package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.entity.spirit.demon.EntityDemoness;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import com.bewitchment.common.entity.spirit.demon.EntitySerpent;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.fortune.*;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	public static void preInit() {
		furnacePreInit();
		ovenPreInit();
		fortunePreInit();

		ModObjects.TOOL_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.TOOL_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		ModObjects.ARMOR_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.ARMOR_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
	}

	public static void postInit() {
		athamePostInit();
		furnacePostInit();
		ovenPostInit();
	}

	private static void athamePostInit() {
		BewitchmentAPI.registerAthameLoot(EntityPlayer.class, Sets.newHashSet(new ItemStack(ModObjects.heart), new ItemStack(Items.SKULL, 1, 3)));
		BewitchmentAPI.registerAthameLoot(EntityVillager.class, Sets.newHashSet(new ItemStack(ModObjects.heart)));
		BewitchmentAPI.registerAthameLoot(EntityZombieVillager.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));

		BewitchmentAPI.registerAthameLoot(EntityWither.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 6)));
		BewitchmentAPI.registerAthameLoot(EntityElderGuardian.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old, 4)));

		BewitchmentAPI.registerAthameLoot(EntityZombie.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 2), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityPigZombie.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 3), new ItemStack(ModObjects.hoof, 2)));

		BewitchmentAPI.registerAthameLoot(EntityHusk.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntitySkeleton.class, Sets.newHashSet(new ItemStack(Items.SKULL), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityWitherSkeleton.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 1), new ItemStack(ModObjects.spectral_dust, 2)));
		BewitchmentAPI.registerAthameLoot(EntityStray.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));

		BewitchmentAPI.registerAthameLoot(EntityCreeper.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 4)));

		BewitchmentAPI.registerAthameLoot(EntityBlaze.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm)));
		BewitchmentAPI.registerAthameLoot(EntityGhast.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2)));

		BewitchmentAPI.registerAthameLoot(EntitySilverfish.class, Sets.newHashSet(new ItemStack(ModObjects.silver_nugget, 2)));
		BewitchmentAPI.registerAthameLoot(EntityEndermite.class, Sets.newHashSet(new ItemStack(ModObjects.dimensional_sand, 2)));

		BewitchmentAPI.registerAthameLoot(EntityEnderman.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm)));
		BewitchmentAPI.registerAthameLoot(EntityVex.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4)));

		BewitchmentAPI.registerAthameLoot(EntityGuardian.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old)));

		BewitchmentAPI.registerAthameLoot(EntityPig.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityCow.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySheep.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityWolf.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog)));
		BewitchmentAPI.registerAthameLoot(EntityRabbit.class, Sets.newHashSet(new ItemStack(Items.RABBIT_FOOT)));
		BewitchmentAPI.registerAthameLoot(EntityHorse.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityDonkey.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityMule.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySkeletonHorse.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 2)));
		BewitchmentAPI.registerAthameLoot(EntityLlama.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityZombieHorse.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityBat.class, Sets.newHashSet(new ItemStack(ModObjects.wool_of_bat, 3)));

		BewitchmentAPI.registerAthameLoot(EntityLizard.class, Sets.newHashSet(new ItemStack(ModObjects.lizard_leg, 4)));
		BewitchmentAPI.registerAthameLoot(EntityNewt.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_newt, 2)));
		BewitchmentAPI.registerAthameLoot(EntityOwl.class, Sets.newHashSet(new ItemStack(ModObjects.owlets_wing, 2)));
		BewitchmentAPI.registerAthameLoot(EntityRaven.class, Sets.newHashSet(new ItemStack(ModObjects.ravens_feather, 4)));
		BewitchmentAPI.registerAthameLoot(EntityToad.class, Sets.newHashSet(new ItemStack(ModObjects.toe_of_frog, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySnake.class, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));

		BewitchmentAPI.registerAthameLoot(EntityBlackDog.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));

		BewitchmentAPI.registerAthameLoot(EntityHellhound.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.hellhound_horn, 2), new ItemStack(Items.BLAZE_POWDER, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySerpent.class, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));

		BewitchmentAPI.registerAthameLoot(EntityDemon.class, Sets.newHashSet(new ItemStack(ModObjects.demon_heart)));
		BewitchmentAPI.registerAthameLoot(EntityDemoness.class, Sets.newHashSet(new ItemStack(ModObjects.demon_heart)));
	}

	private static void furnacePreInit() {
		GameRegistry.addSmelting(ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst), 1);
		GameRegistry.addSmelting(ModObjects.garnet_ore, new ItemStack(ModObjects.garnet), 1);
		GameRegistry.addSmelting(ModObjects.moonstone_ore, new ItemStack(ModObjects.moonstone), 1);
		GameRegistry.addSmelting(ModObjects.silver_ore, new ItemStack(ModObjects.silver_ingot), 1);
		GameRegistry.addSmelting(ModObjects.salt_ore, new ItemStack(ModObjects.salt), 1);

		GameRegistry.addSmelting(ModObjects.cypress_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.elder_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.juniper_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.yew_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);

		GameRegistry.addSmelting(ModObjects.unfired_jar, new ItemStack(ModObjects.empty_jar), 0.15f);
	}

	private static void furnacePostInit() {
		for (Block block : ForgeRegistries.BLOCKS) {
			if (block instanceof BlockSapling && FurnaceRecipes.instance().getSmeltingResult(new ItemStack(block)).isEmpty())
				GameRegistry.addSmelting(block, new ItemStack(ModObjects.wood_ash, 4), 0.15f);
		}
	}

	private static void ovenPreInit() {
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit"), new ItemStack(Blocks.SAPLING, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit_alt"), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "spruce_heart"), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.spruce_heart), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "birch_soul"), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.birch_soul), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil"), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.cloudy_oil), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "acacia_resin"), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.acacia_resin), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ebb_of_death"), new ItemStack(ModObjects.cypress_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.ebb_of_death), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "droplet_of_wisdom"), new ItemStack(ModObjects.elder_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.droplet_of_wisdom), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "liquid_witchcraft"), new ItemStack(ModObjects.juniper_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.liquid_witchcraft), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "essence_of_vitality"), new ItemStack(ModObjects.yew_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.essence_of_vitality), 0.85f));
	}

	private static void ovenPostInit() {
		for (ItemStack stack : FurnaceRecipes.instance().getSmeltingList().keySet()) {
			if (GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection().stream().noneMatch(r -> Util.areStacksEqual(r.input, stack))) {
				ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, stack.getItem().getRegistryName().getPath() + stack.getMetadata());
				int index = 0;
				while (GameRegistry.findRegistry(OvenRecipe.class).containsKey(loc))
					loc = new ResourceLocation(loc.getNamespace(), loc.getPath() + index++);
				BewitchmentAPI.registerOvenRecipe(new OvenRecipe(loc, stack, FurnaceRecipes.instance().getSmeltingResult(stack), stack.getItem() instanceof ItemFood ? new ItemStack(ModObjects.cloudy_oil) : ItemStack.EMPTY, 0.85f));
			}
		}
	}

	private static void fortunePreInit() {
		BewitchmentAPI.registerFortune(new FortuneBadLuck());
		BewitchmentAPI.registerFortune(new FortuneGoodLuck());
		BewitchmentAPI.registerFortune(new FortuneIllness());
		BewitchmentAPI.registerFortune(new FortuneVitality());
		BewitchmentAPI.registerFortune(new FortuneMeetPet());
		BewitchmentAPI.registerFortune(new FortuneMeetMerchant());
		BewitchmentAPI.registerFortune(new FortuneMeetDemon());
		BewitchmentAPI.registerFortune(new FortuneMeetSerpent());
		BewitchmentAPI.registerFortune(new FortuneMeetBlaze());
		BewitchmentAPI.registerFortune(new FortuneMeetDireWolf());
		BewitchmentAPI.registerFortune(new FortuneMeetSilverfish());
		BewitchmentAPI.registerFortune(new FortuneMeetWitch());
		BewitchmentAPI.registerFortune(new FortuneMeetZombie());
		BewitchmentAPI.registerFortune(new FortuneDeath());
		BewitchmentAPI.registerFortune(new FortuneDropItem());
		BewitchmentAPI.registerFortune(new FortuneTreasure());
	}
}