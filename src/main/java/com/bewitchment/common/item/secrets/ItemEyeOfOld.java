package com.bewitchment.common.item.secrets;

import com.bewitchment.client.core.ModelResourceLocations;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 12/28/2017. Credit to Elucent for bits of the code.
 */
public class ItemEyeOfOld extends ItemMod {
	public ItemEyeOfOld() {
		super(LibItemName.EYE_OF_OLD);
		setMaxDamage(0);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	//This easter egg is a reference to an ARG Sunconure11 was involved in at one point.
	//If you want to continue your descent into this insanity, you can follow this link:
	//https://www.reddit.com/r/izuxe43ui520815/
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelBakery.registerItemVariants(this,
				ModelResourceLocations.EYE_OF_OLD_NORMAL,
				ModelResourceLocations.EYE_OF_OLD_HARU,
				ModelResourceLocations.EYE_OF_OLD_IZU);
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				if (stack.getDisplayName().equalsIgnoreCase("Haru") || stack.getDisplayName().equalsIgnoreCase("Haruspex") || stack.getDisplayName().equalsIgnoreCase("H4rv5p3x")) {
					return ModelResourceLocations.EYE_OF_OLD_HARU;
				}
				if (stack.getDisplayName().equalsIgnoreCase("Izuxe") || stack.getDisplayName().equalsIgnoreCase("Izu") || stack.getDisplayName().equalsIgnoreCase("Izuxe43ui520815")) {
					return ModelResourceLocations.EYE_OF_OLD_IZU;
				}
				return ModelResourceLocations.EYE_OF_OLD_NORMAL;
			}
		});
	}
}
