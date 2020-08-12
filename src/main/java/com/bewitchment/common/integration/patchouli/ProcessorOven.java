package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.registry.OvenRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings({"unused"})
public class ProcessorOven implements IComponentProcessor {
	private OvenRecipe recipe;

	@Override
	public void setup(IVariableProvider<String> provider) {
		recipe = GameRegistry.findRegistry(OvenRecipe.class).getValue(new ResourceLocation(provider.get("recipe")));
	}

	@Override
	public String process(String key) {
		if (recipe == null) return null;
		else if (key.equals("input")) return PatchouliAPI.instance.serializeItemStack(recipe.getInput());
		else if (key.equals("output")) return PatchouliAPI.instance.serializeItemStack(recipe.getOutput());
		else if (key.equals("byproduct")) return PatchouliAPI.instance.serializeItemStack(recipe.getByproduct());
		else if (key.equals("name")) return recipe.getByproduct().getDisplayName();
		return null;
	}
}