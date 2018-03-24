package com.bewitchment.common.divination.fortunes;

import com.bewitchment.common.divination.Fortune;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class FortuneMeetPartyParrot extends Fortune {

	public FortuneMeetPartyParrot(int weight, String name, String modid) {
		super(weight, name, modid);
	}

	@Override
	public boolean canBeUsedFor(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(EntityPlayer player) {
		return player.dimension != 1 && player.dimension != -1 && player.getRNG().nextDouble() < 0.0001;
	}

	@Override
	public boolean apply(EntityPlayer player) {
		for (int i = 0; i < 10; i++) {
			BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
			EntityParrot partyparrot = new EntityParrot(player.world);
			if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(partyparrot)) {
				partyparrot.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				partyparrot.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
				partyparrot.setPartying(pos, true);
				player.world.spawnEntity(partyparrot);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isNegative() {
		return false;
	}

}
