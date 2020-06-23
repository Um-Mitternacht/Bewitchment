package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.registry.DistilleryRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("unused")
public class ProcessorDistillery implements IComponentProcessor {
	private DistilleryRecipe recipe;
	
	@Override
	public void setup(IVariableProvider<String> provider) {
		recipe = GameRegistry.findRegistry(DistilleryRecipe.class).getValue(new ResourceLocation(provider.get("recipe")));
	}
	
	@Override
	public String process(String key) {
		if (recipe == null) return null;
		else if (key.startsWith("input")) {
			int id = Integer.parseInt(key.substring(5));
			if (recipe.input.size() > id) return PatchouliAPI.instance.serializeIngredient(recipe.input.get(id));
		}
		else if (key.startsWith("output")) {
			int id = Integer.parseInt(key.substring(6));
			if (recipe.output.size() > id) return PatchouliAPI.instance.serializeItemStack(recipe.output.get(id));
		}
		else if (key.equals("name")) return recipe.output.get(0).getDisplayName();
		return null;
	}
}