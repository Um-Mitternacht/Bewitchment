package com.bewitchment.common.block.natural.plants;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModFlower extends BlockBush implements IModelRegister {

	public BlockModFlower(String name) {
		this.setRegistryName(LibMod.MOD_ID, name);
		setTranslationKey(LibMod.MOD_ID + "." + name);
		this.setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
