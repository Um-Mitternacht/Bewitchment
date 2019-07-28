package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualSolarGlory extends Ritual {
	public RitualSolarGlory() {
		super(new ResourceLocation(Bewitchment.MODID, "solar_glory"), Arrays.asList(Util.get("ingotGold"), Util.get(new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta())), Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, Short.MAX_VALUE), new ItemStack(Blocks.RED_FLOWER, 1, Short.MAX_VALUE)), Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, Short.MAX_VALUE), new ItemStack(Blocks.RED_FLOWER, 1, Short.MAX_VALUE))), null, null, false, 5, 250, 20, BlockGlyph.NORMAL, -1, -1);
	}
	
	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.no_day";
	}
	
	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isDaytime() && !BewitchmentAPI.isVampire(caster);
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) world.setWorldTime(world.getWorldTime() + (30000 - (world.getWorldTime() % 24000)) % 24000);
	}
}