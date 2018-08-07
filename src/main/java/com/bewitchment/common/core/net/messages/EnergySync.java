package com.bewitchment.common.core.net.messages;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.core.net.SimpleMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EnergySync extends SimpleMessage<EnergySync> {

	public int amount, max;

	public EnergySync() {
		amount = 0;
		max = 0;
	}

	public EnergySync(int energy_amount, int energy_max) {
		amount = energy_amount;
		max = energy_max;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			IMagicPowerContainer c = Minecraft.getMinecraft().player.getCapability(IMagicPowerContainer.CAPABILITY, null);
			c.setMaxAmount(max);
			c.setAmount(amount);
		});
		return null;
	}
}
