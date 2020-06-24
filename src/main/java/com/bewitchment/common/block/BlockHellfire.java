package com.bewitchment.common.block;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings({"NullableProblems"})
public class BlockHellfire extends BlockFire {
	public BlockHellfire() {
		super();
		Util.registerBlock(this, "hellfire", Blocks.FIRE);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking() && player.getHeldItem(hand).getItem() == Items.GLASS_BOTTLE) {
			if (!world.isRemote) {
				Util.replaceAndConsumeItem(player, hand, new ItemStack(ModObjects.bottled_hellfire));
				world.setBlockToAir(pos);
				world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1.75f);
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!world.isRemote && entity instanceof EntityLivingBase) {
			entity.attackEntityFrom(DamageSource.IN_FIRE, 2);
			entity.setFire(5);
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.hellfire, 200, 0));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
		if (!world.getBlockState(to.down()).isOpaqueCube()) world.destroyBlock(to, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (rand.nextBoolean())
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0, 0, 0);
	}
}