package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionSpiderNightmare extends BrewMod {

	public PotionSpiderNightmare() {
		super("spider_nightmare", true, 0x353839, false, 20 * 60 * 3);
		this.setIconIndex(5, 1);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {

		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);
		BlockPos.getAllInBox(posI, posF).forEach(pos1 -> {
			if (pos1.distanceSq(pos.getX(), pos.getY(), pos.getZ()) < 1 + box * box / 2 && world.getBlockState(pos1).getBlock().isReplaceable(world, pos1)) {
				world.setBlockState(pos1, Blocks.WEB.getDefaultState());
			}
		});

	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % (20 + 60 * Math.max(0, 5 - amplifier)) == 0; // The higher the amplifier, the higher frequency of spawns
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		World world = entity.world;
		if (!world.isRemote) {
			BlockPos pos = entity.getPosition();
			EntityCaveSpider spider = new EntityCaveSpider(world);
			spider.setPosition(pos.getX(), pos.getY(), pos.getZ());
			world.spawnEntity(spider);
			if (entity.getRNG().nextInt(Math.max(1, 7 - amplifier)) == 0) { // The higher the amplifier, the higher the chance of decreasing the potion level
				PotionEffect pe = new PotionEffect(entity.getActivePotionEffect(this));
				entity.removePotionEffect(this);
				if (amplifier > 0) {
					entity.addPotionEffect(new PotionEffect(this, pe.getDuration(), amplifier - 1, pe.getIsAmbient(), pe.doesShowParticles()));
				}
			}
		}
	}


}
