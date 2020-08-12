package com.bewitchment.api.registry;

import lombok.Data;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Predicate;

@Data
public abstract class Contract extends Curse {
    private Predicate<EntityLivingBase> entities;
    private List<Item> items;

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
