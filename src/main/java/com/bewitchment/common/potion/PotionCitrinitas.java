package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

@SuppressWarnings({"unused"})
public class PotionCitrinitas extends ModPotion {
	private static final Field color = ReflectionHelper.findField(EnumDyeColor.class, "colorValue", "field_193351_w");
	
	public PotionCitrinitas() {
		super("citrinitas", false, 0xffff00);
	}
	
	@Override
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
		super.affectEntity(source, indirectSource, living, amplifier, health);
		if (living instanceof EntitySheep) ((EntitySheep) living).setFleeceColor(EnumDyeColor.YELLOW);
		else if (living instanceof EntityWolf) ((EntityWolf) living).setCollarColor(EnumDyeColor.YELLOW);
		for (ItemStack stack : living.getArmorInventoryList()) {
			if (stack.getItem() instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) stack.getItem();
				if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER || armor.hasColor(stack)) {
					try {armor.setColor(stack, color.getInt(EnumDyeColor.YELLOW));}
					catch (IllegalAccessException e) {e.printStackTrace();}
				}
			}
		}
	}
	
	@Override
	public boolean onImpact(World world, BlockPos pos, int amplifier) {
		boolean flag = false;
		int radius = 2 * (amplifier + 1);
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			FakePlayer thrower = FakePlayerFactory.getMinecraft((WorldServer) world);
			if (!ForgeEventFactory.onPlayerBlockPlace(thrower, new BlockSnapshot(world, pos0, world.getBlockState(pos0)), EnumFacing.fromAngle(thrower.rotationYaw), thrower.getActiveHand()).isCanceled()) {
				if (world.rand.nextInt(3) == 0) {
					Block block = world.getBlockState(pos0).getBlock();
					if (block instanceof BlockCarpet) {
						world.setBlockState(pos0, Blocks.CARPET.getDefaultState().withProperty(BlockCarpet.COLOR, EnumDyeColor.YELLOW));
						flag = true;
					}
					else if (block instanceof BlockHardenedClay) {
						world.setBlockState(pos0, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockStainedHardenedClay.COLOR, EnumDyeColor.YELLOW));
						flag = true;
					}
					else if (block instanceof BlockGlass || block instanceof BlockStainedGlass) {
						world.setBlockState(pos0, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.YELLOW));
						flag = true;
					}
					else if ((block instanceof BlockPane && block.getDefaultState().getMaterial() == Material.GLASS) || block instanceof BlockStainedGlassPane) {
						world.setBlockState(pos0, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.YELLOW));
						flag = true;
					}
					else if (block instanceof BlockSand) {
						world.setBlockState(pos0, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.SAND));
						flag = true;
					}
					else if (block instanceof BlockRedSandstone) {
						world.setBlockState(pos0, Blocks.SANDSTONE.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.DOUBLE_STONE_SLAB2) {
						world.setBlockState(pos0, Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND));
						flag = true;
					}
					else if (block == Blocks.STONEBRICK) {
						world.setBlockState(pos0, ModObjects.despair_bricks.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.STONE_BRICK_STAIRS) {
						world.setBlockState(pos0, ModObjects.despair_brick_stairs.getDefaultState());
						flag = true;
					}
					else if (block == Blocks.STONE_SLAB2) {
						world.setBlockState(pos0, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
						flag = true;
					}
					else if (block == Blocks.RED_SANDSTONE_STAIRS) {
						world.setBlockState(pos0, Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, world.getBlockState(pos0).getValue(BlockStairs.HALF)).withProperty(BlockStairs.FACING, world.getBlockState(pos0).getValue(BlockStairs.FACING)).withProperty(BlockStairs.SHAPE, world.getBlockState(pos0).getValue(BlockStairs.SHAPE)));
						flag = true;
					}
					else if (block instanceof BlockColored) {
						world.setBlockState(pos0, block.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW));
					}
				}
			}
		}
		return flag;
	}
}