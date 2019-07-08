package com.bewitchment.common.item.tool;

import com.bewitchment.Util;
import com.bewitchment.common.block.BlockJuniperChest;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class ItemJuniperKey extends Item {
	public ItemJuniperKey() {
		super();
		Util.registerItem(this, "juniper_key");
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (player.isCreative() && (world.getBlockState(pos).getBlock() instanceof BlockJuniperChest || world.getBlockState(pos).getBlock() == ModObjects.juniper_door.door)) {
			setTags(world, pos, stack);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
			BlockPos pos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
			tooltip.add(I18n.format("tooltip." + getTranslationKey().substring(5), pos.getX(), pos.getY(), pos.getZ(), stack.getTagCompound().getString("dimensionName")));
		}
	}
	
	public static ItemStack setTags(World world, BlockPos pos, ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("location")) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setLong("location", pos.toLong());
			stack.getTagCompound().setInteger("dimension", world.provider.getDimension());
			stack.getTagCompound().setString("dimensionName", world.provider.getDimensionType().getName());
		}
		return stack;
	}
	
	public static boolean canAccess(IBlockAccess world, BlockPos pos, int dimension, ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().getInteger("dimension") == dimension) {
				if (world.getBlockState(pos).getBlock() == ModObjects.juniper_door.door && world.getBlockState(pos.down()).getBlock() == ModObjects.juniper_door.door) return canAccess(world, pos.down(), dimension, stack);
				return stack.getTagCompound().getLong("location") == pos.toLong();
			}
		}
		return false;
	}
}