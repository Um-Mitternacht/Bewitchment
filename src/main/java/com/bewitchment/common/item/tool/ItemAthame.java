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

import java.util.Random;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ItemAthame extends ItemSword {
	public ItemAthame() {
		super(ModObjects.TOOL_SILVER);
		Util.registerItem(this, "athame");
		MinecraftForge.EVENT_BUS.register(this);
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
		if (event.isRecentlyHit() && event.getSource().getTrueSource() instanceof EntityLivingBase && ((EntityLivingBase) event.getSource().getTrueSource()).getHeldItemMainhand().getItem() == ModObjects.athame && !BewitchmentAPI.getAthameLoot(event.getEntityLiving()).isEmpty()) {
			for (ItemStack stack : BewitchmentAPI.getAthameLoot(event.getEntityLiving())) {
				Random rand = event.getEntityLiving().getRNG();
				ItemStack copy = stack.copy();
				copy.setCount(rand.nextInt(stack.getCount()) + rand.nextInt(event.getLootingLevel() + 1));
				if (event.getEntityLiving() instanceof EntityPlayer && copy.getItem() instanceof ItemSkull) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("SkullOwner", event.getEntity().getName());
					copy.setTagCompound(tag);
				}
				event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX + 0.5, event.getEntityLiving().posY + 0.5, event.getEntityLiving().posZ + 0.5, copy));
			}
		}
	}
}