package com.bewitchment.common.item.equipment;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by Joseph on 1/1/2018.
 */
//NVM i feel better lul
public class ItemNazar extends ItemMod implements IBauble {
	public ItemNazar() {
		super(LibItemName.NAZAR);
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
		return BaubleType.AMULET;
	}

	@Override
	public boolean effectOnDamage(LivingHurtEvent event) {
		if (event.getSource().isMagicDamage()) {
			event.setCanceled(true);
		}
		return true;
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		//TODO
	}
}
