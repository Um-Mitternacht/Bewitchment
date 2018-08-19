package com.bewitchment.common.core.event;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.infusion.DefaultInfusions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class InfusionEvents {

	@SubscribeEvent
	public static void onGetCollisionBoxes(GetCollisionBoxesEvent evt) {
		if (isValidForLeavesNoClipping(evt.getEntity())) {
			for (int i = evt.getCollisionBoxesList().size() - 1; i >= 0; i--) {
				AxisAlignedBB bbx = evt.getCollisionBoxesList().get(i);
				BlockPos pos = new BlockPos(bbx.minX + (bbx.maxX - bbx.minX) * 0.5f, bbx.minY + (bbx.maxY - bbx.minY) * 0.5f, bbx.minZ + (bbx.maxZ - bbx.minZ) * 0.5f);
				IBlockState state = evt.getWorld().getBlockState(pos);
				if (state.getBlock().isLeaves(state, evt.getWorld(), pos) && evt.getEntity().posY < bbx.maxY) {
					evt.getCollisionBoxesList().remove(i);
				}
			}
		}
	}

	private static boolean isValidForLeavesNoClipping(Entity e) {
		if (e == null) {
			return false;
		}
		if (e instanceof EntityPlayer) {
			if (BewitchmentAPI.getAPI().getPlayerInfusion((EntityPlayer) e) == DefaultInfusions.OVERWORLD) {
				return true;
			}
		}
		if (e.isBeingRidden()) {
			return isValidForLeavesNoClipping(e.getControllingPassenger());
		}
		return false;
	}

}
