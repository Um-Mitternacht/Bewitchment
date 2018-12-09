package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
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
 * Created by Joseph on 1/1/2018.
 */
public class ItemHellishBauble extends ItemMod implements IBauble, IMagicPowerExpander {
	public ItemHellishBauble() {
		super(LibItemName.HELLISH_BAUBLE);
		this.setMaxStackSize(1);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private static boolean isDemon(Entity e) {
		if (e instanceof EntityLivingBase) {
			if (((EntityLivingBase) e).getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON) {
				return true;
			}
		}
		return false;
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
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.BINDING_CURSE;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		IBauble.super.onEquipped(itemstack, player);
		player.world.playSound((EntityPlayer) player, player.getPosition(), SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.AMBIENT, 0.75F, 1.9f);
		BewitchmentAPI.getAPI().expandPlayerMP(this, (EntityPlayer) player);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		IBauble.super.onUnequipped(itemstack, player);
		if (BaublesApi.isBaubleEquipped((EntityPlayer) player, this) < 0) {
			BewitchmentAPI.getAPI().removeMPExpansion(this, (EntityPlayer) player);
		}
	}

	public String getNameInefficiently(ItemStack stack) {
		return getTranslationKey().substring(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.DARK_RED + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer && doesPlayerHaveAmulet((EntityPlayer) event.getEntityLiving()))
			if (event.getSource().isFireDamage() || event.getSource().isExplosion() || isDemon(event.getSource().getTrueSource())) {
				if (event.getEntityLiving().getCapability(IMagicPowerContainer.CAPABILITY, null).drain(50)) {
					event.setAmount(event.getAmount() * 0.80F);
				}
			}
	}

	private boolean doesPlayerHaveAmulet(EntityPlayer e) {
		IBaublesItemHandler ih = BaublesApi.getBaublesHandler(e);
		for (int i = 0; i < ih.getSlots(); i++) {
			if (ih.getStackInSlot(i).getItem() == this) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResourceLocation getID() {
		return this.getRegistryName();
	}

	@Override
	public int getExtraAmount(EntityPlayer p) {
		return 100;
	}
}