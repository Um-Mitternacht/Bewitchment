package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class PotionDisrobing extends BrewMod {

	public PotionDisrobing() {
		super("disrobing", true, 0xA52A2A, true, 0);
	}

	private static void spawnItem(EntityLivingBase entity, ItemStack is) {
		EntityItem ei = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, is);
		ei.setPickupDelay(100);
		entity.world.spawnEntity(ei);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double mult) {
		if (!entity.world.isRemote) {
			int i = entity.getRNG().nextInt(4 + 16 / (amplifier + 1));
			switch (i) {
				case 0:
					spawnItem(entity, entity.getItemStackFromSlot(EntityEquipmentSlot.FEET));
					entity.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
					break;
				case 1:
					spawnItem(entity, entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
					entity.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
					break;
				case 2:
					spawnItem(entity, entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
					entity.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
					break;
				case 3:
					spawnItem(entity, entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
					entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
					break;
				default:
					Bewitchment.logger.warn("Possible bug in Bewitchment: report this to the authors [PotionDisrobing.java]");
					break;
			}
		}
	}

}
