package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.capability.IEnergy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class was created by Arekkuusu on 20/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class EnergyHandler {

	private EnergyHandler() {
	}

	@SuppressWarnings("ConstantConditions")
	public static <T extends Entity> List<T> getEnergySources(Class<T> type, World world, BlockPos pos, int range) {
		return world.getEntitiesWithinAABB(type, new AxisAlignedBB(pos).grow(range)).stream()
				.filter(entity -> entity.hasCapability(EnergyProvider.ENERGY_CAPABILITY, null)).collect(Collectors.toList());
	}

	/**
	 * Adds the given value to the energy of the player.
	 * <p>
	 * If the amount drained is greater than the available amount,
	 * it automatically gets stored as overchannel
	 * </p>
	 *
	 * @param player The player
	 * @param amount The amount
	 * @return If the amount was greater or equal than 0 and less or equal than the max amount
	 */
	public static boolean addEnergy(EntityPlayer player, int amount) {
		final Optional<IEnergy> optional = getEnergy(player);
		boolean mod = false;

		if (optional.isPresent()) {
			IEnergy energy = optional.get();
			mod = energy.set(energy.get() + amount);
			energy.tickReset();
			if (player instanceof EntityPlayerMP)
				energy.syncTo((EntityPlayerMP) player);
		}
		return mod;
	}

	/**
	 * Returns the {@link IEnergy} interface of the player.
	 *
	 * @param player The player
	 * @return An {@link Optional<IEnergy>} for correctness
	 */
	@SuppressWarnings("ConstantConditions")
	public static Optional<IEnergy> getEnergy(EntityPlayer player) {
		if (player.hasCapability(EnergyProvider.ENERGY_CAPABILITY, null)) {
			return Optional.of(player.getCapability(EnergyProvider.ENERGY_CAPABILITY, null));
		}
		return Optional.empty();
	}

	/**
	 * Sets the max energy a player can hold.
	 *
	 * @param player    The player
	 * @param maxAmount The new max amount
	 */
	public static void setMaxEnergy(EntityPlayer player, int maxAmount) {
		Optional<IEnergy> optional = getEnergy(player);
		if (optional.isPresent() && maxAmount >= 0) {
			optional.get().setMax(maxAmount);
		}
	}

	/**
	 * Sets the regen of the player.
	 * <p>
	 * Any number smaller than 0 will set the regen to -1,
	 * thus deactivating regeneration
	 * </p>
	 *
	 * @param player      The player
	 * @param timeInTicks Ticks
	 */
	public static void setRegen(EntityPlayer player, int timeInTicks) {
		Optional<IEnergy> optional = getEnergy(player);
		optional.ifPresent(iEnergy -> iEnergy.setRegen(timeInTicks));
	}
}
