package com.bewitchment.common.potion;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockMycelium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@SuppressWarnings({"unused", "deprecation"})
public class PotionMending extends ModPotion {
	public PotionMending() {
		super("mending", false, 0xffff00);
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntityZombieVillager) {
			living.getDataManager().set(ObfuscationReflectionHelper.getPrivateValue(EntityZombieVillager.class, (EntityZombieVillager) living, "CONVERTING", "field_184739_bx"), true);
			ObfuscationReflectionHelper.setPrivateValue(EntityZombieVillager.class, (EntityZombieVillager) living, 0, "conversionTime", "field_82234_d");
		}
		if (living.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && living.getCreatureAttribute() != BewitchmentAPI.DEMON && living.getCreatureAttribute() != BewitchmentAPI.SPIRIT)
			living.heal(6 * (amplifier + 1));
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
					if ((block instanceof BlockDirt || block instanceof BlockMycelium) && !world.getBlockState(pos0.up()).isOpaqueCube()) {
						world.setBlockState(pos0, Blocks.GRASS.getDefaultState());
						flag = true;
					}
				}
			}
		}
		return flag;
	}
}