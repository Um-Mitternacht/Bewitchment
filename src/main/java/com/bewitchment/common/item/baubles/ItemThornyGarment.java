package com.bewitchment.common.item.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.common.core.ModCreativeTabs;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
public class ItemThornyGarment extends ItemMod implements IBauble {
	public ItemThornyGarment() {
		super(LibItemName.THORNY_GARMENT);
		this.setMaxStackSize(1);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	//Todo: Specialized effects for thorns. Also, some testing, and allow for more enchants.
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
		return BaubleType.BODY;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, .75F, 1.9f);
	}

	public String getNameInefficiently(ItemStack stack) {
		return getUnlocalizedName().substring(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.AQUA + I18n.format("witch.tooltip." + getNameInefficiently(stack) + "_description.name"));
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.THORNS;
	}

	//Fixme: Gotta work on this. Can't figure it out at the moment.
	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		Entity attacker = source.getTrueSource();

		if (attacker instanceof EntityLivingBase) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(attacker), 2F);
		}
	}
}
