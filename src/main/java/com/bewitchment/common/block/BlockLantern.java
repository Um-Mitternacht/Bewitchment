package com.bewitchment.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions"})
public class BlockLantern extends BlockCandleBase {
	private static final AxisAlignedBB BOX = new AxisAlignedBB(0.2, 0, 0.2, 0.8, 0.9375, 0.8);
	
	public BlockLantern(String name) {
		super(name, Material.IRON, SoundType.METAL, 3, 15, "pickaxe", 0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, 0, 0, 0);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		ItemStack unlit = new ItemStack(this);
		list.add(unlit);
		ItemStack lit = new ItemStack(this);
		lit.setTagCompound(new NBTTagCompound());
		lit.getTagCompound().setBoolean("lit", true);
		list.add(lit);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("lit")) tooltip.add(new TextComponentTranslation("tooltip.lantern_lit").getFormattedText());
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(getPickBlock(state, null, null, null, null));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult result, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(this);
		if (state.getValue(LIT)) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("lit", true);
		}
		return stack;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(LIT, living.getHeldItem(hand).hasTagCompound() && living.getHeldItem(hand).getTagCompound().getBoolean("lit"));
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(LIT) ? 15 : 0;
	}
}