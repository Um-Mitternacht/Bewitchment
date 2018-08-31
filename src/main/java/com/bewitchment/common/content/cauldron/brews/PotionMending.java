package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PotionMending extends BrewMod {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	private Method startConverting;

	public PotionMending() {
		super("mending", false, 0x4CBB17, true, 0);
		stateMap.put(Blocks.MYCELIUM, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.GRASS.getDefaultState());
		stateMap.put(Blocks.RED_MUSHROOM, Blocks.RED_FLOWER.getDefaultState());
		stateMap.put(Blocks.BROWN_MUSHROOM, Blocks.TALLGRASS.getDefaultState());
		stateMap.put(Blocks.SAND, Blocks.DIRT.getDefaultState());
		try {// TODO test in a compiled environment
			startConverting = ReflectionHelper.findMethod(EntityZombieVillager.class, "startConverting", "func_191991_a", UUID.class, int.class);
		} catch (Exception e) {
			throw new LoaderException("[Bewitchment] Failed to find startConverting method in class EntityZombieVillager for PotionMending");
		}
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
		} else if (entity instanceof EntityZombieVillager) {
			UUID starterUUID = null;
			if (source instanceof EntityPlayer) {
				starterUUID = EntityPlayer.getUUID(((EntityPlayer) source).getGameProfile());
			} else if (indirectSource instanceof EntityPlayer) {
				starterUUID = EntityPlayer.getUUID(((EntityPlayer) indirectSource).getGameProfile());
			}
			try {
				startConverting.invoke(entity, starterUUID, 200);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException("Failed to reflect method", e);
			}
		} else {
			entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
		}
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + 3 * modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int ampl = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		BlockPos posI = pos.add(box, box / 3, box);
		BlockPos posF = pos.add(-box, -box / 3, -box);
		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			boolean place = world.rand.nextInt(6) <= ampl;
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			}
			if (place && block == Blocks.TALLGRASS && world.getBlockState(spot).getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.DEAD_BUSH) {
				world.setBlockState(spot, Blocks.YELLOW_FLOWER.getDefaultState(), 3);
			}
		}
	}

}
