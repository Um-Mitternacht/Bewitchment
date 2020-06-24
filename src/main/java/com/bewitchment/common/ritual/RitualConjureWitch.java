package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

public class RitualConjureWitch extends Ritual {
	public RitualConjureWitch() {
		super(new ResourceLocation(Bewitchment.MODID, "conjure_witch"), Arrays.asList(Util.get(ModObjects.athame), Util.get("ingotSilver"), Util.get(ModObjects.pentacle), Util.get(Items.POISONOUS_POTATO)), null, Collections.singletonList(new ItemStack(ModObjects.athame, 1, 0)), 15, 750, 50, BlockGlyph.NORMAL, BlockGlyph.ENDER, BlockGlyph.NORMAL);
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			EntityWitch entity = new EntityWitch(world);
			entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
			entity.setLocationAndAngles(effectivePos.getX(), effectivePos.getY(), effectivePos.getZ(), world.rand.nextInt(360), 0);
			for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
				CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
			world.spawnEntity(entity);
			if (world.rand.nextFloat() < 0.1f)
				entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 2, false, false));
		}
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 15; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}
}