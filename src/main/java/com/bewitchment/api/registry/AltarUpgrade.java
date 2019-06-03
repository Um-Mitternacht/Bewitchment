package com.bewitchment.api.registry;

public class AltarUpgrade {
	public final Type type;
	public final int gain;
	public final double multiplier;
	
	public AltarUpgrade(Type type, int gain, double multiplier) {
		this.type = type;
		this.gain = gain;
		this.multiplier = multiplier;
	}
	
	public enum Type {
		CUP, PENTACLE, SWORD, WAND
	}
}