package com.bewitchment.client.core.event;

import com.bewitchment.client.handler.Keybinds;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlaceHeldItemMessage;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MiscEventHandler {

	private Minecraft mc;

	public MiscEventHandler(Minecraft minecraft) {
		mc = minecraft;
	}

	@SubscribeEvent
	public void onKeyPress(KeyInputEvent evt) {
		if (Keybinds.placeItem.isPressed()) {
			EntityPlayer p = mc.player;
			if (mc.objectMouseOver.typeOfHit == Type.BLOCK && mc.objectMouseOver.sideHit == EnumFacing.UP) {
				if (mc.world.getBlockState(mc.objectMouseOver.getBlockPos().up()).getBlock().isReplaceable(mc.world, mc.objectMouseOver.getBlockPos().up())) {
					if (mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlockFaceShape(p.world, mc.objectMouseOver.getBlockPos(), EnumFacing.UP) == BlockFaceShape.SOLID) {
						NetworkHandler.HANDLER.sendToServer(new PlaceHeldItemMessage(mc.objectMouseOver.getBlockPos().up()));
					}
				}
			}
		}
	}
}
