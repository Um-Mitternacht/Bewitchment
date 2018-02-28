package com.bewitchment.common.potion;

import com.bewitchment.api.capability.transformations.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.List;

public class PotionBloodDrained extends PotionMod {

	public static final float TRESHOLD = 0.2f;
	private static final List<ItemStack> cure = new ArrayList<ItemStack>(0);

	public PotionBloodDrained() {
		super("blood_drain", true, 0x820000);
		this.setIconIndex(1, 0);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return cure;// No healing
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 80 == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {

		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		float amount = br.getPercentFilled();

		if (amount > 0 && amount < TRESHOLD) {
			entity.attackEntityFrom(new DamageSourceDrain(entity.world.getPlayerEntityByUUID(br.getDrinkerUUID())), 0.5f);
			if (br.getDrinkerUUID() != null)
				entity.setRevengeTarget(entity.world.getPlayerEntityByUUID(br.getDrinkerUUID()));
			entity.addPotionEffect(new PotionEffect(this, 200, amplifier));
		} else {
			entity.removePotionEffect(this);
			br.setDrinker(null);
		}

	}

	public static class DamageSourceDrain extends EntityDamageSource {

		public DamageSourceDrain(Entity damageSourceEntity) {
			super("drain_damage", damageSourceEntity);
		}

		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entity) {
			String name = getTrueSource() == null ? entity.getCapability(CapabilityBloodReserve.CAPABILITY, null).getLastDrinker(entity.world) : getTrueSource().getName();
			String s = "death.attack.drain_damage";
			if (name != null) {
				return new TextComponentTranslation(s + ".player", name);
			}
			return new TextComponentTranslation(s);
		}

	}

}
