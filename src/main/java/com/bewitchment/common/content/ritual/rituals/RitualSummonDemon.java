package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.entity.spirits.demons.EntityDemon;
import com.bewitchment.common.entity.spirits.demons.EntityDemoness;
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

/**
 * Created by Joseph on 1/26/2019.
 */
public class RitualSummonDemon extends RitualImpl {
	public RitualSummonDemon(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	//Todo: Only a max of 1 demon or demoness at a time, if a demon or demoness has already been spawned, it will not try to summon another. Takes anything listed as human or villager under MobHelper. Including Villagers and Players.

	@Override
	public void onFinish(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos mainGlyphPos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(3) + 1; i++) {
				BlockPos pos = effectivePosition.add(world.rand.nextInt(11) - 5, 1, world.rand.nextInt(11) - 5);
				Entity e;
				switch (world.rand.nextInt(7)) {
					case 0:
						e = new EntityDemon(world);
						e.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(e);
						break;
					case 1:
						e = new EntityDemoness(world);
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
