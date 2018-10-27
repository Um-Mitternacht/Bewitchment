package com.bewitchment.common.content.crystalBall.fortunes;

import com.bewitchment.common.content.crystalBall.Fortune;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.core.statics.ModLootTables;
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

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Joseph on 2/12/2018.
 */

public class FortuneTreasure extends Fortune {

	public FortuneTreasure(int weight, String name, String modid) {
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
		player.getCapability(CapabilityFortune.CAPABILITY, null).setActive();
		return false;
	}

	@Override
	public boolean isNegative() {
		return false;
	}

	@SubscribeEvent
	public void onDig(BreakEvent evt) {
		CapabilityFortune cap = evt.getPlayer().getCapability(CapabilityFortune.CAPABILITY, null);
		if (cap.getFortune() == this) {
			if (cap.isActive()) {
				Block block = evt.getState().getBlock();
				if (block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.MYCELIUM || block == Blocks.GRAVEL || block == Blocks.SOUL_SAND) {
					LootTable lt = evt.getWorld().getLootTableManager().getLootTableFromLocation(ModLootTables.METALS);
					LootContext lc = (new LootContext.Builder((WorldServer) evt.getWorld()).withLuck(evt.getPlayer().getLuck()).withPlayer(evt.getPlayer())).build();
					List<ItemStack> spawn = lt.generateLootForPools(evt.getPlayer().getRNG(), lc);
					spawn.forEach(s -> spawn(s, evt.getWorld(), evt.getPos()));
					cap.setRemovable();
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
