package com.bewitchment.common.core.net.messages;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.net.SimpleMessage;
import com.bewitchment.common.tile.tiles.TileEntityPlacedItem;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlaceHeldItemMessage extends SimpleMessage<PlaceHeldItemMessage> {

	public BlockPos clicked = null;

	public PlaceHeldItemMessage() {
	}

	public PlaceHeldItemMessage(BlockPos pos) {
		clicked = pos;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
			EntityPlayer p = context.getServerHandler().player;
			if (p.world.getBlockState(clicked).getBlock().isReplaceable(p.world, clicked)) {
				if (p.world.getBlockState(clicked.down()).getBlockFaceShape(p.world, clicked.down(), EnumFacing.UP) == BlockFaceShape.SOLID) {
					p.world.setBlockState(clicked, ModBlocks.placed_item.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(p.rotationYaw)), 3);
					TileEntityPlacedItem te = (TileEntityPlacedItem) p.world.getTileEntity(clicked);
					te.setItem(p.getHeldItemMainhand().splitStack(1));
				}
			}
		});
		return null;
	}

}
