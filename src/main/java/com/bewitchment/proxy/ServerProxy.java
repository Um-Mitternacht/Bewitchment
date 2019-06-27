package com.bewitchment.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"unused"})
public class ServerProxy {
	public boolean isFancyGraphicsEnabled() {
		return false;
	}
	
	public void registerColorOverrides() {
	}
	
	public void registerRenderers() {
	}
	
	public void registerTexture(Item item, String variant) {
	}
	
	public void registerTextureVariant(Item item, List<Predicate<ItemStack>> predicates) {
	}
	
	public void ignoreProperty(Block block, IProperty<?>... properties) {
	}
}