package com.bewitchment.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockMeta<T extends Item> extends ItemBlock {

	private Enum<?>[] itemVariants;
	private EnumNameMode nMode;

	public ItemBlockMeta(Block block, Enum<?>[] variants, EnumNameMode nameMode) {
		super(block);
		if (variants.length == 0) {
			throw new IllegalArgumentException("At least 1 variant is required");
		}
		setRegistryName(block.getRegistryName());
		setMaxDamage(0);
		setHasSubtypes(true);
		itemVariants = variants;
		nMode = nameMode;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (nMode == EnumNameMode.TOOLTIP && stack.getMetadata() >= 0 && stack.getMetadata() < itemVariants.length) {
			tooltip.add(I18n.format(getRegistryName().toString().replace(':', '.') + ".tooltip.variety." + itemVariants[stack.getMetadata()].name().toLowerCase()));
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (nMode == EnumNameMode.NAME) {
			int meta = stack.getMetadata();
			if (meta < 0 || meta >= itemVariants.length) {
				meta = 0;
			}
			return super.getTranslationKey() + "_" + itemVariants[stack.getMetadata()].name().toLowerCase();
		}
		return super.getTranslationKey();
	}

	public static enum EnumNameMode {
		NONE, TOOLTIP, NAME
	}

}
