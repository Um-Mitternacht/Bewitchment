package com.bewitchment.common.potion.potions.brews;

import java.util.HashMap;
import java.util.Map;

import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.api.cauldron.modifiers.BewitchmentModifiers;
import com.bewitchment.common.potion.BrewMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionGrassGrow extends BrewMod {
	
	private final Map<Block, IBlockState> stateMap = new HashMap<>();
	
	public PotionGrassGrow() {
		super("growth", false, 0x4CBB17, true, 0);
		stateMap.put(Blocks.MYCELIUM, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.RED_MUSHROOM, Blocks.RED_FLOWER.getDefaultState());
		stateMap.put(Blocks.BROWN_MUSHROOM, Blocks.TALLGRASS.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.DIRT.getDefaultState());
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		if (entity instanceof EntityMooshroom) {
			entity.setDead();
			EntityCow entitycow = new EntityCow(entity.world);
			entitycow.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			entitycow.setHealth(entity.getHealth());
			entitycow.renderYawOffset = entity.renderYawOffset;
			if (entity.hasCustomName()) {
				entitycow.setCustomNameTag(entity.getCustomNameTag());
			}
			entity.world.spawnEntity(entitycow);
		}
	}
	
	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(BewitchmentModifiers.RADIUS).orElse(0);
		int ampl = modifiers.getLevel(BewitchmentModifiers.POWER).orElse(0);
		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);
		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			boolean place = world.rand.nextInt(7) <= ampl;
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			}
			if (place && block == Blocks.TALLGRASS && world.getBlockState(spot).getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.DEAD_BUSH) {
				world.setBlockState(spot, Blocks.YELLOW_FLOWER.getDefaultState(), 3);
			}
		}
	}
	
}
