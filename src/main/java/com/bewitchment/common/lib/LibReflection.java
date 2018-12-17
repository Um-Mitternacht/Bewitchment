package com.bewitchment.common.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LibReflection {
	public static final String NETWORK_PLAYER_INFO__PLAYER_TEXTURES = "field_187107_a";

	public static Method jumpFix(String isJumping, String field_70703_bu, Class<EntityLivingBase> base, Class<?> returns, Class<?>... args) {
		try {
			return ObfuscationReflectionHelper.findMethod(base, field_70703_bu, returns, args);
		} catch (Exception e) {
			return ObfuscationReflectionHelper.findMethod(base, isJumping, returns, args);
		}
	}

	public static Field jumpField(String isJumping, String field_70703_bu, Class<EntityLivingBase> base) {
		try {
			return ObfuscationReflectionHelper.findField(base, field_70703_bu);
		} catch (Exception e) {
			return ObfuscationReflectionHelper.findField(base, isJumping);
		}
	}
}
