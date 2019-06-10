package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class TileEntityGlyph extends TileEntityAltarStorage {
	public Ritual ritual;
	public BlockPos effectivePos;
	public int effectiveDim;
	
	public void startRitual(EntityPlayer player) {
	
	}
	
	public void stopRitual(EntityPlayer player, boolean finished) {
	
	}
}