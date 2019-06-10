package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class TileEntityGlyph extends TileEntityAltarStorage {
	private final ItemStackHandler inventory = new ItemStackHandler(Short.MAX_VALUE);
	
	public Ritual ritual;
	public UUID caster;
	public BlockPos effectivePos;
	public int effectiveDim;
	
	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}
	
	public void startRitual(EntityPlayer player) {
		ritual = GameRegistry.findRegistry(Ritual.class).getValuesCollection().stream().filter(r -> r.matches(world, pos)).findFirst().orElse(null);
		if (ritual != null) {
			caster = player.getPersistentID();
		}
	}
	
	public void stopRitual(boolean finished) {
		if (ritual != null) {
			if (finished) ritual.onFinished(world, pos, world.getPlayerEntityByUUID(caster));
			else ritual.onHalted(world, pos, world.getPlayerEntityByUUID(caster));
		}
		ritual = null;
		caster = null;
		effectivePos = pos;
		effectiveDim = world.provider.getDimension();
	}
}