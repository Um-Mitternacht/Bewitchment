package com.bewitchment.common.mixin;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;


@Mixin(EntityArrow.class)
public abstract class MixinEntityTippedArrow {

    @SuppressWarnings("deprecation")
    @Inject(method = "onHit", at = @At("HEAD"))
    protected void applyEffects(RayTraceResult result, CallbackInfo ci) {
        Object obj = this;
        if (obj instanceof EntityTippedArrow) {
            EntityTippedArrow entityTippedArrow = (EntityTippedArrow) obj;
            boolean flag = false;
            Set<PotionEffect> effects = ObfuscationReflectionHelper.getPrivateValue(EntityTippedArrow.class, entityTippedArrow, "customPotionEffects", "field_184561_h");
            if (!entityTippedArrow.world.isRemote && result.typeOfHit != RayTraceResult.Type.MISS) {
                BlockPos pos;
                if (result.entityHit != null) pos = result.entityHit.getPosition();
                else pos = result.getBlockPos();
                for (PotionEffect effect : effects)
                    if (effect.getPotion() instanceof ModPotion && ((ModPotion) effect.getPotion()).onImpact(entityTippedArrow.world, pos, effect.getAmplifier()))
                        flag = true;
            }
            if (flag) effects.clear();
        }
    }
}
