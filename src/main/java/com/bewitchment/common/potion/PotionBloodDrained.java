package com.bewitchment.common.potion;

import java.util.ArrayList;
import java.util.List;

import com.bewitchment.api.capability.IBloodReserve;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class PotionBloodDrained extends Potion {
	
	private static final List<ItemStack> cure = new ArrayList<ItemStack>(0);
	public static final DamageSource DRAIN_DAMAGE = new DamageSourceDrain();
	
	public PotionBloodDrained() {
		super(true, 0x820000);
		this.setRegistryName(LibMod.MOD_ID, "blood_drain");
	}
	
	@Override
	public List<ItemStack> getCurativeItems() {
		return cure;// No healing
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		
		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		float amount = br.getPercentFilled();
		
		if (amount > 0 && amount < 0.4f) {
			entity.attackEntityFrom(DRAIN_DAMAGE, 0.5f);
			entity.addPotionEffect(new PotionEffect(this, 200, amplifier));
		} else {
			entity.removePotionEffect(this);
			br.setDrinker(null);
		}
		
	}
	
	public static class DamageSourceDrain extends DamageSource {
		
		public DamageSourceDrain() {
			super("drain_damage");
		}
		
		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entity) {
			String name = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null).getLastDrinker(entity.world);
			String s = "death.attack.drain_damage";
			if (name != null) {
				return new TextComponentTranslation(s + ".player", name);
			}
			return new TextComponentTranslation(s);
			
		}
		
	}
	
}
