package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.spirit.demon.EntityDruden;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualCallOfTheWild extends Ritual {
	public RitualCallOfTheWild() {
		super(new ResourceLocation(Bewitchment.MODID, "call_of_the_wild"), Arrays.asList(Util.get(ModObjects.oak_spirit), Util.get(ModObjects.birch_soul), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1)), Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1))), null, null, 10, 780, 30, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1);
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(3) + 1; i++) {
				EntityLiving entity;
				int rand = world.rand.nextInt(18);
				if (rand == 0) entity = new EntityLizard(world);
				else if (rand == 1) entity = new EntityLizard(world);
				else if (rand == 2) entity = new EntityOwl(world);
				else if (rand == 3) entity = new EntityRaven(world);
				else if (rand == 4) entity = new EntitySnake(world);
				else if (rand == 5) entity = new EntityOcelot(world);
				else if (rand == 6) entity = new EntityWolf(world);
				else if (rand == 7) entity = new EntityParrot(world);
				else if (rand == 8) entity = new EntityDruden(world);
				else if (rand == 9) entity = new EntityWerewolf(world);
				else if (rand == 10) entity = new EntityHorse(world);
				else if (rand == 11) entity = new EntityLlama(world);
				else if (rand == 12) entity = new EntityDonkey(world);
                else if (rand == 13) entity = new EntitySheep(world);
				else if (rand == 14) entity = new EntityCow(world);
				else if (rand == 15) entity = new EntityPig(world);
				else if (rand == 16) entity = new EntityChicken(world);
				else entity = new EntityToad(world);
				entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
				boolean valid = false;
				for (int j = 0; j < 16; j++) {
					if (entity.attemptTeleport(effectivePos.getX() + world.rand.nextInt(12) - 6, effectivePos.getY(), effectivePos.getZ() + world.rand.nextInt(12) - 6)) {
						entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextInt(360), 0);
						valid = true;
						break;
					}
				}
				if (valid) {
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
						CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
				}
			}
		}
	}
	
	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 10; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.CLOUD, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}
}