package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Joseph on 5/24/2019.
 */

//Todo: Everything.
public class ItemGirdleOfTheDryads extends ModItemBauble {
	protected ItemGirdleOfTheDryads(String name, BaubleType type) {
		super("girdle_of_the_dryads", BaubleType.BELT);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.BLOCK_WOOD_STEP, .75F, 1.9f);
	}
}
