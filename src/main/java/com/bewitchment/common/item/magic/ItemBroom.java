package com.bewitchment.common.item.magic;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.tree.BlockModSapling.EnumSaplingType;
import com.bewitchment.common.core.statics.ModSounds;
import com.bewitchment.common.entity.EntityFlyingBroom;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBroom extends ItemMod {

	public ItemBroom(String id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}


	//Todo: Special effects for cypress broom
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this, 1, 0)); // Mundane woods
			items.add(new ItemStack(this, 1, 1)); // Elder
			items.add(new ItemStack(this, 1, 2)); // Juniper
			items.add(new ItemStack(this, 1, 3)); // Yew
			items.add(new ItemStack(this, 1, 4)); // Cypress
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		String ext = stack.getMetadata() == 0 ? "mundane" : EnumSaplingType.values()[stack.getMetadata() - 1].getName();
		return super.getTranslationKey(stack) + "_" + ext;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.getBlockState(pos).getBlock().equals(ModBlocks.ritual_glyphs) || (world.getBlockState(pos).getBlock().equals(ModBlocks.salt_barrier) && player.getHeldItem(hand).getMetadata() != 0)) {
			world.setBlockToAir(pos);
			player.swingArm(hand);
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BROOM_SWEEP, SoundCategory.BLOCKS, 0.8f, world.rand.nextFloat() * 0.4f + 0.8f, false);
			world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, pos.getX() + world.rand.nextDouble(), pos.getY() + 0.1, pos.getZ() + world.rand.nextDouble(), 0, 0, 0);
			world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, pos.getX() + world.rand.nextDouble(), pos.getY() + 0.1, pos.getZ() + world.rand.nextDouble(), 0, 0, 0);
		} else if (player.getHeldItem(hand).getMetadata() != 0) {
			spawnBroom(player, world, pos.offset(side), player.getHeldItem(hand));
			player.setHeldItem(hand, ItemStack.EMPTY);
		} else {
			return EnumActionResult.PASS;
		}
		return EnumActionResult.SUCCESS;
	}

	private void spawnBroom(EntityPlayer player, World world, BlockPos pos, ItemStack itemStack) {
		if (!world.isRemote) {
			EntityFlyingBroom e = new EntityFlyingBroom(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, itemStack.getMetadata());
			e.setRotationYawHead(player.rotationYaw);
			world.spawnEntity(e);
		}
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < EnumSaplingType.values().length + 1; i++) {
			String ext = i == 0 ? "mundane" : EnumSaplingType.values()[i - 1].getName();
			ModelResourceLocation modelResourceLocation = new ModelResourceLocation(this.getRegistryName() + "_" + ext, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, modelResourceLocation);
		}
	}

}
