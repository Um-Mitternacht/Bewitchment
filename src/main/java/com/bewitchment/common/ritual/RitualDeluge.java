package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

public class RitualDeluge extends Ritual {
	public RitualDeluge() {
		super(new ResourceLocation(Bewitchment.MODID, "deluge"), Arrays.asList(Util.get(Items.WATER_BUCKET), Util.get("treeSapling"), Util.get("coquina"), Util.get("sand"), Util.get("gemLapis"), Util.get("gemLapis"), Util.get(ModObjects.toe_of_frog)), null, Collections.singletonList(new ItemStack(Items.BUCKET)), false, 5, 250, 20, BlockGlyph.NORMAL, -1, -1);
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			world.getWorldInfo().setRaining(true);
			world.getWorldInfo().setRainTime(world.rand.nextInt(20 * 60 * 7) + (20 * 60 * 3));
		}
	}
}