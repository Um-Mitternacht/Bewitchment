package com.bewitchment.common.item.tool;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings({"ConstantConditions", "NullableProblems", "unused"})
public class ItemAthame extends ItemSword {
	public ItemAthame() {
		super(ModObjects.TOOL_SILVER);
		Util.registerItem(this, "athame");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private static Collection<ItemStack> getAthameLoot(EntityLivingBase entity) {
		Collection<ItemStack> fin = new HashSet<>();
		for (Predicate<EntityLivingBase> predicate : BewitchmentAPI.ATHAME_LOOT.keySet())
			if (predicate.test(entity)) fin.addAll(BewitchmentAPI.ATHAME_LOOT.get(predicate));
		return fin;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!target.world.isRemote) {
			if (target instanceof EntityEnderman && attacker instanceof EntityPlayer) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 20);
				stack.damageItem(50, attacker);
			}
			else return super.hitEntity(stack, target, attacker);
		}
		return true;
	}
	
	@SubscribeEvent
	public void livingDrop(LivingDropsEvent event) {
		if (event.isRecentlyHit() && event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase) event.getSource().getTrueSource()).getHeldItemMainhand().getItem() == ModObjects.athame && !getAthameLoot(event.getEntityLiving()).isEmpty()) {
			for (ItemStack stack : getAthameLoot(event.getEntityLiving())) {
				Random rand = event.getEntityLiving().getRNG();
				ItemStack copy = stack.copy();
				copy.setCount(rand.nextInt(stack.getCount() + 1) + rand.nextInt(event.getLootingLevel() + 1));
				if (copy.getItem() instanceof ItemSkull) {
					if (rand.nextFloat() <= 0.8f) copy.shrink(copy.getCount());
					else copy.setCount(1 + (int) (rand.nextFloat() * event.getLootingLevel()) / 2);
					if (copy.getCount() > 1 && event.getEntityLiving() instanceof EntityPlayer) {
						NBTTagCompound tag = new NBTTagCompound();
						tag.setString("SkullOwner", event.getEntity().getName());
						copy.setTagCompound(tag);
					}
				}
				event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX + 0.5, event.getEntityLiving().posY + 0.5, event.getEntityLiving().posZ + 0.5, copy));
			}
		}
	}
}