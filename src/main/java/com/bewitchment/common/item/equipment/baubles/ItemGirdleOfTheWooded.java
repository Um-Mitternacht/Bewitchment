package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import baubles.api.render.IRenderBauble;
import com.bewitchment.client.render.entity.model.ModelGirdleOfTheWooded;
import com.bewitchment.client.render.entity.model.ModelGirdleOfTheWoodedArmor;
import com.bewitchment.common.core.capability.simple.BarkCapability;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
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

public class ItemGirdleOfTheWooded extends ItemMod implements IBauble, IRenderBauble {

	private static final BaubleType BAUBTYPE = BaubleType.BELT;

	@SideOnly(Side.CLIENT)
	private static ModelGirdleOfTheWooded model;
	@SideOnly(Side.CLIENT)
	private static ModelGirdleOfTheWooded model_with_armor;

	public ItemGirdleOfTheWooded(String id) {
		super(id);
		this.setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static boolean buildBark(EntityPlayer player) {
		BarkCapability bark = player.getCapability(BarkCapability.CAPABILITY, null);
		int oldAmount = bark.pieces;
		bark.pieces++;
		fixBark(player);
		if (oldAmount < bark.pieces) {
			bark.markDirty((byte) 1);
			return true;
		}
		return false;
	}

	public static int getBarkPieces(EntityPlayer player) {
		return player.getCapability(BarkCapability.CAPABILITY, null).pieces;
	}

	public static void fixBark(EntityPlayer player) {
		int value = player.getCapability(BarkCapability.CAPABILITY, null).pieces;
		int possible = Math.max((10 - (int) Math.ceil(ForgeHooks.getTotalArmorValue(player) / 2f)), 0);
		if (ForgeHooks.getTotalArmorValue(player) == 0) {
			possible = 0;
		}
		possible = Math.min(possible, 4);
		if (value > possible) {
			value = possible;
		}
		if (value < 0) {
			value = 0;
		}
		player.getCapability(BarkCapability.CAPABILITY, null).pieces = value;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BAUBTYPE;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if (!entity.world.isRemote) {
			if (!(entity instanceof EntityPlayer)) {
				return;
			}
			EntityPlayer player = (EntityPlayer) entity;
			if (isValidSpot(player) && entity.getRNG().nextDouble() < 0.0008d) { // ~once a minute
				if (buildBark(player)) {
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 10, false, false));
					player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.PLAYERS, 1f, 1f);
				}
			}

		}
	}

	private boolean isValidSpot(EntityPlayer player) {
		return player.world.getBlockState(player.getPosition().down()).getBlock() == Blocks.GRASS;
	}

	private void destroyBark(EntityPlayer player) {
		player.getCapability(BarkCapability.CAPABILITY, null).pieces -= 1;
		fixBark(player);
		player.getCapability(BarkCapability.CAPABILITY, null).markDirty((byte) 1);
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
		return true;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.BLOCK_WOOD_STEP, 0.75F, 1.9f);
		player.getCapability(BarkCapability.CAPABILITY, null).pieces = ((EntityPlayer) player).isCreative() ? 10 : 0;
		fixBark((EntityPlayer) player);
		player.getCapability(BarkCapability.CAPABILITY, null).markDirty((byte) 1);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.getCapability(BarkCapability.CAPABILITY, null).pieces = 0;
		player.getCapability(BarkCapability.CAPABILITY, null).markDirty((byte) 1);
	}

	@SubscribeEvent
	public void onPlayerDamaged(LivingHurtEvent evt) {
		if (!evt.getEntityLiving().world.isRemote && evt.getAmount() > 2 && evt.getSource().getTrueSource() != null && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
			fixBark(player);
			if (player.getCapability(BarkCapability.CAPABILITY, null).pieces > 0) {
				destroyBark(player);
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
			int base = evt.getEntityLiving().getCapability(BarkCapability.CAPABILITY, null).pieces;
			int possible = Math.min((ForgeHooks.getTotalArmorValue((EntityPlayer) evt.getEntityLiving()) / 2), 5);
			int actual = Math.min(possible, base);
			evt.getEntityLiving().getCapability(BarkCapability.CAPABILITY, null).pieces = actual;
			evt.getEntityLiving().getCapability(BarkCapability.CAPABILITY, null).markDirty((byte) 1);
		}
	}
}