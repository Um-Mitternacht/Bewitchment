package com.bewitchment.common.item.util;

import net.minecraft.item.ItemAxe;

public class ModItemAxe extends ItemAxe {
    public ModItemAxe(ToolMaterial mat) {
        super(mat, 5 + mat.getAttackDamage(), -3.1f);
    }
}