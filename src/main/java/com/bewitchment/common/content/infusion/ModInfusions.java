package com.bewitchment.common.content.infusion;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModInfusions {

	public static final IForgeRegistry<IInfusion> REGISTRY = new RegistryBuilder<IInfusion>().setName(new ResourceLocation(LibMod.MOD_ID, "infusions")).setType(IInfusion.class).setIDRange(0, 20).create();

	public static void init() {
		DefaultInfusions.OVERWORLD = new SimpleInfusion("overworld", 0);
		DefaultInfusions.NETHER = new SimpleInfusion("nether", -1);
		DefaultInfusions.END = new SimpleInfusion("end", 1);
		DefaultInfusions.DREAM = new SimpleInfusion("dream", 0); // TODO make this the correct number
		DefaultInfusions.NONE = new SimpleInfusion("none", 0);
		REGISTRY.registerAll(DefaultInfusions.NONE, DefaultInfusions.OVERWORLD, DefaultInfusions.NETHER, DefaultInfusions.END, DefaultInfusions.DREAM);
	}
}
