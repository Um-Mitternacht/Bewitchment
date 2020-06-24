package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualSandsOfTime extends Ritual {
	public RitualSandsOfTime() {
		super(new ResourceLocation(Bewitchment.MODID, "sands_of_time"), Arrays.asList(Util.get("ingotGold"), Util.get("ingotSilver"), Util.get("sandstone"), Util.get("sand"), Util.get("sand")), null, null, false, -1, 600, 50, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1);
	}

	@Override
	public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onStarted(world, pos, caster, inventory);
		ModTileEntity.clear(inventory);
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		if (!world.isRemote) world.setWorldTime(world.getWorldTime() + 5);
	}
}