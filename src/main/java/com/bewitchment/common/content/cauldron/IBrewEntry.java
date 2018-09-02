package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.cauldron.IBrewModifierList;
import net.minecraft.potion.Potion;

public interface IBrewEntry {

	public Potion getPotion();

	public IBrewModifierList getModifierList();
}