package com.bewitchment.common.village;

import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class DemonTradeHandler {

	public static final DemonTradeHandler INSTANCE = new DemonTradeHandler();

	public VillagerRegistry.VillagerProfession demon;

	public void init() {
		demon = new VillagerRegistry.VillagerProfession("bewitchment:demon", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "noble");
			career.addTrade(1, new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.nethersteel[0]), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)));
			career.addTrade(1, new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.scorned_bricks[0]), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)));
		}
	}
}
