package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class FortuneHellishFortune extends Fortune {
	public FortuneHellishFortune() {
		super(new ResourceLocation(Bewitchment.MODID, "hellish_fortune"), false, 66, 666);
	}

	@Override
	public boolean apply(EntityPlayer player) {
		return player.world.provider.getDimensionType() == DimensionType.NETHER;
	}

	@SubscribeEvent
	public void onDig(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote) {
			ExtendedPlayer cap = event.getPlayer().getCapability(ExtendedPlayer.CAPABILITY, null);
			if (cap.fortune == this) {
				Block block = event.getState().getBlock();
				if (block == Blocks.SOUL_SAND || block == Blocks.NETHERRACK || block == Blocks.MAGMA || block == Blocks.GRAVEL || block == Blocks.NETHER_BRICK || block == Blocks.QUARTZ_ORE) {
					LootTable table = event.getWorld().getLootTableManager().getLootTableFromLocation(new ResourceLocation(Bewitchment.MODID, "chests/nether_materials"));
					LootContext context = (new LootContext.Builder((WorldServer) event.getWorld()).withLuck(event.getPlayer().getLuck()).withPlayer(event.getPlayer())).build();
					List<ItemStack> list = table.generateLootForPools(event.getPlayer().getRNG(), context);
					for (ItemStack stack : list) {
						EntityItem entity = new EntityItem(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, stack);
						entity.setNoPickupDelay();
						event.getWorld().spawnEntity(entity);
					}
					cap.fortune = null;
					ExtendedPlayer.syncToClient(event.getPlayer());
				}
			}
		}
	}
}