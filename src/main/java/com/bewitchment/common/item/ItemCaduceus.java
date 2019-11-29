package com.bewitchment.common.item;

import com.bewitchment.Util;
import com.bewitchment.common.entity.spirit.demon.EntityFeuerwurm;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModEntities;
import com.bewitchment.registry.ModObjects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemCaduceus extends Item {
	public ItemCaduceus() {
		super();
		Util.registerItem(this, "caduceus");
		setMaxDamage(128);
		setMaxStackSize(1);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			EntityLivingBase target = player.getAttackingEntity();
			int random = itemRand.nextInt(3) + 1;
			for (int i = 0; i < random; i++) {
				EntityFeuerwurm temp = (EntityFeuerwurm) ModEntities.feuerwurm.newInstance(world);
				temp.getDataManager().set(ModEntityMob.SPECTRAL, true);
				temp.getDataManager().set(ModEntityMob.SKIN, itemRand.nextInt(temp.getSkinTypes()));
				temp.lifeTimeTicks = 400;
				temp.summoner = player.getPersistentID();
				temp.setAttackTarget(target);
				
				temp.setPosition(pos.getX() + itemRand.nextGaussian() * 0.8, pos.getY() + 1, pos.getZ() + itemRand.nextGaussian() * 0.8);
				world.spawnEntity(temp);
			}
			player.getCooldownTracker().setCooldown(this, 40);
			player.getHeldItem(hand).damageItem(1, player);
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityLivingBase player = event.getEntityLiving();
			ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModObjects.caduceus ? event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND) : event.getEntityLiving().getHeldItem(EnumHand.OFF_HAND).getItem() == ModObjects.caduceus ? event.getEntityLiving().getHeldItem(EnumHand.OFF_HAND) : null;
			if (stack != null) {
				event.setCanceled(true);
				player.setHealth(8);
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
				stack.damageItem(16, event.getEntityLiving());
			}
		}
	}
}
