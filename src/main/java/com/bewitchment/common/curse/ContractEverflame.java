package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ContractEverflame extends Curse {
	public ContractEverflame() {
		super(new ResourceLocation(Bewitchment.MODID, "everflame"), null, false, false, CurseCondition.EXIST, 0.0002);
	}
	
	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		for (BlockPos temp : BlockPos.getAllInBoxMutable(target.getPosition().add(-2, -2, -2), target.getPosition().add(2, 2, 2))) {
			if (target.getRNG().nextDouble() < 0.1) {
				if (target.world.isAirBlock(temp) && target.world.getBlockState(temp.down()).getBlock().isFlammable(target.world, temp.down(), EnumFacing.UP) && target.canPlayerEdit(temp, EnumFacing.DOWN, ItemStack.EMPTY)) {
					target.world.setBlockState(temp, Blocks.FIRE.getDefaultState());
				}
			}
		}
		return false;
	}
}
