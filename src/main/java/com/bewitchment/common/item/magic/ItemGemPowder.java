package com.bewitchment.common.item.magic;

import com.bewitchment.common.item.ItemMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 11/26/2017.
 */
public class ItemGemPowder extends ItemMod {

	public static final String[] names = new String[]{
			"garnet", // 0
			"nuummite", // 1
			"tigers_eye", // 2
			"tourmaline", // 3
			"bloodstone", // 4
			"jasper", // 5
			"malachite", // 6
			"amethyst", // 7
			"alexandrite", // 8
	};

	public ItemGemPowder(String id) {
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() >= names.length) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "." + names[stack.getMetadata()];
	}

	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (this.isInCreativeTab(itemIn)) {
			for (int i = 0; i < names.length; i++)
				tab.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		for (int i = 0; i < names.length; i++) {
			ResourceLocation rl = new ResourceLocation(this.getRegistryName().getNamespace(), "powders/" + this.getRegistryName().getPath() + "_" + names[i]);
			ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}
}
