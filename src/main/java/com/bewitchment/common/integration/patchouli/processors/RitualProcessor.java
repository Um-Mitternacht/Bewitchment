package com.bewitchment.common.integration.patchouli.processors;

import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Objects;

public class RitualProcessor implements IComponentProcessor {

	private AdapterIRitual ritual;

	@Override
	public void setup(IVariableProvider<String> json) {
		this.ritual = AdapterIRitual.REGISTRY.getValue(new ResourceLocation(json.get("ritual")));
		Objects.requireNonNull(this.ritual);
	}

	@Override
	public String process(String val) {
		try {
			if (val.startsWith("circle")) {
				return this.getCircle(val.substring(6));
			}
			if (val.startsWith("input")) {
				return this.getInput(val.substring(5));
			}
			if (val.startsWith("output")) {
				return this.getOutput(val.substring(6));
			}
			if ("ritualname".equals(val)) {
				return I18n.format("ritual.bewitchment." + this.ritual.getRegistryName().getPath() + ".name");
			}
			if ("ritual".equals(val)) {
				return this.ritual.getRegistryName().toString();
			}
			if ("separator".equals(val)) {
				return this.ritual.getOutputRaw().isEmpty() ? "bewitchment:textures/gui/books/ritual/separator_none.png" : "bewitchment:textures/gui/books/ritual/separator.png";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		throw new RuntimeException("Unrecognized ritual value: " + val);
	}

	private String getOutput(String substring) {
		try {
			int index = Integer.parseInt(substring) - 1;
			return PatchouliAPI.instance.serializeItemStack(this.ritual.getOutputRaw().get(index));
		} catch (Exception e) {
			return "minecraft:air";
		}
	}

	private String getInput(String substring) {
		try {
			int index = Integer.parseInt(substring) - 1;
			return PatchouliAPI.instance.serializeItemStack(this.ritual.getInput().get(index).getMatchingStacks()[0]);
		} catch (Exception e) {
			return "minecraft:air";
		}
	}

	private String getCircle(String substring) {
		int index = Integer.parseInt(substring);
		if ((this.ritual.getCircles() & 3) < (index - 1)) {
			return "bewitchment:textures/gui/books/ritual/circle_center.png";
		}
		return "bewitchment:textures/gui/books/ritual/circle_" + EnumGlyphType.fromMeta((this.ritual.getCircles() >> (2 * index)) & 3).name().toLowerCase() + "_" + index + ".png";
	}
}

