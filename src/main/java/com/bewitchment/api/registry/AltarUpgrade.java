package com.bewitchment.api.registry;

public class AltarUpgrade {
    public final Type type;
    public final int upgrade1;
    public final double upgrade2;

    /**
     * @param type     the type of upgrade
     * @param upgrade1 for cups and pentacles, this means gain. has no effect on swords wands, and books
     * @param upgrade2 for cups and swords, this means the total multiplier. for wands, this means the flat bonus multiplier. has no effect on pentacles
     */
    public AltarUpgrade(Type type, int upgrade1, double upgrade2) {
        this.type = type;
        this.upgrade1 = upgrade1;
        this.upgrade2 = upgrade2;
    }

    public enum Type {
        CUP, PENTACLE, SWORD, WAND
    }
}