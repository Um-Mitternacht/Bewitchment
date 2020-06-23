package com.bewitchment.common.entity.spirit.demon;

import net.minecraft.world.World;

public class EntityDemoness extends EntityDemon {
    public EntityDemoness(World world) {
        super(world);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }
}