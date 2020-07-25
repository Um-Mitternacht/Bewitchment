package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Joseph on 3/3/2020.
 */

public class ItemCleaver extends ItemSword {
	public ItemCleaver() {
		super(ModObjects.TOOL_HELLISH);
		Util.registerItem(this, "cleaver_sword");
		setMaxDamage(333);
		setMaxStackSize(1);
		setNoRepair();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!target.world.isRemote && (!(target instanceof EntityPlayer) || !(attacker instanceof EntityPlayer))) {
			int i = itemRand.nextInt(100);
			if (i < 5) {
				target.motionX += 0.6;
				target.motionY += 0.6;
				target.motionZ += 0.6;
				target.addPotionEffect(new PotionEffect(ModPotions.corrosion, 450, 1, false, true));
				stack.damageItem(9, attacker);
				if (target instanceof EntityPlayer)
					((EntityPlayerMP) target).connection.sendPacket(new SPacketEntityVelocity(target));
			}
		}
		return super.hitEntity(stack, target, attacker);
	}
}
