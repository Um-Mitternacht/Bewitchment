package com.bewitchment.api.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class AltarModifierCheckEvent extends BlockEvent {

	private AltarUpgradeController controller;

	public AltarModifierCheckEvent(World world, BlockPos pos, IBlockState state, AltarUpgradeController altarController) {
		super(world, pos, state);
		this.controller = altarController;
	}

	public AltarUpgradeController getController() {
		return controller;
	}

}
