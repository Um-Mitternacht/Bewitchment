package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
public class PotionRuin extends ModPotion {
	public PotionRuin() {
		super("ruin", true, 0x606060);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		living.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (20 * 10) * (amplifier + 1), 1));
		living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (20 * 10) * (amplifier + 1), 1));
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
					if (block instanceof BlockStone) {
						world.setBlockState(pos0, Blocks.COBBLESTONE.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.COBBLESTONE) {
						world.setBlockState(pos0, Blocks.GRAVEL.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockGravel || block instanceof BlockDirt || block instanceof BlockGrass) {
						world.setBlockState(pos0, Blocks.SAND.getDefaultState());
						flag = true;
					}
					else if (block instanceof BlockFlower || block instanceof BlockTallGrass || block instanceof BlockCrops) {
						world.setBlockState(pos0, Blocks.DEADBUSH.getDefaultState());
						flag = true;
					}
				}
			}
		}
		return flag;
	}
}