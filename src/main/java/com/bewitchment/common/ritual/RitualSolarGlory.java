package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualSolarGlory extends Ritual {
	public RitualSolarGlory() {
		super(new ResourceLocation(Bewitchment.MODID, "solar_glory"), Arrays.asList(Util.get("ingotGold"), Util.get(Items.NETHERBRICK), Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, BlockFlower.EnumFlowerType.DANDELION.getMeta()))), null, null, 5, 500, 20, BlockGlyph.NORMAL, -1, -1);
	}
	
	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isDaytime() && !BewitchmentAPI.isVampire(caster);
	}
	
	@Override
	public void onFinished(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, pos, caster, inventory);
		if (!world.isRemote) world.setWorldTime(world.getWorldTime() + (30000 - (world.getWorldTime() % 24000)) % 24000);
	}
}