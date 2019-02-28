package com.bewitchment.common.block.natural.fluid;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * This class was created by Arekkuusu on 03/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class Fluids {

	public static final List<Block> MOD_FLUID_BLOCKS = new ArrayList<>();
	public static final List<Fluid> MOD_FLUIDS = new ArrayList<>();

	public static final Fluid HONEY = registerFluid("honey", Material.WATER, 0, 10, 1500, 8000, true, false);
	public static final Fluid MUNDANE_OIL = registerFluid("oil_mundane", Material.WATER, 0, 0, 800, 4000, true, false);

	private static Fluid registerFluid(String name, Material mat, int temperature, int luminosity, int density, int viscosity, boolean useBucket, boolean useFlowTexture) {
		if (!FluidRegistry.isFluidRegistered(name)) {
			Fluid fluid = new Fluid(name, new ResourceLocation(LibMod.MOD_ID, "blocks/fluid/" + name + "_still"), new ResourceLocation(LibMod.MOD_ID, "blocks/fluid/" + name + (useFlowTexture ? "_flowing" : "_still"))).setTemperature(temperature).setLuminosity(luminosity).setDensity(density).setViscosity(viscosity);
			FluidRegistry.registerFluid(fluid);
			Block block = new BlockFluidClassic(fluid, mat).setTemperature(temperature).setDensity(density).setRegistryName(new ResourceLocation(LibMod.MOD_ID, "fluid_" + name)).setTranslationKey(fluid.getUnlocalizedName());
			fluid.setBlock(block);
			if (useBucket) FluidRegistry.addBucketForFluid(fluid);
			Bewitchment.proxy.registerTexture(fluid);
			ForgeRegistries.BLOCKS.register(block);
			MOD_FLUIDS.add(fluid);
		}
		return FluidRegistry.getFluid(name);
	}

	public static List<Fluid> init() {
		return MOD_FLUIDS;
	}

	public static class Handler {
		@SubscribeEvent
		public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
			if (event.getEntityLiving().world.getBlockState(event.getEntityLiving().getPosition()).getBlock() == HONEY.getBlock()) {
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60));
			}
		}
	}
}
