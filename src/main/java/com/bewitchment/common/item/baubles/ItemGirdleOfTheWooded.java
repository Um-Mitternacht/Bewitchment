package com.bewitchment.common.item.baubles;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.bewitchment.client.render.baubles.ModelGirdleOfTheWooded;
import com.bewitchment.client.render.baubles.ModelGirdleOfTheWoodedArmor;
import com.bewitchment.common.attributes.BarkAmountAttribute;
import com.bewitchment.common.core.helper.AttributeModifierModeHelper;
import com.bewitchment.common.item.ItemMod;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGirdleOfTheWooded extends ItemMod implements IBauble, IRenderBauble {

	private static final BaubleType BAUBTYPE = BaubleType.BELT;
	private static final UUID pieces_modifier = UUID.fromString("b190875d-bfa0-4670-9342-cd816f42c13d");

	@SideOnly(Side.CLIENT)
	private static ModelGirdleOfTheWooded model;
	@SideOnly(Side.CLIENT)
	private static ModelGirdleOfTheWooded model_with_armor;

	public ItemGirdleOfTheWooded(String id) {
		super(id);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BAUBTYPE;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if (!entity.world.isRemote) {
			if (entity.getRNG().nextDouble() < 0.0008d) { // ~once a minute
				if (!(entity instanceof EntityPlayer))
					return;
				EntityPlayer player = (EntityPlayer) entity;
				if (buildBark(player)) {
					player.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 0.75F, 0.5F);// TODO Needs to be synced with server
				}
			}
		}
	}

	public static boolean buildBark(EntityPlayer player) {
		IAttributeInstance attr = getAttribute(player);
		int base = (int) attr.getAttributeValue();
		int possible = Math.min((ForgeHooks.getTotalArmorValue(player) / 2), 5);
		int value = Math.min(possible, base);
		attr.removeModifier(pieces_modifier);
		attr.applyModifier(new AttributeModifier(pieces_modifier, "bark_pieces", value + 1, AttributeModifierModeHelper.ADD));
		return value < 5;
	}
	
	private boolean destroyBark(EntityPlayer player) {
		IAttributeInstance attr = getAttribute(player);
		int value = (int) attr.getAttributeValue();
		attr.removeModifier(pieces_modifier);
		attr.applyModifier(new AttributeModifier(pieces_modifier, "bark_pieces", value - 1, AttributeModifierModeHelper.ADD));
		return value > 0;
	}
	
	public static int getBarkPieces(EntityPlayer player) {
		IAttributeInstance attr = getAttribute(player);
		int base = (int) attr.getAttributeValue();
		int possible = Math.min((ForgeHooks.getTotalArmorValue(player) / 2), 5);
		int actual = Math.min(possible, base);
		return actual;
	}
	
	private static IAttributeInstance getAttribute(EntityPlayer player) {
		IAttributeInstance attr = player.getEntityAttribute(BarkAmountAttribute.INSTANCE);
		if (attr == null) {
			attr = player.getAttributeMap().registerAttribute(BarkAmountAttribute.INSTANCE);
		}
		return attr;
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
	public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
		return false;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.BLOCK_WOOD_STEP, 0.75F, 1.9f);
		IAttributeInstance aii = getAttribute((EntityPlayer) player);
		aii.removeAllModifiers();
		if (((EntityPlayer) player).isCreative()) {
			aii.applyModifier(new AttributeModifier(pieces_modifier, "bark_pieces", Math.min((ForgeHooks.getTotalArmorValue((EntityPlayer) player) / 2), 5), AttributeModifierModeHelper.ADD));
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		getAttribute((EntityPlayer) player).removeAllModifiers();
	}

	@SubscribeEvent
	public void onPlayerDamaged(LivingHurtEvent evt) {
		if (!evt.getEntityLiving().world.isRemote && evt.getAmount() > 2 && evt.getSource().getTrueSource() != null && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
			if (destroyBark(player)) {
				evt.setCanceled(true);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.DARK_GREEN + I18n.format("witch.tooltip." + getUnlocalizedNameInefficiently(stack).substring(5) + "_description.name"));
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.BINDING_CURSE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
		if (type == RenderType.BODY) {
			if (model == null) {
				model = new ModelGirdleOfTheWooded();
				model_with_armor = new ModelGirdleOfTheWoodedArmor();
			}
			GL11.glPushMatrix();
			IRenderBauble.Helper.rotateIfSneaking(player);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glTranslated(0, 0, 0.02);
			GL11.glScaled(0.12, 0.12, 0.12);
			IRenderBauble.Helper.translateToChest();
			IRenderBauble.Helper.defaultTransforms();
			if (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty()) {
				model.render(player, player.limbSwing, player.limbSwingAmount, player.ticksExisted, player.rotationYaw, player.rotationPitch, 1);
			} else {
				model_with_armor.render(player, player.limbSwing, player.limbSwingAmount, player.ticksExisted, player.rotationYaw, player.rotationPitch, 1);
			}
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent
	public void onEquipmentChanged(LivingEquipmentChangeEvent evt) {
		if (!evt.getEntityLiving().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer) {
			IAttributeInstance ai = getAttribute((EntityPlayer) evt.getEntityLiving());
			int base = (int) ai.getAttributeValue();
			int possible = Math.min((ForgeHooks.getTotalArmorValue((EntityPlayer) evt.getEntityLiving()) / 2), 5);
			int actual = Math.min(possible, base);
			ai.removeAllModifiers();
			ai.applyModifier(new AttributeModifier(pieces_modifier, "bark_pieces", actual, AttributeModifierModeHelper.ADD));
		}
	}

}