package com.bewitchment.common.block.tile.container;

import com.bewitchment.api.message.TarotInfo;
import com.bewitchment.common.block.tile.container.util.ModContainer;

import java.util.List;

public class ContainerTarotTable extends ModContainer {
	public List<TarotInfo> infoList;

	public ContainerTarotTable(List<TarotInfo> infoList) {
		this.infoList = infoList;
	}
}