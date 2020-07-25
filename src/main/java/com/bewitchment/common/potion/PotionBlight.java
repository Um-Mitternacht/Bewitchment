package com.bewitchment.common.potion;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionBlight extends ModPotion {
	public PotionBlight() {
		super("blight", true, 0x7f4000);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntityMooshroom) Util.convertEntity((EntityLiving) living, new EntityCow(living.world));
		if (living.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && living.getCreatureAttribute() != BewitchmentAPI.DEMON && living.getCreatureAttribute() != BewitchmentAPI.SPIRIT) {
			living.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (20 * (6 * (amplifier + 1))), 1));
			living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (20 * (6 * (amplifier + 1))), 1));
		}
	}

	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = 3 * (amplifier + 1);
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				if (world.rand.nextInt(3) == 0) {
					Block block = world.getBlockState(pos0).getBlock();
					if (block instanceof BlockGrass || block instanceof BlockDirt || block instanceof BlockMycelium || block instanceof BlockFarmland || block instanceof BlockGrassPath) {
						world.setBlockState(pos0, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
						flag = true;
					} else if (block instanceof BlockLeaves || block instanceof IGrowable) {
						world.destroyBlock(pos0, true);
						flag = true;
					}
				}
			}
		}
		return flag;
	}
}