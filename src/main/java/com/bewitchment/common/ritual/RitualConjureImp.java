package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.spirit.demon.EntityImp;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

public class RitualConjureImp extends Ritual {
	public RitualConjureImp() {
		super(new ResourceLocation(Bewitchment.MODID, "conjure_imp"), Arrays.asList(Util.get(ModObjects.athame), Util.get("ingotGold"), Util.get("cropHellebore"), Util.get(ModObjects.hellhound_horn)), s -> s instanceof EntityChicken, Collections.singletonList(new ItemStack(ModObjects.athame, 1, 0)), true, 15, 825, 66, BlockGlyph.NETHER, BlockGlyph.ANY, BlockGlyph.NETHER);
	}

	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.sacrifice";
	}

	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isDaytime();
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			EntityImp entity = new EntityImp(world);
			entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
			entity.setLocationAndAngles(effectivePos.getX(), effectivePos.getY(), effectivePos.getZ(), world.rand.nextInt(360), 0);
			for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
				CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
			world.spawnEntity(entity);
		}
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