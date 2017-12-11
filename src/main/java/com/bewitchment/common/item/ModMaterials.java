package com.bewitchment.common.item;

import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

/**
 * This class was created by Arekkuusu on 04/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class ModMaterials {

	public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial(LibItemName.SILVER, 1, 200, 3.5F, 2.5F, 24);
	public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial(LibItemName.SILVER, LibMod.MOD_ID + ":" + LibItemName.SILVER, 24, new int[]{2, 9, 4, 2}, 22, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.5F);

	public static final Item.ToolMaterial TOOL_COLD_IRON = EnumHelper.addToolMaterial(LibItemName.COLD_IRON, 2, 450, 3.5F, 3.0F, 12);
	public static final ItemArmor.ArmorMaterial ARMOR_COLD_IRON = EnumHelper.addArmorMaterial(LibItemName.COLD_IRON, LibMod.MOD_ID + ":" + LibItemName.COLD_IRON, 9, new int[]{3, 6, 7, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.5F);

	public static final Item.ToolMaterial TOOL_RITUAL = EnumHelper.addToolMaterial(LibItemName.RITUAL, 2, 300, 2F, 1.5F, 30);

	private ModMaterials() {
	}
}
