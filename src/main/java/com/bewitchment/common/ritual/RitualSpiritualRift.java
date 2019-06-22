package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualSpiritualRift extends Ritual {
	public RitualSpiritualRift() {
		super(new ResourceLocation(Bewitchment.MODID, "spiritual_rift"), Arrays.asList(Util.get("cropWormwood"), Util.get(ModObjects.bone_needle), Util.get(Items.ROTTEN_FLESH), Util.get(ModObjects.ebb_of_death), Util.get("bone")), null, null, 15, 1500, 66, BlockGlyph.ENDER, BlockGlyph.ENDER, -1);
	}
	
	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isDaytime();
	}
	
	@Override
	public void onFinished(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, pos, caster, inventory);
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(4) + 2; i++) {
				EntityLiving entity;
				entity = world.rand.nextBoolean() ? new EntityVex(world) : world.rand.nextBoolean() ? new EntityBlackDog(world) : world.rand.nextBoolean() ? new EntitySkeleton(world) : new EntityZombie(world);
				entity.onInitialSpawn(world.getDifficultyForLocation(pos), null);
				boolean valid = false;
				for (int j = 0; j < 16; j++) {
					if (entity.attemptTeleport(pos.getX() + world.rand.nextInt(12) - 6, pos.getY(), pos.getZ() + world.rand.nextInt(12) - 6)) {
						entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextInt(360), 0);
						valid = true;
						break;
					}
				}
				if (valid) {
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50))) CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onClientUpdate(World world, BlockPos pos) {
		for (int i = 0; i < 5; i++) {
			double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
		}
	}
}