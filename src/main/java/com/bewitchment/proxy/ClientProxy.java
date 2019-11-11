package com.bewitchment.proxy;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.TarotInfo;
import com.bewitchment.client.gui.GuiTarotTable;
import com.bewitchment.client.model.block.*;
import com.bewitchment.client.render.entity.living.*;
import com.bewitchment.client.render.entity.misc.RenderCypressBroom;
import com.bewitchment.client.render.entity.misc.RenderDragonsBloodBroom;
import com.bewitchment.client.render.entity.misc.RenderElderBroom;
import com.bewitchment.client.render.entity.misc.RenderJuniperBroom;
import com.bewitchment.client.render.entity.spirit.demon.*;
import com.bewitchment.client.render.entity.spirit.ghost.RenderBlackDog;
import com.bewitchment.client.render.entity.spirit.ghost.RenderGhost;
import com.bewitchment.client.render.fx.ModParticleBubble;
import com.bewitchment.client.render.tile.*;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.container.ContainerTarotTable;
import com.bewitchment.common.block.tile.entity.*;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.misc.EntityCypressBroom;
import com.bewitchment.common.entity.misc.EntityDragonsBloodBroom;
import com.bewitchment.common.entity.misc.EntityElderBroom;
import com.bewitchment.common.entity.misc.EntityJuniperBroom;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends ServerProxy {
	public static final Map<Item, ModelBase> IDOL_MODELS = new HashMap<>();
	public static final Map<Item, ResourceLocation> IDOL_TEXTURES = new HashMap<>();
	
	@SubscribeEvent
	public static void stitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(RenderTileEntityWitchesCauldron.TEX);
		event.getMap().registerSprite(ModParticleBubble.TEX);
	}
	
	public static void registerIdol(Item item, ModelBase model, ResourceLocation texture) {
		IDOL_MODELS.put(item, model);
		IDOL_TEXTURES.put(item, texture);
	}
	
	@Override
	public List<ItemStack> getEntireInventory(EntityPlayer unused) {
		return super.getEntireInventory(Minecraft.getMinecraft().player);
	}
	
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
	public void handleTarot(List<TarotInfo> infoList) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		p.openGui(Bewitchment.instance, GuiHandler.ModGui.TAROT_TABLE.ordinal(), p.world, (int) p.posX, (int) p.posY, (int) p.posZ);
		if (Minecraft.getMinecraft().currentScreen instanceof GuiTarotTable) {
			Minecraft.getMinecraft().addScheduledTask(() -> ((GuiTarotTable) Minecraft.getMinecraft().currentScreen).loadData(infoList));
		}
		else {
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
		
		// Register lenny model
		ModelBase ilenny = new ModelLeonardIdol();
		registerIdol(ModObjects.stone_leonard_idol, ilenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/stone.png"));
		registerIdol(ModObjects.gold_leonard_idol, ilenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/gold.png"));
		registerIdol(ModObjects.nether_brick_leonard_idol, ilenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_leonard_idol, ilenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/scorned_brick.png"));
		
		// Register lilith model
		ModelBase ililith = new ModelLilithIdol();
		registerIdol(ModObjects.stone_lilith_idol, ililith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/stone.png"));
		registerIdol(ModObjects.gold_lilith_idol, ililith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/gold.png"));
		registerIdol(ModObjects.nether_brick_lilith_idol, ililith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_lilith_idol, ililith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/scorned_brick.png"));
		
		// Register baphomet model
		ModelBase ibaphomet = new ModelBaphometIdol();
		registerIdol(ModObjects.stone_baphomet_idol, ibaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/stone.png"));
		registerIdol(ModObjects.gold_baphomet_idol, ibaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/gold.png"));
		registerIdol(ModObjects.nether_brick_baphomet_idol, ibaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_baphomet_idol, ibaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/scorned_brick.png"));
		
		// Register herne model
		ModelBase iherne = new ModelHerneIdol();
		registerIdol(ModObjects.stone_herne_idol, iherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/stone.png"));
		registerIdol(ModObjects.gold_herne_idol, iherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/gold.png"));
		registerIdol(ModObjects.nether_brick_herne_idol, iherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_herne_idol, iherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/scorned_brick.png"));
		
		// Register lenny model
		ModelBase slenny = new ModelLeonardStatue();
		registerIdol(ModObjects.stone_leonard_statue, slenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/stone.png"));
		registerIdol(ModObjects.gold_leonard_statue, slenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/gold.png"));
		registerIdol(ModObjects.nether_brick_leonard_statue, slenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_leonard_statue, slenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/leonard/scorned_brick.png"));
		
		// Register lilith model
		ModelBase slilith = new ModelLilithStatue();
		registerIdol(ModObjects.stone_lilith_statue, slilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/stone.png"));
		registerIdol(ModObjects.gold_lilith_statue, slilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/gold.png"));
		registerIdol(ModObjects.nether_brick_lilith_statue, slilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_lilith_statue, slilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/lilith/scorned_brick.png"));
		
		// Register baphomet model
		ModelBase sbaphomet = new ModelBaphometStatue();
		registerIdol(ModObjects.stone_baphomet_statue, sbaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/stone.png"));
		registerIdol(ModObjects.gold_baphomet_statue, sbaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/gold.png"));
		registerIdol(ModObjects.nether_brick_baphomet_statue, sbaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_baphomet_statue, sbaphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/baphomet/scorned_brick.png"));
		
		// Register herne model
		ModelBase sherne = new ModelHerneStatue();
		registerIdol(ModObjects.stone_herne_statue, sherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/stone.png"));
		registerIdol(ModObjects.gold_herne_statue, sherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/gold.png"));
		registerIdol(ModObjects.nether_brick_herne_statue, sherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/nether_brick.png"));
		registerIdol(ModObjects.scorned_brick_herne_statue, sherne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/statue/herne/scorned_brick.png"));
	}
	
	@Override
	public void registerRendersPreInit() {
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
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlyph.class, new RenderTileEntityGlyph());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWitchesCauldron.class, new RenderTileEntityWitchesCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJuniperChest.class, new RenderTileEntityJuniperChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDBChest.class, new RenderTileEntityDBChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlacedItem.class, new RenderTileEntityPlacedItem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIdol.class, new RenderTileEntityIdol());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoppetShelf.class, new RenderTileEntityPoppetShelf());
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