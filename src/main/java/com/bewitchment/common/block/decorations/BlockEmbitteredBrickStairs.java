package com.bewitchment.common.block.decorations;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockEmbitteredBrickStairs extends BlockStairs implements IModelRegister {

	public BlockEmbitteredBrickStairs(String unlocalizedName, IBlockState state, Material material) {
		super(state);
		setTranslationKey(LibMod.MOD_ID + "." + LibBlockName.EMBITTERED_BRICK_STAIRS);
		setRegistryName(new ResourceLocation(LibMod.MOD_ID, unlocalizedName));
		setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		useNeighborBrightness = true;
		setResistance(5F);
		setHardness(5F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
