package com.bewitchment.common.brew.brews;

import com.bewitchment.api.cauldron.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This was created originally in Covens
 * And ported over, in this format
 */
public class RottingBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
			entity.attackEntityFrom(DamageSource.MAGIC, 0.5f + amplifier / 4f);
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
		return 0x4B5320;
	}

	@Override
	public String getName() {
		return "rotting";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		//NO-OP
	}
}
