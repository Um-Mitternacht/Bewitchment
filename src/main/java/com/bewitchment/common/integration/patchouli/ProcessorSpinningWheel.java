package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.registry.SpinningWheelRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("unused")
public class ProcessorSpinningWheel implements IComponentProcessor {
	private SpinningWheelRecipe recipe;

	@Override
	public void setup(IVariableProvider<String> provider) {
		recipe = GameRegistry.findRegistry(SpinningWheelRecipe.class).getValue(new ResourceLocation(provider.get("recipe")));
	}

	@Override
	public String process(String key) {
		if (recipe == null) return null;
		else if (key.startsWith("input")) {
			int id = Integer.parseInt(key.substring(5));
			if (recipe.getInput().size() > id) return PatchouliAPI.instance.serializeIngredient(recipe.getInput().get(id));
		} else if (key.startsWith("output")) {
			int id = Integer.parseInt(key.substring(6));
			if (recipe.getOutput().size() > id) return PatchouliAPI.instance.serializeItemStack(recipe.getOutput().get(id));
		} else if (key.equals("name")) return recipe.getOutput().get(0).getDisplayName();
		return null;
	}
}