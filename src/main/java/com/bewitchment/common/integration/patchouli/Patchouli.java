package com.bewitchment.common.integration.patchouli;

import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.state.StateProperties;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Patchouli {

	private static Predicate<IBlockState> anyGlyph = state -> (state.getBlock() == ModBlocks.ritual_glyphs) && (state.getValue(StateProperties.GLYPH_TYPE) != EnumGlyphType.GOLDEN);
	private static Predicate<IBlockState> goldGlyph = state -> (state.getBlock() == ModBlocks.ritual_glyphs) && (state.getValue(StateProperties.GLYPH_TYPE) == EnumGlyphType.GOLDEN);

	public static void init() {
		PatchouliAPI.instance.registerMultiblock(new ResourceLocation(LibMod.MOD_ID, "circle_small"), PatchouliAPI.instance.makeMultiblock(new String[][]{
				{
						"  AAA  ", " A   A ", "A     A", "A  0  A", "A     A", " A   A ", "  AAA  ",
				}
		}, 'A', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.ANY), anyGlyph), '0', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.GOLDEN), goldGlyph), ' ', PatchouliAPI.instance.anyMatcher()).setSymmetrical(true));
		PatchouliAPI.instance.registerMultiblock(new ResourceLocation(LibMod.MOD_ID, "circle_medium"), PatchouliAPI.instance.makeMultiblock(new String[][]{
				{
						"   AAAAA   ", "  A     A  ", " A  AAA  A ", "A  A   A  A", "A A     A A", "A A  0  A A", "A A     A A", "A  A   A  A", " A  AAA  A ", "  A     A  ", "   AAAAA   "
				}
		}, 'A', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.ANY), anyGlyph), '0', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.GOLDEN), goldGlyph), ' ', PatchouliAPI.instance.anyMatcher()).setSymmetrical(true));
		PatchouliAPI.instance.registerMultiblock(new ResourceLocation(LibMod.MOD_ID, "circle_big"), PatchouliAPI.instance.makeMultiblock(new String[][]{
				{
						"    AAAAAAA    ", "   A       A   ", "  A  AAAAA  A  ", " A  A     A  A ", "A  A  AAA  A  A", "A A  A   A  A A", "A A A     A A A", "A A A  0  A A A", "A A A     A A A", "A A  A   A  A A", "A  A  AAA  A  A", " A  A     A  A ", "  A  AAAAA  A  ", "   A       A   ", "    AAAAAAA    "
				}
		}, 'A', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.ANY), anyGlyph), '0', PatchouliAPI.instance.predicateMatcher(ModBlocks.ritual_glyphs.getDefaultState().withProperty(StateProperties.GLYPH_TYPE, EnumGlyphType.GOLDEN), goldGlyph), ' ', PatchouliAPI.instance.anyMatcher()).setSymmetrical(true));
	}

	public static List<Ingredient> getInputsFromRegistry(String registry, String name, String type) {
		try {
			if ("bewitchment:rituals".equals(registry)) {
				AdapterIRitual ritual = AdapterIRitual.REGISTRY.getValue(new ResourceLocation(name));
				if ("input".equals(type)) {
					return ritual.getInput();
				} else if ("output".equals(type)) {
					return ritual.getOutputRaw().parallelStream().map(is -> Ingredient.fromStacks(is)).collect(Collectors.toList());
				}
			}
		} catch (NullPointerException e) {
			return Lists.newArrayList();
		}
		return Lists.newArrayList();
	}
}

