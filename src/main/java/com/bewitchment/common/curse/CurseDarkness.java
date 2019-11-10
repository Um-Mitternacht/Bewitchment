package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;
import java.util.List;

public class CurseDarkness extends Curse {
	public CurseDarkness() {
		super(new ResourceLocation(Bewitchment.MODID, "darkness"), Arrays.asList(Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.iron_gall_ink), Util.get(ModObjects.belladonna), Util.get(ModObjects.aconitum), Util.get(ModObjects.taglock)), true, false, CurseCondition.EXIST, 0.0005);
	}
	
	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
		List<EntityLiving> entities = target.world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(target.posX - 4, target.posY - 4, target.posZ - 4, target.posX + 4, target.posY + 4, target.posZ + 4));
		for (EntityLiving e : entities) e.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
		return false;
	}
}
