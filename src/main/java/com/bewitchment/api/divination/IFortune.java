package com.bewitchment.api.divination;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public interface IFortune extends IForgeRegistryEntry<IFortune> {

	/**
	 * This works like a lottery ticket extraction, all the (applicable) fortunes get this number of tickets each.
	 * One is then extracted, choosing what fortune will be read from the crystal ball to the player
	 *
	 * @return The number of tickets this fortune should account for. The higher the number the higher the chance
	 */
	public int getDrawingWeight();

	/**
	 * This method is called when a player is being told their future to decide what fortunes are
	 * available to them. Use this to exclude profecies that only appear once or in similar particular cases
	 *
	 * @param player the player the fortunes are being read to
	 * @return true if this fortune is available to be read, false otherwise
	 */
	public boolean canBeUsedFor(@Nonnull EntityPlayer player);

	/**
	 * Used to determine if now is a good moment to execute the profecy.
	 * Don't use this to do stuff linked to the profecy, as this method
	 * gets called multiple times per second. Use {@link #apply(EntityPlayer)} for that.
	 *
	 * @param player the player this fortune is being applied to
	 * @return true if this fortune can and should be applied right now
	 */
	public abstract boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player);

	/**
	 * Execute the logic of the profecy
	 * On "instant" profecies this should return true.
	 * On event dependent profecies this should mark the fortune as active in the capability,
	 * while the removable part should be set after the event has happened succesfully
	 * Only called server side
	 *
	 * @param player the player this fortune is being applied to
	 * @return true if the fortune should be automatically removed after this
	 */
	public boolean apply(@Nonnull EntityPlayer player);

	/**
	 * @return true if the fortune can be considered negative
	 */
	public boolean isNegative();

	/**
	 * @return the unlocalized name for the card.
	 */
	default String getTranslationKey() {
		return "fortunes." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".name";
	}
}
