package com.bewitchment.common.block.util;

import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class ModBlockOre extends ModBlock {
	public ModBlockOre(String name, Block base, String... oreDictionaryNames) {
		super(name, base, oreDictionaryNames);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return this == ModObjects.salt_ore ? 1 + random.nextInt(3) : 1;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this == ModObjects.garnet_ore) {
			return ModObjects.garnet;
		}
		else if (this == ModObjects.amethyst_ore) {
			return ModObjects.amethyst;
		}
		else if (this == ModObjects.opal_ore) {
			return ModObjects.opal;
		}
		else if (this == ModObjects.salt_ore) {
			return ModObjects.salt;
		}
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune)) {
			int i = random.nextInt(fortune + 2) - 1;
			if (i < 0) {
				i = 0;
			}
			return this.quantityDropped(random) * (i + 1);
		}
		else {
			return this.quantityDropped(random);
		}
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
			int i = 0;
			if (this == ModObjects.amethyst_ore) {
				i = MathHelper.getInt(rand, 3, 7);
			}
			else if (this == ModObjects.garnet_ore) {
				i = MathHelper.getInt(rand, 3, 7);
			}
			else if (this == ModObjects.opal_ore) {
				i = MathHelper.getInt(rand, 3, 7);
			}
			else if (this == ModObjects.salt_ore) {
				i = MathHelper.getInt(rand, 0, 2);
			}
			return i;
		}
		else {
			return 0;
		}
	}
}
