package com.bewitchment.common.crafting.fermenting;

import com.bewitchment.api.crafting.BarrelRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class SlimeRecipe extends BarrelRecipe {

	public SlimeRecipe(ItemStack output, int ticks, int power) {
		super(output, ticks, power);
	}

	@Override
	public boolean isValidRecipe(World world, List<ItemStack> stacks, BlockPos pos, FluidStack fluid) {
		return fluid != null && fluid.getFluid() != null && fluid.amount == Fluid.BUCKET_VOLUME && fluid.getFluid().equals(FluidRegistry.WATER) && checkEmpty(stacks);// && world.getBiome(pos) instanceof BiomeSwamp;
	}

	private boolean checkEmpty(List<ItemStack> stacks) {
		for (ItemStack is : stacks) if (!is.isEmpty()) return false;
		return true;
	}

}
