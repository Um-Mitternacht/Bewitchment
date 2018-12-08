package com.bewitchment.client.core;

import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.api.state.StateProperties;
import com.bewitchment.client.ResourceLocations;
import com.bewitchment.client.core.event.*;
import com.bewitchment.client.core.hud.*;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.client.gui.GuiTarots;
import com.bewitchment.client.handler.ColorPropertyHandler;
import com.bewitchment.client.handler.ItemCandleColorHandler;
import com.bewitchment.client.handler.Keybinds;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.client.render.entity.layer.MantleLayer;
import com.bewitchment.client.render.entity.renderer.*;
import com.bewitchment.client.render.tile.TileRenderCauldron;
import com.bewitchment.client.render.tile.TileRenderGemBowl;
import com.bewitchment.client.render.tile.TileRenderPlacedItem;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.misc.BlockWitchFire;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.tarot.TarotHandler.TarotInfo;
import com.bewitchment.common.core.net.GuiHandler;
import com.bewitchment.common.core.proxy.ISidedProxy;
import com.bewitchment.common.entity.*;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemSpellPage;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.bewitchment.common.tile.tiles.TileEntityGemBowl;
import com.bewitchment.common.tile.tiles.TileEntityPlacedItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements ISidedProxy {

	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		ModelHandler.registerModels();
	}

	@SubscribeEvent
	public static void stitchEventPre(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(ResourceLocations.STEAM);
		event.getMap().registerSprite(ResourceLocations.BEE);
		event.getMap().registerSprite(ResourceLocations.FLAME);
		event.getMap().registerSprite(ResourceLocations.GRAY_WATER);
		event.getMap().registerSprite(ResourceLocations.BAT);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		registerRenders();

		HudController.registerNewComponent(new BloodViewerHUD());
		HudController.registerNewComponent(new EnergyHUD());
		HudController.registerNewComponent(new MoonHUD());
		HudController.registerNewComponent(new SelectedActionHUD());
		HudController.registerNewComponent(ExtraBarButtonsHUD.INSTANCE);
		HudController.registerNewComponent(new VampireBloodBarHUD());

		MinecraftForge.EVENT_BUS.register(new GirdleOfTheWoodedHUD());
		MinecraftForge.EVENT_BUS.register(new WerewolfEventHandler());
		MinecraftForge.EVENT_BUS.register(new RenderingHacks());
		MinecraftForge.EVENT_BUS.register(new MiscEventHandler(Minecraft.getMinecraft()));
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Keybinds.registerKeys();
		RenderPlayer defaultRP = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		defaultRP.addLayer(new MantleLayer(defaultRP.getMainModel()));
		RenderPlayer slimRP = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		slimRP.addLayer(new MantleLayer(slimRP.getMainModel()));
		BlockColors blocks = Minecraft.getMinecraft().getBlockColors();
		// Block Colors
		blocks.registerBlockColorHandler(ColorPropertyHandler.INSTANCE, ModBlocks.candle_medium, ModBlocks.candle_small, ModBlocks.candle_medium_lit, ModBlocks.candle_small_lit, ModBlocks.lantern, ModBlocks.revealing_lantern);

		blocks.registerBlockColorHandler(new IBlockColor() {

			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				return state.getValue(BlockWitchFire.TYPE).getColor();
			}
		}, ModBlocks.witchfire);

		blocks.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				EnumGlyphType type = state.getValue(StateProperties.GLYPH_TYPE);
				switch (type) {
					case ENDER:
						return 0x770077;
					case GOLDEN:
						return 0xe3dc3c;
					case NETHER:
						return 0xbb0000;
					default:
					case NORMAL:
						return 0xFFFFFF;
					case ANY:
						return 0x00FF00; // A green one should never happen!
				}
			}
		}, ModBlocks.ritual_glyphs);

		blocks.registerBlockColorHandler(new IBlockColor() {

			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				return (worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : -1);
			}
		}, ModBlocks.spanish_moss, ModBlocks.spanish_moss_end);

		blocks.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (tintIndex == 1) {
					return Color.HSBtoRGB((pos.getX() + pos.getY() + pos.getZ()) % 50 / 50f, 0.4f, 1f);
				}
				return -1;
			}
		}, ModBlocks.crystal_ball);

		ItemColors items = Minecraft.getMinecraft().getItemColors();
		// Item Colors
		items.registerItemColorHandler(new ItemCandleColorHandler(), Item.getItemFromBlock(ModBlocks.candle_medium), Item.getItemFromBlock(ModBlocks.candle_small));

		items.registerItemColorHandler(new IItemColor() {

			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				if (tintIndex == 0) {
					ISpell s = ItemSpellPage.getSpellFromItemStack(stack);
					if (s != null)
						return s.getColor();
				}
				return -1;
			}
		}, ModItems.spell_page);

		items.registerItemColorHandler(new IItemColor() {

			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				if (tintIndex == 0) {
					return BrewData.fromStack(stack).getColor();
				}
				return -1;
			}
		}, ModItems.brew_phial_drink, ModItems.brew_phial_linger, ModItems.brew_phial_splash, ModItems.brew_arrow);

		items.registerItemColorHandler(ColorPropertyHandler.INSTANCE, Item.getItemFromBlock(ModBlocks.lantern), Item.getItemFromBlock(ModBlocks.revealing_lantern));

		NetworkRegistry.INSTANCE.registerGuiHandler(Bewitchment.instance, new GuiHandler());

		ClientCommandHandler.instance.registerCommand(new ClientCommandGuiConfig());
	}

	@Override
	public void spawnParticle(ParticleF particleF, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args) {
		if (doParticle()) {
			Minecraft.getMinecraft().effectRenderer.addEffect(particleF.newInstance(x, y, z, xSpeed, ySpeed, zSpeed, args));
		}
	}

	private boolean doParticle() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			return false;

		float chance = 1F;
		if (Minecraft.getMinecraft().gameSettings.particleSetting == 1)
			chance = 0.6F;
		else if (Minecraft.getMinecraft().gameSettings.particleSetting == 2)
			chance = 0.2F;

		return chance == 1F || Math.random() < chance;
	}

	private void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellCarrier.class, SpellRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingBroom.class, RenderBroom::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBatSwarm.class, RenderBatSwarm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBrewArrow.class, RenderBrewArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBrew.class, RenderBrewBottle::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLingeringBrew.class, EmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAoE.class, EmptyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, RenderOwl::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySnake.class, RenderSnake::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlindworm.class, RenderBlindworm::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLizard.class, RenderLizard::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityNewt.class, RenderNewt::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityToad.class, RenderToad::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRaven.class, RenderRaven::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityUran.class, RenderUran::new);
		MinecraftForge.EVENT_BUS.register(new RenderBatSwarm.PlayerHider());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCauldron.class, new TileRenderCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGemBowl.class, new TileRenderGemBowl());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlacedItem.class, new TileRenderPlacedItem());
	}

	@Override
	public boolean isFancyGraphicsEnabled() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}

	@Override
	public void handleTarot(ArrayList<TarotInfo> tarots) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		p.openGui(Bewitchment.instance, LibGui.TAROT.ordinal(), p.world, (int) p.posX, (int) p.posY, (int) p.posZ);
		if (Minecraft.getMinecraft().currentScreen instanceof GuiTarots) {
			Minecraft.getMinecraft().addScheduledTask(() -> ((GuiTarots) Minecraft.getMinecraft().currentScreen).loadData(tarots));
		} else {
			GuiTarots gt = new GuiTarots();
			Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(gt));
			Minecraft.getMinecraft().addScheduledTask(() -> gt.loadData(tarots));
		}
	}

	@Override
	public void loadActionsClient(ArrayList<IHotbarAction> actions, EntityPlayer player) {
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getUniqueID() == player.getUniqueID()) {
			ExtraBarButtonsHUD.INSTANCE.setList(actions);
		}
	}

	@Override
	public boolean isPlayerInEndFire() {
		Minecraft mc = Minecraft.getMinecraft();
		IBlockState ibs = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ));
		boolean res = (ibs.getBlock() == ModBlocks.witchfire && ibs.getValue(BlockWitchFire.TYPE) == BlockWitchFire.EnumFireType.ENDFIRE);
		return res;
	}

	@Override
	public void stopMimicking(EntityPlayer p) {
		MimicEventHandler.stopMimicking((AbstractClientPlayer) p);
	}
}
