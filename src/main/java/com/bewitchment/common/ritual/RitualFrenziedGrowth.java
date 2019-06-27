package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualFrenziedGrowth extends Ritual {
	public RitualFrenziedGrowth() {
		super(new ResourceLocation(Bewitchment.MODID, "frenzied_growth"), Arrays.asList(Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get("vine"), Util.get("vine"), Util.get(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getMetadata())), Util.get(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getMetadata()))), null, null, 5, 550, 40, BlockGlyph.ANY, BlockGlyph.ANY, BlockGlyph.ANY);
	}
	
	@Override
	public void onFinished(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, pos, caster, inventory);
		if (!world.isRemote) {
			for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-16, -16, -16), pos.add(16, 16, 16))) {
				if (world.getBlockState(pos0).getBlock() == Blocks.SAPLING) {
					BlockPlanks.EnumType type = world.getBlockState(pos0).getValue(BlockSapling.TYPE);
					if (type == BlockPlanks.EnumType.OAK || type == BlockPlanks.EnumType.BIRCH) world.setBlockState(pos0, ModObjects.elder_sapling.getDefaultState());
					if (type == BlockPlanks.EnumType.SPRUCE || type == BlockPlanks.EnumType.JUNGLE) world.setBlockState(pos0, ModObjects.cypress_sapling.getDefaultState());
					if (type == BlockPlanks.EnumType.ACACIA) world.setBlockState(pos0, ModObjects.juniper_sapling.getDefaultState());
					if (type == BlockPlanks.EnumType.DARK_OAK) world.setBlockState(pos0, ModObjects.yew_sapling.getDefaultState());
				}
			}
		}
	}
}