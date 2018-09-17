package com.bewitchment.common.content.ritual;

import com.bewitchment.api.ritual.IRitual;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.tiles.TileEntityGlyph;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterIRitual implements IForgeRegistryEntry<AdapterIRitual> {

	public static final IForgeRegistry<AdapterIRitual> REGISTRY = new RegistryBuilder<AdapterIRitual>().setName(new ResourceLocation(LibMod.MOD_ID, "rituals")).setType(AdapterIRitual.class).setIDRange(0, 200).create();

	private List<List<ItemStack>> jei_cache;
	private IRitual ritual;

	public AdapterIRitual(IRitual iritual) {
		this.ritual = iritual;

		for (int i = 0; i < ritual.getInput().size(); i++) {
			Ingredient ing = ritual.getInput().get(i);
			if (ing.getMatchingStacks().length == 0)
				throw new IllegalArgumentException("Ritual inputs must be valid: ingredient #" + i + " for " + ritual.getRegistryName() + " has no matching items");
		}

		if (ritual.getInput().size() == 0) {
			throw new IllegalArgumentException("Cannot have an empty input in a ritual");
		}
	}

	public static NonNullList<ItemStack> getItemsUsedForInput(NBTTagCompound tag) {
		NonNullList<ItemStack> list = NonNullList.create();
		NBTTagList tagList = tag.getTagList("itemsUsed", NBT.TAG_COMPOUND);
		tagList.forEach(nbt -> {
			NBTTagCompound itemTag = (NBTTagCompound) nbt;
			list.add(new ItemStack(itemTag));
		});
		return list;
	}

	public boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe, BlockPos effectivePosition, int covenSize) {
		return ritual.isValid(player, world, pos, recipe, effectivePosition, covenSize);
	}

	public void onUpdate(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
		ritual.onUpdate(player, tile, world, pos, data, ticks, effectivePosition, covenSize);
	}

	public void onFinish(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		ritual.onFinish(player, tile, world, pos, data, effectivePosition, covenSize);
	}

	public void onStopped(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		ritual.onStopped(player, tile, world, pos, data, effectivePosition, covenSize);
	}

	public void onStarted(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		ritual.onStarted(player, tile, world, pos, data, effectivePosition, covenSize);
	}

	public boolean onLowPower(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
		return ritual.onLowPower(player, tile, world, pos, data, ticks, effectivePosition, covenSize);
	}

	public int getTime() {
		return ritual.getTime();
	}

	public NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound data) {
		return ritual.getOutput(input, data);
	}

	public boolean isValidInput(List<ItemStack> ground, boolean circles) {
		ArrayList<ItemStack> checklist = new ArrayList<ItemStack>(ground.size());
		for (ItemStack item : ground)
			for (int j = 0; j < item.getCount(); j++) {
				ItemStack copy = item.copy();
				copy.setCount(1);
				checklist.add(copy);
			}

		if (checklist.size() != ritual.getInput().size()) {
			return false;
		}
		ArrayList<Ingredient> removalList = new ArrayList<Ingredient>(ritual.getInput());

		for (ItemStack stack_on_ground : checklist) {
			Ingredient found = null;
			for (Ingredient ingredient : removalList) {
				if (ingredient.apply(stack_on_ground)) {
					found = ingredient;
					break;
				}
			}
			if (found == null) {
				return false;
			}
			removalList.remove(found);
		}
		if (!removalList.isEmpty()) {
			return false;
		}
		return circles;
	}

	public int getCircles() {
		return ritual.getCircles();
	}

	public int getRequiredStartingPower() {
		return ritual.getRequiredStartingPower();
	}

	public int getRunningPower() {
		return ritual.getRunningPower();
	}

	public NonNullList<Ingredient> getInput() {
		return ritual.getInput();
	}

	public List<List<ItemStack>> getJeiInput() {
		if (jei_cache == null) {
			generateCache();
		}
		return jei_cache;
	}

	private void generateCache() { //FIXME LibIngredients.anyDye has the wrong stack number
		jei_cache = Lists.newArrayList();
		HashMap<Ingredient, Integer> sizes = new HashMap<>();
		for (Ingredient i : getInput()) {
			if (sizes.containsKey(i)) {
				sizes.put(i, sizes.get(i) + 1);
			} else {
				sizes.put(i, 1);
			}
		}
		for (Ingredient i : sizes.keySet()) {
			List<ItemStack> l = Lists.newArrayList();
			for (ItemStack is : i.getMatchingStacks()) {
				ItemStack nis = is.copy();
				nis.setCount(sizes.get(i));
				l.add(nis);
			}
			jei_cache.add(l);
		}
	}

	public NonNullList<ItemStack> getOutputRaw() {
		return ritual.getOutputRaw();
	}

	@Override
	public AdapterIRitual setRegistryName(ResourceLocation name) {
		ritual.setRegistryName(name);
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return ritual.getRegistryName();
	}

	@Override
	public Class<AdapterIRitual> getRegistryType() {
		return AdapterIRitual.class;
	}

}
