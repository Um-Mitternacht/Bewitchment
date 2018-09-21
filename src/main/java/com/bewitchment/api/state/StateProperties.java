package com.bewitchment.api.state;

import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.state.enums.EnumWoodType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;


public class StateProperties {

	public static final PropertyEnum<EnumWoodType> WOOD_TYPE = PropertyEnum.create("wood", EnumWoodType.class);

	public static final PropertyEnum<EnumGlyphType> GLYPH_TYPE = PropertyEnum.create("type", EnumGlyphType.class);

	public static final PropertyInteger LETTER = PropertyInteger.create("letter", 0, 5);

	public static final PropertyInteger MIRROR_VARIANTS = PropertyInteger.create("mirror", 0, 3);

	public static final PropertyBool LOWER_HALF = PropertyBool.create("half");
}
