package com.bewitchment.common.item.util;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"NullableProblems", "WeakerAccess"})
public class ModItemBauble extends Item implements IBauble {
    private final BaubleType type;

    protected ModItemBauble(String name, BaubleType type, List<Predicate<ItemStack>> predicates) {
        super();
        setMaxStackSize(1);
        this.type = type;
        Util.registerItem(this, name, predicates);
    }

    protected ModItemBauble(String name, BaubleType type) {
        this(name, type, Collections.emptyList());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
            for (int i = 0; i < handler.getSlots(); i++) {
                if (handler.getStackInSlot(i).isEmpty() && handler.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
                    handler.setStackInSlot(i, player.getHeldItem(hand).splitStack(1));
                    onEquipped(handler.getStackInSlot(i), player);
                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BINDING_CURSE;
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return type;
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase living) {
        return !EnchantmentHelper.hasBindingCurse(stack);
    }

    @Override
    public boolean willAutoSync(ItemStack stack, EntityLivingBase living) {
        return true;
    }
}