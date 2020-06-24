package com.bewitchment.api.registry.item;

import com.bewitchment.ModConfig;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.common.entity.misc.EntityDragonsBloodBroom;
import com.bewitchment.common.item.ItemSigil;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemBroom extends Item {
    private final EntityEntry entry;

    public ItemBroom(EntityEntry entry) {
        super();
        this.entry = entry;
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (Arrays.asList(ModConfig.misc.broomSweepables).contains(block.getTranslationKey())) {
            if (!world.isRemote) {
                block.dropBlockAsItem(world, pos, state, 0);
                world.setBlockToAir(pos);
                player.swingArm(hand);
                world.playSound(null, pos, ModSounds.BROOM_SWEEP, SoundCategory.BLOCKS, 0.8f, world.rand.nextFloat() * 0.4f + 0.8f);
            } else
                world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, pos.getX() + world.rand.nextDouble(), pos.getY() + 0.1, pos.getZ() + world.rand.nextDouble(), 0, 0, 0);
            return EnumActionResult.SUCCESS;
        } else if (entry != null) {
            if (!world.isRemote) {
                EntityBroom entity = (EntityBroom) entry.newInstance(world);
                entity.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                entity.rotationYaw = player.rotationYaw;
                entity.rotationPitch = player.rotationPitch;
                if (this == ModObjects.dragons_blood_broom && player.getHeldItem(hand).hasTagCompound()) {
                    String boundSigil = player.getHeldItem(hand).getTagCompound().getString("sigil");
                    ((EntityDragonsBloodBroom) entity).sigil = (ItemSigil) ForgeRegistries.ITEMS.getValue(new ResourceLocation(boundSigil));
                }
                entity.item = player.getHeldItem(hand).splitStack(1);
                world.spawnEntity(entity);
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("sigil")) {
            tooltip.add(I18n.format("item." + stack.getTagCompound().getString("sigil").replace(":", ".") + ".name"));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.hasTagCompound();
    }
}