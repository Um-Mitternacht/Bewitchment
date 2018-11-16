package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.world.BiomeChangingUtils.BiomeChangerWalker;
import com.bewitchment.common.world.biome.ModBiomes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class RitualCreateVampireLair extends RitualImpl {

	private static final int RADIUS = 10;

	public RitualCreateVampireLair(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePos, int covenSize) {
		BiomeChangerWalker walker = new BiomeChangerWalker(Biome.getIdForBiome(ModBiomes.VAMPIRE_LAIR));
		MutableBlockPos mpos = new MutableBlockPos();
		int blocksUsed = 0;
		for (int i = getAvailableBlocks(player) < 317 ? 1 : RADIUS; i <= RADIUS; i++) {
			mpos.setPos(effectivePos.getX() - RADIUS, 0, effectivePos.getZ() - RADIUS);
			for (int x = -RADIUS; x <= RADIUS; x++) {
				for (int z = -RADIUS; z <= RADIUS; z++) {
					if (x * x + z * z <= RADIUS * RADIUS && getAvailableBlocks(player) > 0) {
						if (walker.visit(world, mpos)) {
							blocksUsed++;
						}
					}
					mpos.move(EnumFacing.SOUTH);
				}
				mpos.move(EnumFacing.NORTH, 2 * RADIUS + 1);
				mpos.move(EnumFacing.EAST);
			}
			if (getAvailableBlocks(player) == 0) {
				break;
			}
		}
		removeVampireLairAvailableBlocks(player, blocksUsed);
		walker.complete();
	}

	private int getAvailableBlocks(EntityPlayer player) {
		//TODO
		return 30;
	}

	private void removeVampireLairAvailableBlocks(EntityPlayer player, int blocksUsed) {
		Log.i("Remove blocks from " + player.getName() + " pool: " + blocksUsed);
		//TODO
	}

	@Override
	public boolean isValid(EntityPlayer player, World world, BlockPos mainGlyphPos, List<ItemStack> recipe, BlockPos effectivePosition, int covenSize) {
		if (player.getCapability(CapabilityTransformation.CAPABILITY, null).getType() != DefaultTransformations.VAMPIRE || getAvailableBlocks(player) <= 0) {
			return false;
		}
		return super.isValid(player, world, mainGlyphPos, recipe, effectivePosition, covenSize);
	}

}
