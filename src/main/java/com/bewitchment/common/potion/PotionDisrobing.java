package com.bewitchment.common.potion;

import baubles.api.BaublesApi;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused"})
public class PotionDisrobing extends ModPotion {
	public PotionDisrobing() {
		super("disrobing", true, 0x407f20);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (!living.world.isRemote) {
			List<ItemStack> list = new ArrayList<>();
			for (ItemStack stack : living.getArmorInventoryList()) list.add(stack);
			if (living instanceof EntityPlayer) for (int i = 0; i < BaublesApi.getBaublesHandler((EntityPlayer) living).getSlots(); i++)
				list.add(BaublesApi.getBaublesHandler((EntityPlayer) living).getStackInSlot(i));
			for (int i = 0; i < amplifier + 1; i++)
				if (living.world.rand.nextBoolean()) InventoryHelper.spawnItemStack(living.world, living.posX, living.posY, living.posZ, list.get(living.world.rand.nextInt(list.size())).splitStack(1));
		}
	}
}