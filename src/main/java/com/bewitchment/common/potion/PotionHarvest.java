package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionHarvest extends ModPotion {
	public PotionHarvest() {
		super("harvest", false, 0xff7f00);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = amplifier + 1;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				if (world.getBlockState(pos0).getBlock() instanceof BlockCrops) {
					world.getBlockState(pos0).getBlock().dropBlockAsItem(world, pos0, world.getBlockState(pos0), amplifier);
					world.setBlockState(pos0, world.getBlockState(pos0).getBlock().getDefaultState());
					flag = true;
				} else if (world.rand.nextInt(10) == 0 && world.getBlockState(pos0).getBlock() == Blocks.LEAVES && world.getBlockState(pos0).getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK) {
					InventoryHelper.spawnItemStack(world, pos0.getX(), pos0.getY(), pos0.getZ(), new ItemStack(Items.APPLE));
					flag = true;
				} else if (world.rand.nextInt(4) == 0 && world.getBlockState(pos0).getBlock() instanceof IGrowable && world.getBlockState(pos0).getBlock().isReplaceable(world, pos0)) {
					world.setBlockState(pos0, Blocks.PUMPKIN.getDefaultState().withProperty(BlockPumpkin.FACING, EnumFacing.HORIZONTALS[world.rand.nextInt(4)]));
					flag = true;
				}
			}
		}
		return flag;
	}
}