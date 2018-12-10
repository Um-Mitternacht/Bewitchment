package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/10/2018.
 */
public class EntityHellhound extends EntityMultiSkin {
	public EntityHellhound(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}

	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.DROWN, 1.0F);
		}
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
