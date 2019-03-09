package com.bewitchment.common.block.decorations;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockScornedBrickFence extends BlockFence implements IModelRegister {

	public BlockScornedBrickFence() {
		super(Material.ROCK, Material.ROCK.getMaterialMapColor());
		setTranslationKey(LibMod.MOD_ID + "." + LibBlockName.SCORNED_BRICK_FENCE);
		setRegistryName(LibMod.MOD_ID, LibBlockName.SCORNED_BRICK_FENCE);
		setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		setResistance(5F);
		setHardness(5F);
		useNeighborBrightness = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
