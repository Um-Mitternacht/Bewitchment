package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.ritual.RitualConjureEntity;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;

public class RitualConjureWither extends RitualConjureEntity<EntityWither> {

	public RitualConjureWither() {
		super(Util.newResource("conjure_wither"), Arrays.asList(
				Util.get(ModObjects.athame),
				Util.get(new ItemStack(Items.SKULL, 1, 1)),
				Util.get(Blocks.SOUL_SAND),
				Util.get(Blocks.SOUL_SAND),
				Util.get(Blocks.SOUL_SAND),
				Util.get(Blocks.SOUL_SAND),
				Util.get("cropWormwood"),
				Util.get("cropWormwood"),
				Util.get("cropWormwood"),
				Util.get(ModObjects.ectoplasm)),
				null,
				Collections.singletonList(new ItemStack(ModObjects.athame, 1, 0)),
				true,
				15, 1332, 66,
				BlockGlyph.NETHER, BlockGlyph.NETHER, BlockGlyph.NETHER);
	}

	@Override
	protected void conjureEntity(@NotNull World world, @NotNull BlockPos pos, @NotNull EntityWither wither) {
		super.conjureEntity(world, pos, wither);
		wither.ignite();
	}

	@Override
	protected EntityWither createEntity(World world) {
		return new EntityWither(world);
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 25; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.FLAME, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}
}