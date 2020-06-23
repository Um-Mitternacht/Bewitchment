package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Contract;
import com.bewitchment.common.handler.BlockDropHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;


public class ContractIgnition extends Contract {
    public ContractIgnition() {
        super(new ResourceLocation(Bewitchment.MODID, "ignition"), true, true, CurseCondition.BLOCK_DROP, null, Arrays.asList(Items.BLAZE_ROD, Items.MAGMA_CREAM));
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
