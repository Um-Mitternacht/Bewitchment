package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.item.tool.ItemAthame;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.registry.ModObjects;
import lombok.Data;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "SameReturnValue", "EmptyMethod"})
@Data
public class Ritual extends IForgeRegistryEntry.Impl<Ritual> {
    public static final int[][] small = {{0, 0, 1, 1, 1, 0, 0}, {0, 1, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 1, 0}, {0, 0, 1, 1, 1, 0, 0}};
    public static final int[][] medium = {{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}};
    public static final int[][] large = {{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}};

    private final List<Ingredient> input;
    private final List<ItemStack> output;

    private final Predicate<EntityLivingBase> sacrificePredicate;
    private final int[] circles = new int[3];

    private final int time;
    private final int startingPower;
    private final int runningPower;

    private final boolean canBePerformedRemotely;

    public Ritual(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, boolean canBePerformedRemotely, int time, int startingPower, int runningPower, int small, int medium, int big) {
        setRegistryName(name);
        if (input.size() > 10) throw new IllegalArgumentException("Rituals can only have at most 10 input items");
        if (output != null && output.size() > 5)
            throw new IllegalArgumentException("Rituals can only have at most 5 output items");
        this.input = input;
        this.sacrificePredicate = sacrificePredicate;
        this.output = output;
        this.canBePerformedRemotely = canBePerformedRemotely;
        this.time = time;
        this.startingPower = startingPower;
        this.runningPower = runningPower;
        if (small < 0) throw new IllegalArgumentException("Cannot have the smaller circle missing");
        if (medium < 0 && big > 0)
            throw new IllegalArgumentException("Cannot have missing middle circle when a big circle is present");
        if (small == BlockGlyph.GOLDEN || medium == BlockGlyph.GOLDEN || big == BlockGlyph.GOLDEN)
            throw new IllegalArgumentException("No golden circles allowed");
        circles[0] = small;
        circles[1] = medium;
        circles[2] = big;
    }

    public Ritual(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, int time, int startingPower, int runningPower, int small, int medium, int big) {
        this(name, input, sacrificePredicate, output, true, time, startingPower, runningPower, small, medium, big);
    }

    public String getPreconditionMessage() {
        return "ritual.precondition.empty";
    }

    public boolean isValid(World world, BlockPos altarPos, EntityPlayer caster, ItemStackHandler inventory) {
        return sacrificePredicate == null;
    }

    public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
    }

    public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
        world.playSound(null, altarPos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.7f, 0.7f);
        world.playSound(null, effectivePos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.7f, 0.7f);
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemAthame) stack.damageItem(35, caster);
            if (stack.getItem() instanceof ItemBoline) stack.damageItem(50, caster);
            else inventory.extractItem(i, 1, false);
        }
        if (!world.isRemote && output != null) for (ItemStack stack : output)
            InventoryHelper.spawnItemStack(world, altarPos.getX(), altarPos.getY(), altarPos.getZ(), stack.copy());
    }

    public void onHalted(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
        world.playSound(null, altarPos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.7f, 0.7f);
        if (!world.isRemote) for (int i = 0; i < inventory.getSlots(); i++)
            InventoryHelper.spawnItemStack(world, altarPos.getX(), altarPos.getY(), altarPos.getZ(), inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false));
    }

    public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
    }

    public final boolean matches(World world, BlockPos pos, ItemStackHandler handler) {
        for (int x = 0; x < small.length; x++) {
            for (int z = 0; z < small.length; z++) {
                IBlockState state = world.getBlockState(pos.add(x - small.length / 2, 0, z - small.length / 2));
                if (small[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) == BlockGlyph.GOLDEN || (state.getValue(BlockGlyph.TYPE) != circles[0] && circles[0] != BlockGlyph.ANY))))
                    return false;
            }
        }
        if (circles[1] >= 0) {
            for (int x = 0; x < medium.length; x++) {
                for (int z = 0; z < medium.length; z++) {
                    IBlockState state = world.getBlockState(pos.add(x - medium.length / 2, 0, z - medium.length / 2));
                    if (medium[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) == BlockGlyph.GOLDEN || (state.getValue(BlockGlyph.TYPE) != circles[1] && circles[1] != BlockGlyph.ANY))))
                        return false;
                }
            }
        }
        if (circles[2] >= 0) {
            for (int x = 0; x < large.length; x++) {
                for (int z = 0; z < large.length; z++) {
                    IBlockState state = world.getBlockState(pos.add(x - large.length / 2, 0, z - large.length / 2));
                    if (large[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) == BlockGlyph.GOLDEN || (state.getValue(BlockGlyph.TYPE) != circles[2] && circles[2] != BlockGlyph.ANY))))
                        return false;
                }
            }
        }
        return Util.areISListsEqual(input, handler);
    }
}
