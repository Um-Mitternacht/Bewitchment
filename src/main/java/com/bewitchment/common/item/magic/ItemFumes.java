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

public class ItemFumes extends ItemMod {
	public static final String[] names = new String[]{
			"unfired_jar", // 0
			"empty_jar", // 1

			"oak_spirit", // 2
			"birch_soul", // 3
			"acacia_essence", // 4
			"spruce_heart", // 5

			"cloudy_oil", // 6 ~ equivalent of foul fume - byproduct

			"cleansing_aura", // 7 ~ connected with cleaning, purifying
			"emanation_of_dishonesty", // 8 ~ connected with evil
			"everchanging_presence", // 9 ~ connected with changing
			"undying_image", // 10 ~ connected with rebirth

			"demonic_dew", // 11 ~ connected with nether/infernal stuff
			"otherworld_tears", // 12 ~ connected with end/ethereal stuff

			"fiery_breeze", // 13 ~ connected with fire
			"heavenly_winds", // 14 ~ connected with air
			"petrichor_odour", // 15 ~ connected with earth
			"zephyr_of_the_depths" // 16 ~ connected with water
	};

	public ItemFumes(String id) {
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
			ResourceLocation rl = new ResourceLocation(this.getRegistryName().getResourceDomain(), "fumes/" + this.getRegistryName().getResourcePath() + "_" + names[i]);
			ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}

}
