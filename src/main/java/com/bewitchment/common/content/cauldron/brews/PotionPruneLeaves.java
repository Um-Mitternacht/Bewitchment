package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionPruneLeaves extends BrewMod {

	public PotionPruneLeaves() {
		super("prune_leaves", false, 0x5bff6e, true, 0);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {

		int box = 3 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = world.rand.nextInt(5) <= amplifier;
			if (place && state.getBlock() instanceof BlockLeaves) {
				if (thrower instanceof EntityPlayer) {
					state.getBlock().removedByPlayer(state, world, spot, (EntityPlayer) thrower, true);
					state.getBlock().harvestBlock(world, (EntityPlayer) thrower, spot, state, world.getTileEntity(spot), ItemStack.EMPTY);
				} else {
					state.getBlock().dropBlockAsItemWithChance(world, spot, state, 1f, 0);
					world.setBlockToAir(spot);
				}
			}
		}
	}

}
