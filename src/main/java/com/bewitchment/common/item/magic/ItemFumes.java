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

	public ItemFumes(String id) {
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() >= Type.values().length)
			return super.getTranslationKey(stack);
		return super.getTranslationKey(stack) + "." + Type.values()[stack.getMetadata()].name();
	}

	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (this.isInCreativeTab(itemIn)) {
			for (Type t : Type.values())
				tab.add(new ItemStack(this, 1, t.ordinal()));
		}
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return stack.getMetadata() > 1;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(this, 1, Type.empty_jar.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		for (Type t : Type.values()) {
			ResourceLocation rl = new ResourceLocation(this.getRegistryName().getNamespace(), "fumes/" + this.getRegistryName().getPath() + "_" + t.name());
			ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, t.ordinal(), mrl);
		}
	}

	public static enum Type {
		unfired_jar, empty_jar, // Empty

		oak_spirit, birch_soul, acacia_essence, spruce_heart, // common trees

		cloudy_oil, // equivalent of foul fume - byproduct

		cleansing_aura, // connected with cleaning, purifying
		emanation_of_dishonesty, // connected with evil
		everchanging_presence, // connected with changing
		undying_image, // connected with rebirth

		demonic_dew, // connected with nether/infernal stuff
		otherworld_tears, // connected with end/ethereal stuff

		fiery_breeze, // connected with fire
		heavenly_winds, // connected with air
		petrichor_odour, // connected with earth
		zephyr_of_the_depths, // connected with water

		reek_of_death, vital_essence, droplet_of_wisdom, bottled_magic //Trees from our mod. In order: Cypress, Yew, Elder, Juniper
	}

}
