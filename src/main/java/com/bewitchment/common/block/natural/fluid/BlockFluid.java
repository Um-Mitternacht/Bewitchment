package com.bewitchment.common.block.natural.fluid;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * This class was created by Arekkuusu on 03/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockFluid extends BlockFluidClassic implements IModelRegister {

	private final boolean flammable;
	private final int flammability;

	public BlockFluid(Fluid fluid, Material liquid) {
		this(fluid, 0, false, liquid);
	}

	public BlockFluid(Fluid fluid, int flammability, boolean flammable, Material liquid) {
		super(fluid, liquid);
		setTranslationKey(fluid.getName());
		setRegistryName(LibMod.MOD_ID, fluid.getName());
		setDensity(fluid.getDensity());

		this.flammability = flammability;
		this.flammable = flammable;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double d0 = pos.getX();
		double d1 = pos.getY();
		double d2 = pos.getZ();

		int i = stateIn.getValue(LEVEL);

		if (i > 0 && i < 8) {
			if (rand.nextInt(64) == 0) {
				worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
			}
		} else if (rand.nextInt(10) == 0) {
			worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return flammability;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return flammable;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return flammable ? 30 : 0;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return flammable && flammability == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		final Item item = Item.getItemFromBlock(this);
		assert item != null;

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(LibMod.MOD_ID + ":fluid", getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
}
