package com.bewitchment.common.ritual;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.api.ritual.IRitualHandler;
import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.item.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualDrawing extends Ritual {

	ArrayList<int[]> coords;

	public RitualDrawing(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick, ArrayList<int[]> coords) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
		this.coords = coords;
	}

	@Override
	public void onFinish(EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {
		final IBlockState state = ModBlocks.ritual_glyphs.getExtendedState(ModBlocks.ritual_glyphs.getDefaultState(), world, pos).withProperty(BlockCircleGlyph.FACING, EnumFacing.HORIZONTALS[(int) (Math.random() * 4)]).withProperty(BlockCircleGlyph.TYPE, BlockCircleGlyph.GlyphType.values()[data.getInteger("chalkType")]);
		coords.forEach(rc -> {
			world.setBlockState(pos.add(rc[0], 0, rc[1]), state, 3);
		});
	}

	@Override
	public void onStarted(EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {
		ItemStack chalk = player.getHeldItemOffhand();
		data.setInteger("chalkType", chalk.getMetadata());
		if (!player.isCreative()) {
			int usesLeft = chalk.getTagCompound().getInteger("usesLeft") - coords.size();
			chalk.getTagCompound().setInteger("usesLeft", usesLeft);
			if (usesLeft < 1)
				chalk.setCount(0);
		}
		player.setHeldItem(EnumHand.OFF_HAND, chalk);
	}

	@Override
	public boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe) {
		for (int[] rc : coords) {
			BlockPos pos2 = pos.add(rc[0], 0, rc[1]);
			if (!world.isAirBlock(pos2) && !world.getBlockState(pos2).getBlock().isReplaceable(world, pos2) && world.getBlockState(pos2).getBlock() != ModBlocks.ritual_glyphs)
				return false;
		}

		return player.getHeldItemOffhand().getItem() == ModItems.ritual_chalk && player.getHeldItemOffhand().getMetadata() != 1 && (player.isCreative() || player.getHeldItemOffhand().getTagCompound().getInteger("usesLeft") >= coords.size());
	}

}
