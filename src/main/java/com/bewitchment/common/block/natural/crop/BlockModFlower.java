package com.bewitchment.common.block.natural.crop;

import com.bewitchment.api.helper.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockBush;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModFlower extends BlockBush implements IModelRegister {

	public BlockModFlower(String name) {
		this.setRegistryName(LibMod.MOD_ID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(BewitchmentCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
