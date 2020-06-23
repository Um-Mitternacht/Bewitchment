package com.bewitchment.common.item.tool;

import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class ItemJuniperKeyRing extends ItemJuniperKey {
    public ItemJuniperKeyRing() {
        super();
    }

    public static List<NBTTagCompound> getKeyTags(NBTTagCompound keyRingData) {
        List<NBTTagCompound> tags = new ArrayList<>();
        keyRingData.getTagList("Keys", Constants.NBT.TAG_COMPOUND).forEach(nbt -> {
            tags.add((NBTTagCompound) nbt);
        });
        return tags;
    }

    public static ItemStack addKeyTag(ItemStack keyRing, NBTTagCompound keyTag) {
        if (keyRing.getItem() instanceof ItemJuniperKeyRing) {
            keyRing.getTagCompound().getTagList("Keys", Constants.NBT.TAG_COMPOUND).appendTag(keyTag);
        }
        return keyRing;
    }

    public static ItemStack createKeyRing(ItemStack keyRing, Set<NBTTagCompound> keys) {
        keyRing.setTagCompound(new NBTTagCompound());
        keyRing.getTagCompound().setTag("Keys", new NBTTagList());
        keys.forEach(tag -> addKeyTag(keyRing, tag));
        return keyRing;
    }

    @Override
    public boolean canAccess(IBlockAccess world, BlockPos pos, int dimension, ItemStack stack, NBTTagCompound data) {
        return getKeyTags(data).stream().anyMatch(tag -> ((ItemJuniperKey) ModObjects.juniper_key).canAccess(world, pos, dimension, stack, tag));
    }

    @Override
    public ItemStack setTags(World world, BlockPos pos, ItemStack stack) {
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag("Keys", new NBTTagList());
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound()) {
            tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5) + ".header"));
            getKeyTags(stack.getTagCompound()).forEach(tag -> {
                if (tag.hasKey("location")) {
                    BlockPos pos = BlockPos.fromLong(tag.getLong("location"));
                    tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), pos.getX(), pos.getY(), pos.getZ(), tag.getString("dimensionName")));
                }
            });
        }
    }
}