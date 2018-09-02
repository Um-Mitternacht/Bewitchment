package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PotionHarvest extends BrewMod {
	public PotionHarvest() {
		super("harvest", true, 0xC48F31, true, 0);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
		if (entityLivingBaseIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack.getItem() instanceof ItemFood && player.getRNG().nextInt(7) <= amplifier) {
					player.entityDropItem(stack.splitStack(1), 0.5f);
				}
			}
		}
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);

		BlockPos posI = pos.add(box, 1, box);
		BlockPos posF = pos.add(-box, -1, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		int chance = 10 + amplifier * 2;
		int fortune = MathHelper.clamp(amplifier, 0, 5);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 1 || world.rand.nextBoolean();
			if (place && state.getBlock() instanceof BlockCrops) {
				BlockCrops crop = (BlockCrops) state.getBlock();
				if (crop.isMaxAge(state)) {
					crop.dropBlockAsItemWithChance(world, spot, state, chance, fortune);
					world.setBlockToAir(spot);
				}
			}
		}
	}
}
