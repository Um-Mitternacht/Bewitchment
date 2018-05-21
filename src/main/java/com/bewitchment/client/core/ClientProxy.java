package com.bewitchment.client.core;

import java.awt.Color;
import java.util.ArrayList;

import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.client.ResourceLocations;
import com.bewitchment.client.core.event.*;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.client.gui.GuiTarots;
import com.bewitchment.client.handler.*;
import com.bewitchment.client.render.entity.renderer.*;
import com.bewitchment.client.render.tile.TileRenderCauldron;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.magic.BlockWitchFire;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.cauldron.BrewData;
import com.bewitchment.common.core.net.GuiHandler;
import com.bewitchment.common.core.proxy.ISidedProxy;
import com.bewitchment.common.divination.TarotHandler.TarotInfo;
import com.bewitchment.common.entity.*;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemSpellPage;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.TileEntityCauldron;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockAccess;
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
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		registerRenders();
		MinecraftForge.EVENT_BUS.register(new EnergyHUD());
		MinecraftForge.EVENT_BUS.register(new GirdleOfTheWoodedHUD());
		MinecraftForge.EVENT_BUS.register(new BloodViewerHUD());
		MinecraftForge.EVENT_BUS.register(new VampireBloodBarHUD());
		MinecraftForge.EVENT_BUS.register(ExtraBarButtonsHUD.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new WerewolfEventHandler());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Keybinds.registerKeys();
		BlockColors blocks = Minecraft.getMinecraft().getBlockColors();
		// Block Colors
		blocks.registerBlockColorHandler(new BlockCandleColorHandler(), ModBlocks.candle_medium, ModBlocks.candle_small, ModBlocks.candle_medium_lit, ModBlocks.candle_small_lit);

		blocks.registerBlockColorHandler(new IBlockColor() {
			
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				return state.getValue(BlockWitchFire.TYPE).getColor();
			}
		}, ModBlocks.witchfire);
		
		blocks.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				EnumGlyphType type = state.getValue(BlockCircleGlyph.TYPE);
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

		NetworkRegistry.INSTANCE.registerGuiHandler(Bewitchment.instance, new GuiHandler());
	}

	@Override
	public void displayRecordText(ITextComponent text) {
		Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(text.getFormattedText());
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
		MinecraftForge.EVENT_BUS.register(new RenderBatSwarm.PlayerHider());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCauldron.class, new TileRenderCauldron());
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
			System.out.println("Found valid GUI, loading data");
			Minecraft.getMinecraft().addScheduledTask(() -> ((GuiTarots) Minecraft.getMinecraft().currentScreen).loadData(tarots));
		} else {
			System.out.println("Gui not found, loading a new one");
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
		IBlockState ibs = mc.world.getBlockState(new BlockPos(mc.player.posX + 0.5, mc.player.posY + 0.5, mc.player.posZ + 0.5));
		boolean res = (ibs.getBlock() == ModBlocks.witchfire && ibs.getValue(BlockWitchFire.TYPE) == BlockWitchFire.EnumFireType.ENDFIRE);
		System.out.println(res);
		return res;
	}
}
