package com.bewitchment.common.item.magic;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ItemMod;
import com.bewitchment.common.tile.tiles.TileEntityTarotsTable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemTarots extends ItemMod {

	public ItemTarots(String id) {
		super(id);
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("read_name")) {
			String readFutureOf = stack.getTagCompound().getString("read_name");
			tooltip.add(I18n.format(this.getTranslationKey() + ".read_to", readFutureOf));
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if (!playerIn.isSneaking()) {
			if (target instanceof EntityPlayer) {
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}
				stack.getTagCompound().setString("read_id", EntityPlayer.getUUID(((EntityPlayer) target).getGameProfile()).toString());
				stack.getTagCompound().setString("read_name", ((EntityPlayer) target).getDisplayNameString());
				return true;
			}
		}
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (playerIn.isSneaking()) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.getTagCompound().setString("read_id", EntityPlayer.getUUID(playerIn.getGameProfile()).toString());
			stack.getTagCompound().setString("read_name", playerIn.getDisplayNameString());
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() == ModBlocks.tarot_table) {
			TileEntityTarotsTable te = (TileEntityTarotsTable) worldIn.getTileEntity(pos);
			te.read(player.getHeldItem(hand), player);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
