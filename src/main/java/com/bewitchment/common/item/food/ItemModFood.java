package com.bewitchment.common.item.food;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemModFood extends ItemFood implements IModelRegister {

	private IMagicPowerExpander expander;

	public ItemModFood(String id, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setRegistryName(id);
		setTranslationKey(id);
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	public ItemModFood setMPExpansionValue(int amount) {
		expander = new IMagicPowerExpander() {

			@Override
			public ResourceLocation getID() {
				return getRegistryName();
			}

			@Override
			public int getExtraAmount(EntityPlayer p) {
				return amount;
			}
		};
		return this;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		if (expander != null) {
			BewitchmentAPI.getAPI().expandPlayerMP(expander, player);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
