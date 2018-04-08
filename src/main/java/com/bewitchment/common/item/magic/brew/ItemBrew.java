package com.bewitchment.common.item.magic.brew;

import java.util.List;
import java.util.Optional;

import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.api.cauldron.modifiers.BewitchmentModifiers;
import com.bewitchment.common.cauldron.BrewData;
import com.bewitchment.common.cauldron.BrewData.BrewEntry;
import com.bewitchment.common.cauldron.BrewModifierListImpl;
import com.bewitchment.common.core.ModCreativeTabs;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;
import com.bewitchment.common.item.ItemMod;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrew extends ItemMod {
	
	public ItemBrew(String id) {
		super(id);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.BREW_CREATIVE_TAB);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			CauldronRegistry.BREW_POTION_MAP.values().forEach(p -> addPotionType(items, p));
		}
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
		BrewData.fromStack(stack).getEffects().forEach(brewEntry -> {
			TextFormatting color = brewEntry.getPotion().isBadEffect() ? TextFormatting.RED : TextFormatting.DARK_AQUA;
			IBrewModifierList list = brewEntry.getModifierList();
			if (GuiScreen.isShiftKeyDown()) {
				tooltip.add(color + I18n.format("effect." + brewEntry.getPotion().getRegistryName().getResourcePath() + ".name"));
				list.getModifiers().stream().filter(modifier -> list.getLevel(modifier).isPresent()).forEach(bm -> {
					tooltip.add(I18n.format("brew.parameters.formatting", bm.getTooltipString(brewEntry.getModifierList().getLevel(bm).get())));
				});
			} else {
				Optional<Integer> lvl = list.getLevel(BewitchmentModifiers.POWER);
				if (lvl.isPresent() && lvl.get() > 1) {
					tooltip.add(color + I18n.format("effect." + brewEntry.getPotion().getRegistryName().getResourcePath() + ".name") + " " + lvl.get()); // TODO fix roman
				} else {
					tooltip.add(color + I18n.format("effect." + brewEntry.getPotion().getRegistryName().getResourcePath() + ".name"));
				}
				
				String ref = TextFormatting.DARK_GRAY + I18n.format("effect." + brewEntry.getPotion().getRegistryName().getResourcePath() + ".desc");
				tooltip.add(I18n.format("brew.description.formatting", ref));
			}
		});
	}
	
}
