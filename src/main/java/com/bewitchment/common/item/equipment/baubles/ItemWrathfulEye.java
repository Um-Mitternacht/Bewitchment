package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.item.util.ModItemBauble;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 5/23/2019.
 */
public class ItemWrathfulEye extends ModItemBauble {
	
	//This will be used for trading in the diabolic update, and also provide a minute amount of protection from fire. Not as good as the hellish bauble though.
	public ItemWrathfulEye() {
		super("wrathful_eye", BaubleType.TRINKET);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (Util.hasBauble(event.getEntityLiving(), this) && event.getSource().isFireDamage()) event.setAmount(event.getAmount() * 0.95f);
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase living) {
		if (living.ticksExisted % 40 == 0 && living.isPotionActive(MobEffects.WEAKNESS)) living.removePotionEffect(MobEffects.WEAKNESS);
	}
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, .75F, 1.9f);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
		return BaubleType.AMULET;
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
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.BINDING_CURSE;
	}
	
	@SuppressWarnings("unused")
	private boolean doesPlayerHaveAmulet(EntityPlayer e) {
		return BaublesApi.isBaubleEquipped(e, this) > 0;
	}
	
}
