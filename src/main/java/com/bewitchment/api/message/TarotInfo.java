package com.bewitchment.api.message;

import com.bewitchment.api.registry.Tarot;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;

public class TarotInfo {
	private final ResourceLocation texture;
	private final boolean isReversed;
	private final int number;

	public TarotInfo(Tarot tarot, EntityPlayer player) {
		this.texture = tarot.texture;
		this.isReversed = tarot.isReversed(player);
		this.number = tarot.getNumber(player);
	}

	private TarotInfo(ResourceLocation tarot, boolean isReversed, int number) {
		this.texture = tarot;
		this.isReversed = isReversed;
		this.number = number;
	}

	public static ArrayList<TarotInfo> fromBuffer(ByteBuf buf) {
		ArrayList<TarotInfo> result = new ArrayList<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			String res = ByteBufUtils.readUTF8String(buf);
			boolean reversed = buf.readBoolean();
			int num = buf.readInt();
			result.add(new TarotInfo(new ResourceLocation(res), reversed, num));
		}
		return result;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public boolean isReversed() {
		return isReversed;
	}

	public int getNumber() {
		return number;
	}
}
