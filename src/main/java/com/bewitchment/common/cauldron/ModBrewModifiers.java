package com.bewitchment.common.cauldron;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.api.cauldron.modifiers.BewitchmentModifiers;
import com.bewitchment.common.core.helper.ColorHelper;
import com.bewitchment.common.crafting.IngredientOr;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.TileEntityCauldron;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.oredict.OreIngredient;

public class ModBrewModifiers {
	
	public static void init() {
		
		initApiModifiers();
		
		BewitchmentAPI api = BewitchmentAPI.getAPI();
		api.registerBrewModifier(BewitchmentModifiers.POWER);
		api.registerBrewModifier(BewitchmentModifiers.DURATION);
		api.registerBrewModifier(BewitchmentModifiers.RADIUS);
		api.registerBrewModifier(BewitchmentModifiers.GAS_CLOUD_DURATION);
		api.registerBrewModifier(BewitchmentModifiers.SUPPRESS_ENTITY_EFFECT);
		api.registerBrewModifier(BewitchmentModifiers.SUPPRESS_IN_WORLD_EFFECT);
		api.registerBrewModifier(BewitchmentModifiers.COLOR);
	}
	
	private static void initApiModifiers() {
		BewitchmentModifiers.POWER = new SimpleModifier("power", Ingredient.fromItem(Items.GLOWSTONE_DUST)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};
		
		BewitchmentModifiers.DURATION = new SimpleModifier("length", Ingredient.fromItem(Items.REDSTONE)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return !BewitchmentAPI.getAPI().getPotionFromBrew(brew).isInstant();
			}
		};
		
		BewitchmentModifiers.RADIUS = new SimpleModifier("radius", Ingredient.fromItem(ModItems.sagebrush)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};
		
		BewitchmentModifiers.GAS_CLOUD_DURATION = new SimpleModifier("gas_duration", Ingredient.fromItem(ModItems.hellebore)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
		};
		
		BewitchmentModifiers.SUPPRESS_ENTITY_EFFECT = new SimpleModifier("suppress_entity", Ingredient.fromItem(Items.BRICK)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
			
			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};
		
		BewitchmentModifiers.SUPPRESS_IN_WORLD_EFFECT = new SimpleModifier("suppress_in_world", Ingredient.fromItem(Items.NETHERBRICK)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return brew.hasInWorldEffect();
			}
			
			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};
		
		BewitchmentModifiers.SUPPRESS_PARTICLES = new SimpleModifier("suppress_particles", Ingredient.fromItem(Items.DIAMOND)) {
			
			@Override
			public boolean canApply(IBrewEffect brew) {
				return true;
			}
			
			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
		};
		
		BewitchmentModifiers.COLOR = new IBrewModifier() {
			
			private final ResourceLocation name = new ResourceLocation(LibMod.MOD_ID, "color");
			private final Ingredient ingredient = new IngredientOr(Ingredient.fromItem(Items.DYE), new OreIngredient("dye")); // TODO add the dye tag to items
			
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
					int newColor = DyeUtils.colorFromStack(stack).map(e -> e.getColorValue()).orElse(currentColor);
					return new ModifierResult(ColorHelper.blendColor(currentColor, newColor, 0.5f), ResultType.SUCCESS);
				}
				return new ModifierResult(ResultType.PASS);
			}
			
			@Override
			public boolean hasMultipleLevels() {
				return false;
			}
			
			@Override
			public String getTooltipString(int lvl) {
				return I18n.format("modifier.bewitchment.color", lvl);
			}
		};
	}
}
