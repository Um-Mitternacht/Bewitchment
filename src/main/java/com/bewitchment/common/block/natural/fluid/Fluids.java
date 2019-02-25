package com.bewitchment.common.block.natural.fluid;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * This class was created by Arekkuusu on 03/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class Fluids {

	public static final List<Block> MOD_FLUID_BLOCKS = new ArrayList<>();
	public static final List<Fluid> MOD_FLUIDS = new ArrayList<>();
	
	public static final Fluid BW_HONEY = registerFluid("honey", Material.WATER, 0, 10, 1500, 8000, true, false);
	public static final Fluid MUNDANE_OIL = registerFluid("honey", Material.WATER, 0, 0, 800, 4000, true, true);

	private static Fluid registerFluid(String name, Material mat, int temperature, int luminosity, int density, int viscosity, boolean useBucket, boolean useFlowTexture)
	{
		if (!FluidRegistry.isFluidRegistered(name))
		{
			Fluid fluid = new Fluid(name, new ResourceLocation(LibMod.MOD_ID, "blocks/fluid/" + name + "_still"), new ResourceLocation(LibMod.MOD_ID, "blocks/fluid/" + name + (useFlowTexture ? "_flowing" : "_still"))).setTemperature(temperature).setLuminosity(luminosity).setDensity(density).setViscosity(viscosity);
			FluidRegistry.registerFluid(fluid);
			Block block = new BlockFluidClassic(fluid, mat).setTemperature(temperature).setDensity(density).setRegistryName(new ResourceLocation(LibMod.MOD_ID, "fluid_" + name)).setTranslationKey(fluid.getUnlocalizedName());
			fluid.setBlock(block);
			if (useBucket) FluidRegistry.addBucketForFluid(fluid);
			Bewitchment.proxy.registerTexture(fluid);
			MOD_FLUID_BLOCKS.add(block);
			MOD_FLUIDS.add(fluid);
		}
		return FluidRegistry.getFluid(name);
	}
}
