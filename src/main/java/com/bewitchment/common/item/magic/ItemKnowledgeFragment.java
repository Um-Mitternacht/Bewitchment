package com.bewitchment.common.item.magic;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * This class was created by <Misero> on 03/12/2018.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemKnowledgeFragment extends ItemMod {
	public ItemKnowledgeFragment() {
		super(LibItemName.KNOWLEDGE_FRAGMENT);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack heldItem = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote && heldItem.getMetadata() == 0) { //Only execute on server, and if it's a blank knowledge fragment.
			int data = new Random().nextInt(Fragment.values().length - 1) + 1; //Don't translate to a blank page.
			heldItem = new ItemStack(this, 1, data);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.getMetadata() < 0 || stack.getMetadata() >= Fragment.values().length) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "_" + Fragment.values()[stack.getMetadata()].getName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < Fragment.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
	    /*For clarity, this code is more or less directly copied from ModelHandler.registerModel, but considering that
          we may use quite a few different metadata values for these pages, it's best to do this rather than make a new
          JSON file for each.*/
		for (int i = 0; i < Fragment.values().length; i++) {
			String variant;
			//Maybe make this a switch statement if we want to get fancy and have several different models.
			if (i != 0) {
				variant = "translated";
			} else {
				variant = "untranslated";
			}
			ResourceLocation location = new ResourceLocation(this.getRegistryName() + "_" + variant);
			ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, modelResourceLocation);
		}
	}

	public enum Fragment implements IStringSerializable {
		UNTRANSLATED;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}