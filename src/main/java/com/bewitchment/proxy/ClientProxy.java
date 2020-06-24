package com.bewitchment.proxy;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.TarotInfo;
import com.bewitchment.client.gui.GuiTarotTable;
import com.bewitchment.client.render.entity.living.*;
import com.bewitchment.client.render.entity.misc.*;
import com.bewitchment.client.render.entity.spirit.demon.*;
import com.bewitchment.client.render.entity.spirit.ghost.RenderBlackDog;
import com.bewitchment.client.render.entity.spirit.ghost.RenderGhost;
import com.bewitchment.client.render.fx.ModParticleBubble;
import com.bewitchment.client.render.tile.*;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.container.ContainerTarotTable;
import com.bewitchment.common.block.tile.entity.*;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.misc.*;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.common.integration.dynamictrees.DynamicTreesCompat;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends ServerProxy {

	@SubscribeEvent
	public static void stitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(RenderTileEntityWitchesCauldron.TEX);
		event.getMap().registerSprite(ModParticleBubble.TEX);
	}

	@Override
	public List<ItemStack> getEntireInventory(EntityPlayer unused) {
		return super.getEntireInventory(Minecraft.getMinecraft().player);
	}

	@SuppressWarnings("deprecation")
	public boolean doesPlayerHaveAdvancement(EntityPlayer player, ResourceLocation name) {
		if (player instanceof EntityPlayerSP) {
			ClientAdvancementManager manager = ((EntityPlayerSP) player).connection.getAdvancementManager();
			Advancement adv = manager.getAdvancementList().getAdvancement(name);
			if (adv != null) {
				AdvancementProgress progress = ObfuscationReflectionHelper.getPrivateValue(ClientAdvancementManager.class, manager, "advancementToProgress", "field_192803_d");
				return progress != null && progress.isDone();
			}
		}
		return super.doesPlayerHaveAdvancement(player, name);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		RenderingRegistry.registerEntityRenderingHandler(EntityCypressBroom.class, RenderCypressBroom::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityElderBroom.class, RenderElderBroom::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityJuniperBroom.class, RenderJuniperBroom::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonsBloodBroom.class, RenderDragonsBloodBroom::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityLizard.class, RenderLizard::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, RenderOwl::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRaven.class, RenderRaven::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySnake.class, RenderSnake::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityToad.class, RenderToad::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityBlackDog.class, RenderBlackDog::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, RenderGhost::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHellhound.class, RenderHellhound::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFeuerwurm.class, RenderFeuerwurm::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityDemon.class, RenderDemon::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemoness.class, RenderDemoness::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, RenderImp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDruden.class, RenderDruden::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowPerson.class, RenderShadowPerson::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBaphomet.class, RenderBaphomet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLeonard.class, RenderLeonard::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCleaver.class, RenderCleaver::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCambion.class, RenderCambion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBafometyr.class, RenderBafometyr::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityWerewolf.class, RenderWerewolf::new);


		RenderingRegistry.registerEntityRenderingHandler(EntitySilverArrow.class, RenderSilverArrow::new);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlyph.class, new RenderTileEntityGlyph());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWitchesCauldron.class, new RenderTileEntityWitchesCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJuniperChest.class, new RenderTileEntityJuniperChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDBChest.class, new RenderTileEntityDBChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlacedItem.class, new RenderTileEntityPlacedItem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStatue.class, new RenderTileEntityStatue());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoppetShelf.class, new RenderTileEntityPoppetShelf());

		if (Loader.isModLoaded("dynamictrees")) {
			DynamicTreesCompat.clientPreInit();
		}
	}

	@Override
	public void handleTarot(List<TarotInfo> infoList) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		p.openGui(Bewitchment.instance, GuiHandler.ModGui.TAROT_TABLE.ordinal(), p.world, (int) p.posX, (int) p.posY, (int) p.posZ);
		if (Minecraft.getMinecraft().currentScreen instanceof GuiTarotTable) {
			Minecraft.getMinecraft().addScheduledTask(() -> ((GuiTarotTable) Minecraft.getMinecraft().currentScreen).loadData(infoList));
		} else {
			GuiTarotTable gtt = new GuiTarotTable(new ContainerTarotTable(infoList));
			Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(gtt));
			Minecraft.getMinecraft().addScheduledTask(() -> gtt.loadData(infoList));
		}
	}

	@Override
	public boolean isFancyGraphicsEnabled() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}

	@Override
	public void registerRendersInit() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
			int type = state.getValue(BlockGlyph.TYPE);
			return type == BlockGlyph.GOLDEN ? 0xe3dc3c : type == BlockGlyph.NETHER ? 0xbb0000 : type == BlockGlyph.ENDER ? 0x770077 : 0xffffff;
		}, ModObjects.glyph);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? 0xe6c44f : 0xffffff, ModObjects.snake_venom);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? 0x9e0000 : 0xffffff, ModObjects.bottle_of_blood);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? 0x590000 : 0xffffff, ModObjects.bottle_of_vampire_blood);

		TileEntityItemStackRenderer.instance = new RenderTileEntityStatue.ForwardingTEISR(TileEntityItemStackRenderer.instance);
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

	@Override
	public void setStatueTEISR(Item item) {
		item.setTileEntityItemStackRenderer(new RenderTileEntityStatue.ForwardingTEISR(TileEntityItemStackRenderer.instance));
	}
}