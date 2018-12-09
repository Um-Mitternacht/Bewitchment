package com.bewitchment.common.item.magic.brew;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.BrewEntry;
import com.bewitchment.common.content.cauldron.BrewModifierListImpl;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.entity.EntityBrewArrow;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBrewArrow extends ItemArrow implements IModelRegister {

	public ItemBrewArrow() {
		super();
		setRegistryName(LibItemName.BREW_ARROW);
		setTranslationKey(LibItemName.BREW_ARROW);
		setCreativeTab(ModCreativeTabs.BREW_CREATIVE_TAB);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			CauldronRegistry.BREW_POTION_MAP.values().forEach(p -> addPotionType(items, p));
		}
	}

	@Override
	public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
		EntityBrewArrow brewArrow = new EntityBrewArrow(worldIn, shooter);
		brewArrow.setArrowStack(stack.copy().splitStack(1));
		return brewArrow;
	}

	private void addPotionType(NonNullList<ItemStack> items, Potion p) {
		BrewData data = new BrewData();
		BrewModifierListImpl list = new BrewModifierListImpl();
		data.addEntry(new BrewEntry(p, list));
		ItemStack stack = new ItemStack(this);
		data.saveToStack(stack);
		items.add(stack);
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		ItemBrew.addTooltip(stack, worldIn, tooltip, flagIn);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Items.TIPPED_ARROW.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(this, 0, modelResourceLocation);
	}

}
