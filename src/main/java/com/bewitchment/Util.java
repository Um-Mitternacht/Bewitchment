package com.bewitchment;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.common.block.BlockFrostfire;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.BlockSaltBarrier;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"deprecation", "ArraysAsListWithZeroOrOneArgument"})
public class Util {
	public static <T extends Block> T registerBlock(T block, String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		block.setRegistryName(loc);
		block.setTranslationKey(loc.toString().replace(":", "."));
		block.setCreativeTab(Bewitchment.proxy.tab);
		ObfuscationReflectionHelper.setPrivateValue(Block.class, block, sound, "blockSoundType", "field_149762_H");
		block.setHardness(hardness);
		block.setResistance(resistance);
		block.setHarvestLevel(tool, level);
		if (mat == Material.CARPET) Blocks.FIRE.setFireInfo(block, 60, 20);
		if (mat == Material.CLOTH || mat == Material.LEAVES) Blocks.FIRE.setFireInfo(block, 30, 60);
		if (mat == Material.PLANTS) Blocks.FIRE.setFireInfo(block, 60, 100);
		if (mat == Material.TNT || mat == Material.VINE) Blocks.FIRE.setFireInfo(block, 15, 100);
		if (mat == Material.WOOD) Blocks.FIRE.setFireInfo(block, 5, 20);
		if (mat == Material.ICE) block.setDefaultSlipperiness(0.98f);
		ForgeRegistries.BLOCKS.register(block);
		if (/*!(block instanceof BlockWitchesLight) && */ !(block instanceof BlockGlyph) && !(block instanceof BlockFrostfire) && !(block instanceof BlockSaltBarrier) && !(block instanceof BlockCrops) && !(block instanceof BlockDoor) && !(block instanceof BlockSlab) && !(block instanceof IFluidBlock)) {
			Item item = new ItemBlock(block).setRegistryName(loc).setTranslationKey(block.getTranslationKey());
			ForgeRegistries.ITEMS.register(item);
			Bewitchment.proxy.registerTexture(item, block instanceof BlockBush ? "inventory" : "normal");
		}
		for (String ore : oreDictionaryNames) OreDictionary.registerOre(ore, block);
		return block;
	}
	
	public static <T extends Block> T registerBlock(T block, String name, T base, String... oreDictionaryNames) {
		return registerBlock(block, name, base.getDefaultState().getMaterial(), base.getSoundType(), base.getBlockHardness(null, null, null), base.getExplosionResistance(null) * 5, base.getHarvestTool(base.getDefaultState()), base.getHarvestLevel(base.getDefaultState()), oreDictionaryNames);
	}
	
	public static <T extends Item> T registerItem(T item, String name, List<Predicate<ItemStack>> predicates, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		item.setRegistryName(loc);
		item.setTranslationKey(loc.toString().replace(":", "."));
		item.setCreativeTab(Bewitchment.proxy.tab);
		ForgeRegistries.ITEMS.register(item);
		if (predicates.isEmpty()) Bewitchment.proxy.registerTexture(item, "normal");
		else Bewitchment.proxy.registerTextureVariant(item, predicates);
		for (String ore : oreDictionaryNames)
			OreDictionary.registerOre(ore, item);
		return item;
	}
	
	public static Item registerItem(Item item, String name, String... oreDictionaryNames) {
		return registerItem(item, name, Arrays.asList(), oreDictionaryNames);
	}
	
	public static Item registerItem(String name, String... oreDictionaryNames) {
		return registerItem(new Item(), name, oreDictionaryNames);
	}
	
	public static List<ItemStack> getEntireInventory(EntityPlayer player) {
		List<ItemStack> fin = new ArrayList<>();
		for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++)
			fin.add(BaublesApi.getBaublesHandler(player).getStackInSlot(i));
		fin.addAll(player.inventory.mainInventory);
		fin.addAll(player.inventory.armorInventory);
		fin.addAll(player.inventory.offHandInventory);
		return fin;
	}
	
	public static Ingredient fromOres(String... oreDictionaryEntries) {
		List<ItemStack> stacks = new ArrayList<>();
		for (String ore : oreDictionaryEntries) stacks.addAll(OreDictionary.getOres(ore));
		return Ingredient.fromStacks(stacks.toArray(new ItemStack[0]));
	}
	
	public static Ingredient get(Item item) {
		return Ingredient.fromStacks(new ItemStack(item));
	}
	
	public static Ingredient get(Block block) {
		return Ingredient.fromStacks(new ItemStack(block));
	}
	
	public static boolean areISListsEqual(List<Ingredient> ings, ItemStackHandler handler) {
		List<ItemStack> checklist = new ArrayList<>();
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if (!stack.isEmpty()) checklist.add(stack);
		}
		if (ings.size() != checklist.size()) return false;
		for (Ingredient ing : ings) {
			boolean found = false;
			for (ItemStack stack : checklist) {
				if (ing.apply(stack)) {
					found = true;
					checklist.remove(stack);
					break;
				}
			}
			if (!found) return false;
		}
		return true;
	}
	
	public static boolean areStacksEqual(ItemStack stack0, ItemStack stack1) {
		return stack0.getItem() == stack1.getItem() && (stack0.getMetadata() == stack1.getMetadata() || stack1.getMetadata() == Short.MAX_VALUE);
	}
	
	public static boolean canMerge(ItemStack stack0, ItemStack stack1) {
		return stack0.isEmpty() || (areStacksEqual(stack0, stack1) && stack0.getCount() + stack1.getCount() <= stack0.getMaxStackSize());
	}
	
	public static boolean hasBauble(EntityLivingBase living, IBauble item) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++) if (BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem() == item) return true;
		}
		return false;
	}
	
	public static boolean isRelated(ItemStack stack, String oreDictionaryEntry) {
		for (ItemStack ore : OreDictionary.getOres(oreDictionaryEntry)) {
			
			if (stack.getItem() instanceof ItemSword && ore.getDisplayName().toLowerCase().replaceAll(" ", "_").contains(((ItemSword) stack.getItem()).getToolMaterialName().toLowerCase())) return true;
			if (stack.getItem() instanceof ItemTool && ore.getDisplayName().toLowerCase().replaceAll(" ", "_").contains(((ItemTool) stack.getItem()).getToolMaterialName().toLowerCase())) return true;
			if (stack.getItem() instanceof ItemHoe && ore.getDisplayName().toLowerCase().replaceAll(" ", "_").contains(((ItemHoe) stack.getItem()).getMaterialName().toLowerCase())) return true;
			if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getArmorMaterial().getRepairItemStack().getItem() == ore.getItem()) return true;
		}
		return false;
	}
	
	public static boolean isTransparent(IBlockState state) {
		return state.getMaterial() == Material.GLASS || state.getMaterial() == Material.ICE;
	}
	
	public static int getArmorPieces(EntityLivingBase living, ItemArmor.ArmorMaterial mat) {
		int fin = 0;
		for (ItemStack stack : living.getArmorInventoryList()) {
			if (stack.getItem() instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) stack.getItem();
				if (armor.getArmorMaterial() == mat) fin++;
			}
		}
		return fin;
	}
	
	public static void giveAndConsumeItem(EntityPlayer player, EnumHand hand, ItemStack stack) {
		if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
		if (player.getHeldItem(hand).isEmpty()) player.setHeldItem(hand, stack);
		else if (!player.inventory.addItemStackToInventory(stack)) player.dropItem(stack, false);
	}
	
	public static void registerAltarUpgradeItem(Item item, AltarUpgrade upgrade) {
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == ModObjects.placed_item && s.getTileEntity() instanceof TileEntityPlacedItem && ((TileEntityPlacedItem) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == item, upgrade);
	}
}