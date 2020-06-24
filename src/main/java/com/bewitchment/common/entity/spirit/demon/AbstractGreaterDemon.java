package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public abstract class AbstractGreaterDemon extends ModEntityMob implements IPledgeable {
	public final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);

	public AbstractGreaterDemon(World world, ResourceLocation lootTableLocation) {
		super(world, lootTableLocation);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	public boolean isNonBoss() {
		return false;
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		if (!ExtendedWorld.playerPledgedToDemon(world, player, this.getPledgeName())) this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		this.bossInfo.removePlayer(player);
	}
}
