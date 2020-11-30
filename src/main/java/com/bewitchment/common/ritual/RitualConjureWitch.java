package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.ritual.RitualConjureEntity;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RitualConjureWitch extends RitualConjureEntity<EntityWitch> {

	public RitualConjureWitch() {
		super(Util.newResource("conjure_witch"), Arrays.asList(
				Util.get(ModObjects.athame),
				Util.get("ingotSilver"),
				Util.get(ModObjects.pentacle),
				Util.get(Items.POISONOUS_POTATO)),
				null,
				Collections.singletonList(new ItemStack(ModObjects.athame, 1, 0)),
				true,
				15, 750, 50,
				BlockGlyph.NORMAL, BlockGlyph.ENDER, BlockGlyph.NORMAL);
	}

	@Override
	protected void conjureEntity(@NotNull World world, @NotNull BlockPos pos, @NotNull EntityWitch witch) {
		super.conjureEntity(world, pos, witch);
		if (world.rand.nextFloat() < 0.1F)
			witch.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 2, false, false));
	}

	@Override
	protected EntityWitch createEntity(World world) {
		return new EntityWitch(world);
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		Random rand = world.rand;
		double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;

		for (int i = 0; i < 15; i++) {
			double sx = cx + rand.nextGaussian() * 0.5, sy = cy + rand.nextGaussian() * 0.5, sz = cz + rand.nextGaussian() * 0.5;
			SpawnParticle particle = new SpawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
			Bewitchment.network.sendToDimension(particle, world.provider.getDimension());
		}
	}
}