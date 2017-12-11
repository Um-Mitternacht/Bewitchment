package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.brew.IBrewHurt;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class ShellArmorBrew implements IBrew, IBrewHurt {

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
		return 0xCCFF00;
	}

	@Override
	public String getName() {
		return "shell_armor";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 0);
	}

	@Override
	public void onHurt(LivingHurtEvent event, DamageSource source, EntityLivingBase affected, int amplifier) {
		Entity attacker = source.getImmediateSource();
		int redo = 5 - amplifier;
		if (attacker != null && (redo < 0 || attacker.world.rand.nextInt(redo) == 0)) {
			float damage = event.getAmount();
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(affected), damage);
		}
	}
}
