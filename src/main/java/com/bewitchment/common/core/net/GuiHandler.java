package com.bewitchment.common.core.net;

import com.bewitchment.client.gui.*;
import com.bewitchment.client.gui.container.*;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (LibGui.values()[ID]) {
			case APIARY:
				return tile != null && (tile instanceof TileEntityApiary) ? new ContainerApiary(player.inventory, (TileEntityApiary) tile) : null;
			case OVEN:
				return tile != null && (tile instanceof TileEntityOven) ? new ContainerOven(player.inventory, (TileEntityOven) tile) : null;
			case THREAD_SPINNER:
				return tile != null && (tile instanceof TileEntityThreadSpinner) ? new ContainerThreadSpinner(player.inventory, (TileEntityThreadSpinner) tile) : null;
			case BARREL:
				return tile != null && (tile instanceof TileEntityBarrel) ? new ContainerBarrel(player.inventory, (TileEntityBarrel) tile) : null;
			case TAROT:
				return new ContainerFake();// No container
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (LibGui.values()[ID]) {
			case APIARY:
				return tile != null && (tile instanceof TileEntityApiary) ? new GuiApiary(player.inventory, (TileEntityApiary) tile) : null;
			case OVEN:
				return tile != null && (tile instanceof TileEntityOven) ? new GuiOven(player.inventory, (TileEntityOven) tile) : null;
			case THREAD_SPINNER:
				return tile != null && (tile instanceof TileEntityThreadSpinner) ? new GuiThreadSpinner((Container) getServerGuiElement(ID, player, world, x, y, z), (TileEntityThreadSpinner) tile) : null;
			case BARREL:
				return tile != null && (tile instanceof TileEntityBarrel) ? new GuiBarrel((Container) getServerGuiElement(ID, player, world, x, y, z), (TileEntityBarrel) tile) : null;
			case TAROT:
				return tile != null && (tile instanceof TileEntityTarotsTable) ? new GuiTarots() : null;
			default:
				return null;
		}
	}
}
