package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Joseph on 1/1/2018.
 */
public class ItemRemedyTalisman extends ItemMod implements IBauble {

	public ItemRemedyTalisman() {
		super(LibItemName.REMEDY_TALISMAN);
		this.setMaxStackSize(1);
		this.setMaxDamage(8);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for (int i = 0; i < baubles.getSlots(); i++)
				if (baubles.getStackInSlot(i).isEmpty() && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
						player.setHeldItem(hand, ItemStack.EMPTY);
					}
					onEquipped(player.getHeldItem(hand), player);
					break;
				}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
		return BaubleType.TRINKET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		// Check the condition that is easier to fail first, so it's more performant
		if (player instanceof EntityPlayer && player.ticksExisted % 40 == 0) {
			EntityPlayer p = (EntityPlayer) player;
			boolean flag = p.getActivePotionEffect(MobEffects.POISON) != null || p.getActivePotionEffect(MobEffects.NAUSEA) != null || p.getActivePotionEffect(MobEffects.WITHER) != null || p.getActivePotionEffect(MobEffects.BLINDNESS) != null || p.getActivePotionEffect(MobEffects.WEAKNESS) != null;
			// by putting "flag" first (in the AND operation) we don't drain MP if there was nothing to cure
			// due to java lazy evaluation
			if (flag && player.getCapability(IMagicPowerContainer.CAPABILITY, null).drain(50)) {
				p.removePotionEffect(MobEffects.NAUSEA);
				p.removePotionEffect(MobEffects.WITHER);
				p.removePotionEffect(MobEffects.BLINDNESS);
				p.removePotionEffect(MobEffects.POISON);
				p.removePotionEffect(MobEffects.WEAKNESS);
				itemstack.damageItem(1, player); // this also takes care of the breaking
			}
		}
	}

	@Override
	public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.AQUA + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}
}
