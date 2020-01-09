package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Contract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class ContractMahapadma extends Contract {
	public ContractMahapadma() {
		super(new ResourceLocation(Bewitchment.MODID, "mahapadma"), false, false, CurseCondition.INSTANT, null, Arrays.asList(Item.getItemFromBlock(Blocks.ICE), Items.WATER_BUCKET, Items.SLIME_BALL));
	}
	
	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		for (BlockPos pos : BlockPos.MutableBlockPos.getAllInBoxMutable(target.getPosition().add(-1, -2, -1), target.getPosition().add(1, 2, 1))) {
			if (target.world.getBlockState(pos).getBlock().isReplaceable(target.world, pos)) {
				target.world.setBlockState(pos, target.getRNG().nextBoolean() ? Blocks.PACKED_ICE.getDefaultState() : Blocks.ICE.getDefaultState());
			}
		}
		target.attackEntityFrom(DamageSource.MAGIC, Integer.MAX_VALUE);
		return true;
	}
}
