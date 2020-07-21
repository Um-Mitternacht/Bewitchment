package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ModBlockFenceGate extends BlockFenceGate {
	public ModBlockFenceGate(String name, Block base, String... oreDictionaryNames) {
		super(BlockPlanks.EnumType.OAK);
		Util.registerBlock(this, name, base, oreDictionaryNames);
		if (base == ModObjects.juniper_planks) setResistance(Integer.MAX_VALUE);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this == ModObjects.juniper_fence_gate) {
			if (!ItemJuniperKey.checkAccess(worldIn, pos, playerIn, true)) {
				return true;
			}
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return (!Util.isTransparent(state) || world.getBlockState(pos.offset(face)).getBlock() != this) && super.shouldSideBeRendered(state, world, pos, face);
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
		float val = super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
		if (this == ModObjects.juniper_fence_gate) {
			if (ItemJuniperKey.checkAccess(worldIn, pos, player, false)) return val;
			return -1;
		}
		return val;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placer instanceof EntityPlayer) {
			BlockPos pos0 = pos;
			if (this == ModObjects.juniper_fence_gate)
				Util.giveItem((EntityPlayer) placer, ((ItemJuniperKey) ModObjects.juniper_key).setTags(worldIn, pos0, new ItemStack(ModObjects.juniper_key)));
		}

		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return state.getBlock() == ModObjects.juniper_fence_gate ? EnumPushReaction.BLOCK : super.getPushReaction(state);
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (this != ModObjects.juniper_fence_gate) super.onNeighborChange(world, pos, neighbor);
	}
}