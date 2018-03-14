package com.bewitchment.common.brew;

import com.bewitchment.api.brew.BrewAttributeModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class MarsWaterBrew extends BrewAttributeModifier {

	public MarsWaterBrew() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void initAtributes() {
		register(SharedMonsterAttributes.LUCK, "f7140fc2-4ed6-11e7-b114-b2f933d5fe66", -1.0D, 0);
		register(SharedMonsterAttributes.ATTACK_SPEED, "f714136e-4ed6-11e7-b114-b2f933d5fe66", -0.10000000149011612D, 2);
		register(SharedMonsterAttributes.ATTACK_DAMAGE, "f7141562-4ed6-11e7-b114-b2f933d5fe66", 0.0D, 0);
	}

	@Override
	public void onStart(World world, BlockPos pos, EntityLivingBase entity, int amplifier) {
		entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 1500, 0));
		entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1500, 0));
	}

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
	}

	@Override
	public boolean isBad() {
		return true;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0x7C0A02;
	}

	@Override
	public String getName() {
		return "mars_water";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 2);
	}
}
