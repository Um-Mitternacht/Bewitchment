package com.bewitchment.common.item.poppet;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class ItemVoodooPoppet extends ItemPoppet {
    public ItemVoodooPoppet() {
        super(false);
    }


    //todo: set target on fire when item burns, need to find a hook or something
//    @Override
//    public boolean onEntityItemUpdate(EntityItem entityItem) {
//        Block current = entityItem.world.getBlockState(entityItem.getPosition()).getBlock();
//        if (current == Blocks.LAVA || current == Blocks.FLOWING_LAVA || current == Blocks.FIRE || current == ModObjects.hellfire) {
//            ItemStack poppet = entityItem.getItem();
//            if (poppet.hasTagCompound() && poppet.getTagCompound().hasKey("boundId")) {
//                EntityPlayer target = Util.findPlayer(poppet.getTagCompound().getString("boundId"));
//                if (target != null) target.setFire(20);
//            }
//        }
//        return super.onEntityItemUpdate(entityItem);
//    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("boundId") && stack.getTagCompound().hasKey("boundName")) {
            EntityLivingBase target = world.getEntities(EntityLivingBase.class, e -> e != null && e.getPersistentID().toString().equals(stack.getTagCompound().getString("boundId"))).stream().findFirst().orElse(null);
            if (target != null) {
                if (player.isSneaking()) {
                    List<ItemStack> inv = Bewitchment.proxy.getEntireInventory(player);
                    for (ItemStack itemStack : inv) {
                        if (itemStack.getItem() == ModObjects.bone_needle) {
                            target.attackEntityFrom(DamageSource.MAGIC, 3);
                            itemStack.shrink(1);
                            stack.damageItem(1, player);
                            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
                        }
                    }
                }
            } else player.sendStatusMessage(new TextComponentTranslation("poppet.not_found"), true);
        }
        return super.onItemRightClick(world, player, hand);
    }
}
