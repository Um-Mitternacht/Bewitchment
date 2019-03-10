package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/9/2019.
 */
public class EntityLeonard extends EntityMultiSkin {
	public EntityLeonard(World worldIn) {
		super(worldIn);
		setSize(1F, 3.5F);
	}

	@Override
	public int getSkinTypes() {
		return 1;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
