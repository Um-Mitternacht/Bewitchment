package com.bewitchment.common.item.tool;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.core.ModelResourceLocations;
import com.bewitchment.common.core.helper.MobHelper;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompatBridge;
import com.bewitchment.common.item.ModMaterials;
import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.client.model.ModelLoader;
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
		setRegistryName(LibItemName.COLD_IRON_SWORD);
		setTranslationKey(LibMod.MOD_ID + "." + LibItemName.COLD_IRON_SWORD);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.bewitchment." + getToolMaterialName() + "_tool" + "_description.name"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelBakery.registerItemVariants(this,
				ModelResourceLocations.COLD_IRON_SWORD_NORMAL,
				ModelResourceLocations.HUDSON_BAT);
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				if (stack.getDisplayName().equalsIgnoreCase("Hudson Bat") || stack.getDisplayName().equalsIgnoreCase("Masashi Bat") || stack.getDisplayName().equalsIgnoreCase("Emmanuel Bat") || stack.getDisplayName().equalsIgnoreCase("Michael Bat") || stack.getDisplayName().equalsIgnoreCase("Yoshihiro Bat") || stack.getDisplayName().equalsIgnoreCase("Lewis Bat") || stack.getDisplayName().equalsIgnoreCase("Katushiro Bat") || stack.getDisplayName().equalsIgnoreCase("Ashley Bat")) {
					return ModelResourceLocations.HUDSON_BAT;
				}
				return ModelResourceLocations.COLD_IRON_SWORD_NORMAL;
			}
		});
	}
}