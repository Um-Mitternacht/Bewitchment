package com.bewitchment.api.misc;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Predicate is here for support of old way of how weakness was handled.
 * Should not use class names theyre not consistent
 */
public class Weakness {
	private final Set<ResourceLocation> cache = Sets.newHashSet();
	private final Predicate<EntityLivingBase> predicate;

	private Weakness(Predicate<EntityLivingBase> predicate) {
		this.predicate = predicate;
	}

	public static Weakness create(Predicate<EntityLivingBase> predicate, String... ids) {
		return new Weakness(predicate).register(ids);
	}

	/**
	 * See {@link Weakness#register(String)}
	 */
	public Weakness register(String... ids) {
		Arrays.stream(ids).forEach(this::register);
		return this;
	}

	/**
	 * <p> To be used by other mods on {@link FMLPreInitializationEvent}</p>
	 * <p> Registerer id into weakness</p>
	 *
	 * @param id ResourceLocation id. e.g "minecraft:vex"
	 * @return the Weakness const, id is being registered to.
	 */
	public Weakness register(String id) {
		if (!id.isEmpty()) cache.add(new ResourceLocation(id));
		return this;
	}

	/**
	 * @param entity Entity to check/
	 * @return True if entity has a weakness.
	 */
	public boolean contains(EntityLivingBase entity) {
		return get(entity) > 1.0F;
	}

	public float get(EntityLivingBase entity) {
		float weakness = 1.0F;

		if (entity != null && (cache.contains(EntityList.getKey(entity)) || predicate.test(entity))) {
			weakness = 1.5F;
			if (entity instanceof EntityPlayer) weakness *= 1.5F;
		}

		return weakness;
	}
}
