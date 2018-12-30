package com.bewitchment.common.entity.spirits.ghosts;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/29/2018.
 */
public class EntityBlackDog extends EntityMultiSkin {
	public EntityBlackDog(World worldIn) {
		super(worldIn);
		setSize(1.4F, 1.4F);
	}

	@Override
	public int getSkinTypes() {
		return 5;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().SPIRIT;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 10;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
