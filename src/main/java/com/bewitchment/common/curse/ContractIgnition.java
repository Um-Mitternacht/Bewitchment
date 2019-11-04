package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.handler.BlockDropHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class ContractIgnition extends Curse {
	public ContractIgnition() {
		super(new ResourceLocation(Bewitchment.MODID, "ignition"), null, true, true, CurseCondition.BLOCK_DROP);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		BlockEvent.HarvestDropsEvent event0 = (BlockEvent.HarvestDropsEvent) event;
		Block brokenBlock = event0.getState().getBlock();
		int meta = brokenBlock.getMetaFromState(event0.getState());
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(brokenBlock, 1, meta));
		BlockDropHandler.replaceDrop(event0, s -> !result.isEmpty(), result, 100, 1, true);
		return true;
	}
}
