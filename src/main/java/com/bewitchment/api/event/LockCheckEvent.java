package com.bewitchment.api.event;

import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;

/**
 * Fired when a locked block is checked (i.e. when a player tries to open a Juniper Chest)
 * <p>
 * When cancelled, the check will be skipped (return true)
 */
@Cancelable
@Data
public class LockCheckEvent extends Event {
    @Nullable
    private EntityPlayer user;
    private BlockPos lock;

    public LockCheckEvent(EntityPlayer user, BlockPos lock) {
        this.user = user;
        this.lock = lock;
    }


    /**
     * Fired after the lock has been checked
     */
    @Cancelable
    public static class LockCheckedEvent extends LockCheckEvent {
        private boolean result;
        private boolean sendMessage;

        public LockCheckedEvent(EntityPlayer user, BlockPos lock, boolean result, boolean sendMessage) {
            super(user, lock);
            this.result = result;
            this.sendMessage = sendMessage;
        }

        public boolean isOpened() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public boolean shouldSendMessage() {
            return sendMessage;
        }

        public void setSendMessage(boolean sendMessage) {
            this.sendMessage = sendMessage;
        }
    }

    /**
     * Fired when a locked block is checked for the usage of a key; if the super-event is cancelled, this won't get called.
     * If this event is cancelled, the key will "fit"
     */
    @Cancelable
    public static class KeyCheckEvent extends LockCheckEvent {
        private final ItemStack key;

        public KeyCheckEvent(EntityPlayer user, BlockPos lock, ItemStack key) {
            super(user, lock);
            this.key = key;
        }

        public ItemStack getKey() {
            return key;
        }
    }
}
