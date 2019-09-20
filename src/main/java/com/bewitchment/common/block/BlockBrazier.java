package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityBrazier;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockBrazier extends ModBlockContainer {
	public static final PropertyBool HANGING = PropertyBool.create("hanging");
	public static final PropertyBool LIT = PropertyBool.create("lit");
	
	public BlockBrazier() {
		super(Bewitchment.instance, "brazier", Material.IRON, SoundType.STONE, 5, 30, "pickaxe", -1);
		setDefaultState(getBlockState().getBaseState().withProperty(HANGING, false).withProperty(LIT, false));
	}
	
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBrazier();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(3 / 16d, 0, 3 / 16d, 13 / 16d, 1, 13 / 16d);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(HANGING, face == EnumFacing.DOWN);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(HANGING, (meta & 1) > 0).withProperty(LIT, (meta & 2) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta |= state.getValue(HANGING) ? 1 : 0;
		meta |= state.getValue(LIT) ? 2 : 0;
		return meta;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HANGING, LIT);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		TileEntityBrazier te = (TileEntityBrazier) world.getTileEntity(pos);
		return te.interact(player, hand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
		if (stateIn.getValue(LIT) && rand.nextBoolean()) world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + rand.nextDouble(), pos.getY() + 0.5, pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}
}
