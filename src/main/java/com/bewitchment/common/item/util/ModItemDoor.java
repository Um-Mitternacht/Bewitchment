package com.bewitchment.common.item.util;

import com.bewitchment.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class ModItemDoor extends ItemDoor {
	public final ModBlockDoor door;

	public ModItemDoor(String name, Block base, String... oreDictionaryNames) {
		this(name, base, new ModBlockDoor("block_" + name, base), oreDictionaryNames);
	}

	private ModItemDoor(String name, Block base, ModBlockDoor door, String... oreDictionaryNames) {
		super(door);
		Util.registerItem(this, name, oreDictionaryNames);
		this.door = door;
		this.door.drop = new ItemStack(this);
	}

	public static class ModBlockDoor extends BlockDoor {
		public ItemStack drop;

		public ModBlockDoor(String name, Block base) {
			super(base.getDefaultState().getMaterial());
			Util.registerBlock(this, name, base);
			setCreativeTab(null);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getRenderLayer() {
			return getDefaultState().getMaterial() == Material.ICE || getDefaultState().getMaterial() == Material.GLASS ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
		}

		@Override
		public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
			return drop;
		}

		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
			return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : drop.getItem();
		}
	}
}