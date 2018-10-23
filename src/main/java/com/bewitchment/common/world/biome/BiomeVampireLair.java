package com.bewitchment.common.world.biome;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class BiomeVampireLair extends Biome {

	public BiomeVampireLair() {
		super(getNewPropertiesInstance());
		this.setRegistryName(LibMod.MOD_ID, "vampire_lair");
	}

	public static BiomeProperties getNewPropertiesInstance() {
		BiomeProperties bp = new BiomeProperties("Vampire Lair");
		bp.setRainDisabled();
		bp.setWaterColor(0x220033);
		return bp;
	}

	@Override
	public float getSpawningChance() {
		return 0f;
	}

	@Override
	public boolean canRain() {
		return false;
	}

	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0x220033;
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return getModdedBiomeFoliageColor(0x1a2d00);
	}

	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return this.getFoliageColorAtPos(pos);
	}

}
