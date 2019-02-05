package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.item.ModItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public class RitualConjurationWitch extends RitualImpl {

	public RitualConjurationWitch(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (!world.isRemote) {
			EntityWitch witch = new EntityWitch(world);
			witch.setLocationAndAngles(effectivePosition.getX(), effectivePosition.getY(), effectivePosition.getZ(), (float) (Math.random() * 360), 0);
			witch.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(witch)), (IEntityLivingData) null);
			world.spawnEntity(witch);
			if (Math.random() < 0.1) {
				witch.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 2, false, false));
			}
		}
	}

	@Override
	public void onRandomDisplayTick(World world, BlockPos mainGlyphPos, BlockPos ep, Random rng) {
		for (int i = 0; i < 20; i++) {
			double cx = ep.getX() + 0.5;
			double cy = ep.getY() + 0.5;
			double cz = ep.getZ() + 0.5;
			double sx = cx + rng.nextGaussian() * 0.5;
			double sy = cy + rng.nextGaussian() * 0.5;
			double sz = cz + rng.nextGaussian() * 0.5;
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
		}
	}

	@Override
	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(input, data);
		Optional<ItemStack> oldAthame = input.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst();
		if (oldAthame.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}

}
