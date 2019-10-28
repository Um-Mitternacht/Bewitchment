package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import com.bewitchment.Util;
import com.bewitchment.common.item.util.ModItemBauble;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemFortuneFavor extends ModItemBauble {
	public ItemFortuneFavor() {
		super("fortunes_favor", BaubleType.CHARM);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onBlockDrop(BlockEvent.HarvestDropsEvent event) {
		if (Util.hasBauble(event.getHarvester(), this)) {
			List<ItemStack> drops = new ArrayList<>(event.getDrops());
			event.getDrops().clear();
			for (ItemStack stack : drops) {
				Block block = event.getState().getBlock();
				if (block == Blocks.COAL_ORE || block == Blocks.DIAMOND_ORE || block == Blocks.EMERALD_ORE || block == Blocks.LAPIS_BLOCK || block == ModObjects.salt_ore || block == ModObjects.amethyst_ore
					|| block == ModObjects.garnet_ore || block == ModObjects.opal_ore) {
					stack.setCount(stack.getCount() + itemRand.nextInt(2));
				}
				event.getDrops().add(stack);
			}
		}
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100, 0));
	}
}
