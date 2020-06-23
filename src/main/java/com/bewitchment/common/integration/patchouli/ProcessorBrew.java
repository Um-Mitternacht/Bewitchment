package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.registry.Brew;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("unused")
public class ProcessorBrew implements IComponentProcessor {
	private Brew recipe;
	
	@Override
	public void setup(IVariableProvider<String> provider) {
		recipe = GameRegistry.findRegistry(Brew.class).getValue(new ResourceLocation(provider.get("recipe")));
	}
	
	@Override
	public String process(String key) {
		if (recipe == null) return null;
		else if (key.equals("input")) return PatchouliAPI.instance.serializeIngredient(recipe.input);
		else if (key.equals("name")) return new TextComponentTranslation(recipe.effect.getEffectName()).getUnformattedComponentText();
		return null;
	}
}