package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemBottledHellfire extends Item {
    public ItemBottledHellfire() {
        super();
        Util.registerItem(this, "bottled_hellfire");
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
        if (player.canPlayerEdit(pos0, face, player.getHeldItem(hand)) && world.mayPlace(world.getBlockState(pos0).getBlock(), pos0, false, face, player) && world.getBlockState(pos0.down()).isOpaqueCube()) {
            Util.replaceAndConsumeItem(player, hand, new ItemStack(Items.GLASS_BOTTLE));
            world.setBlockState(pos0, ModObjects.hellfire.getDefaultState());
            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}
