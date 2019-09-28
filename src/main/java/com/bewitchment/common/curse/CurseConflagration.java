package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class CurseConflagration extends Curse {
	
	public CurseConflagration() {
		super(new ResourceLocation(Bewitchment.MODID, "conflagration"), Arrays.asList(Util.get(ModObjects.fiery_unguent), Util.get("cropHellebore"), Util.get(ModObjects.bottled_hellfire), Util.get(new ItemStack(Items.COAL, 1, 1)), Util.get(ModObjects.taglock)), true, CurseCondition.EXIST);
	}
	//replace diamond with charcoal, which is coal with  meta 1
	
	@Override
	public boolean doCurse(EntityPlayer player) {
		if (player.ticksExisted % 20 == 0) {
			player.addPotionEffect(new PotionEffect(ModPotions.hellfire, 200, 0, false, false));
		}
		return true;
	}
}
