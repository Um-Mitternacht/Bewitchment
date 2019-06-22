package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualConjureWitch extends Ritual {
	public RitualConjureWitch() {
		super(new ResourceLocation(Bewitchment.MODID, "conjure_witch"), Arrays.asList(Util.get(ModObjects.athame), Util.get(Items.APPLE), Util.get(ModObjects.pentacle), Util.get(Items.POISONOUS_POTATO)), null, null, 15, 750, 50, BlockGlyph.NORMAL, BlockGlyph.ENDER, BlockGlyph.NORMAL);
	}
	
	@Override
	public void onFinished(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, pos, caster, inventory);
		if (!world.isRemote) {
			EntityWitch entity = new EntityWitch(world);
			entity.onInitialSpawn(world.getDifficultyForLocation(pos), null);
			entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.rand.nextInt(360), 0);
			for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50))) CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
			world.spawnEntity(entity);
			if (world.rand.nextFloat() < 0.1f) entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 2, false, false));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onClientUpdate(World world, BlockPos pos) {
		for (int i = 0; i < 15; i++) {
			double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
		}
	}
}