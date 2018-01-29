package com.bewitchment.common.item.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import baubles.api.render.IRenderBauble;
import com.bewitchment.client.render.baubles.ModelBarkBelt;
import com.bewitchment.client.render.baubles.ModelBarkBeltArmor;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.item.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
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
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBarkBelt extends ItemMod implements IBauble, IRenderBauble {

	private static final int BARK_PIECES = 5;// 0 means max charge, 5 means break
	private static final BaubleType BAUBTYPE = BaubleType.BELT;

	@SideOnly(Side.CLIENT)
	private static ModelBarkBelt model;
	@SideOnly(Side.CLIENT)
	private static ModelBarkBelt model_with_armor;

	public ItemBarkBelt(String id) {
		super(id);
		this.setMaxDamage(BARK_PIECES + 1);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * Returns how many bark pieces are currently on the item work.
	 * This doesn't take into account the max number of pieces! If you need a
	 * precise amount, refresh it first if on a server ({@link #refreshMaxBark(EntityPlayer)}), or use {@link #getBarkPiecesForRendering(EntityPlayer)}
	 * on a client
	 *
	 * @param player
	 *            The player
	 * @return The amount of pieces
	 */
	public static int getBarkPieces(EntityPlayer player) {
		ItemStack belt = getBarkBelt(player);
		if (belt.getItem() == ModItems.bark_belt) {
			return BARK_PIECES - belt.getItemDamage();
		}
		return 0;
	}

	/**
	 * Retrieves the bark belt itemstack worn by a player
	 *
	 * @param player The player
	 * @return The Bark Belt itemstack, if the player wears one, or an empty stack if the player is wearing something else or nothing
	 */
	public static ItemStack getBarkBelt(EntityPlayer player) {
		ItemStack bb = BaublesApi.getBaublesHandler(player).getStackInSlot(BAUBTYPE.getValidSlots()[0]);
		if (bb.getItem() == ModItems.bark_belt)
			return bb;
		return ItemStack.EMPTY;
	}

	/**
	 * Sets the number of bark pieces on a player.
	 * If the player has no belt, this does nothing.
	 * If the amount is too high, it sets it to the maximum
	 * If too few it breaks the belt
	 *
	 * @param player The player
	 * @param pieces The amount of pieces to have on a player
	 */
	public static void setBarkBeltPieces(EntityPlayer player, int pieces) {
		ItemStack is = getBarkBelt(player);
		if (is.isEmpty())
			return;
		if (pieces > BARK_PIECES) {
			pieces = BARK_PIECES;
		} else if (pieces < 1) {
			is.setCount(0);
		} else {
			int possible = Math.min((ForgeHooks.getTotalArmorValue(player) / 2), BARK_PIECES);
			int actual = Math.min(possible, pieces);
			is.setItemDamage(BARK_PIECES - actual);
		}
	}

	/**
	 * Restores a piece of bark on a player
	 *
	 * @param player The player
	 */
	public static void buildBark(EntityPlayer player) {
		ItemStack bb = getBarkBelt(player);
		if (!bb.isEmpty()) {
			setBarkBeltPieces(player, getBarkPieces(player) + 1);
		}
	}

	/**
	 * Pops off a piece of bark from a player
	 *
	 * @param player The player
	 */
	public static void destroyBark(EntityPlayer player) {
		ItemStack bb = getBarkBelt(player);
		if (!bb.isEmpty()) {
			setBarkBeltPieces(player, getBarkPieces(player) - 1);
		}
	}

	/**
	 * ONLY CALL IF SURE TO HAVE A BARK BELT EQUIPPED, THIS DOESN'T CHECK
	 * <p>
	 * This recalibrates the max bark pieces that can stay on a player after the armor's value changes
	 */
	public static void refreshMaxBark(EntityPlayer player) {
		ItemStack is = getBarkBelt(player);
		int possible = Math.min((ForgeHooks.getTotalArmorValue(player) / 2), BARK_PIECES);
		int actual = Math.min(possible, getBarkPieces(player));
		is.setItemDamage(BARK_PIECES - actual);
	}

	/**
	 * Returns how many pieces of bark are currently on the player, capped for desync reasons (hud would go on top of missing armor)
	 *
	 * @param player The player
	 */
	@SideOnly(Side.CLIENT)
	public static int getBarkPiecesForRendering(EntityPlayer player) {
		ItemStack belt = getBarkBelt(player);
		if (belt.getItem() == ModItems.bark_belt) {
			int possible = Math.min((ForgeHooks.getTotalArmorValue(player) / 2), BARK_PIECES);
			int actual = Math.min(possible, BARK_PIECES - belt.getItemDamage());
			return actual;
		}
		return 0;
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
		return true;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if (!entity.world.isRemote) {
			if (entity.getRNG().nextDouble() < 0.0008d) { // ~once a minute
				if (!(entity instanceof EntityPlayer))
					return;
				EntityPlayer player = (EntityPlayer) entity;
				int barkPrev = getBarkPieces(player);
				buildBark(player);
				int barkCurr = getBarkPieces(player);
				if (barkCurr > barkPrev)
					player.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 0.75F, 0.5F);// Needs to be synced with server
			}
		}
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
		return true;// TODO swap from bark damage to an internal counter and sync up manually when it changes
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.BLOCK_WOOD_STEP, 0.75F, 1.9f);
		if (player instanceof EntityPlayer) {
			if (((EntityPlayer) player).isCreative())
				itemstack.setItemDamage(0);
			else
				itemstack.setItemDamage(BARK_PIECES);
		}
		refreshMaxBark((EntityPlayer) player);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		itemstack.setItemDamage(BARK_PIECES);
	}

	@SubscribeEvent
	public void onPlayerDamaged(LivingHurtEvent evt) {
		if (!evt.getEntityLiving().world.isRemote && evt.getAmount() > 2 && evt.getSource().getTrueSource() != null && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
			if (!getBarkBelt(player).isEmpty()) {
				refreshMaxBark(player);
				if (getBarkPieces(player) > 0) {
					destroyBark(player);
					evt.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEquipmentChanged(LivingEquipmentChangeEvent evt) {
		if (!evt.getEntityLiving().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer) {
			if (!getBarkBelt((EntityPlayer) evt.getEntityLiving()).isEmpty()) {
				refreshMaxBark((EntityPlayer) evt.getEntityLiving());
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
				model = new ModelBarkBelt();
				model_with_armor = new ModelBarkBeltArmor();
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

}
