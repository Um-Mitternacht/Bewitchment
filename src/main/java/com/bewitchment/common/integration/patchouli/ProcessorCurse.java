package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.registry.Curse;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("unused")
public class ProcessorCurse implements IComponentProcessor {
	private Curse recipe;
	
	@Override
	public void setup(IVariableProvider<String> provider) {
		recipe = GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(provider.get("recipe")));
	}
	
	@Override
	public String process(String key) {
		if (recipe == null) return null;
		else if (key.startsWith("input")) {
			int id = Integer.parseInt(key.substring(5));
			if (recipe.input.size() > id) return PatchouliAPI.instance.serializeIngredient(recipe.input.get(id));
		}
		else if (key.equals("name")) {
			return new TextComponentTranslation("curse." + recipe.getRegistryName().toString().replace(":", ".")).getUnformattedComponentText();
		}
		return null;
	}
}
