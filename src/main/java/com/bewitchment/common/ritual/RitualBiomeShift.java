package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.world.BiomeChangingUtils;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Joseph on 11/30/2019.
 */
public class RitualBiomeShift extends Ritual {
	public RitualBiomeShift(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		super(new ResourceLocation(Bewitchment.MODID, "biome_shift"), Arrays.asList(Util.get("treeSapling"), Util.get("logWood"), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.boline))), null, null, 10, 780, 30, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1);
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		NonNullList<ItemStack> recipe = AdapterIRitual.getItemsUsedForInput(data);
		int id = Biome.getIdForBiome(Biomes.PLAINS);
		for (ItemStack is : recipe) {
			if (is.getItem() == ModObjects.boline && is.hasTagCompound() && is.getTagCompound().hasKey("biome_id")) {
				id = is.getTagCompound().getInteger("biome_id");
			}
		}
		int radius = (int) MathHelper.sqrt(64 * (1 + 2) * (1 + 2));
		BiomeChangingUtils.BiomeChangerWalker walker = new BiomeChangingUtils.BiomeChangerWalker(id);
		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
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
		Optional<ItemStack> oldBoline = input.parallelStream().filter(is -> is.getItem() == ModObjects.boline).findFirst();
		if (oldBoline.isPresent()) {
			oldOutput.parallelStream().filter(is -> is.getItem() == ModObjects.boline).findFirst().ifPresent(is -> is.setItemDamage(is.getItemDamage() + 50));
		}
		return oldOutput;
	}
	
}
