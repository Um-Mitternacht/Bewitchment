package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.common.world.BiomeChangingUtils;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Random;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards, with bits stuck on from Ael.
 */
public class RitualBiomeShift extends Ritual {
	private int biome;

	public RitualBiomeShift() {
		super(new ResourceLocation(Bewitchment.MODID, "biome_shift"), Arrays.asList(Util.get("treeSapling"), Util.get("logWood"), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.dimensional_sand)), Util.get(new ItemStack(ModObjects.boline))), null, null, true, 50, 1300, 33, BlockGlyph.ENDER, BlockGlyph.ENDER, BlockGlyph.ENDER);
	}

	@Override
	public void read() {
		super.read();
		biome = nbt.getInteger("biome");
	}

	@Override
	public void write() {
		super.write();
		nbt.setInteger("biome", biome);
	}

	@Override
	public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onStarted(world, pos, caster, inventory);

		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack.getItem() instanceof ItemBoline)
				biome = stack.getTagCompound().getInteger("biome_id");
		}
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		if (biome <= 0) {
			canceled = true;
			return;
		}

		Random rand = world.rand;
        double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;

		for (int i = 0; i < 15; i++) {
			double sx = cx + rand.nextGaussian() * 0.5, sy = cy + rand.nextGaussian() * 0.5, sz = cz + rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		int radius = 32;

		for (double x = -radius; x < radius; x++) for (double z = -radius; z < radius; z++) {

			if (Math.sqrt((x * x) + (z * z)) < radius) {
				BlockPos pos = effectivePos.add(x, 0, z);
				BiomeChangingUtils.setBiomeSync(world, pos, biome, radius);
			}
		}

		for (int i = 0; i < inventory.getSlots(); i++) inventory.extractItem(i, 1, false);
	}
}