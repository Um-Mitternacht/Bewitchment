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

	AdapterIRitual ritual;

	@Override
	public void setup(IVariableProvider<String> json) {
		ritual = AdapterIRitual.REGISTRY.getValue(new ResourceLocation(json.get("ritual")));
		Objects.requireNonNull(ritual);
	}

	@Override
	public String process(String val) {
		try {
			if (val.startsWith("circle")) {
				return getCircle(val.substring(6));
			}
			if (val.startsWith("input")) {
				return getInput(val.substring(5));
			}
			if (val.startsWith("output")) {
				return getOutput(val.substring(6));
			}
			if (val.equals("ritualname")) {
				return I18n.format("ritual." + ritual.getRegistryName().toString().replace(':', '.') + ".name");
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
			return PatchouliAPI.instance.serializeItemStack(ritual.getOutputRaw().get(index));
		} catch (Exception e) {
			return "minecraft:air";
		}
	}

	private String getInput(String substring) {
		try {
			int index = Integer.parseInt(substring) - 1;
			return PatchouliAPI.instance.serializeItemStack(ritual.getInput().get(index).getMatchingStacks()[0]);
		} catch (Exception e) {
			return "minecraft:air";
		}
	}

	private String getCircle(String substring) {
		int index = Integer.parseInt(substring);
		if ((ritual.getCircles() & 3) < index - 1) {
			return "bewitchment:textures/gui/books/ritual/circle_center.png";
		}
		return "bewitchment:textures/gui/books/ritual/circle_" + EnumGlyphType.fromMeta((ritual.getCircles() >> (2 * index)) & 3).name().toLowerCase() + "_" + index + ".png";
	}
}
