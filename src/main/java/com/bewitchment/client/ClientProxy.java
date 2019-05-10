package com.bewitchment.client;

import com.bewitchment.client.handler.ClientHandler;
import com.bewitchment.common.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("ConstantConditions")
public class ClientProxy extends CommonProxy {
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.register(new ClientHandler());
	}

	@Override
	public boolean isFancyGraphicsEnabled() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}

	@Override
	public void registerTexture(Item item, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), variant));
	}

	@Override
	public void registerTextureVariant(Item item, List<Predicate<ItemStack>> predicates) {
		ResourceLocation[] names = new ResourceLocation[predicates.size() + 1];
		for (int i = 0; i <= predicates.size(); i++)
			names[i] = new ResourceLocation(item.getRegistryName().toString() + (i == 0 ? "" : "_variant" + (predicates.size() == 1 ? "" : (i - 1))));
		ModelBakery.registerItemVariants(item, names);
		ModelLoader.setCustomMeshDefinition(item, stack -> {
			for (int i = 0; i < predicates.size(); i++)
				if (predicates.get(i).test(stack)) return new ModelResourceLocation(names[i + 1], "inventory");
			return new ModelResourceLocation(item.getRegistryName(), "inventory");
		});
	}

	@Override
	public void ignoreProperty(Block block, IProperty<?>... properties) {
		ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(properties).build());
	}
}