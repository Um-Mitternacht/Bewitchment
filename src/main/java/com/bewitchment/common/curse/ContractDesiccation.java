package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ContractDesiccation extends Curse {
	public ContractDesiccation() {
		super(new ResourceLocation(Bewitchment.MODID, "desiccation"), null, true, true, CurseCondition.BLOCK_BREAK);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		BlockEvent.BreakEvent event0 = (BlockEvent.BreakEvent) event;
		if (event0.getWorld().getBlockState(event0.getPos()).getBlock() instanceof BlockCrops) {
			target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2));
			return true;
		}
		return false;
	}
}
