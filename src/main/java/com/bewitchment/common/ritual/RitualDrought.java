package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

public class RitualDrought extends Ritual {
	public RitualDrought() {
		super(new ResourceLocation(Bewitchment.MODID, "drought"), Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.acacia_resin), Util.get(Blocks.CACTUS), Util.get("sand"), Util.get("gemOpal"), Util.get("gemOpal"), Util.get(ModObjects.lizard_leg)), null, Collections.singletonList(new ItemStack(ModObjects.empty_jar)), false, 5, 250, 20, BlockGlyph.NORMAL, -1, -1);
	}
	
	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.rain";
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote && world.isRaining()) {
			world.getWorldInfo().setRaining(false);
		}
	}
}