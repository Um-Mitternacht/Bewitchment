package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;

@SuppressWarnings({"unused"})
public class PotionFertility extends ModPotion {
	public PotionFertility() {
		super("fertility", false, 0x40c000);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntityAnimal) ((EntityAnimal) living).setInLove(null);
	}

	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = amplifier + 1;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				if (world.getBlockState(pos0).getBlock() instanceof IGrowable) {
					IGrowable growable = ((IGrowable) world.getBlockState(pos0).getBlock());
					if (growable.canGrow(world, pos0, world.getBlockState(pos0), true)) {
						growable.grow(world, world.rand, pos0, world.getBlockState(pos0));
						flag = true;
					}
				}
			}
		}
		return flag;
	}
}