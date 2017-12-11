package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
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
public class BaneArthropodsBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
			if (amplifier >= 3) {
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
		return true;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public int getColor() {
		return 0x50C878;
	}

	@Override
	public String getName() {
		return "bane_arthropods";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 12);
	}
}
