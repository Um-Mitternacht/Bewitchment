package com.bewitchment.api.event;

import com.bewitchment.api.registry.Curse;
import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Fired whenever an event involving curses occurs
 * <p>
 * If a method utilizes this {@link Event} as its parameter, the method will
 * receive every child event of this class.<br>
 */
@Data
public class CurseEvent extends Event {
    private EntityPlayer target;
    private Curse curse;
    private int curseDuration;

    public CurseEvent(EntityPlayer target, Curse curse, int curseDuration) {
        this.target = target;
        this.curse = curse;
        this.curseDuration = curseDuration;
    }

    /**
     * PlayerCursedEvent is fired whenever a caster is attempting to curse a player.
     * <p>
     * When canceled, the target will not be cursed.
     */
    @Cancelable
    public static class PlayerCursedEvent extends CurseEvent {
        private EntityPlayer caster;

        public PlayerCursedEvent(EntityPlayer target, EntityPlayer caster, Curse curse, int curseDuration) {
            super(target, curse, curseDuration);
            this.caster = caster;
        }

        public EntityPlayer getCaster() {
            return caster;
        }

        public void setCaster(EntityPlayer caster) {
            this.caster = caster;
        }
    }
}
