package com.bewitchment.common.item.block;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.head.HeadTypes;
import com.bewitchment.common.core.statics.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author its_meow
 *
 * Mar 7, 2019
 */
public class ItemBlockHeadType extends ItemBlockSkull implements IModelRegister {
	
	private final HeadTypes type;
	
	public ItemBlockHeadType(Block block, HeadTypes type, int i) {
		super(block, type.allowFloor, i);
		this.type = type;
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

}