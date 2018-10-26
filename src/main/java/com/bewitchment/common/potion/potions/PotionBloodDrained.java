package com.bewitchment.common.potion.potions;

import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.helper.PlayerHelper;
import com.bewitchment.common.potion.PotionMod;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PotionBloodDrained extends PotionMod {

	public static final float TRESHOLD = 0.2f;
	private static final List<ItemStack> cure = new ArrayList<ItemStack>(0);

	public PotionBloodDrained() {
		super("blood_drain", true, 0x820000, false);
		this.setIconIndex(1, 0);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return cure;// No healing
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (!entity.world.isRemote) {
			IBloodReserve reserve = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
			if (reserve.getPercentFilled() < TRESHOLD) {
				if (entity instanceof EntityLiving) {
					handleNPCEffect((EntityLiving) entity, reserve);
				}
				entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, amplifier, true, false));
				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, amplifier, true, false));
				entity.addPotionEffect(new PotionEffect(this, 200, amplifier));
			} else {
				entity.removePotionEffect(this);
			}
		}
	}

	private void handleNPCEffect(EntityLiving entity, IBloodReserve reserve) {
		if (reserve.getDrinkerUUID() != null) {
			EntityPlayer vampire = PlayerHelper.getPlayerAcrossDimensions(reserve.getDrinkerUUID());
			if (vampire != null) {
				entity.setRevengeTarget(vampire);
			}
		}
	}
}
