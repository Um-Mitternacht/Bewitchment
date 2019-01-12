package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.ritual.RitualImpl;
import net.minecraft.block.*;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RitualFrenziedGrowth extends RitualImpl {
	public RitualFrenziedGrowth(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos mainGlyphPos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		for (int x = -16; x <= 16; x++) {
			for (int y = -16; y <= 16; y++) {
				for (int z = -16; z <= 16; z++) {
					BlockPos pos = effectivePosition.add(x, y, z);
					IBlockState state = world.getBlockState(pos);
					if (state.getBlock() == Blocks.LOG) {
						if (state.getValue(BlockOldLog.VARIANT) == EnumType.OAK)
							world.setBlockState(pos, ModBlocks.log_elder.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
						if (state.getValue(BlockOldLog.VARIANT) == EnumType.SPRUCE)
							world.setBlockState(pos, ModBlocks.log_cypress.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
					}
					if (state.getBlock() == Blocks.LOG2) {
						if (state.getValue(BlockNewLog.VARIANT) == EnumType.ACACIA)
							world.setBlockState(pos, ModBlocks.log_juniper.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
						if (state.getValue(BlockNewLog.VARIANT) == EnumType.DARK_OAK)
							world.setBlockState(pos, ModBlocks.log_yew.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
					}
					if (state.getBlock() == Blocks.LEAVES) {
						if (state.getValue(BlockOldLeaf.VARIANT) == EnumType.OAK)
							world.setBlockState(pos, ModBlocks.leaves_elder.getDefaultState());
						if (state.getValue(BlockOldLeaf.VARIANT) == EnumType.SPRUCE)
							world.setBlockState(pos, ModBlocks.leaves_cypress.getDefaultState());
					}
					if (state.getBlock() == Blocks.LEAVES2) {
						if (state.getValue(BlockNewLeaf.VARIANT) == EnumType.ACACIA)
							world.setBlockState(pos, ModBlocks.leaves_juniper.getDefaultState());
						if (state.getValue(BlockNewLeaf.VARIANT) == EnumType.DARK_OAK)
							world.setBlockState(pos, ModBlocks.leaves_yew.getDefaultState());
					}
				}
			}
		}
	}
}