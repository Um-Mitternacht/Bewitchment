package com.bewitchment.common;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.common.handler.BlockDropHandler;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.common.world.gen.ModWorldGen;
import com.bewitchment.registry.ModEntities;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModRecipes;
import com.bewitchment.registry.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class CommonProxy {
	public ModConfig config;

	public final CreativeTabs tab = new CreativeTabs(Bewitchment.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.SNOWBALL);
		}
	};

	public void preInit(FMLPreInitializationEvent event) {
		config = new ModConfig((event.getSuggestedConfigurationFile()));
		ModSounds.preInit();
		ModEntities.preInit();
		ModObjects.preInit();
		ModRecipes.preInit();
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Bewitchment.instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.postInit();
	}

	public boolean isFancyGraphicsEnabled() {
		return false;
	}

	public void registerTexture(Item item, String variant) {}

	public void registerTextureVariant(Item item, List<Predicate<ItemStack>> predicates) {}

	public void ignoreProperty(Block block, IProperty<?>... properties) {}

	public enum ModGui {
		DISTILLERY, LOOM, OVEN, TAROT
	}
}