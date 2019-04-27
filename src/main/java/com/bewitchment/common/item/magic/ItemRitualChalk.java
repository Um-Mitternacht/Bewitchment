package com.bewitchment.common.item.magic;

import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.state.StateProperties;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModSounds;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.item.ModItems;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRitualChalk extends ItemMod {

	public ItemRitualChalk(String id) {
		super(id);
		this.setMaxStackSize(1);
		this.setMaxDamage(40);
		this.setNoRepair();
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}

	@Override
	public boolean canItemEditBlocks() {
		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean isReplacing = worldIn.getBlockState(pos).getBlock().equals(ModBlocks.ritual_glyphs) && worldIn.getBlockState(pos).getValue(StateProperties.GLYPH_TYPE) != EnumGlyphType.GOLDEN;
		if (!worldIn.isRemote && (facing == EnumFacing.UP && ModBlocks.ritual_glyphs.canPlaceBlockAt(worldIn, pos.up()) || isReplacing)) {
			ItemStack chalk = player.getHeldItem(hand);
			Item item = chalk.getItem();
			if (!player.isCreative()) {
				chalk.damageItem(1, player);
			}
			IBlockState state = ModBlocks.ritual_glyphs.getExtendedState(ModBlocks.ritual_glyphs.getDefaultState(), worldIn, pos);
			state = state.withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[(int) (Math.random() * 4)]);
			state = state.withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.values()[item == ModItems.ritual_chalk_normal ? EnumGlyphType.NORMAL.ordinal() : item == ModItems.ritual_chalk_golden ? EnumGlyphType.GOLDEN.ordinal() : item == ModItems.ritual_chalk_ender ? EnumGlyphType.ENDER.ordinal() : item == ModItems.ritual_chalk_nether ? EnumGlyphType.NETHER.ordinal() : -1]);
			worldIn.setBlockState(isReplacing ? pos : pos.up(), state);
			worldIn.playSound(null, pos, ModSounds.CHALK_SCRIBBLE, SoundCategory.BLOCKS, 0.5f, 1f + 0.5f * player.getRNG().nextFloat());
		}
		return EnumActionResult.SUCCESS;
	}
}
