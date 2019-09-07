package com.bewitchment.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPoppet extends Item {
    public ItemPoppet(boolean oneTimeUse) {
        super();
        setMaxStackSize(1);
        setMaxDamage(oneTimeUse ? 1 : 50);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("boundName")) {
            String boundName = stack.getTagCompound().getString("boundName");
            tooltip.add(I18n.format("tooltip.bewitchment.poppet.bound", boundName));
        }
    }
}
