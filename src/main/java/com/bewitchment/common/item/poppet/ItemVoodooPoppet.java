package com.bewitchment.common.item.poppet;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.event.VoodooEvent;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class ItemVoodooPoppet extends ItemPoppet {
	public ItemVoodooPoppet() {
		super(false);
	}


	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		Block current = entityItem.world.getBlockState(entityItem.getPosition().down()).getBlock();
		World world = entityItem.world;
		if (current == Blocks.LAVA || current == Blocks.FLOWING_LAVA) {
			ItemStack poppet = entityItem.getItem();
			if (poppet.hasTagCompound() && poppet.getTagCompound().hasKey("boundId")) {
				EntityLivingBase target = world.getEntities(EntityLivingBase.class, e -> e != null && e.getPersistentID().toString().equals(poppet.getTagCompound().getString("boundId"))).stream().findFirst().orElse(null);
				if (target != null) {
					VoodooEvent event = new VoodooEvent(null, target);
					MinecraftForge.EVENT_BUS.post(event);
					if (!event.isCanceled()) target.setFire(30);
				}
			}
		}
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("boundId") && stack.getTagCompound().hasKey("boundName")) {
			EntityLivingBase target = world.getEntities(EntityLivingBase.class, e -> e != null && e.getPersistentID().toString().equals(stack.getTagCompound().getString("boundId"))).stream().findFirst().orElse(null);
			if (target != null) {
				VoodooEvent event = new VoodooEvent(player, target);
				MinecraftForge.EVENT_BUS.post(event);
				if (!event.isCanceled()) {
					if (player.isSneaking()) {
						List<ItemStack> inv = Bewitchment.proxy.getEntireInventory(player);
						for (ItemStack itemStack : inv) {
							if (itemStack.getItem() == ModObjects.bone_needle) {
								target.attackEntityFrom(DamageSource.MAGIC, 2);
								itemStack.shrink(1);
								stack.damageItem(1, player);
								return new ActionResult<>(EnumActionResult.SUCCESS, stack);
							}
						}
					}
				} else player.sendStatusMessage(new TextComponentTranslation("poppet.protect"), true);
			} else player.sendStatusMessage(new TextComponentTranslation("poppet.not_found"), true);
		}
		return super.onItemRightClick(world, player, hand);
	}
}
