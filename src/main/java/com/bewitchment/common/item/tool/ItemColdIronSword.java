package com.bewitchment.common.item.tool;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.helper.MobHelper;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompatBridge;
import com.bewitchment.common.item.ModMaterials;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Joseph on 6/14/2018.
 */
public class ItemColdIronSword extends ItemSword implements IModelRegister {

	public ItemColdIronSword() {
		super(ModMaterials.TOOL_COLD_IRON);
		this.setMaxStackSize(1);
		setRegistryName(LibItemName.COLD_IRON_SWORD);
		setTranslationKey(LibItemName.COLD_IRON_SWORD);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
		if (!target.world.isRemote) {
			if ((target.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON || target.getCreatureAttribute() == BewitchmentAPI.getAPI().SPIRIT || target instanceof EntityEnderman || target instanceof EntityBlaze || target instanceof EntityVex || target instanceof EntityEndermite || target instanceof EntityGhast || target instanceof EntityWither || target instanceof EntityGuardian || MobHelper.isSpirit(target) || MobHelper.isDemon(target) || ThaumcraftCompatBridge.isTCSpiritMob(target)) && attacker instanceof EntityPlayer) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 12);
				stack.damageItem(5, attacker);
			} else {
				stack.damageItem(1, attacker);
			}
		}

		return true;
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.GRAY + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}