package com.bewitchment.common.core.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

//Original code created by InsomniaKitten, on 12/16/2018, in the Modded Minecraft's private dev channel.

public final class BewitchmentMethodHandles {
	private static final MethodHandle ENTITY_IS_JUMPING;

	static {
		final MethodHandles.Lookup lookup = MethodHandles.lookup();
		try {
			ENTITY_IS_JUMPING = lookup.unreflectGetter(
					BewitchmentMethodHandles.findField(EntityLivingBase.class, "field_70703_bu")
			);
		} catch (final IllegalAccessException | NoSuchFieldException e) {
			throw new IllegalStateException("MethodHandle lookups failed", e);
		}
	}

	private BewitchmentMethodHandles() {
	}

	public static boolean isJumping(final EntityLivingBase entity) {
		try {
			return (boolean) BewitchmentMethodHandles.ENTITY_IS_JUMPING.invokeExact(entity);
		} catch (final Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static Field findField(final Class<?> owner, final String field) throws NoSuchFieldException {
		final FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
		final String unmappedOwner = remapper.unmap(owner.getName());
		final String mappedField = remapper.mapFieldName(unmappedOwner, field, null);
		final Field declaredField = owner.getDeclaredField(mappedField);
		declaredField.setAccessible(true);
		return declaredField;
	}
}