package com.bewitchment.api.divination;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ITarot extends IForgeRegistryEntry<ITarot> {

	/**
	 * @return the ResourceLocation of the artwork for this card
	 */
	public ResourceLocation getTexture();

	/**
	 * Used to define whether or not this card should be shown for a player
	 *
	 * @param player the player whose cards are getting read
	 * @return true if the card can be shown, false otherwise
	 */
	public boolean isApplicableToPlayer(EntityPlayer player);

	/**
	 * @param player the player whose cards are getting read
	 * @return true if this card should show a number
	 */
	public boolean hasNumber(EntityPlayer player);

	/**
	 * An upside down card may be used to display other data, for instance
	 * an reversed card for the familiar shows that the familiar exists
	 * but has been dismissed. If the familiar does not exist this card
	 * shouldn't be shown at all
	 *
	 * @param player the player whose cards are getting read
	 * @return true if this card should be shown upside down
	 */
	public boolean isReversed(EntityPlayer player);

	/**
	 * @return the unlocalized name of this card
	 */
	public String getTranslationKey();

	/**
	 * @param player the player whose cards are getting read
	 * @return the number to display underneath the name
	 */
	public int getNumber(EntityPlayer player);
}
