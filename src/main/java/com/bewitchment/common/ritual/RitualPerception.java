package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualPerception extends Ritual {
    public RitualPerception() {
        super(new ResourceLocation(Bewitchment.MODID, "perception"), Arrays.asList(Util.get("glowstone"), Util.get(Items.BLAZE_POWDER), Util.get(Items.BLAZE_POWDER)), null, null, -1, 500, 25, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.NORMAL);
    }

    @Override
    public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
        super.onStarted(world, pos, caster, inventory);
        ModTileEntity.clear(inventory);
    }

    @Override
    public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
        if (!world.isRemote && world.getTotalWorldTime() % 100 == 0) {
            for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(effectivePos).grow(20))) {
                entity.removePotionEffect(MobEffects.INVISIBILITY);
                entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 110, 0, false, false));
            }
        }
    }
}