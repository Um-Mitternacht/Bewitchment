package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This was created originally in Covens
 * And ported over, in this format
 */
public class DisrobingBrew implements IBrew {

	private static void spawnItem(EntityLivingBase entity, ItemStack is) {
		EntityItem ei = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, is);
		ei.setPickupDelay(100);
		entity.world.spawnEntity(ei);
	}

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
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
			}
		}
	}

	@Override
	public boolean isBad() {
		return true;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		//NO-OP
	}

	@Override
	public int getColor() {
		return 0xA52A2A;
	}

	@Override
	public String getName() {
		return "disrobing";
	}
}
