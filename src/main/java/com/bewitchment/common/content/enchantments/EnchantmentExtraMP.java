package com.bewitchment.common.content.enchantments;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class EnchantmentExtraMP extends BaublesEnchantment {

	private static final ResourceLocation expander_name = new ResourceLocation(LibMod.MOD_ID, "enchantment_extra_mp");

	protected EnchantmentExtraMP() {
		super("extra_mp", Rarity.RARE, 3);
	}

	@Override
	public void onEquipped(EntityPlayer player) {
		updateExpansion(player);
	}

	@Override
	public void onUnequipped(EntityPlayer player) {
		updateExpansion(player);
	}

	public void updateExpansion(EntityPlayer player) {
		int currentLevel = this.getMaxLevelOnPlayer(player);
		BewitchmentAPI.getAPI().removeMPExpansion(expander_name, player);
		if (currentLevel > 0) {
			BewitchmentAPI.getAPI().expandPlayerMP(new EnchantmentExpander(currentLevel), player);
		}
	}

	public static class EnchantmentExpander implements IMagicPowerExpander {

		private int amount;

		public EnchantmentExpander(int level) {
			amount = (2 << amount) * 100;
		}

		@Override
		public ResourceLocation getID() {
			return expander_name;
		}

		@Override
		public int getExtraAmount(EntityPlayer p) {
			return amount;
		}

	}
}
