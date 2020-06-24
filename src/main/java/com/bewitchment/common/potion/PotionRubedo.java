package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
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

@SuppressWarnings({"unused", "deprecation"})
public class PotionRubedo extends ModPotion {
    private static final Field color = ReflectionHelper.findField(EnumDyeColor.class, "colorValue", "field_193351_w");

    public PotionRubedo() {
        super("rubedo", false, 0xff0000);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        if (living instanceof EntitySheep) ((EntitySheep) living).setFleeceColor(EnumDyeColor.RED);
        else if (living instanceof EntityWolf) ((EntityWolf) living).setCollarColor(EnumDyeColor.RED);
        for (ItemStack stack : living.getArmorInventoryList()) {
            if (stack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) stack.getItem();
                if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER || armor.hasColor(stack)) {
                    try {
                        armor.setColor(stack, color.getInt(EnumDyeColor.RED));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
                        world.setBlockState(pos0, Blocks.CARPET.getDefaultState().withProperty(BlockCarpet.COLOR, EnumDyeColor.RED));
                        flag = true;
                    } else if (block instanceof BlockHardenedClay) {
                        world.setBlockState(pos0, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockStainedHardenedClay.COLOR, EnumDyeColor.RED));
                        flag = true;
                    } else if (block instanceof BlockGlass || block instanceof BlockStainedGlass) {
                        world.setBlockState(pos0, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
                        flag = true;
                    } else if ((block instanceof BlockPane && block.getDefaultState().getMaterial() == Material.GLASS) || block instanceof BlockStainedGlassPane) {
                        world.setBlockState(pos0, Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
                        flag = true;
                    } else if (block instanceof BlockSand) {
                        world.setBlockState(pos0, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
                        flag = true;
                    } else if (block instanceof BlockSandStone) {
                        world.setBlockState(pos0, Blocks.RED_SANDSTONE.getDefaultState());
                        flag = true;
                    } else if (block instanceof BlockDoubleStoneSlab && world.getBlockState(pos0).getValue(BlockDoubleStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND) {
                        world.setBlockState(pos0, Blocks.DOUBLE_STONE_SLAB2.getDefaultState());
                        flag = true;
                    } else if (block instanceof BlockStoneSlab && world.getBlockState(pos0).getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND) {
                        world.setBlockState(pos0, Blocks.STONE_SLAB2.getDefaultState().withProperty(BlockSlab.HALF, world.getBlockState(pos0).getValue(BlockSlab.HALF)));
                        flag = true;
                    } else if (block == Blocks.SANDSTONE_STAIRS) {
                        world.setBlockState(pos0, Blocks.RED_SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, world.getBlockState(pos0).getValue(BlockStairs.HALF)).withProperty(BlockStairs.FACING, world.getBlockState(pos0).getValue(BlockStairs.FACING)).withProperty(BlockStairs.SHAPE, world.getBlockState(pos0).getValue(BlockStairs.SHAPE)));
                        flag = true;
                    } else if (block instanceof BlockColored) {
                        world.setBlockState(pos0, block.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED));
                    }
                }
            }
        }
        return flag;
    }
}