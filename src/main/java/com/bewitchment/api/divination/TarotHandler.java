package com.bewitchment.api.divination;

import java.util.ArrayList;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TarotHandler {
	
	private static final ArrayList<ITarot> REGISTRY = new ArrayList<ITarot>(30);
	private static final int MAX_CARDS_PER_READING = 5;
	
	private TarotHandler() {
	}
	
	public static void registerTarot(ITarot tarot) {
		REGISTRY.add(tarot);
	}
	
	public static ArrayList<TarotInfo> getTarotsForPlayer(EntityPlayer player) {
		
		ArrayList<TarotInfo> res = new ArrayList<TarotInfo>(5);
		
		// Can't instantiate since the collected List<> might not support remove operations,
		// depending on the jre, so this needs to be addAll for safety
		res.addAll(REGISTRY.parallelStream() //
				.filter(it -> it.isApplicableToPlayer(player)) //
				.map(it -> new TarotInfo(it, player)) //
				.collect(Collectors.toList()));
		
		// Remove random cards until we have less or equal than max per reading
		while (res.size() > MAX_CARDS_PER_READING)
			res.remove(player.getRNG().nextInt(res.size()));
		
		return res;
	}
	
	public static class TarotInfo {
		
		ITarot tarot;
		boolean reversed;
		int number = -1;
		
		private TarotInfo(ITarot tarot, boolean reversed, int number) {
			this.tarot = tarot;
			this.reversed = reversed;
			if (number >= 0)
				this.number = number;
		}
		
		TarotInfo(ITarot tarot, EntityPlayer player) {
			this(tarot, tarot.isReversed(player), tarot.hasNumber(player) ? tarot.getNumber(player) : -1);
		}
		
		public boolean isReversed() {
			return reversed;
		}
		
		public boolean hasNumber() {
			return number >= 0;
		}
		
		public int getNumber() {
			return number;
		}
		
		public ResourceLocation getTexture() {
			return tarot.getTexture();
		}
		
		public String getUnlocalizedName() {
			return tarot.getUnlocalizedName();
		}
		
		@Override
		public String toString() {
			return I18n.format(getUnlocalizedName()) + ", " + number + (reversed ? ", reversed" : "");
		}
		
	}
	
}
