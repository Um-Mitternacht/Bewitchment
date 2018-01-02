package com.bewitchment.common.item.tool;

import com.bewitchment.api.helper.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.BewitchmentCreativeTabs;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemBoline extends ItemShears implements IModelRegister {

	@Nonnull
	public ItemBoline() {
		super();
		setMaxDamage(600);
		this.setMaxStackSize(1);
		setRegistryName(LibItemName.BOLINE);
		setUnlocalizedName(LibItemName.BOLINE);
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
		if (!target.world.isRemote) {
			if (attacker instanceof EntityPlayer)
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 5);
			stack.damageItem(1, attacker);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}
}
