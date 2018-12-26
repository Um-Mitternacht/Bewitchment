package com.bewitchment.common.block.misc;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Joseph on 7/16/2018.
 */
//Todo: Allow grass to grow over this. Also create a class for graveyard dirt, which will act as a slow spawner of undead. Graveyard dirt's spawn rate will only be slightly higher than vanilla undead.
public class BlockPurifyingEarth extends BlockMod {

	public BlockPurifyingEarth() {
		super(LibBlockName.PURIFYING_EARTH, Material.GROUND);
		setResistance(1F);
		setHardness(1F);
		this.setSoundType(SoundType.GROUND);
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}


	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		EnumCreatureAttribute attr = ((EntityLivingBase) entityIn).getCreatureAttribute();
		if (attr == EnumCreatureAttribute.UNDEAD || attr == BewitchmentAPI.getAPI().DEMON || attr == BewitchmentAPI.getAPI().SPIRIT || isTransformedPlayer(entityIn)) {
			if (!entityIn.isBurning()) {
				entityIn.setFire(1500);
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.demons_bane, 1000, 1, false, false));
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.holy_water, 1000, 1, false, false));
			}
			super.onEntityWalk(worldIn, pos, entityIn);
		}
	}

	private boolean isTransformedPlayer(Entity e) {
		if (e instanceof EntityPlayer) {
			ITransformation transformation = ((EntityPlayer) e).getCapability(CapabilityTransformation.CAPABILITY, null).getType();
			return !transformation.canCrossSalt();
		}
		return false;
	}
}
