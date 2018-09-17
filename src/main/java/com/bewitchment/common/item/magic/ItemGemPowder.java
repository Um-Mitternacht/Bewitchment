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
			"gem_powder_garnet", // 0
			"gem_powder_nuummite", // 1
			"gem_powder_tigers_eye", // 2
			"gem_powder_tourmaline", // 3
			"gem_powder_bloodstone", // 4
			"gem_powder_jasper", // 5
			"gem_powder_malachite", // 6
			"gem_powder_amethyst", // 7
			"gem_powder_alexandrite", // 8
	};

	public ItemGemPowder(String id) {
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() >= names.length) return super.getUnlocalizedName(stack);
		return super.getUnlocalizedName(stack) + "." + names[stack.getMetadata()];
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
			ResourceLocation rl = new ResourceLocation(this.getRegistryName().getResourceDomain(), "powders/" + this.getRegistryName().getResourcePath() + "_" + names[i]);
			ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}
}
