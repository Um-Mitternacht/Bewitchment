package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/29/2020.
 */
public class EntityWerewolf extends ModEntityMob {
	//private static final ResourceLocation AFRICAN = new ResourceLocation(Bewitchment.MODID, "textures/entity/elephants/african_elephant.png");
	//private static final ResourceLocation ASIAN = new ResourceLocation(Bewitchment.MODID, "textures/entity/elephants/asian_elephant.png");
	
	protected EntityWerewolf(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/werewolf"));
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
	
	//@Override
	//public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
	//	BlockPos pos = getPosition();
	//	World world = getEntityWorld();
	//
	//	if (this.world.getBiomeForCoordsBody(pos, world.getBiome(BiomeDictionary.Type.SAVANNA))) {
	//		return (IEntityLivingData) AFRICAN;
	//	}
	//	else if (this.world.getBiomeForCoordsBody(pos, world.getBiome(BiomeDictionary.Type.JUNGLE))) {
	//		return (IEntityLivingData) ASIAN;
	//	}
	//	return data;
	//}
}
