package com.bewitchment.common.ritual;

import com.bewitchment.api.ritual.IRitualHandler;
import com.bewitchment.api.ritual.Ritual;
import com.bewitchment.common.item.ModItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class RitualConjurationWitch extends Ritual {

	public RitualConjurationWitch(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data) {
		if (!world.isRemote) {
			EntityWitch witch = new EntityWitch(world);
			witch.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), (float) (Math.random() * 360), 0);
			witch.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(witch)), (IEntityLivingData) null);
			world.spawnEntity(witch);
			if (Math.random() < 0.1)
				witch.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 2, false, false));
		}
	}

	@Override
	public NonNullList<ItemStack> getOutput(NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(data);
		NonNullList<ItemStack> oldInput = getItemsUsedForInput(data);
		Optional<ItemStack> oldAthame = oldInput.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst();
		if (oldAthame.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}

}
