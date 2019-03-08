package com.bewitchment.common.entity.ai;

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
public class EntityDemonBase extends EntityMultiSkin implements IAnimatedEntity{
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
