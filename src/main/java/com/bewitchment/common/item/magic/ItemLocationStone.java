package com.bewitchment.common.item.magic;

import com.bewitchment.client.core.ModelResourceLocations;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.util.DimensionalPosition;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibItemName;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Optional;

public class ItemLocationStone extends ItemMod {

	public ItemLocationStone() {
		super(LibItemName.LOCATION_STONE);
		this.setMaxStackSize(1);
		this.setMaxDamage(3);
	}

	public static boolean isBound(ItemStack stack) {
		return stack.getMetadata() == 1;
	}

	public static Optional<DimensionalPosition> getLocation(ItemStack stack) {
		NBTTagCompound tag = checkOrSetTag(stack);
		if (!isBound(stack)) {
			return Optional.empty();
		}
		NBTTagCompound coordTag = tag.getCompoundTag("coords");
		int x = coordTag.getInteger("x");
		int y = coordTag.getInteger("y");
		int z = coordTag.getInteger("z");
		int d = coordTag.getInteger("d");
		return Optional.of(new DimensionalPosition(x, y, z, d));
	}

	public static Optional<DimensionalPosition> getLocationAndDamageStack(ItemStack stack, EntityLivingBase entityIn) {
		Optional<DimensionalPosition> odp = getLocation(stack);
		if (odp.isPresent()) {
			stack.damageItem(1, entityIn);
		}
		return odp;
	}

	public static ItemStack bind(ItemStack stack, DimensionalPosition pos) {
		ItemStack tempStack = stack.copy();
		NBTTagCompound tag = checkOrSetTag(tempStack);
		NBTTagCompound posTag = new NBTTagCompound();
		posTag.setInteger("x", pos.getX());
		posTag.setInteger("y", pos.getY());
		posTag.setInteger("z", pos.getZ());
		posTag.setInteger("d", pos.getDim());
		tag.setTag("coords", posTag);
		ItemStack res = new ItemStack(ModItems.location_stone, tempStack.getCount(), 1);
		res.setTagCompound(tempStack.getTagCompound());
		return res;
	}

	private static NBTTagCompound checkOrSetTag(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		tag.setInteger("damage", 0);
		if (isBound(stack) && !tag.hasKey("coords")) {
			Bewitchment.logger.warn("Stone was bound but had no location data attached");
		}
		return tag;
	}

	@Override
	public int getDamage(ItemStack stack) {
		return checkOrSetTag(stack).getInteger("damage");
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		checkOrSetTag(stack).setInteger("damage", damage);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return isBound(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (isBound(stack)) {
			DimensionalPosition pos = getLocation(stack).get();
			String dimName = "Dim " + pos.getDim(); // TODO transform this to the dimension name
			tooltip.add(I18n.format("item.bewitchment.location_stone.bound_to", pos.getX(), pos.getY(), pos.getZ(), dimName));
		} else {
			tooltip.add(I18n.format("item.bewitchment.location_stone.unbound"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!isBound(stack)) {
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, bind(stack, new DimensionalPosition(playerIn)));
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocations.UNBOUND_LOCATION_STONE);
		ModelLoader.setCustomModelResourceLocation(this, 1, ModelResourceLocations.BOUND_LOCATION_STONE);
	}
}
