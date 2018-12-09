/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.block.natural.tree;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockModLeaves extends BlockLeaves implements IModelRegister {

	public BlockModLeaves(String id) {
		setTranslationKey(id);
		setRegistryName(LibMod.MOD_ID, id);
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
		if (this.getBlockState().getBlock() == ModBlocks.leaves_juniper && worldIn.rand.nextInt(chance) == 0) {
			spawnAsEntity(worldIn, pos, new ItemStack(ModItems.juniper_berries));
		} else if (this.getBlockState().getBlock() == ModBlocks.leaves_yew && worldIn.rand.nextInt(chance) == 0) {
			spawnAsEntity(worldIn, pos, new ItemStack(ModItems.yew_aril));
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DECAYABLE, ((meta) & 1) == 1).withProperty(CHECK_DECAY, ((meta) & 2) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta += (state.getValue(DECAYABLE) ? 1 : 0);
		meta += (state.getValue(CHECK_DECAY) ? 2 : 0);
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty<?>[]{CHECK_DECAY, DECAYABLE});
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		if (!worldIn.isRemote && stack.getItem() instanceof ItemShears) {
			player.addStat(StatList.getBlockStats(this));
			spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this)));
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.sapling);
	}

	@Override
	public int damageDropped(IBlockState state) {
		if (this == ModBlocks.leaves_elder) return 0;
		if (this == ModBlocks.leaves_juniper) return 1;
		if (this == ModBlocks.leaves_yew) return 2;
		if (this == ModBlocks.leaves_cypress) return 3;
		return 4; // give a useless item, but should never happen
	}

	@Override
	protected int getSaplingDropChance(IBlockState state) {
		if (state.getBlock() == ModBlocks.leaves_juniper) return super.getSaplingDropChance(state) * 3;
		return super.getSaplingDropChance(state);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		this.leavesFancy = Bewitchment.proxy.isFancyGraphicsEnabled();
		return !this.leavesFancy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);

		// I'm not really sure why, but removing the following code messes up hwyla block model rendering
		// I probably left some code dealing with metadata around and I cannot find it anymore
		if (this == ModBlocks.leaves_juniper)
			ModelHandler.registerModel(this, 1);
		else if (this == ModBlocks.leaves_yew)
			ModelHandler.registerModel(this, 2);
		else if (this == ModBlocks.leaves_cypress)
			ModelHandler.registerModel(this, 3);
	}

}
