package com.bewitchment.common.entity.misc;

import com.bewitchment.api.registry.entity.EntityBroom;
import net.minecraft.world.World;

public class EntityElderBroom extends EntityBroom {
    public EntityElderBroom(World world) {
        super(world);
    }

    @Override
    protected float getSpeed() {
        return 2.25f;
    }

    @Override
    protected float getMaxSpeed() {
        return 1.3f;
    }

    @Override
    protected float getThrust() {
        return 0.15f;
    }

    @Override
    protected int getMagicCost() {
        return 1;
    }
}