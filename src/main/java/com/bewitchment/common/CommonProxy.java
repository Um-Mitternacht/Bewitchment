package com.bewitchment.common;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayerHandler;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.handler.ArmorHandler;
import com.bewitchment.common.handler.BlockDropHandler;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.common.integration.thaumcraft.BewitchmentThaumcraft;
import com.bewitchment.common.world.gen.ModWorldGen;
import com.bewitchment.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class CommonProxy {
	public final CreativeTabs tab = new CreativeTabs(Bewitchment.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModObjects.stone_witches_altar);
		}
	};
	public ModConfig config;
	
	public void preInit(FMLPreInitializationEvent event) {
		config = new ModConfig((event.getSuggestedConfigurationFile()));
		ModSounds.preInit();
		ModEntities.preInit();
		ModObjects.preInit();
		ModEnchantments.preInit();
		
		CapabilityManager.INSTANCE.register(ExtendedPlayer.class, new ExtendedPlayer(), ExtendedPlayer::new);
		MinecraftForge.EVENT_BUS.register(new ExtendedPlayerHandler());
		CapabilityManager.INSTANCE.register(MagicPower.class, new MagicPower(), MagicPower::new);
	}
	
	public void init(FMLInitializationEvent event) {
		ModRecipes.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Bewitchment.instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new ArmorHandler());
		MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
		if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(new BewitchmentThaumcraft());
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.postInit();
	}
	
	public boolean isFancyGraphicsEnabled() {
		return false;
	}
	
	public void registerTexture(Item item, String variant) {
	}
	
	public void registerTextureVariant(Item item, List<Predicate<ItemStack>> predicates) {
	}
	
	public void ignoreProperty(Block block, IProperty<?>... properties) {
	}
	
	public enum ModGui {
		OVEN, DISTILLERY, SPINNING_WHEEL, TAROT, JUNIPER_CHEST
	}
}