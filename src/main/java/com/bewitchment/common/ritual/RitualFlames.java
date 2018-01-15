package com.bewitchment.common.ritual;

import java.util.List;
import java.util.Random;

import com.bewitchment.api.ritual.IRitualHandler;
import com.bewitchment.api.ritual.Ritual;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualFlames extends Ritual {
	
	private static final Random rng = new Random();
	
	public RitualFlames(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}
	
	@Override
	public void onUpdate(EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {
		if (ticks % 40 == 0) {
			List<EntityItem> smeltables = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(3), ie -> !FurnaceRecipes.instance().getSmeltingResult(ie.getItem()).isEmpty());
			smeltables.parallelStream().filter(ei -> !ei.getItem().getItem().hasContainerItem(ei.getItem())).findAny().ifPresent(e -> smeltAndSpawn(e));
			if (rng.nextDouble() < 0.1) {
				if (world.getGameRules().getBoolean("doFireTick")) {
					int x = Math.round(pos.getX()) - 5 + rng.nextInt(12);
					int y = Math.round(pos.getY()) - 5 + rng.nextInt(12);
					int z = Math.round(pos.getZ()) - 5 + rng.nextInt(12);
					if (y < 1)
						y = 1;
					if (y > world.getActualHeight())
						y = world.getActualHeight() - 2;
					BlockPos posn = new BlockPos(x, y, z);
					if (canBurn(world, posn))
						world.setBlockState(posn, Blocks.FIRE.getDefaultState());
				}
			}
		}
	}
	
	private void smeltAndSpawn(EntityItem e) {
		ItemStack copy = e.getItem().copy();
		ItemStack is = copy.splitStack(1);
		if (rng.nextDouble() < 0.7d) {
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(is).copy();
			e.getEntityWorld().spawnEntity(new EntityItem(e.getEntityWorld(), e.posX, e.posY, e.posZ, result));
		} else {
			e.getEntityWorld().spawnEntity(new EntityXPOrb(e.getEntityWorld(), e.posX, e.posY, e.posZ, 2));
		}
		e.setItem(copy);
	}
	
	private boolean canBurn(World world, BlockPos pos) {
		if (!world.isAirBlock(pos))
			return false;
		for (EnumFacing side : EnumFacing.VALUES) {
			BlockPos offset = pos.offset(side);
			if (!world.isAirBlock(pos.offset(side))) {
				Block block = world.getBlockState(offset).getBlock();
				if (block != Blocks.FIRE)
					return true;
			}
		}
		return false;
	}
	
}
