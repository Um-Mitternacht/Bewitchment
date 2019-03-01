package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.core.helper.ColorHelper;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Arrays;

public class ModBrewModifiers {

	public static final int[] dyeColors = {16383998, 16351261, 13061821, 3847130, 16701501, 8439583, 15961002, 4673362, 10329495, 1481884, 8991416, 3949738, 8606770, 6192150, 11546150, 1908001};

	public static void init() {

		initApiModifiers();

		BewitchmentAPI api = BewitchmentAPI.getAPI();
		api.registerBrewModifier(DefaultModifiers.POWER);
		api.registerBrewModifier(DefaultModifiers.DURATION);
		api.registerBrewModifier(DefaultModifiers.RADIUS);
		api.registerBrewModifier(DefaultModifiers.GAS_CLOUD_DURATION);
		api.registerBrewModifier(DefaultModifiers.SUPPRESS_ENTITY_EFFECT);
		api.registerBrewModifier(DefaultModifiers.SUPPRESS_IN_WORLD_EFFECT);
		api.registerBrewModifier(DefaultModifiers.COLOR);
		api.registerBrewModifier(DefaultModifiers.SUPPRESS_PARTICLES);
	}

	private static void initApiModifiers() {
		DefaultModifiers.POWER = new SimpleModifier("power", Ingredient.fromItem(Items.GLOWSTONE_DUST)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};

		DefaultModifiers.DURATION = new SimpleModifier("length", Ingredient.fromItem(Items.REDSTONE)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return !BewitchmentAPI.getAPI().getPotionFromBrew(brew).isInstant();
			}
		};

		DefaultModifiers.RADIUS = new SimpleModifier("radius", Ingredient.fromItem(ModItems.sagebrush)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};

		DefaultModifiers.GAS_CLOUD_DURATION = new SimpleModifier("gas_duration", Ingredient.fromItem(ModItems.yew_aril)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};

		DefaultModifiers.SUPPRESS_ENTITY_EFFECT = new SimpleModifier("suppress_entity", Ingredient.fromItem(ModItems.toe_of_frog)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}

			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};

		DefaultModifiers.SUPPRESS_IN_WORLD_EFFECT = new SimpleModifier("suppress_in_world", Ingredient.fromItem(ModItems.chrysanthemum)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return brew.hasInWorldEffect();
			}

			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};

		//Fixme: Make sure this actually works
		DefaultModifiers.SUPPRESS_PARTICLES = new SimpleModifier("suppress_particles", Ingredient.fromItem(ModItems.chromatic_quill)) {

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}

			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};

		DefaultModifiers.COLOR = new IBrewModifier() {

			private final ResourceLocation name = new ResourceLocation(LibMod.MOD_ID, "color");
			private final Ingredient ingredient = new CompoundIngredient(Arrays.asList(Ingredient.fromItem(Items.DYE), new OreIngredient("dye"))) {
			}; // TODO add the dye tag to items

			@Override
			public IBrewModifier setRegistryName(ResourceLocation name) {
				throw new UnsupportedOperationException("Don't mess with bewitchment default implementation of modifiers!");
			}

			@Override
			public Class<IBrewModifier> getRegistryType() {
				return IBrewModifier.class;
			}

			@Override
			public ResourceLocation getRegistryName() {
				return name;
			}

			@Override
			public Ingredient getJEIStackRepresentative() {
				return ingredient;
			}

			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}

			@Override
			public ModifierResult acceptIngredient(IBrewEffect brew, ItemStack stack, IBrewModifierList currentModifiers) {
				if (DyeUtils.isDye(stack)) {
					int currentColor = currentModifiers.getLevel(this).orElse(TileEntityCauldron.DEFAULT_COLOR);
					int newColor = DyeUtils.colorFromStack(stack).map(e -> getDyeColor(e)).orElse(currentColor);
					return new ModifierResult(ColorHelper.blendColor(currentColor, newColor, 0.5f), ResultType.SUCCESS);
				}
				return new ModifierResult(ResultType.PASS);
			}

			@Override
			public boolean hasMultipleLevels() {
				return false;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTooltipString(int lvl) {
				return I18n.format("modifier.bewitchment.color", String.format("%06X", lvl));
			}
		};
	}

	public static int getDyeColor(EnumDyeColor dye) {
		return dyeColors[dye.ordinal()];
	}
}
