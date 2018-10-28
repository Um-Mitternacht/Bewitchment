package com.bewitchment.common.block.natural;

import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import javax.annotation.Nonnull;

import static com.bewitchment.common.core.statics.ModCreativeTabs.BLOCKS_CREATIVE_TAB;


@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", modid = "thaumcraft")
public class BlockGem extends BlockMod implements IInfusionStabiliserExt {
	public static final PropertyEnum<BlockGem.Gem> GEM = PropertyEnum.create("gem", BlockGem.Gem.class);

	public BlockGem() {
		super(LibBlockName.GEM_BLOCK, Material.ROCK);
		setHardness(5.0F);
		setCreativeTab(BLOCKS_CREATIVE_TAB);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(GEM, BlockGem.Gem.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(GEM).ordinal();
	}

	@Override
	public void getSubBlocks(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		for (int i = 0; i < BlockGem.Gem.values().length; ++i) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, GEM);
	}

	@Override
	public void registerModel() {
		BlockGem.Gem[] values = BlockGem.Gem.values();
		for (int i = 0; i < values.length; i++) {
			BlockGem.Gem gem = values[i];
			ModelHandler.registerForgeModel(this, i, "gem=" + gem.getName());
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(GEM).ordinal();
	}

	@Optional.Method(modid = "thaumcraft")
	@Override
	public boolean canStabaliseInfusion(World world, BlockPos blockPos) {
		return true;
	}

	@Override
	@Optional.Method(modid = "thaumcraft")
	public float getStabilizationAmount(World world, BlockPos pos) {
		return 0;
	}

	public enum Gem implements IStringSerializable {
		GARNET,
		NUUMMITE,
		TIGERS_EYE,
		TOURMALINE,
		BLOODSTONE,
		JASPER,
		MALACHITE,
		AMETHYST,
		ALEXANDRITE;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
