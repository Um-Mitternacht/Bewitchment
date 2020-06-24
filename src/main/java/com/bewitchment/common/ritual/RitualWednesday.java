package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.living.EntityToad;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Date;

public class RitualWednesday extends Ritual {
	public RitualWednesday() {
		super(new ResourceLocation(Bewitchment.MODID, "wednesday"), Arrays.asList(Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.mandrake_root), Util.get("slimeball"), Util.get(ModObjects.liquid_witchcraft)), null, null, 10, 666, 66, BlockGlyph.NORMAL, BlockGlyph.ENDER, -1);
	}

	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.wednesday";
	}

	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return new Date().toString().toLowerCase().contains("wed");
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(128) + 64; i++) {
				EntityLiving entity = new EntityToad(world);
				entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
				boolean valid = false;
				for (int j = 0; j < 16; j++) {
					if (entity.attemptTeleport(effectivePos.getX() + (world.rand.nextInt(12) - 6), effectivePos.getY(), effectivePos.getZ() + (world.rand.nextInt(12) - 6))) {
						entity.setLocationAndAngles(entity.posX, entity.posY + 2 + world.rand.nextInt(6), entity.posZ, world.rand.nextInt(360), 0);
						valid = true;
						break;
					}
				}
				if (valid) {
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
						CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
					entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 9));
					entity.addPotionEffect(new PotionEffect(ModPotions.wednesday, (20 * 3) + world.rand.nextInt(100)));
					entity.motionX += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
					entity.motionY += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
					entity.motionZ += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
				}
			}
		}
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 10; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.SLIME, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
		if (world.getTotalWorldTime() % 10 == 0) {
			if (world.rand.nextInt(6) == 0) {
				EntityLiving entity = new EntityToad(world);
				entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
				boolean valid = false;
				for (int j = 0; j < 16; j++) {
					if (entity.attemptTeleport(effectivePos.getX() + (world.rand.nextInt(12) - 6), effectivePos.getY(), effectivePos.getZ() + (world.rand.nextInt(12) - 6))) {
						entity.setLocationAndAngles(entity.posX, entity.posY + 2 + world.rand.nextInt(6), entity.posZ, world.rand.nextInt(360), 0);
						valid = true;
						break;
					}
				}
				if (valid) {
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
						CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
					entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 9));
					entity.addPotionEffect(new PotionEffect(ModPotions.wednesday, (20 * 3) + world.rand.nextInt(100)));
					entity.motionX += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
					entity.motionY += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
					entity.motionZ += ((world.rand.nextFloat() * 2 - world.rand.nextFloat()) - world.rand.nextFloat()) / 2;
				}
			}
		}
	}
}