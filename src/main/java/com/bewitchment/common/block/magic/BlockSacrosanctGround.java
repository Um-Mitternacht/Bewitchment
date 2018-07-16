package com.bewitchment.common.block.magic;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (entityIn instanceof EntityLivingBase) {
			EnumCreatureAttribute attr = ((EntityLivingBase) entityIn).getCreatureAttribute();
			if (attr == EnumCreatureAttribute.UNDEAD || attr == BewitchmentAPI.getAPI().DEMON || attr == BewitchmentAPI.getAPI().SPIRIT) {
				entityIn.attackEntityFrom(DamageSource.ON_FIRE, 6);
			}
		}
	}
}
