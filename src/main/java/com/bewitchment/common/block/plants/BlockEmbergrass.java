package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

//Notice: The diabolic update WILL introduce more magic plants. Using this class for such plants may be ill-advised.
@SuppressWarnings({"NullableProblems", "deprecation", "WeakerAccess"})
public class BlockEmbergrass extends BlockBush {
	public static final PropertyInteger TIMES_SPREAD = PropertyInteger.create("times_spread", 0, 4);
	
	public BlockEmbergrass() {
		this("embergrass");
	}
	
	protected BlockEmbergrass(String name) {
		super();
		Util.registerBlock(this, name, Material.PLANTS, SoundType.PLANT, 0, 0, "shears", 0);
		setDefaultState(getBlockState().getBaseState().withProperty(TIMES_SPREAD, 0));
	}
	
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!world.isRemote && entity instanceof EntityLivingBase && this == ModObjects.embergrass) entity.setFire(10);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean()) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if (rand.nextInt(10) == 0 && state.getValue(TIMES_SPREAD) < TIMES_SPREAD.getAllowedValues().size() - 1) {
			int i = rand.nextInt(4);
			BlockPos pos0 = i == 0 ? pos.north() : i == 1 ? pos.south() : i == 2 ? pos.east() : pos.west();
			if (canSustainBush(world.getBlockState(pos0.down())) && world.isAirBlock(pos0)) world.setBlockState(pos0, rand.nextInt(10) == 0 ? world.getBlockState(pos) : world.getBlockState(pos).cycleProperty(TIMES_SPREAD));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIMES_SPREAD, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIMES_SPREAD);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TIMES_SPREAD);
	}
}