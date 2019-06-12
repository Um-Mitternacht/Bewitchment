package com.bewitchment.common.block.tile.container;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.Tarot;
import com.bewitchment.common.block.tile.container.util.ModContainer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContainerTarotTable extends ModContainer {
	public final List<Tarot> toRead = new ArrayList<>();
	
	public final EntityPlayer player;
	
	public ContainerTarotTable(UUID id) {
		player = Util.findPlayer(id);
		if (player != null) {
			List<Tarot> valid = BewitchmentAPI.REGISTRY_TAROT.getValuesCollection().stream().filter(f -> f.isCounted(player)).collect(Collectors.toList());
			if (!valid.isEmpty()) {
				while (!valid.isEmpty() && toRead.size() < 4) {
					int i = player.getRNG().nextInt(valid.size());
					toRead.add(valid.get(i));
					valid.remove(i);
				}
			}
		}
	}
}