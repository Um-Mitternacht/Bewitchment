package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualHighMoon extends Ritual {
	public RitualHighMoon() {
		super(new ResourceLocation(Bewitchment.MODID, "high_moon"), Arrays.asList(Util.get("ingotSilver"), Util.get(Blocks.RED_FLOWER), Util.get(ModObjects.ectoplasm), Util.get(Items.SPIDER_EYE)), null, null, false, 5, 250, 20, BlockGlyph.NORMAL, -1, -1);
	}

	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.no_night";
	}

	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return world.isDaytime();
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote)
			world.setWorldTime(world.getWorldTime() + (41600 - (world.getWorldTime() % 24000)) % 24000);
	}
}