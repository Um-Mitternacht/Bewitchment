package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class Curse extends IForgeRegistryEntry.Impl<Curse> {
	public final double chance;
	final List<Ingredient> input;
	final boolean isLesser;
	final CurseCondition condition;
	private int level;
	private int curseTime;
	
	public Curse(ResourceLocation name, List<Ingredient> input, boolean isLesser, CurseCondition condition) {
		this(name, input, isLesser, condition, 0);
	}
	
	public Curse(ResourceLocation name, List<Ingredient> input, boolean isLesser, CurseCondition condition, double chance) {
		setRegistryName(name);
		this.input = input;
		this.isLesser = isLesser;
		this.condition = condition;
		this.chance = chance;
	}
	
	@Nullable
	public static EntityPlayer getPlayerFromTaglock(ItemStackHandler handler) {
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack temp = handler.getStackInSlot(i);
			if (temp.getItem() instanceof ItemTaglock && temp.hasTagCompound() && temp.getTagCompound().hasKey("boundId")) {
				return Util.findPlayer(UUID.fromString(temp.getTagCompound().getString("boundId")));
			}
		}
		return null;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public final boolean matches(ItemStackHandler input) {
		return Util.areISListsEqual(this.input, input);
	}
	
	private boolean isValid(EntityPlayer player) {
		return player != null;
	}
	
	public boolean apply(@Nullable EntityPlayer player, int days, int level) {
		if (isValid(player)) {
			if (player.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
				ExtendedPlayer ep = player.getCapability(ExtendedPlayer.CAPABILITY, null);
				this.level = level;
				ep.addCurse(this, days);
				return true;
			}
		}
		return false;
	}
	
	public boolean isLesser() {
		return isLesser;
	}
	
	public abstract boolean doCurse(EntityPlayer target);
	
	public CurseCondition getCurseCondition() {
		return condition;
	}
	
	/**
	 * EXIST - the curse is active every tick the player exists
	 * REACTION - the curse is not active on its own; must be used manually
	 */
	public enum CurseCondition {
		EXIST, //add other conditions like SLEEP or so for curses that are only active in certain conditions
		REACTION, RANDOM
	}
}
