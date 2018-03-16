package com.bewitchment.common.infusion;

import com.bewitchment.api.capability.IInfusion;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ModInfusions {

	public static final IForgeRegistry<IInfusion> REGISTRY = new RegistryBuilder<IInfusion>().setName(new ResourceLocation(LibMod.MOD_ID, "infusions")).setType(IInfusion.class).setIDRange(0, 20).create();

	public static final IInfusion OVERWORLD = new SimpleInfusion("overworld", 0);
	public static final IInfusion NETHER = new SimpleInfusion("nether", -1);
	public static final IInfusion END = new SimpleInfusion("end", 1);
	public static final IInfusion DREAM = new SimpleInfusion("dream", 0); // TODO make this the correct number
	public static final IInfusion NONE = new SimpleInfusion("none", 0);

	public static void init() {
		REGISTRY.registerAll(OVERWORLD, NETHER, END, DREAM);
	}
}
