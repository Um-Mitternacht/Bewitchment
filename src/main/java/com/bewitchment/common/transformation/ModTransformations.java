package com.bewitchment.common.transformation;

import com.bewitchment.api.capability.transformations.ITransformation;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModTransformations {

	public static final IForgeRegistry<ITransformation> REGISTRY = new RegistryBuilder<ITransformation>().setName(new ResourceLocation(LibMod.MOD_ID, "transformation")).setType(ITransformation.class).setIDRange(0, 20).create();

	public static SimpleTransformation NONE, WEREWOLF, VAMPIRE, SPECTRE, HUNTER;

	public static void init() {

		NONE = new SimpleTransformation("none");
		WEREWOLF = new SimpleTransformation("werewolf");
		VAMPIRE = new SimpleTransformation("vampire");
		SPECTRE = new SimpleTransformation("spectre");
		HUNTER = new SimpleTransformation("hunter");

		REGISTRY.registerAll(NONE, WEREWOLF, VAMPIRE, SPECTRE, HUNTER);
	}
}
