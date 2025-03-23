package com.bewitchment.common.item;

import com.bewitchment.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLeonardsWand extends Item {
    public ItemLeonardsWand() {
        super();
        Util.registerItem(this, "leonards_wand");
        setMaxDamage(128);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.hasTagCompound()) {
            ItemStack potion = new ItemStack(Items.SPLASH_POTION);
            List<PotionEffect> effects = PotionUtils.getEffectsFromStack(itemstack);
            PotionUtils.appendEffects(potion, effects);
            potion.getTagCompound().setInteger("CustomPotionColor", PotionUtils.getPotionColorFromEffectList(effects));
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                EntityPotion entitypotion = new EntityPotion(worldIn, playerIn, potion);
                entitypotion.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 1F, 1.0F);
                worldIn.spawnEntity(entitypotion);
            }
            playerIn.addStat(StatList.getObjectUseStats(this));
            int uses = itemstack.getTagCompound().getInteger("uses");
            if (uses - 1 <= 0) {
                itemstack.setTagCompound(null);
            } else {
                itemstack.getTagCompound().setInteger("uses", uses - 1);
            }
            itemstack.damageItem(1, playerIn);
            playerIn.getCooldownTracker().setCooldown(this, 10);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            tooltip.add(I18n.format("tooltip.leonards_wand.uses", stack.getTagCompound().getInteger("uses")));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.hasTagCompound();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
