package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

@Data
public abstract class Curse extends IForgeRegistryEntry.Impl<Curse> {
    private final double chance;
    private final List<Ingredient> input;
    private final boolean lesser, positive;
    private final CurseCondition condition;

    public Curse(ResourceLocation name, List<Ingredient> input, boolean lesser, boolean positive, CurseCondition condition) {
        this(name, input, lesser, positive, condition, 1);
    }

    public Curse(ResourceLocation name, List<Ingredient> input, boolean lesser, boolean positive, CurseCondition condition, double chance) {
        setRegistryName(name);
        this.input = input;
        this.lesser = lesser;
        this.positive = positive;
        this.condition = condition;
        this.chance = chance;
    }

    public final boolean matches(ItemStackHandler input) {
        return Util.areISListsEqual(this.input, input);
    }

    public boolean apply(@Nullable EntityPlayer player, int days) {
        if (player != null) {
            if (player.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
                ExtendedPlayer ep = player.getCapability(ExtendedPlayer.CAPABILITY, null);
                ep.addCurse(this, days);
                return true;
            }
        }
        return false;
    }

    public boolean isLesser() {
        return lesser;
    }

    public boolean isPositive() {
        return positive;
    }

    public abstract boolean doCurse(Event event, EntityPlayer target);

    public CurseCondition getCurseCondition() {
        return condition;
    }

    /**
     * EXIST - the curse is active every tick the player exists
     * REACTION - the curse is not active on its own; must be used manually
     */
    public enum CurseCondition {
        EXIST, //add other conditions like SLEEP or so for curses that are only active in certain conditions
        REACTION, BLOCK_BREAK, BLOCK_DROP, KILL, DAMAGE, HURT, INSTANT
    }
}
