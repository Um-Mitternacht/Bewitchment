package com.bewitchment.common.item.food;

import com.bewitchment.Util;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class ItemJuniperTea extends ItemFood {
    public ItemJuniperTea() {
        super(6, 0.55f, false);
        Util.registerItem(this, "juniper_tea");
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);
        this.setPotionEffect(new PotionEffect(ModPotions.absence, 1, 0), 0.2F);
        player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
    }
}