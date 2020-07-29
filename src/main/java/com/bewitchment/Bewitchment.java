package com.bewitchment;

//R DZH SVIV
//HFMXLMFIV11
//HKRMLHZFIFH111
//MLGOVTZOGVMWVI
//OVHLERPP
//WZGFIZ
//GSILFTS GSVHV ORMVH R WL WVXIVV
//GSZG YB NVIVOB KFGGRMT NB MZNV RM
//NB RMUOFVMXV TILDH, ZMW GSFH, R YVXLNV RNNLIGZO
//XBYVIMVGRX DVY


//GL HLNV, R ZN YFG Z HSZWV
//YFG SVIV, R VCVIG KLDVI LEVI GSVN
//R NZWV Z XZHGOV SVIV LM GSRH SROO
//ZMW SZEV KFG NB UOZT RM GSV TILFMW
//GSRH RH NB MVD DLIOW


//HSLFOW GSV GIVHKZHHVIH LU GSV LOW DLIOW
//ZIIREV SVIV, YVZIRMT SLHGRORGRVH
//GSVB DROO YV NVG DRGS DIZGS

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayerHandler;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.message.*;
import com.bewitchment.client.handler.ClientHandler;
import com.bewitchment.common.command.CommandCurse;
import com.bewitchment.common.command.CommandFortune;
import com.bewitchment.common.handler.*;
import com.bewitchment.common.integration.Compat;
import com.bewitchment.common.integration.dynamictrees.DynamicTreesCompat;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompat;
import com.bewitchment.common.network.PacketChangeBiome;
import com.bewitchment.common.village.VillagerTradeHandler;
import com.bewitchment.common.world.gen.ModWorldGen;
import com.bewitchment.proxy.ServerProxy;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = Bewitchment.MODID, name = Bewitchment.NAME, version = Bewitchment.VERSION, guiFactory = Bewitchment.GUI_FACTORY, dependencies = Bewitchment.DEPENDENCIES)
public class Bewitchment {
	public static final String MODID = "bewitchment", NAME = "Bewitchment", VERSION = "0.22.27", GUI_FACTORY = "com.bewitchment.client.gui.GuiFactory";
	public static final String DEPENDENCIES = "required-after:baubles;required-after:patchouli;after:mowziesmobs;after:covetedmobs;after:elementaristics;after:miskatonicmysteries;after:betteranimalsplus;after:toughasnails;after:consecration;after:quark;after:dynamictrees@[1.12.2-0.9.1e,);after:thaumcraft@[6.1.BETA26,);";
	public static final Logger logger = LogManager.getLogger(NAME);
	public static final CreativeTabs tab = new CreativeTabs(Bewitchment.MODID) {
		@Override
		public ItemStack createIcon() {
			setBackgroundImageName("item_search.png");
			return new ItemStack(ModObjects.stone_witches_altar);
		}

		@Override
		public boolean hasSearchBar() {
			return true;
		}
	};
	@Mod.Instance
	public static Bewitchment instance;
	@SidedProxy(serverSide = "com.bewitchment.proxy.ServerProxy", clientSide = "com.bewitchment.proxy.ClientProxy")
	public static ServerProxy proxy;
	public static SimpleNetworkWrapper network = new SimpleNetworkWrapper(MODID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger.info("Remember when I told you how my");
		logger.info("Kin is different in some ways?");

		Bewitchment.proxy.preInit(event);

		CapabilityManager.INSTANCE.register(ExtendedPlayer.class, new ExtendedPlayer(), ExtendedPlayer::new);
		CapabilityManager.INSTANCE.register(MagicPower.class, new MagicPower(), MagicPower::new);
		MinecraftForge.EVENT_BUS.register(new ExtendedPlayerHandler());

		if (FMLCommonHandler.instance().getSide().isClient()) MinecraftForge.EVENT_BUS.register(new ClientHandler());
		MinecraftForge.EVENT_BUS.register(new MaterialHandler());
		MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
		MinecraftForge.EVENT_BUS.register(new MiscHandler());
		MinecraftForge.EVENT_BUS.register(new PoppetHandler());
		MinecraftForge.EVENT_BUS.register(new ArmorHandler());
		MinecraftForge.EVENT_BUS.register(new CurseHandler());
		MinecraftForge.EVENT_BUS.register(new Compat());
		if (Loader.isModLoaded("thaumcraft")) MinecraftForge.EVENT_BUS.register(new ThaumcraftCompat());
		if (Loader.isModLoaded("dynamictrees")) MinecraftForge.EVENT_BUS.register(new DynamicTreesCompat());
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);

		NetworkRegistry.INSTANCE.registerGuiHandler(Bewitchment.instance, new GuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		logger.info("It's a fact, she is exactly that!");
		logger.info("A harbinger of death from the world of witchcraft,");
		logger.info("And she's feeding them cakes and her ale to this innocent boy,");
		logger.info("And her magic brings dismay!");

		logger.info("I hear her in the wind, the bane of our town");
		logger.info("Come with me, father, I'm to expose a heathen");

		proxy.registerRendersInit();

		ModRecipes.init();
		Compat.init();

		int id = -1;
		Bewitchment.network.registerMessage(SyncExtendedPlayer.Handler.class, SyncExtendedPlayer.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(SpawnParticle.Handler.class, SpawnParticle.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(SpawnBubble.Handler.class, SpawnBubble.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(TeleportPlayerClient.Handler.class, TeleportPlayerClient.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(SyncBroom.Handler.class, SyncBroom.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(CauldronTeleport.Handler.class, CauldronTeleport.class, ++id, Side.SERVER);
		Bewitchment.network.registerMessage(TarotMessage.Handler.class, TarotMessage.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(SyncDragonsBloodBroom.Handler.class, SyncDragonsBloodBroom.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(DismountBroomMessage.Handler.class, DismountBroomMessage.class, ++id, Side.SERVER);
		Bewitchment.network.registerMessage(DismountBroomMessage.Handler.class, DismountBroomMessage.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(SyncExtendedWorld.Handler.class, SyncExtendedWorld.class, ++id, Side.CLIENT);
		Bewitchment.network.registerMessage(PacketChangeBiome.Handler.class, PacketChangeBiome.class, ++id, Side.CLIENT);

		for (Item item : ForgeRegistries.ITEMS) {
			if (Util.isRelated(true, item, "silver")) MaterialHandler.SILVER_ARMOR.add(item);
			if (Util.isRelated(false, item, "silver")) MaterialHandler.SILVER_TOOLS.add(item);
			if (Util.isRelated(true, item, "cold_iron")) MaterialHandler.COLD_IRON_ARMOR.add(item);
			if (Util.isRelated(false, item, "cold_iron")) MaterialHandler.COLD_IRON_TOOLS.add(item);
		}

		/*Village*/
		VillagerTradeHandler.INSTANCE.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.postInit();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandFortune());
		event.registerServerCommand(new CommandCurse());
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent evt) {
		FMLCommonHandler.instance().getMinecraftServerInstance().setAllowFlight(true);
	}
}
