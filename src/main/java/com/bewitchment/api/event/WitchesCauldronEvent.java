package com.bewitchment.api.event;

import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Fired whenever an event involving the witches cauldron occurs
 * <p>
 * If a method utilizes this {@link Event} as its parameter, the method will
 * receive every child event of this class.<br>
 */
public class WitchesCauldronEvent extends Event {
	private final TileEntityWitchesCauldron cauldron;
	private final World world;
	
	public WitchesCauldronEvent(TileEntityWitchesCauldron cauldron, World world) {
		this.cauldron = cauldron;
		this.world = world;
	}
	
	public TileEntityWitchesCauldron getCauldron() {
		return cauldron;
	}
	
	public World getWorld() {
		return world;
	}
	
	/**
	 * CreatePotionEvent is fired whenever a potion is about to be created and bottled.
	 * <p>
	 * When canceled, no potion will be created and the cauldron will not be drained.
	 */
	@Cancelable
	public static class CreatePotionEvent extends WitchesCauldronEvent {
		private EntityPlayer user;
		private int bottles;
		private boolean boosted;
		private boolean allowHigher;
		
		public CreatePotionEvent(TileEntityWitchesCauldron cauldron, EntityPlayer user, int bottles, boolean boosted) {
			super(cauldron, cauldron.getWorld());
			this.user = user;
			this.bottles = bottles;
			this.boosted = boosted;
			this.allowHigher = false;
		}
		
		public EntityPlayer getUser() {
			return user;
		}
		
		public void setUser(EntityPlayer user) {
			this.user = user;
		}
		
		public int getBottles() {
			return bottles;
		}
		
		public void setBottles(int bottles) {
			this.bottles = bottles;
		}
		
		public boolean isBoosted() {
			return boosted;
		}
		
		public void setBoosted(boolean boosted) {
			this.boosted = boosted;
		}
		
		public boolean allowsHigher() {
			return allowHigher;
		}
		
		public void setAllowHigher(boolean allowHigher) {
			this.allowHigher = allowHigher;
		}
	}
	
	/**
	 * PotionCreatedEvent is fired AFTER the potion to be created is determined.
	 * <p>
	 * When canceled, no potion will be created.
	 */
	@Cancelable
	public static class PotionCreatedEvent extends WitchesCauldronEvent {
		private EntityPlayer user;
		private int bottles;
		private ItemStack potionStack;
		
		public PotionCreatedEvent(TileEntityWitchesCauldron cauldron, EntityPlayer user, int bottles, ItemStack potionStack) {
			super(cauldron, cauldron.getWorld());
			this.user = user;
			this.bottles = bottles;
			this.potionStack = potionStack;
		}
		
		public EntityPlayer getUser() {
			return user;
		}
		
		public void setUser(EntityPlayer user) {
			this.user = user;
		}
		
		public int getBottles() {
			return bottles;
		}
		
		public void setBottles(int bottles) {
			this.bottles = bottles;
		}
		
		public ItemStack getPotionStack() {
			return potionStack;
		}
		
		public void setPotionStack(ItemStack potionStack) {
			this.potionStack = potionStack;
		}
	}
}
