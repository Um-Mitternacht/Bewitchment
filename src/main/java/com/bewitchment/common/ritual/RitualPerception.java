package com.bewitchment.common.ritual;

import com.bewitchment.api.ritual.IRitualHandler;
import com.bewitchment.api.ritual.Ritual;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPerception extends Ritual {

	public RitualPerception(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onUpdate(EntityPlayer player, IRitualHandler tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {
		if (!world.isRemote && ticks % 100 == 0)
			world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(20, 20, 20).expand(-20, -20, -20)).forEach(e -> {
				e.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 110, 0, false, false));
			});
	}

}
