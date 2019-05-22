package com.bewitchment.common.block.util;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings({"deprecation", "NullableProblems", "ConstantConditions"})
public class ModBlockLeaves extends BlockLeaves {
	public ModBlockLeaves(String name, String... oreDictionaryNames) {
		super();
		Util.registerBlock(this, name, Material.LEAVES, SoundType.PLANT, 0.2f, 0, "shears", 0, oreDictionaryNames);
		setDefaultState(this.getBlockState().getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		this.leavesFancy = Bewitchment.proxy.isFancyGraphicsEnabled();
		return !this.leavesFancy;
	}
	
	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return null;
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
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack stack) {
		if (!world.isRemote && stack.getItem() instanceof ItemShears) {
			player.addStat(StatList.getBlockStats(this));
			spawnAsEntity(world, pos, new ItemStack(this));
		}
		else super.harvestBlock(world, player, pos, state, tile, stack);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
	}
}