package com.bewitchment.common.block;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"deprecation", "ConstantConditions", "NullableProblems"})
public class BlockSaltBarrier extends BlockRedstoneWire {
	private static final AxisAlignedBB WALL = new AxisAlignedBB(0, -16, 0, 1, 16, 1);
	
	public BlockSaltBarrier() {
		super();
		Util.registerBlock(this, "salt_barrier", Material.CIRCUITS, SoundType.SAND, 0, 0, "", 0);
		setCreativeTab(null);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB box, List<AxisAlignedBB> boxes, Entity entity, boolean wut) {
		if (entity instanceof EntityLivingBase) {
			if ((BewitchmentAPI.getSilverWeakness((EntityLivingBase) entity) > 1 && !BewitchmentAPI.isWerewolf((EntityLivingBase) entity)) || entity instanceof EntitySlime || BewitchmentAPI.getColdIronWeakness((EntityLivingBase) entity) > 1) addCollisionBoxToList(pos, box, boxes, WALL);
		}
	}
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getBlock() == this;
	}
	
	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
		return PathNodeType.BLOCKED;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
		if (!world.isRemote && !canPlaceBlockAt(world, to)) {
			dropBlockAsItem(world, to, state, 0);
			world.setBlockToAir(to);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModObjects.salt;
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 0;
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(getItemDropped(state, world.rand, 0));
	}
}