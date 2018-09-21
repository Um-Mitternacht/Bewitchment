package com.bewitchment.common.item.tool;

import javax.annotation.Nonnull;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.ModCreativeTabs;
import com.bewitchment.common.integration.thaumcraft.ThaumcraftCompatBridge;
import com.bewitchment.common.item.ModMaterials;
import com.bewitchment.common.lib.LibItemName;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 6/14/2018.
 */
public class ItemColdIronPickaxe extends ItemPickaxe implements IModelRegister {

	public ItemColdIronPickaxe() {
		super(ModMaterials.TOOL_COLD_IRON);
		this.setMaxStackSize(1);
		setRegistryName(LibItemName.COLD_IRON_PICKAXE);
		setUnlocalizedName(LibItemName.COLD_IRON_PICKAXE);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
		if (!target.world.isRemote) {
			if ((target.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON || target.getCreatureAttribute() == BewitchmentAPI.getAPI().SPIRIT || target instanceof EntityEnderman || target instanceof EntityBlaze || target instanceof EntityVex || target instanceof EntityEndermite || target instanceof EntityGhast || target instanceof EntityWither || target instanceof EntityGuardian || ThaumcraftCompatBridge.isTCSpiritMob(target)) && attacker instanceof EntityPlayer) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 12);
				stack.damageItem(5, attacker);
			} else {
				stack.damageItem(1, attacker);
			}
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}