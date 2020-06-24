package com.bewitchment.common.item;

import com.bewitchment.common.block.tile.entity.TileEntitySigil;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemSigil extends Item {
	public int cooldown;
	public boolean positive;

	public ItemSigil(int cooldown, boolean positive) {
		this.cooldown = cooldown;
		this.positive = positive;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		BlockPos toPlace = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
		if (!world.isRemote && ModObjects.sigil.canPlaceBlockOnSide(world, toPlace, face)) {
			ItemStack stack = player.getHeldItem(hand);
			world.setBlockState(toPlace, ModObjects.sigil.getStateForPlacement(world, pos, face, hitX, hitY, hitZ, 0, player, hand));
			((TileEntitySigil) world.getTileEntity(toPlace)).setupTileEntity(this);
			((TileEntitySigil) world.getTileEntity(toPlace)).whiteListUUIDSet.add(player.getUniqueID().toString());
			world.playSound(null, pos, ModSounds.CHALK_SCRIBBLE, SoundCategory.BLOCKS, 0.5f, 1 + 0.5f * player.getRNG().nextFloat());
			if (!player.isCreative()) stack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

	public abstract void applyEffects(EntityLivingBase entity);
}
