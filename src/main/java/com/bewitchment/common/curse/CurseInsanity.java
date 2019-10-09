package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class CurseInsanity extends Curse {
	public CurseInsanity() {
		super(new ResourceLocation(Bewitchment.MODID, "insanity"), Arrays.asList(Util.get(Blocks.RED_MUSHROOM), Util.get(ModObjects.belladonna), Util.get(ModObjects.elderberries), Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.spanish_moss), Util.get(ModObjects.taglock)), false, CurseCondition.RANDOM, 0.01);
	}

	@Override
	public boolean doCurse(EntityPlayer target) {
		target.world.spawnEntity(getRandomFakeMob(target.getRNG(), target));
		return true;
	}

	private EntityLiving getRandomFakeMob(Random rand, EntityPlayer target) {
		World world = target.world;
		List<Biome.SpawnListEntry> spawns = world.getBiome(target.getPosition()).getSpawnableList(EnumCreatureType.MONSTER);
		EntityLiving temp;
		if(spawns.size() <= 0) {
			temp = new EntityZombie(world);
		} else {
			try {
				temp = spawns.get(rand.nextInt(spawns.size())).entityClass.getConstructor(World.class).newInstance(world);
			} catch(Exception e) {
				temp = new EntityZombie(world);
			}
		}
		int x = (int) target.posX + rand.nextInt(6) - 5;
		int z = (int) target.posZ + rand.nextInt(6) - 5;
		temp.setPosition(x, world.getHeight(x, z), z);
		temp.setEntityInvulnerable(true);
//		List<EntityAIBase> toRemove = new ArrayList<>();
//		for (EntityAITasks.EntityAITaskEntry task : temp.tasks.taskEntries) {
//			if (task.action instanceof EntityAIAttackMelee || task.action instanceof EntityAIAttackRanged || task.action instanceof EntityAIAttackRangedBow)
//				toRemove.add(task.action);
//		}
//		for(EntityAIBase remove : toRemove) {
//			temp.tasks.removeTask(remove);
//		}
//		temp.tasks.addTask(1, new EntityAIFollow(temp, 1d, 0f, 5f));
		temp.addTag("fake");
		temp.setAttackTarget(target);
		temp.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0);
		//temp.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(UUID.fromString("cc64aa1a-727f-4750-b0cf-aabd5d8de354"), "noDamage", 0, 0));
		return temp;
	}
}
