package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 1/13/2019.
 */
public class EntityDemonBase extends EntityMultiSkin implements IAnimatedEntity, IMob, IMerchant {
	private int animationTick;
	private Animation currentAnimation;

	public EntityDemonBase(World worldIn) {
		super(worldIn);
	}

	@Override
	public int getSkinTypes() {
		return 0;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[0];
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public void setCustomer(@Nullable EntityPlayer player) {

	}

	@Nullable
	@Override
	public EntityPlayer getCustomer() {
		return null;
	}

	@Nullable
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		return null;
	}

	@Override
	public void setRecipes(@Nullable MerchantRecipeList recipeList) {

	}

	@Override
	public void useRecipe(MerchantRecipe recipe) {

	}

	@Override
	public void verifySellingItem(ItemStack stack) {

	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public BlockPos getPos() {
		return null;
	}
}
