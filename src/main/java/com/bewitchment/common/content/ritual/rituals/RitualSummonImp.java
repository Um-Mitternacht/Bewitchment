package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.entity.spirits.demons.EntityImp;
import com.bewitchment.common.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.Optional;
import java.util.Random;

public class RitualSummonImp extends RitualImpl {

	public RitualSummonImp(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	public RitualSummonImp(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<EntityEntry> sacrifices, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, sacrifices, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (!world.isRemote) {
			EntityImp entityimp = new EntityImp(world);
			BlockPos blockpos1 = effectivePosition.add(0, 0, 0);
			entityimp.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.55D, blockpos1.getZ() + 0.5D, 0F, 0F);
			for (EntityPlayerMP entityplayermp : world.getEntitiesWithinAABB(EntityPlayerMP.class, entityimp.getEntityBoundingBox().grow(50.0D))) {
				CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entityimp);
			}
			world.spawnEntity(entityimp);
		}
	}

	@Override
	public void onRandomDisplayTick(World world, BlockPos mainGlyphPos, BlockPos ep, Random rng) {
		for (int i = 0; i < 20; i++) {
			double cx = ep.getX() + 0.5;
			double cy = ep.getY() + 0.5;
			double cz = ep.getZ() + 0.5;
			double sx = cx + rng.nextGaussian() * 0.5;
			double sy = cy + rng.nextGaussian() * 0.5;
			double sz = cz + rng.nextGaussian() * 0.5;
			world.spawnParticle(EnumParticleTypes.FLAME, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz));
		}
	}

	@Override
	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(input, data);
		Optional<ItemStack> oldAthame = input.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst();
		if (oldAthame.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.athame).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 35));
		}
		return oldOutput;
	}

}
