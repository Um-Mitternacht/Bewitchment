package com.bewitchment.common.block;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntityFrostfire;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions"})
public class BlockFrostfire extends ModBlockContainer {
	private static final AxisAlignedBB BOX = new AxisAlignedBB(0, 0, 0, 1, 0, 1);
	
	public BlockFrostfire() {
		super(null, "frostfire", Material.FIRE, SoundType.CLOTH, 0, 0, "", 0, -1);
		setLightLevel(7 / 15f);
		setCreativeTab(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFrostfire();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return world.getBlockState(pos.down()).getBlock().getItem(world, pos.down(), world.getBlockState(pos.down()));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking() && player.getHeldItem(hand).getItem() == Items.GLASS_BOTTLE) {
			if (!world.isRemote) {
				Util.giveAndConsumeItem(player, hand, new ItemStack(ModObjects.bottled_frostfire));
				world.setBlockToAir(pos);
				world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1.75f);
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
		if (!world.getBlockState(to.down()).isFullBlock()) world.destroyBlock(to, false);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean()) world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}
}