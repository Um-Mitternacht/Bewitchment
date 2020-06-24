package com.bewitchment.common.block.plants;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntitySiphoningFlower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BlockSiphoningFlower extends BlockBush implements ITileEntityProvider {
	public BlockSiphoningFlower(String name) {
		super();
		Util.registerBlock(this, "flower_siphoning_" + name, Material.PLANTS, SoundType.PLANT, 0, 0, "shears", 0);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntitySiphoningFlower();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (world.getTileEntity(pos) instanceof TileEntitySiphoningFlower && Minecraft.getMinecraft().player.getPersistentID().toString().equals(((TileEntitySiphoningFlower) Objects.requireNonNull(world.getTileEntity(pos))).ownerId)) {
			TileEntitySiphoningFlower te = (TileEntitySiphoningFlower) world.getTileEntity(pos);
			if (te.isBound())
				world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0, 0, 0);
		}
		super.randomDisplayTick(state, world, pos, rand);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getTileEntity(pos) instanceof TileEntitySiphoningFlower)
			return ((TileEntitySiphoningFlower) worldIn.getTileEntity(pos)).activate(worldIn, pos, playerIn, hand, facing);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (world.getTileEntity(pos) instanceof TileEntitySiphoningFlower && entity instanceof EntityLivingBase && entity.isNonBoss()) {
			TileEntitySiphoningFlower te = (TileEntitySiphoningFlower) world.getTileEntity(pos);
			if (te != null && !te.isBound()) {
				te.boundId = entity.getPersistentID().toString();
				te.boundName = entity.getName();
				te.markDirty();
				entity.attackEntityFrom(DamageSource.CACTUS, 2);
			}
		}
		super.onEntityCollision(world, pos, state, entity);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (world.getTileEntity(pos) instanceof TileEntitySiphoningFlower) {
			TileEntitySiphoningFlower te = (TileEntitySiphoningFlower) world.getTileEntity(pos);
			te.ownerId = placer.getPersistentID().toString();
			te.markDirty();
		}
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.RED + I18n.format("tooltip.bewitchment.siphoning_flower"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
	}
}
