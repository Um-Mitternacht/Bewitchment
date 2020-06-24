package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class FortuneOrphan extends Fortune {
	public FortuneOrphan() {
		super(new ResourceLocation(Bewitchment.MODID, "orphan"), false, (60 * 5), (60 * 30));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = new BlockPos(player.posX + player.getRNG().nextGaussian() * 4, player.posY, player.posZ + player.getRNG().nextGaussian() * 4);
		EntityVillager villager = new EntityVillager(player.world);
		if (player.world.isAirBlock(pos) && player.world.isAirBlock(pos.up()) && player.world.getBlockState(pos.down()).canEntitySpawn(villager)) {
			villager.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			villager.onInitialSpawn(player.world.getDifficultyForLocation(pos), null);
			villager.setGrowingAge(-2400);
			player.world.spawnEntity(villager);
			VillagerRegistry.setRandomProfession(villager, player.getRNG());
			if (villager.getProfessionForge().getRegistryName().getPath().equals("nitwit"))
				villager.setProfession(VillagerRegistry.FARMER);
			return true;
		}
		return false;
	}
}