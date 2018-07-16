package com.bewitchment.common.block.magic;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Joseph on 7/16/2018.
 */
public class BlockSacrosanctGround extends BlockMod {
	public BlockSacrosanctGround() {
		super(LibBlockName.SACROSANCT_GROUND, Material.GROUND);
		setResistance(1F);
		setHardness(1F);
	}

	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}


	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		EnumCreatureAttribute attr = ((EntityLivingBase) entityIn).getCreatureAttribute();
		if (attr == EnumCreatureAttribute.UNDEAD || attr == BewitchmentAPI.getAPI().DEMON || attr == BewitchmentAPI.getAPI().SPIRIT) {
			if (!entityIn.isBurning()) {
				entityIn.setFire(1500);
			}

			super.onEntityWalk(worldIn, pos, entityIn);
		}
	}
}
