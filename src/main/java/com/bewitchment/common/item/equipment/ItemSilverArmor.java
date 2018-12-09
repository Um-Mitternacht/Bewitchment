package com.bewitchment.common.item.equipment;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This class was created by BerciTheBeast on 11.4.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemSilverArmor extends ItemArmor implements IModelRegister {

	public ItemSilverArmor(String id, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndex, equipmentSlotIn);
		this.setMaxStackSize(1);
		setRegistryName(id);
		setTranslationKey(id);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.GRAY + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();

		Entity attacker = source.getTrueSource();
		if (attacker instanceof EntityLivingBase) {
			if (((EntityLivingBase) attacker).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				event.setAmount(event.getAmount() * 0.9F);
			}
			if (attacker instanceof EntityPlayer && attacker.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
				event.setAmount(event.getAmount() * 0.9F);
				EntityPlayer a = (EntityPlayer) attacker;
				a.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), MathHelper.clamp(event.getAmount() / 2, 1f, 4f));
			}
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			player.attackEntityFrom(DamageSource.MAGIC, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

}
