package com.bewitchment.common.block.decorations;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWoodStairs extends BlockStairs implements IModelRegister {

	public BlockWoodStairs(String unlocalizedName, IBlockState state, Material material) {
		super(state);
		setTranslationKey(LibMod.MOD_ID + "." + unlocalizedName);
		setRegistryName(new ResourceLocation(LibMod.MOD_ID, unlocalizedName));
		setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		useNeighborBrightness = true;
		setResistance(15F); // default value for wood plank
		setHardness(2F); // default value for wood plank
		this.setSoundType(SoundType.WOOD);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
