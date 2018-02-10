package com.bewitchment.common.brew;

import com.bewitchment.api.brew.BrewAtributeModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class HolyWaterBrew extends BrewAtributeModifier {

	//Todo: Make this damage vampires and spectres when their respective codebases are finished
	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.isEntityUndead()) {
			if (amplifier >= 3 && entity.isEntityUndead()) {
				entity.setFire(500);
				entity.attackEntityFrom(DamageSource.MAGIC, 20);
			} else if (amplifier == 2) {
				entity.attackEntityFrom(DamageSource.MAGIC, 16);
			} else {
				entity.attackEntityFrom(DamageSource.MAGIC, 10);
			}
		}
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
		return 0x8DA399;
	}

	@Override
	public String getName() {
		return "holy_water";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 4);
	}

	@Override
	protected void initAtributes() {
		register(SharedMonsterAttributes.MOVEMENT_SPEED, "70c48882-4e42-11e7-b114-b2f933d5fe66", -0.15000000596046448D, 2);
	}
}
