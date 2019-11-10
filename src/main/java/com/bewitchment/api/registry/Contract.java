package com.bewitchment.api.registry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Predicate;

public abstract class Contract extends Curse {
	public Predicate<EntityLivingBase> entities;
	public List<Item> items;
	
	public Contract(ResourceLocation name, boolean lesser, boolean positive, CurseCondition condition, Predicate<EntityLivingBase> entities, List<Item> items) {
		super(name, null, lesser, positive, condition);
		this.entities = entities;
		this.items = items;
	}
	
	public boolean requiresItems() {
		return items != null;
	}
	
	public boolean requiresEntities() {
		return entities != null;
	}
}
