package com.bewitchment.common.entity.ai;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkinMonster;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Joseph on 1/13/2019.
 * <p>
 * Credit to AlexThe666 for bits and pieces of the code, so that I may create a merchant not based on a villager.
 */
public class EntityDemonBase extends EntityMultiSkinMonster implements IAnimatedEntity, IMob, IMerchant {
	private int animationTick;
	private Animation currentAnimation;
	@Nullable
	private EntityPlayer buyingPlayer;
	@Nullable
	private MerchantRecipeList buyingList;
	private java.util.UUID lastBuyingPlayer;
	private int careerId;
	private int careerLevel;
	private int wealth;

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

	@Override
	public void setCustomer(@Nullable EntityPlayer player) {
		this.buyingPlayer = player;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		tag.setInteger("Career", this.careerId);
		tag.setInteger("CareerLevel", this.careerLevel);
		tag.setInteger("Riches", this.wealth);
		if (this.buyingList != null) {
			tag.setTag("Offers", this.buyingList.getRecipiesAsTags());
		}
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

	public static class BasicTrade implements EntityVillager.ITradeList {
		public ItemStack first;
		public ItemStack second;
		public EntityVillager.PriceInfo firstPrice;
		public EntityVillager.PriceInfo secondPrice;

		public BasicTrade(ItemStack first, ItemStack second, EntityVillager.PriceInfo firstPrice, EntityVillager.PriceInfo secondPrice) {
			this.first = first;
			this.second = second;
			this.firstPrice = firstPrice;
			this.secondPrice = secondPrice;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			int i = firstPrice.getPrice(random);
			int j = secondPrice.getPrice(random);
			recipeList.add(new MerchantRecipe(new ItemStack(first.getItem(), i, first.getItemDamage()), new ItemStack(second.getItem(), j, second.getItemDamage())));
		}
	}
}
