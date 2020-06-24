package com.bewitchment.common.integration.chisel;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModBlockChisel extends ModBlock {
	private final String name;

	public ModBlockChisel(String name, Block base, String... oreDictionaryEntries) {
		super(base.getDefaultState().getMaterial());
		Util.registerBlock(this, base.getTranslationKey().substring(6 + Bewitchment.MODID.length()) + "_" + name, base, oreDictionaryEntries);
		setTranslationKey(base.getTranslationKey().substring(5));
		StringBuilder finalName = new StringBuilder();
		for (String s : name.split("_"))
			finalName.append(s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toUpperCase())).append(" ");
		this.name = finalName.toString();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format(name));
	}
}