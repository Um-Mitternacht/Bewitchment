package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import net.minecraft.block.BlockGlazedTerracotta;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class PotionOzymandias extends GenericBrewDamageVS {
	public PotionOzymandias() {
		super("ozymandias", 0x303E0C);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 2 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<MutableBlockPos> spots = BlockPos.getAllInBoxMutable(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < 2 + box * box / 2) {
				IBlockState state = world.getBlockState(spot);
				if (!state.getBlock().hasTileEntity(state) && world.rand.nextInt(4) <= modifiers.getLevel(DefaultModifiers.POWER).orElse(0) / 2) {
					if (state.getMaterial() == Material.ROCK && state.getBlock().isNormalCube(state, world, spot) && state.getBlock() != Blocks.BEDROCK) {
						world.setBlockState(spot, Blocks.SANDSTONE.getDefaultState(), 3);
					} else if ((state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND) && state.getBlock().isNormalCube(state, world, spot)) {
						world.setBlockState(spot, Blocks.SAND.getDefaultState(), 2);
					} else if (state.getBlock() instanceof IPlantable) {
						world.setBlockState(spot, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.DEAD_BUSH), 2);
					} else if (state.getBlock() instanceof BlockGlazedTerracotta) {
						world.setBlockState(spot, Blocks.YELLOW_GLAZED_TERRACOTTA.getDefaultState().withProperty(BlockGlazedTerracotta.FACING, state.getValue(BlockGlazedTerracotta.FACING)), 3);
					} else if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER) {
						world.setBlockToAir(spot);
					}
				}
			}
		}
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		return (entity instanceof EntitySlime) && !(entity instanceof EntityMagmaCube);
	}

	@Override
	protected void applyExtraEffect(EntityLivingBase entity, int amplifier) {
		if (amplifier > 2 && entity.isEntityAlive() && entity.getRNG().nextInt(4) == 0) {
			entity.entityDropItem(new ItemStack(Items.SLIME_BALL), 0.5f);
		}
	}
}
