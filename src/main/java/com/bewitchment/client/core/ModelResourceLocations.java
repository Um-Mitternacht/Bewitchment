package com.bewitchment.client.core;

import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelResourceLocations {

	public static final ModelResourceLocation UNBOUND_LOCATION_STONE = new ModelResourceLocation(new ResourceLocation(LibMod.MOD_ID, LibItemName.LOCATION_STONE).toString());
	public static final ModelResourceLocation BOUND_LOCATION_STONE = new ModelResourceLocation(new ResourceLocation(LibMod.MOD_ID, LibItemName.LOCATION_STONE) + "_bound");
	public static final ModelResourceLocation EYE_OF_OLD_NORMAL = new ModelResourceLocation(new ResourceLocation(LibMod.MOD_ID, LibItemName.EYE_OF_OLD).toString());
	public static final ModelResourceLocation EYE_OF_OLD_HARU = new ModelResourceLocation(LibMod.MOD_ID + ":haru");
	public static final ModelResourceLocation EYE_OF_OLD_IZU = new ModelResourceLocation(LibMod.MOD_ID + ":izu");

}
