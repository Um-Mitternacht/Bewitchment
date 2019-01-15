package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 1/13/2019.
 */
public class EntityDemonBase extends EntityMultiSkin implements IAnimatedEntity, IMob {
	private int animationTick;
	private Animation currentAnimation;

	public EntityDemonBase(World worldIn) {
		super(worldIn);
	}

	@Override
	public int getSkinTypes() {
		return 0;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[0];
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
