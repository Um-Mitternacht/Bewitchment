/**
 * This class was created by <ArekkuusuJerii>. It's distributed as
 * part of the Grimoire Of Alice Mod. Get the Source Code in github:
 * https://github.com/ArekkuusuJerii/Grimore-Of-Alice
 * <p>
 * Grimoire Of Alice is Open Source and distributed under the
 * Grimoire Of Alice license: https://github.com/ArekkuusuJerii/Grimoire-Of-Alice/blob/master/LICENSE.md
 */
package com.bewitchment.common.core.statics;

import com.bewitchment.client.core.hud.HudComponent.EnumHudAnchor;
import com.bewitchment.common.lib.LibMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess"})
@Config(modid = LibMod.MOD_ID)
@Mod.EventBusSubscriber
public final class ModConfig {

	@Comment("Change vein sizes, generation height and generation chance")
	@Config.RequiresMcRestart
	@Config.LangKey("bewitchment.config.world_gen")
	public static WorldGen WORLD_GEN = new WorldGen();
	@Comment("Customize the client-side only settings")
	@Config.LangKey("bewitchment.config.client")
	public static ClientConfig CLIENT = new ClientConfig();
	@Comment("The lower this number, the more time it takes for an altar to realize something has changed around it, the better the TPS")
	@Config.RangeInt(min = 1, max = 46656)
	//46656 is the max amount of blocks in altar range, meaning it will scan once per tick. More than that is useless
	public static int altar_scan_blocks_per_tick = 80;

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent evt) {
		if (evt.getModID().equals(LibMod.MOD_ID)) {
			ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
		}
	}

	public static class WorldGen {

		@Comment("Silver Ore gen, this determines how much can spawn in a chunk, and how far up")
		public SilverOre silver = new SilverOre();
		@Comment("Bloodstone Ore gen, this determines how much can spawn in a chunk, and how far up")
		public BloodstoneOre blood_stone = new BloodstoneOre();
		@Comment("Tourmaline Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Tourmaline tourmaline = new Tourmaline();
		@Comment("Malachite Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Malachite malachite = new Malachite();
		@Comment("Tigers Eye Ore gen, this determines how much can spawn in a chunk, and how far up")
		public TigersEye tigers_eye = new TigersEye();
		@Comment("Nuummite Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Nuummite nuummite = new Nuummite();
		@Comment("Garnet Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Garnet garnet = new Garnet();
		@Comment("Jasper Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Jasper jasper = new Jasper();
		@Comment("Salt Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Salt salt = new Salt();
		@Comment("Amethyst Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Amethyst amethyst = new Amethyst();
		@Comment("Alexandrite Ore gen, this determines how much can spawn in a chunk, and how far up")
		public Alexandrite alexandrite = new Alexandrite();
		@Comment("Coquina gen, this determines how much can spawn in a chunk, and how far up")
		public Coquina coquina = new Coquina();
		@Comment("Beehive gen, this determines how many can spawn in a chunk, and how far up")
		public Beehive beehive = new Beehive();

		public static class SilverOre {
			public int silver_min_vein = 1;
			public int silver_max_vein = 8;
			public int silver_min_height = 10;
			public int silver_max_height = 128;
			public int silver_gen_chance = 8;
		}

		public static class BloodstoneOre {
			public int bloodStone_min_vein = 1;
			public int bloodStone_max_vein = 2;
			public int bloodStone_min_height = 10;
			public int bloodStone_max_height = 100;
			public int bloodStone_gen_chance = 6;
		}

		public static class Tourmaline {
			public int tourmaline_min_vein = 1;
			public int tourmaline_max_vein = 2;
			public int tourmaline_min_height = 10;
			public int tourmaline_max_height = 80;
			public int tourmaline_gen_chance = 6;
		}

		public static class Malachite {
			public int malachite_min_vein = 1;
			public int malachite_max_vein = 2;
			public int malachite_min_height = 10;
			public int malachite_max_height = 80;
			public int malachite_gen_chance = 6;
		}

		public static class TigersEye {
			public int tigersEye_min_vein = 1;
			public int tigersEye_max_vein = 2;
			public int tigersEye_min_height = 10;
			public int tigersEye_max_height = 60;
			public int tigersEye_gen_chance = 6;
		}

		public static class Nuummite {
			public int nuummite_min_vein = 1;
			public int nuummite_max_vein = 2;
			public int nuummite_min_height = 10;
			public int nuummite_max_height = 80;
			public int nuummite_gen_chance = 6;
		}

		public static class Garnet {
			public int garnet_min_vein = 1;
			public int garnet_max_vein = 2;
			public int garnet_min_height = 10;
			public int garnet_max_height = 65;
			public int garnet_gen_chance = 6;
		}

		public static class Jasper {
			public int jasper_min_vein = 1;
			public int jasper_max_vein = 2;
			public int jasper_min_height = 10;
			public int jasper_max_height = 80;
			public int jasper_gen_chance = 6;
		}

		public static class Salt {
			public int salt_min_vein = 1;
			public int salt_max_vein = 4;
			public int salt_min_height = 10;
			public int salt_max_height = 128;
			public int salt_gen_chance = 6;
		}

		public static class Amethyst {
			public int amethyst_min_vein = 1;
			public int amethyst_max_vein = 4;
			public int amethyst_min_height = 10;
			public int amethyst_max_height = 65;
			public int amethyst_gen_chance = 6;
		}

		public static class Alexandrite {
			public int alexandrite_min_vein = 1;
			public int alexandrite_max_vein = 2;
			public int alexandrite_min_height = 10;
			public int alexandrite_max_height = 40;
			public int alexandrite_gen_chance = 6;
		}

		public static class Coquina {
			public int coquina_min_vein = 1;
			public int coquina_max_vein = 4;
			public int coquina_min_height = 55;
			public int coquina_max_height = 65;
			public int coquina_gen_chance = 4;
		}

		public static class Beehive {
			public int beehive_gen_chance = 12;
		}
	}

	public static class ClientConfig {
		@Comment("Customize the Energy HUD positions in the screen")
		@Config.LangKey("bewitchment.config.energy_hud")
		public EnergyHUD ENERGY_HUD = new EnergyHUD();

		@Comment("Customize the Blood Drain HUD positions in the screen")
		@Config.LangKey("bewitchment.config.blood_hud")
		public BloodIndicatorHUD BLOOD_HUD = new BloodIndicatorHUD();

		@Comment("Customize the Moon HUD positions in the screen")
		@Config.LangKey("bewitchment.config.moon_hud")
		public MoonHUD MOON_HUD = new MoonHUD();

		@Comment("Customize the currently selected bar action HUD positions in the screen")
		@Config.LangKey("bewitchment.config.current_action_hud")
		public CurrentActionHUD CURRENTACTION_HUD = new CurrentActionHUD();

		@Comment("Customize the action bar HUD positions in the screen")
		@Config.LangKey("bewitchment.config.action_bar")
		public AbilityBarHUD ACTION_BAR_HUD = new AbilityBarHUD();


		@Comment("Customize the vampire blood meter HUD positions in the screen")
		@Config.LangKey("bewitchment.config.vampire_blood_meter")
		public VampireBloodHUD VAMPIRE_METER_HUD = new VampireBloodHUD();

		@Comment("The amount of visual imprecision to give to chalk runes. Use 0 to have them perfectly aligned to the block")
		@Config.RangeDouble(min = 0d, max = 1d)
		public double glyphImprecision = 0.6d;

		public static class EnergyHUD {
			@Comment("Should the energy HUD be automatically hidden?")
			public boolean autoHide = true;

			@Comment("Should the energy HUD be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 10;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 10;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.START_ABSOULTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		}

		public static class BloodIndicatorHUD {
			@Comment("Should the blood indicator be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 10;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 10;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		}

		public static class MoonHUD {
			@Comment("Should the moon indicator be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 10;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 10;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.START_ABSOULTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.START_ABSOULTE;
		}

		public static class VampireBloodHUD {
			@Comment("Should the vampire blood meter be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 9;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 39;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.END_ABSOLUTE;
		}

		public static class AbilityBarHUD {
			@Comment("Should the ability bar be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 130;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 2;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.END_ABSOLUTE;
			@Comment("Setting this to false will cause the hand to show anyway when an ability is selected")
			public boolean hideHandWithAbility = true;
			@Comment("Should the arrows to the extra bar buttons be shown?")
			public boolean showArrowsInBar = true;
			@Comment("When true, scrolling right while having the last itembar slot selected will take you to the action bar")
			public boolean autoJumpToBar = false;
		}

		public static class CurrentActionHUD {
			@Comment("Should the indicator be completely removed?")
			public boolean deactivate = false;

			@Comment("Horizontal position of the HUD in the screen")
			public double x = 0;
			@Comment("Vertical position of the HUD in the screen")
			public double y = 0.25;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
			@Comment("Defines how to calculate the x offset")
			public EnumHudAnchor v_anchor = EnumHudAnchor.START_RELATIVE;
			@Comment("Defines how big the icon is")
			public double scale = 1;
		}
	}
}
