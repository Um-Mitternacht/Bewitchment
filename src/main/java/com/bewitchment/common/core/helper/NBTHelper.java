package com.bewitchment.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

/**
 * This class was created by Arekkuusu on 02/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class NBTHelper {

	private NBTHelper() {
	}

	public static void setByte(ItemStack stack, String tag, byte i) {
		fixNBT(stack).setByte(tag, i);
	}

	public static NBTTagCompound fixNBT(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null) {
			tagCompound = new NBTTagCompound();
			stack.setTagCompound(tagCompound);
		}
		return tagCompound;
	}

	public static void setInteger(ItemStack stack, String tag, int i) {
		fixNBT(stack).setInteger(tag, i);
	}

	public static void setFloat(ItemStack stack, String tag, float i) {
		fixNBT(stack).setFloat(tag, i);
	}

	public static void setBoolean(ItemStack stack, String tag, boolean i) {
		fixNBT(stack).setBoolean(tag, i);
	}

	public static void setString(ItemStack stack, String tag, String i) {
		fixNBT(stack).setString(tag, i);
	}

	public static void setUniqueID(ItemStack stack, String tag, UUID i) {
		fixNBT(stack).setUniqueId(tag, i);
	}

	public static <T extends NBTBase> void setNBT(ItemStack stack, String tag, T base) {
		fixNBT(stack).setTag(tag, base);
	}

	public static byte getByte(ItemStack stack, String tag) {
		return fixNBT(stack).getByte(tag);
	}

	public static int getInteger(ItemStack stack, String tag) {
		return fixNBT(stack).getInteger(tag);
	}

	public static float getFloat(ItemStack stack, String tag) {
		return fixNBT(stack).getFloat(tag);
	}

	public static boolean getBoolean(ItemStack stack, String tag) {
		return fixNBT(stack).getBoolean(tag);
	}

	public static String getString(ItemStack stack, String tag) {
		return fixNBT(stack).getString(tag);
	}

	public static UUID getUniqueID(ItemStack stack, String tag) {
		return fixNBT(stack).getUniqueId(tag);
	}

	@SuppressWarnings("unchecked")
	public static <T extends NBTBase> T getNBT(ItemStack stack, String tag) {
		return (T) fixNBT(stack).getTag(tag);
	}

	public static <T extends Entity> Optional<T> getEntityByUUID(Class<T> clazz, UUID uuid, World world) {
		for (Entity entity : world.loadedEntityList) {
			if (clazz.isInstance(entity) && entity.getUniqueID().equals(uuid)) return Optional.of(clazz.cast(entity));
		}

		return Optional.empty();
	}

	public static boolean hasTag(ItemStack stack, String tag, int type) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		return tagCompound != null && tagCompound.hasKey(tag, type);
	}

	public static boolean hasTag(ItemStack stack, String tag) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		return tagCompound != null && tagCompound.hasKey(tag);
	}

	public static void removeTag(ItemStack stack, String tag) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound != null && tagCompound.hasKey(tag)) {
			tagCompound.removeTag(tag);
		}
	}
}
