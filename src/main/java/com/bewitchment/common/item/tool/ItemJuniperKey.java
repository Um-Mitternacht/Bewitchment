package com.bewitchment.common.item.tool;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.event.LockCheckEvent;
import com.bewitchment.common.block.BlockJuniperChest;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class ItemJuniperKey extends Item {
    public ItemJuniperKey() {
        super();
    }

    private static ItemStack setTagCompound(World world, BlockPos pos, ItemStack stack) {
        if (stack.getItem() instanceof ItemJuniperKey && !stack.hasTagCompound() || !stack.getTagCompound().hasKey("location")) {
            return ((ItemJuniperKey) stack.getItem()).setTags(world, pos, stack);
        }
        return stack;
    }

    private static boolean canAccess(IBlockAccess world, BlockPos pos, int dimension, ItemStack stack) {
        if (stack.getItem() instanceof ItemJuniperKey && stack.hasTagCompound()) {
            return ((ItemJuniperKey) stack.getItem()).canAccess(world, pos, dimension, stack, stack.getTagCompound());
        }
        return false;
    }

    public static boolean checkAccess(World world, BlockPos pos, EntityPlayer player, boolean direct) {
        LockCheckEvent pre = new LockCheckEvent(player, pos);
        boolean result = false;
        MinecraftForge.EVENT_BUS.post(pre);
        if (pre.isCanceled()) return true;
        for (ItemStack stack : Bewitchment.proxy.getEntireInventory(pre.getUser())) {
            LockCheckEvent.KeyCheckEvent keyCheck = new LockCheckEvent.KeyCheckEvent(pre.getUser(), pre.getLock(), stack);
            player = keyCheck.getUser();
            pos = keyCheck.getLock();
            MinecraftForge.EVENT_BUS.post(keyCheck);
            if (keyCheck.isCanceled()) {
                result = true;
                break;
            }
            if (canAccess(world, keyCheck.getLock(), world.provider.getDimension(), keyCheck.getKey())) {
                result = true;
                break;
            }
        }

        LockCheckEvent.LockCheckedEvent post = new LockCheckEvent.LockCheckedEvent(player, pos, result, direct);
        MinecraftForge.EVENT_BUS.post(post);
        if (!world.isRemote && !post.isOpened() && post.shouldSendMessage()) {
            player.sendStatusMessage(new TextComponentTranslation("juniper_key.invalid"), true);
        }
        return post.isOpened();
    }

    public boolean canAccess(IBlockAccess world, BlockPos pos, int dimension, ItemStack stack, NBTTagCompound data) {
        if (data.getInteger("dimension") == dimension) {
            if (world.getBlockState(pos).getBlock() == ModObjects.juniper_door.door && world.getBlockState(pos.down()).getBlock() == ModObjects.juniper_door.door)
                return canAccess(world, pos.down(), dimension, stack);
            return data.getLong("location") == pos.toLong();
        }
        return false;
    }

    public ItemStack setTags(World world, BlockPos pos, ItemStack stack) {
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setLong("location", pos.toLong());
        stack.getTagCompound().setInteger("dimension", world.provider.getDimension());
        stack.getTagCompound().setString("dimensionName", world.provider.getDimensionType().getName());
        return stack;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isCreative() && (world.getBlockState(pos).getBlock() instanceof BlockJuniperChest || world.getBlockState(pos).getBlock() == ModObjects.juniper_door.door)) {
            setTagCompound(world, pos, stack);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
            BlockPos pos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
            tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), pos.getX(), pos.getY(), pos.getZ(), stack.getTagCompound().getString("dimensionName")));
        }
    }
}