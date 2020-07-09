package com.bewitchment.proxy;

import baubles.api.BaublesApi;
import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.TarotInfo;
import com.bewitchment.common.integration.dynamictrees.DynamicTreesCompat;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"unused"})
public class ServerProxy {
	public List<ItemStack> getEntireInventory(EntityPlayer player) {
		List<ItemStack> fin = new ArrayList<>();
		if (player != null) {
			for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++)
				fin.add(BaublesApi.getBaublesHandler(player).getStackInSlot(i));
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

	public void preInit(FMLPreInitializationEvent event) {
		ModObjects.preInit();
		if (Loader.isModLoaded("dynamictrees")) {
			DynamicTreesCompat.preInit();
		}
	}

	public void handleTarot(List<TarotInfo> infoList) {
	}

	public boolean isFancyGraphicsEnabled() {
		return false;
	}

	public void registerRendersInit() {
	}

	public void registerTexture(Item item, String variant) {
	}

	public void registerTextureVariant(Item item, List<Predicate<ItemStack>> predicates) {
	}

	public EntityPlayer getPlayer(MessageContext ctx){
		if (ctx.side.isServer()) {
			return ctx.getServerHandler().player;
		} else {
			throw new RuntimeException("Tried to get the player from a client-side MessageContext on the dedicated server");
		}
	}

	public IThreadListener getThreadListener(final MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.server;
		} else {
			throw new RuntimeException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
		}
	}

	public void ignoreProperty(Block block, IProperty<?>... properties) {
	}

	public void setStatueTEISR(Item item) {
	}
}