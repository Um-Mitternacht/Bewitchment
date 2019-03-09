package com.bewitchment.common.block.decorations;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockFakeIceFence extends BlockFence implements IModelRegister {

	public BlockFakeIceFence() {
		super(Material.ICE, Material.ICE.getMaterialMapColor());
		setTranslationKey(LibMod.MOD_ID + "." + LibBlockName.FAKE_ICE_FENCE);
		setRegistryName(LibMod.MOD_ID, LibBlockName.FAKE_ICE_FENCE);
		setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		setResistance(2F);
		setHardness(2F);
		this.setDefaultSlipperiness(0.98F);
		useNeighborBrightness = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
