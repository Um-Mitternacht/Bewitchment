package com.bewitchment.common.potion.util;

import com.bewitchment.Bewitchment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModPotion extends Potion {
	protected ModPotion(String name, boolean isNegative, int color) {
		super(isNegative, color);
		setRegistryName(new ResourceLocation(Bewitchment.MODID, name));
		setPotionName(getRegistryName().toString().replace(":", "."));
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		onImpact(living.world, living.getPosition(), amplifier);
	}
	
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		return false;
	}
}