package com.bewitchment.common.block.util;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by Joseph on 6/10/2019.
 */

//Todo: Everything. This is incredibly WIP, and only pushed forth to avoid corruption from oncoming storms.
public class ModBlockChest extends BlockChest {
	
	protected ModBlockChest(Type chestTypeIn) {
		super(chestTypeIn);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && hasTileEntity(state) && world.getTileEntity(pos) instanceof ModTileEntity) {
			ModTileEntity tile = (ModTileEntity) world.getTileEntity(pos);
			for (IItemHandler inventory : tile.getInventories())
				for (int i = 0; i < inventory.getSlots(); i++)
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
		}
		super.breakBlock(world, pos, state);
	}
}
