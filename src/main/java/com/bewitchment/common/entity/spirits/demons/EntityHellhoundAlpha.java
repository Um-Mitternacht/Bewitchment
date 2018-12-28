package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/28/2018.
 */
public class EntityHellhoundAlpha extends EntityMultiSkin {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/hellhound");
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityHellhound.class, DataSerializers.VARINT);

	public EntityHellhoundAlpha(World worldIn) {
		super(worldIn);
		setSize(1.6F, 1.6F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.DROWN, 2.5F);
		}
	}
}
