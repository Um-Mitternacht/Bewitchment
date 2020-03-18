package com.bewitchment.common.item.tool;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.event.LockCheckEvent;
import com.bewitchment.common.block.BlockJuniperChest;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class ItemJuniperKeyRing extends ItemJuniperKey {
	public ItemJuniperKeyRing() {
		super();
	}

	@Override
	public boolean canAccess(IBlockAccess world, BlockPos pos, int dimension, ItemStack stack, NBTTagCompound data) {
		return getKeyTags(data).stream().anyMatch(tag -> ((ItemJuniperKey) ModObjects.juniper_key).canAccess(world, pos, dimension, stack, tag));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		//if (stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
	//		BlockPos pos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
//			tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), pos.getX(), pos.getY(), pos.getZ(), stack.getTagCompound().getString("dimensionName")));
//		}
	}

	@Override
	public ItemStack setTags(World world, BlockPos pos, ItemStack stack){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("Keys", new NBTTagList());
		return stack;
	}

	public static List<NBTTagCompound> getKeyTags(NBTTagCompound keyRingData){
		List<NBTTagCompound> tags = new ArrayList<>();
		keyRingData.getTagList("Keys", Constants.NBT.TAG_LIST).forEach(nbt -> {
			tags.add((NBTTagCompound) nbt);
		});
		return tags;
	}

	public static ItemStack addKeyTag(ItemStack keyRing, NBTTagCompound keyTag){
		if (keyRing.getItem() instanceof ItemJuniperKeyRing){
			keyRing.getTagCompound().getTagList("Keys", Constants.NBT.TAG_LIST).appendTag(keyTag);
		}
		return keyRing;
	}

	public static ItemStack createKeyRing(ItemStack keyRing, List<NBTTagCompound> keys){
		keyRing.setTagCompound(new NBTTagCompound());
		keyRing.getTagCompound().setTag("Keys", new NBTTagList());
		keys.forEach(tag -> addKeyTag(keyRing, tag));
		return keyRing;
	}
}