package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.entity.living.animals.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RitualCallOfTheWild extends RitualImpl {
	public RitualCallOfTheWild(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos mainGlyphPos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(3) + 1; i++) {
				BlockPos pos = effectivePosition.add(world.rand.nextInt(11) - 5, 1, world.rand.nextInt(11) - 5);
				Entity e;
				switch (world.rand.nextInt(7)) {
					case 0:
						e = new EntityBlindworm(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 1:
						e = new EntityLizard(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 2:
						e = new EntityNewt(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 3:
						e = new EntityOwl(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 4:
						e = new EntityRaven(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 5:
						e = new EntitySnake(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 6:
						e = new EntityToad(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					default:
						break;
				}
			}
		}
	}
}