package com.bewitchment.common.brew.brews;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bewitchment.api.cauldron.brew.IBrew;
import com.bewitchment.api.cauldron.brew.special.IBrewHurt;

/**
 * This was created originally in Covens
 * And ported over, in this format
 */

public class ArrowDeflectionBrew implements IBrew, IBrewHurt {

	Field inground = ReflectionHelper.findField(EntityArrow.class, "inGround", "field_70254_i");
	Method arrowstack = ReflectionHelper.findMethod(EntityArrow.class, "getArrowStack", "func_184550_j");

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO - OP
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0xFFFACD;
	}

	@Override
	public String getName() {
		return "arrow_deflection";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 15);
	}

	@Override
	public void onHurt(LivingHurtEvent event, DamageSource source, EntityLivingBase affected, int amplifier) {
		amplifier++;
		affected.world.getEntitiesWithinAABB(EntityArrow.class, affected.getEntityBoundingBox().expand(amplifier / 3, 1, amplifier / 3).expand(-amplifier / 3, -1, -amplifier / 3))
				.parallelStream()
				.filter(a -> a.shootingEntity != affected)
				.filter(a -> !isInGround(a))
				.forEach(a -> {
					if (!affected.world.isRemote && a.pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
						ItemStack arrow;
						try {
							arrow = (ItemStack) arrowstack.invoke(a);
							EntityItem ei = new EntityItem(affected.world, a.posX, a.posY, a.posZ, arrow);
							affected.world.spawnEntity(ei);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}

					}
					a.setDead();
				});
	}

	private boolean isInGround(EntityArrow a) {
		try {
			return (boolean) inground.get(a);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
}