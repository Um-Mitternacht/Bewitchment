package com.bewitchment.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerFake extends Container {

	public ContainerFake() {
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
