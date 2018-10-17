package com.bewitchment.common.content.ritual.rituals;

import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.content.ritual.RitualImpl;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.world.BiomeChangingUtils.BiomeChangerWalker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class RitualBiomeShift extends RitualImpl {


	public RitualBiomeShift(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(registryName, input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePos, int covenSize) {
		covenSize = 4; //TODO use the actual coven size
		NonNullList<ItemStack> recipe = AdapterIRitual.getItemsUsedForInput(data);
		int id = Biome.getIdForBiome(Biomes.PLAINS);
		for (ItemStack is : recipe) {
			if (is.getItem() == ModItems.boline && is.hasTagCompound() && is.getTagCompound().hasKey("biome_id")) {
				id = is.getTagCompound().getInteger("biome_id");
			}
		}
		int radius = (int) MathHelper.sqrt(64 * (1 + 2 * covenSize) * (1 + 2 * covenSize));
		BiomeChangerWalker walker = new BiomeChangerWalker(id);
		MutableBlockPos mpos = new MutableBlockPos();
		mpos.setPos(effectivePos.getX() - radius, 0, effectivePos.getZ() - radius);
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (x * x + z * z <= radius * radius) {
					walker.visit(world, mpos);
				}
				mpos.move(EnumFacing.SOUTH);
			}
			mpos.move(EnumFacing.NORTH, 2 * radius + 1);
			mpos.move(EnumFacing.EAST);
		}
		walker.complete();
	}

	@Override
	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		NonNullList<ItemStack> oldOutput = super.getOutput(input, data);
		Optional<ItemStack> oldBoline = input.parallelStream().filter(is -> is.getItem() == ModItems.boline).findFirst();
		if (oldBoline.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModItems.boline).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}

}
