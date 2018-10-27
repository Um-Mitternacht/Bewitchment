package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibIngredients;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

import java.util.HashMap;

class AltarScanHelper {

	TileEntityWitchAltar te;
	boolean upgradeCheckScheduled = false;
	private int dx = -TileEntityWitchAltar.RADIUS, dy = -TileEntityWitchAltar.RADIUS, dz = -TileEntityWitchAltar.RADIUS;
	private HashMap<Block, Integer> map = new HashMap<Block, Integer>();
	private BlockPos.MutableBlockPos checking = new BlockPos.MutableBlockPos(0, 0, 0);
	private boolean complete = false;

	public AltarScanHelper(TileEntityWitchAltar te) {
		this.te = te;
	}

	void scanNature() {
		if (upgradeCheckScheduled) {
			te.refreshUpgrades();
			upgradeCheckScheduled = false;
		}
		for (int i = 0; i < ModConfig.altar_scan_blocks_per_tick; i++) {
			getNextCycle();
			performCurrentCycle();
		}
	}

	private void performCurrentCycle() {
		updateScore();
		if (complete) {
			refreshNature();
		}
	}

	private void getNextCycle() {
		complete = false;
		int radius_c = TileEntityWitchAltar.RADIUS;
		if (te.swordItem.getItem() == ModItems.boline) {
			radius_c += 2;
		}
		dx++;
		if (dx > radius_c) {
			dx = -radius_c;
			dy++;
		}
		if (dy > radius_c) {
			dy = -radius_c;
			dz++;
		}
		if (dz > radius_c) {
			dz = -radius_c;
			complete = true;
		}
		checking.setPos(te.getPos().getX() + dx, te.getPos().getY() + dy, te.getPos().getZ() + dz);
	}

	private int getPowerValue(BlockPos add) {
		IBlockState blockState = te.getWorld().getBlockState(add);
		if (blockState.getBlock().equals(Blocks.AIR)) return 0;
		if (blockState.getBlock() instanceof IPlantable) return 30;
		if (blockState.getBlock() instanceof IGrowable) return 30;
		if (blockState.getBlock().equals(Blocks.MELON_BLOCK)) return 30;
		if (blockState.getBlock().equals(Blocks.PUMPKIN)) return 30;
		ItemStack stack = new ItemStack(blockState.getBlock());
		if (!stack.isEmpty()) {
			if (LibIngredients.anyLog.apply(stack)) return 15;
			if (LibIngredients.anyLeaf.apply(stack)) return 8;
		}
		return 0;
	}

	private void updateScore() {
		int score = getPowerValue(checking);
		if (score > 0) {
			Block block = te.getWorld().getBlockState(checking).getBlock();
			int currentScore = 0;
			if (map.containsKey(block)) {
				currentScore = map.get(block);
			}
			int max_score = TileEntityWitchAltar.MAX_SCORE_PER_CATEGORY;
			if (currentScore < max_score) {
				map.put(block, currentScore + score);
			}
		}
	}

	private void refreshNature() {
		te.refreshUpgrades();
		int maxPower = map.values().parallelStream().reduce(0, (a, b) -> a + b);
		int varietyMultiplier = 40;
		if (te.swordItem.getItem() == ModItems.silver_sword) {
			varietyMultiplier = 51;
		}
		maxPower += (map.keySet().size() * varietyMultiplier); //Variety is the most important thing
		maxPower *= te.multiplier;
		te.storage.setMaxAmount(maxPower);
		map.clear();
		te.markDirty();
	}

	public void forceFullScan() {
		dx = -TileEntityWitchAltar.RADIUS;
		dy = -TileEntityWitchAltar.RADIUS;
		dz = -TileEntityWitchAltar.RADIUS;
		complete = false;
		while (!complete) {
			getNextCycle();
			performCurrentCycle();
		}
	}
}