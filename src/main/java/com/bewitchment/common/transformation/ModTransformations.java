package com.bewitchment.common.transformation;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModTransformations {

	public static final IForgeRegistry<ITransformation> REGISTRY = new RegistryBuilder<ITransformation>().setName(new ResourceLocation(LibMod.MOD_ID, "transformation")).setType(ITransformation.class).setIDRange(0, 20).create();

	public static void init() {

		DefaultTransformations.NONE = new SimpleTransformation("none");
		DefaultTransformations.WEREWOLF = new SimpleTransformation("werewolf");
		DefaultTransformations.VAMPIRE = new SimpleTransformation("vampire");
		DefaultTransformations.SPECTRE = new SimpleTransformation("spectre");
		DefaultTransformations.HUNTER = new SimpleTransformation("hunter");

		REGISTRY.registerAll(DefaultTransformations.NONE, DefaultTransformations.WEREWOLF, DefaultTransformations.VAMPIRE, DefaultTransformations.SPECTRE, DefaultTransformations.HUNTER);
	}
}
