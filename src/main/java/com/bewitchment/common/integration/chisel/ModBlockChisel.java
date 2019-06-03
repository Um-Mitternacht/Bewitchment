package com.bewitchment.common.integration.chisel;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.util.ModBlock;
import com.bewitchment.registry.util.IOreDictionaryContainer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class ModBlockChisel extends ModBlock {
	private final String name;
	
	public ModBlockChisel(String name, Block base) {
		super(name, Material.CLOTH, SoundType.CLOTH, 1, 1, "", 0);
		//Util.registerValues(this, base.getTranslationKey().substring(6 + Bewitchment.MODID.length()) + "_" + name, base, Util.toArray(base instanceof IOreDictionaryContainer ? ((IOreDictionaryContainer) base).getOreDictionaryNames() : Arrays.asList("")));
		setTranslationKey(base.getTranslationKey().substring(5));
		String finalName = "";
		for (String s : name.split("_"))
			finalName += s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toUpperCase()) + " ";
		this.name = finalName;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format(name));
	}
}