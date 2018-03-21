package com.bewitchment.common.ritual;

import com.bewitchment.api.ritual.IRitual;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.tile.TileEntityGlyph;
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
import java.util.Arrays;
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

	public boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe) {
		return ritual.isValid(player, world, pos, recipe);
	}

	public void onUpdate(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {
		ritual.onUpdate(player, tile, world, pos, data, ticks);
	}

	public void onFinish(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data) {
		ritual.onFinish(player, tile, world, pos, data);
	}

	public void onStopped(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data) {
		ritual.onStopped(player, tile, world, pos, data);
	}

	public void onStarted(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data) {
		ritual.onStarted(player, tile, world, pos, data);
	}

	public boolean onLowPower(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks) {
		return ritual.onLowPower(player, tile, world, pos, data, ticks);
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
		if (jei_cache == null) generateCache();
		return jei_cache;
	}

	private void generateCache() {
		jei_cache = new ArrayList<List<ItemStack>>();
		for (Ingredient i : ritual.getInput()) {
			jei_cache.add(Arrays.asList(i.getMatchingStacks()));
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
