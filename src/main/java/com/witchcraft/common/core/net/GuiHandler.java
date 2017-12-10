package com.witchcraft.common.core.net;

import com.witchcraft.client.gui.GuiApiary;
import com.witchcraft.client.gui.GuiOven;
import com.witchcraft.client.gui.container.ContainerApiary;
import com.witchcraft.client.gui.container.ContainerOven;
import com.witchcraft.common.tile.TileApiary;
import com.witchcraft.common.tile.TileOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 16/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case 0:
				final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
				return tile != null && (tile instanceof TileApiary) ? new ContainerApiary(player.inventory, (TileApiary) tile) : null;
			case 1:
				final TileEntity tile1 = world.getTileEntity(new BlockPos(x, y, z));
				return tile1 != null && (tile1 instanceof TileOven) ? new ContainerOven(player.inventory, (TileOven) tile1) : null;
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case 0:
				final TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
				return tile != null && (tile instanceof TileApiary) ? new GuiApiary(player.inventory, (TileApiary) tile) : null;
			case 1:
				final TileEntity tile1 = world.getTileEntity(new BlockPos(x, y, z));
				return tile1 != null && (tile1 instanceof TileOven) ? new GuiOven(player.inventory, (TileOven) tile1) : null;
			default:
				return null;
		}
	}
}
