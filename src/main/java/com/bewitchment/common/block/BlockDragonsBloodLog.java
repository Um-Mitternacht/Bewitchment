package com.bewitchment.common.block;

import com.bewitchment.common.block.util.ModBlockLog;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings({"NullableProblems"})
public class BlockDragonsBloodLog extends ModBlockLog {
	public static final PropertyBool NATURAL = PropertyBool.create("natural");
	public static final PropertyBool SLASHED = PropertyBool.create("slashed");

	public BlockDragonsBloodLog() {
		super("dragons_blood_wood", Blocks.LOG, "logWood");
		setTickRandomly(true);
		setDefaultState(getBlockState().getBaseState().withProperty(LOG_AXIS, EnumAxis.Y).withProperty(NATURAL, true).withProperty(SLASHED, false));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		BlockPos dropPos = pos.offset(EnumFacing.HORIZONTALS[rand.nextInt(4)], 1);
		if (worldIn.isAreaLoaded(pos, 1) && state.getValue(SLASHED).equals(true) && rand.nextInt(100) <= 7) {
			worldIn.spawnEntity(new EntityItem(worldIn, dropPos.getX(), dropPos.getY(), dropPos.getZ(), new ItemStack(ModObjects.dragons_blood_resin)));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.getHeldItem(hand).getItem() instanceof ItemBoline && state.getValue(NATURAL).equals(true) && state.getValue(SLASHED).equals(false)) {
			worldIn.setBlockState(pos, state.withProperty(SLASHED, true));
			playerIn.getHeldItem(hand).damageItem(1, playerIn);
			if (worldIn.isRemote)
				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();
		switch (meta & 12) {
			case 0:
				iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
				break;
			case 4:
				iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
				break;
			case 8:
				iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
				break;
			default:
				iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
		}
		if ((meta & 1) == 0) {
			iblockstate = iblockstate.withProperty(NATURAL, false);
		} else {
			iblockstate = iblockstate.withProperty(NATURAL, true);
		}
		if ((meta & 2) == 0) {
			iblockstate = iblockstate.withProperty(SLASHED, false);
		} else {
			iblockstate = iblockstate.withProperty(SLASHED, true);
		}
		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		switch (state.getValue(LOG_AXIS)) {
			case X:
				i |= 4;
				break;
			case Z:
				i |= 8;
				break;
			case NONE:
				i |= 12;
		}
		if (state.getValue(NATURAL)) i |= 1;
		if (state.getValue(SLASHED)) i |= 2;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NATURAL, SLASHED, LOG_AXIS);
	}
}
