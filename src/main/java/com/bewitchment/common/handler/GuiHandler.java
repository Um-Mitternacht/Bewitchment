package com.bewitchment.common.handler;

import com.bewitchment.client.gui.*;
import com.bewitchment.common.block.tile.container.*;
import com.bewitchment.common.block.tile.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.Collections;

@SuppressWarnings("ConstantConditions")
public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof TileEntityWitchesOven) return new ContainerWitchesOven(player.inventory, (TileEntityWitchesOven) tile);
		if (tile instanceof TileEntityDistillery) return new ContainerDistillery(player.inventory, (TileEntityDistillery) tile);
		if (tile instanceof TileEntitySpinningWheel) return new ContainerSpinningWheel(player.inventory, (TileEntitySpinningWheel) tile);
		if (tile instanceof TileEntityTarotTable) {
			ItemStack stack = player.getHeldItemMainhand();
			if (stack.getItem() instanceof ItemTarotCards && stack.hasTagCompound() && stack.getTagCompound().hasKey("readId")) {
				return new ContainerTarotTable(Collections.emptyList());
			}
		}
		if (tile instanceof TileEntityJuniperChest) return new ContainerJuniperChest(player.inventory, (TileEntityJuniperChest) tile);
		if (tile instanceof TileEntityDBChest) return new ContainerDBChest(player.inventory, (TileEntityDBChest) tile);
		if (tile instanceof TileEntitySigilTable) return new ContainerSigilTable(player.inventory, (TileEntitySigilTable) tile);
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof TileEntityWitchesOven) return new GuiWitchesOven((ContainerWitchesOven) getServerGuiElement(ModGui.OVEN.ordinal(), player, world, x, y, z), player.inventory);
		if (tile instanceof TileEntityDistillery) return new GuiDistillery((ContainerDistillery) getServerGuiElement(ModGui.DISTILLERY.ordinal(), player, world, x, y, z));
		if (tile instanceof TileEntitySpinningWheel) return new GuiSpinningWheel((ContainerSpinningWheel) getServerGuiElement(ModGui.SPINNING_WHEEL.ordinal(), player, world, x, y, z));
		if (tile instanceof TileEntityTarotTable) return new GuiTarotTable((ContainerTarotTable) getServerGuiElement(ModGui.TAROT_TABLE.ordinal(), player, world, x, y, z));
		if (tile instanceof TileEntityJuniperChest) return new GuiJuniperChest((ContainerJuniperChest) getServerGuiElement(ModGui.JUNIPER_CHEST.ordinal(), player, world, x, y, z), player.inventory);
		if (tile instanceof TileEntitySigilTable) return new GuiSigilTable((ContainerSigilTable) getServerGuiElement(ModGui.SIGIL_TABLE.ordinal(), player, world, x, y, z));
		if (tile instanceof TileEntityDBChest) return new GuiDragonsBloodChest((ContainerDBChest) getServerGuiElement(ModGui.DRAGONS_BLOOD_CHEST.ordinal(), player, world, x, y, z), player.inventory);
		return null;
	}
	
	public enum ModGui {
		OVEN, DISTILLERY, SPINNING_WHEEL, TAROT_TABLE, JUNIPER_CHEST, DRAGONS_BLOOD_CHEST, SIGIL_TABLE
	}
}