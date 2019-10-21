package com.bewitchment.common.block;

import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions", "unused"})
public class BlockWitchesCauldron extends ModBlockContainer {
	private static final AxisAlignedBB BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 15 * 0.0625, 11 * 0.0625, 15 * 0.0625);
	private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0, 0, 0, 1, 0.3125, 1);
	private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0, 0, 0, 1, 11 * 0.0625, 0.125);
	private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0, 0, 0.875, 1, 11 * 0.0625, 1);
	private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875, 0, 0, 1, 11 * 0.0625, 1);
	private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0, 0, 0, 0.125, 11 * 0.0625, 1);
	
	public BlockWitchesCauldron() {
		super(null, "witches_cauldron", Blocks.CAULDRON, -1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWitchesCauldron();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB box, List<AxisAlignedBB> boxes, Entity entity, boolean wut) {
		addCollisionBoxToList(pos, box, boxes, AABB_LEGS);
		addCollisionBoxToList(pos, box, boxes, AABB_WALL_WEST);
		addCollisionBoxToList(pos, box, boxes, AABB_WALL_NORTH);
		addCollisionBoxToList(pos, box, boxes, AABB_WALL_EAST);
		addCollisionBoxToList(pos, box, boxes, AABB_WALL_SOUTH);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		ExtendedWorld ext = ExtendedWorld.get(world);
		for (NBTTagCompound cauldron : ext.storedCauldrons) {
			if (cauldron.getInteger("dimension") == world.provider.getDimension() && cauldron.getLong("position") == pos.toLong()) {
				ext.storedCauldrons.remove(cauldron);
				ext.markDirty();
				break;
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(pos) instanceof ModTileEntity) return ((ModTileEntity) world.getTileEntity(pos)).activate(world, pos, player, hand, face);
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound cauldron = new NBTTagCompound();
		cauldron.setLong("position", pos.toLong());
		cauldron.setInteger("dimension", placer.dimension);
		ExtendedWorld ext = ExtendedWorld.get(world);
		ext.storedCauldrons.add(cauldron);
		ext.markDirty();
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}
}