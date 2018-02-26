package com.bewitchment.common.divination.fortunes;

import java.util.List;

import javax.annotation.Nonnull;

import com.bewitchment.api.divination.Fortune;
import com.bewitchment.common.core.BewitchmentLootTables;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 2/12/2018.
 */

//Todo: Create loot table.
public class FortuneShinies extends Fortune {

	public FortuneShinies(int weight, String name, String modid) {
		super(weight, name, modid);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean canBeUsedFor(@Nonnull EntityPlayer player) {
		return true;
	}

	@Override
	public boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player) {
		return false;
	}

	@Override
	public boolean apply(@Nonnull EntityPlayer player) {
		// Don't enable after a random time, enable after a random number of block broken, maybe,
		// to prevent players from going afk and getting it the first time they dig
		return false;
	}

	@Override
	public boolean isNegative() {
		return false;
	}

	@SubscribeEvent
	public void onDig(BreakEvent evt) {
		CapabilityDivination cap = evt.getPlayer().getCapability(CapabilityDivination.CAPABILITY, null);
		if (cap.getFortune() == this) {
			if (cap.isActive()) {
				Block block = evt.getState().getBlock();
				if (block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.MYCELIUM || block == Blocks.GRAVEL || block == Blocks.SOUL_SAND) {
					LootTable lt = evt.getWorld().getLootTableManager().getLootTableFromLocation(BewitchmentLootTables.JEWELS);
					LootContext lc = (new LootContext.Builder((WorldServer) evt.getWorld()).withLuck(evt.getPlayer().getLuck()).withPlayer(evt.getPlayer())).build();
					List<ItemStack> spawn = lt.generateLootForPools(evt.getPlayer().getRNG(), lc);
					spawn.forEach(s -> spawn(s, evt.getWorld(), evt.getPos()));
					cap.setRemovable();
				}
			} else {
				if (evt.getPlayer().getRNG().nextInt(100) == 0) { // On average, enable after digging 100 blocks after getting the read
					cap.setActive();
				}
			}
		}
	}

	private void spawn(ItemStack s, World world, BlockPos pos) {
		EntityItem i = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, s);
		i.setNoPickupDelay();
		world.spawnEntity(i);
	}
}
