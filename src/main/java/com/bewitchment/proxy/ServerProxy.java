package com.bewitchment.proxy;

import baubles.api.BaublesApi;
import com.bewitchment.Bewitchment;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"unused"})
public class ServerProxy {
	public List<ItemStack> getEntireInventory(EntityPlayer player) {
		List<ItemStack> fin = new ArrayList<>();
		if (player != null) {
			for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++) fin.add(BaublesApi.getBaublesHandler(player).getStackInSlot(i));
			fin.addAll(player.inventory.mainInventory);
			fin.addAll(player.inventory.armorInventory);
			fin.addAll(player.inventory.offHandInventory);
		}
		return fin;
	}
	
	public boolean doesPlayerHaveAdvancement(EntityPlayer player, ResourceLocation name) {
		if (player instanceof EntityPlayerMP) {
			Advancement advancement = ((EntityPlayerMP) player).getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(Bewitchment.MODID, "crafted_altar"));
			return advancement != null && ((EntityPlayerMP) player).getAdvancements().getProgress(advancement).isDone();
		}
		return false;
	}
	
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