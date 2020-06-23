package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RitualCurseCleansing extends Ritual {
	private static final List<Ingredient> lesser_input = Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.white_sage), Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.birch_soul), Util.get(Items.WATER_BUCKET));
	private static final List<Ingredient> greater_input = Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.white_sage), Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.birch_soul), Util.get(Items.WATER_BUCKET), Util.get("cropGarlic"), Util.get("ingotSilver"));
	private final boolean lesser;
	
	public RitualCurseCleansing(boolean lesser) {
		super(new ResourceLocation(Bewitchment.MODID, (lesser ? "lesser" : "greater") + "_curse_cleansing"), lesser ? lesser_input : greater_input, null, Collections.singletonList(new ItemStack(Items.BUCKET)), 15, lesser ? 500 : 1000, 10, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1);
		this.lesser = lesser;
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (caster.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
			ExtendedPlayer ep = caster.getCapability(ExtendedPlayer.CAPABILITY, null);
			List<Curse> curses = ep.getCurses().stream().filter(p -> !p.isPositive()).filter(p -> {
				if (lesser) return p.isLesser();
				return true;
			}).collect(Collectors.toList());
			for (Curse curse : curses) {
				if (caster.getRNG().nextDouble() < (lesser ? 0.6 : 0.9)) ep.removeCurse(curse);
			}
		}
	}
}
