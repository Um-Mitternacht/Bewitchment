package com.bewitchment.common.item.secrets;

import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelBakery.registerItemVariants(this, new ModelResourceLocation(getRegistryName().toString()), new ModelResourceLocation(getRegistryName().toString())
				, new ModelResourceLocation(LibMod.MOD_ID + ":haru"), new ModelResourceLocation(LibMod.MOD_ID + ":izu"));
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				ResourceLocation baseName = getRegistryName();
				if (stack.getDisplayName().compareToIgnoreCase("Haru") == 0) {
					baseName = new ResourceLocation(LibMod.MOD_ID + ":haru");
				}
				if (stack.getDisplayName().compareToIgnoreCase("Izu") == 0) {
					baseName = new ResourceLocation(LibMod.MOD_ID + ":izu");
				} else {
					return new ModelResourceLocation(baseName.toString());
				}
				return new ModelResourceLocation(baseName.toString());
			}
		});
	}
}
