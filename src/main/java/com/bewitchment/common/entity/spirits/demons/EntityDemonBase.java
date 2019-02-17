package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 1/13/2019.
 * <p>
 * Credit to AlexThe666 for bits and pieces of the code, so that I may create a merchant not based on a villager.
 */
public class EntityDemonBase extends EntityMultiSkin implements IAnimatedEntity, IMob, IMerchant {
	private int animationTick;
	private Animation currentAnimation;
	@Nullable
	private EntityPlayer buyingPlayer;
	@Nullable
	private MerchantRecipeList buyingList;
	private java.util.UUID lastBuyingPlayer;
	private int careerId;
	private int careerLevel;

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
		this.buyingPlayer = player;
	}

	@Nullable
	@Override
	public EntityPlayer getCustomer() {
		return this.buyingPlayer;
	}

	@Nullable
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		if (this.buyingList == null) {
			this.populateBuyingList();
		}

		return this.buyingList;
	}

	@Override
	public void setRecipes(@Nullable MerchantRecipeList recipeList) {

	}

	public VillagerRegistry.VillagerProfession getProfessionForge() {
		return null;
	}

	private void populateBuyingList() {
		if (this.careerId != 0 && this.careerLevel != 0) {
			++this.careerLevel;
		} else {
			this.careerId = this.getProfessionForge().getRandomCareer(this.rand) + 1;
			this.careerLevel = 1;
		}

		if (this.buyingList == null) {
			this.buyingList = new MerchantRecipeList();
		}

		int i = this.careerId - 1;
		int j = this.careerLevel - 1;
		java.util.List<EntityVillager.ITradeList> trades = this.getProfessionForge().getCareer(i).getTrades(j);

		if (trades != null) {
			for (EntityVillager.ITradeList entityvillager$itradelist : trades) {
				entityvillager$itradelist.addMerchantRecipe(this, this.buyingList, this.rand);
			}
		}
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
