package com.bewitchment.common.block.util;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

@SuppressWarnings({"deprecation", "NullableProblems", "ConstantConditions"})
public class ModBlockLeaves extends BlockLeaves {
	private final String name;

	public ModBlockLeaves(String name, String... oreDictionaryNames) {
		super();
		this.name = name;
		Util.registerBlock(this, name, Material.LEAVES, SoundType.PLANT, 0.2f, 0, "shears", 0, oreDictionaryNames);
		setDefaultState(this.getBlockState().getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}
	
	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return null;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		this.leavesFancy = Bewitchment.proxy.isFancyGraphicsEnabled();
		return !this.leavesFancy;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack stack) {
		if (!world.isRemote && stack.getItem() instanceof ItemShears) {
			player.addStat(StatList.getBlockStats(this));
			spawnAsEntity(world, pos, new ItemStack(this));
		}
		else super.harvestBlock(world, player, pos, state, tile, stack);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		switch(name) {
			case "cypress_leaves":
				return Item.getItemFromBlock(ModObjects.cypress_sapling);
			case "juniper_leaves":
				return Item.getItemFromBlock(ModObjects.juniper_sapling);
			case "dragons_blood_leaves":
				return Item.getItemFromBlock(ModObjects.dragons_blood_sapling);
			case "elder_leaves":
				return Item.getItemFromBlock(ModObjects.elder_sapling);
		}
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DECAYABLE, ((meta) & 1) == 1).withProperty(CHECK_DECAY, ((meta) & 2) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta += (state.getValue(DECAYABLE) ? 1 : 0);
		meta += (state.getValue(CHECK_DECAY) ? 2 : 1);
		return meta;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
	}
}