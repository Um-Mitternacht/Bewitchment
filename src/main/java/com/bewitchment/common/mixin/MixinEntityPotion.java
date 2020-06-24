package com.bewitchment.common.mixin;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPotion.class)
public abstract class MixinEntityPotion {

	@Inject(method = "onImpact", at = @At("HEAD"))
	protected void applyEffects(RayTraceResult result, CallbackInfo ci) {
		Object obj = this;
		if (obj instanceof EntityPotion) {
			EntityPotion entityPotion = (EntityPotion) obj;
			if (!entityPotion.world.isRemote && result.typeOfHit != RayTraceResult.Type.MISS) {
				BlockPos pos;
				if (result.entityHit != null) pos = result.entityHit.getPosition();
				else pos = result.getBlockPos();
				for (PotionEffect effect : PotionUtils.getEffectsFromStack(entityPotion.getPotion()))
					if (effect.getPotion() instanceof ModPotion)
						((ModPotion) effect.getPotion()).onImpact(entityPotion.world, pos, effect.getAmplifier());
			}
		}
	}

}
