package com.bewitchment.common.item.natural.seed;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This class was created by Arekkuusu on 27/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
public class ItemSeed extends ItemSeeds implements IModelRegister {

	protected final Block crop;
	protected final Block soil;

	public ItemSeed(String id, Block crop, Block soil) {
		super(crop, soil);
		setRegistryName(id);
		setTranslationKey(id);
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.crop = crop;
		this.soil = soil;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.ITALIC + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return soil == Blocks.FARMLAND ? EnumPlantType.Crop : EnumPlantType.Water;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
