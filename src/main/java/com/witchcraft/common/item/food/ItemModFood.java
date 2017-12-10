package com.witchcraft.common.item.food;

import com.witchcraft.api.helper.IModelRegister;
import com.witchcraft.client.handler.ModelHandler;
import com.witchcraft.common.core.WitchcraftCreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 28/02/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ItemModFood extends ItemFood implements IModelRegister {

	public ItemModFood(String id, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setRegistryName(id);
		setUnlocalizedName(id);
		setCreativeTab(WitchcraftCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
