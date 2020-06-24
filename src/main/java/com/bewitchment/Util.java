package com.bewitchment;

import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.api.message.TeleportPlayerClient;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.api.registry.Contract;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.block.*;
import com.bewitchment.common.block.plants.BlockSpanishMoss;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.common.block.tile.entity.TileEntityPoppetShelf;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModRegistries;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"deprecation", "ArraysAsListWithZeroOrOneArgument", "WeakerAccess"})
public class Util {
	public static <T extends Block> void registerBlock(T block, String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		block.setRegistryName(loc);
		block.setTranslationKey(loc.toString().replace(":", "."));
		block.setCreativeTab(Bewitchment.tab);
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
		if (!(block instanceof BlockPlacedItem) && !(block instanceof BlockGlyph) && !(block instanceof BlockFrostfire) && !(block instanceof BlockSaltBarrier) && !(block instanceof BlockCrops) && !(block instanceof BlockDoor) && !(block instanceof BlockSlab) && !(block instanceof IFluidBlock) && !(block instanceof BlockHellfire) && !(block instanceof BlockSigil) && !(block instanceof BlockStatue.BlockFiller) && !(block instanceof BlockSpanishMoss && ((BlockSpanishMoss) block).isTerminalPiece())) {
			Item item = new ItemBlock(block).setRegistryName(loc).setTranslationKey(block.getTranslationKey());
			if (block instanceof BlockStatue) Bewitchment.proxy.setStatueTEISR(item);
			ForgeRegistries.ITEMS.register(item);
			Bewitchment.proxy.registerTexture(item, (block instanceof BlockBush || block instanceof BlockStatue) ? "inventory" : "normal");
		}
		for (String ore : oreDictionaryNames) OreDictionary.registerOre(ore, block);
	}

	public static <T extends Block> void registerBlock(T block, String name, T base, String... oreDictionaryNames) {
		registerBlock(block, name, base.getDefaultState().getMaterial(), base.getSoundType(), base.getBlockHardness(null, null, null), base.getExplosionResistance(null) * 5, base.getHarvestTool(base.getDefaultState()), base.getHarvestLevel(base.getDefaultState()), oreDictionaryNames);
	}

	public static <T extends Item> T registerItem(T item, String name, List<Predicate<ItemStack>> predicates, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		item.setRegistryName(loc);
		item.setTranslationKey(loc.toString().replace(":", "."));
		item.setCreativeTab(Bewitchment.tab);
		ModRegistries.MODEL_PREDICATES.put(item, predicates);
		ModRegistries.ORE_DICTIONARY_ENTRIES.put(item, oreDictionaryNames);
		return item;
	}

	public static Item registerItem(Item item, String name, String... oreDictionaryNames) {
		return registerItem(item, name, Arrays.asList(), oreDictionaryNames);
	}

	public static Item registerItem(String name, String... oreDictionaryNames) {
		return registerItem(new Item(), name, oreDictionaryNames);
	}

	public static EntityPlayer findPlayer(UUID uuid) {
		for (WorldServer ws : DimensionManager.getWorlds()) {
			EntityPlayer player = ws.getPlayerEntityByUUID(uuid);
			if (player != null) return player;
		}
		return null;
	}

	public static EntityPlayer findPlayer(String uuid) {
		return findPlayer(UUID.fromString(uuid));
	}

	public static Ingredient get(Object... objects) {
		List<Ingredient> list = new ArrayList<>();
		for (Object obj : objects) {
			if (obj instanceof String)
				for (ItemStack stack : OreDictionary.getOres((String) obj)) list.add(Ingredient.fromStacks(stack));
			else if (obj instanceof ItemStack) list.add(Ingredient.fromStacks((ItemStack) obj));
			else if (obj instanceof Item)
				list.add(Ingredient.fromStacks(new ItemStack((Item) obj, 1, ((Item) obj).isDamageable() ? Short.MAX_VALUE : 0)));
			else if (obj instanceof Block) list.add(Ingredient.fromStacks(new ItemStack((Block) obj)));
			else throw new IllegalArgumentException(obj + " is not a valid parameter.");
		}
		return Ingredient.merge(list);
	}

	public static boolean areISListsEqual(List<Ingredient> ings, ItemStackHandler handler) {
		if (ings == null) return false;
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

	public static boolean canMerge(ItemStack stack0, ItemStack stack1) {
		return stack0.isEmpty() || (OreDictionary.itemMatches(stack0, stack1, true) && stack0.getCount() + stack1.getCount() <= stack0.getMaxStackSize());
	}

	public static boolean hasBauble(EntityLivingBase living, IBauble item) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			for (int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); i++)
				if (BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem() == item) return true;
		}
		return false;
	}

	public static boolean isRelated(boolean armor, Item item, String... names) {
		for (String name : names) {
			if (armor) {
				if (item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial().name().toLowerCase().contains(name))
					return true;
			} else {
				if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().toLowerCase().contains(name))
					return true;
				if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().toLowerCase().contains(name))
					return true;
				if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().toLowerCase().contains(name))
					return true;
			}
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

	public static void convertEntity(EntityLiving to, EntityLiving from) {
		if (!to.world.isRemote && !to.isDead) {
			from.setLocationAndAngles(to.posX, to.posY, to.posZ, to.rotationYaw, to.rotationPitch);
			from.setNoAI(to.isAIDisabled());
			if (to.hasCustomName()) {
				from.setCustomNameTag(to.getCustomNameTag());
				from.setAlwaysRenderNameTag(to.getAlwaysRenderNameTag());
			}
			to.world.spawnEntity(from);
			to.setDead();
		}
	}

	public static void giveAndConsumeItem(EntityPlayer player, EnumHand hand, ItemStack stack) {
		giveItem(player, stack);
		if (!player.isCreative()) {
			player.getHeldItem(hand).shrink(1);
		}
	}

	public static void replaceAndConsumeItem(EntityPlayer player, EnumHand hand, ItemStack stack) {
		if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
		if (player.getHeldItem(hand).isEmpty()) player.setHeldItem(hand, stack);
		else giveItem(player, stack);
	}

	public static void giveItem(EntityPlayer player, ItemStack stack) {
		if (!player.inventory.addItemStackToInventory(stack)) player.dropItem(stack, false);
	}

	public static void teleportPlayer(EntityPlayer player, double x, double y, double z) {
		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
			Bewitchment.network.sendTo(new TeleportPlayerClient(x, y, z), (EntityPlayerMP) player);
		}
	}

	/**
	 * @param entity the entity to check
	 * @param poppet the poppet variant to find
	 * @return true if poppet found and damaged
	 */
	public static boolean attemptDamagePoppet(EntityLivingBase entity, Item poppet) {
		World world = entity.getEntityWorld();
		ExtendedWorld ext = ExtendedWorld.get(world);

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack.getItem() == poppet && stack.hasTagCompound() && stack.getTagCompound().hasKey("boundId")) {
					String uuid = stack.getTagCompound().getString("boundId");
					if (entity.getUniqueID().toString().equals(uuid)) {
						stack.damageItem(1, entity);
						if (stack.getItemDamage() == stack.getItem().getMaxDamage()) stack.damageItem(1, entity);
						return true;
					}
				}
			}
		}

		for (NBTTagCompound poppetShelves : ext.storedPoppetShelves) {
			BlockPos pos = BlockPos.fromLong(poppetShelves.getLong("position"));
			if (world.getTileEntity(pos) instanceof TileEntityPoppetShelf) {
				TileEntityPoppetShelf te = (TileEntityPoppetShelf) world.getTileEntity(pos);
				if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
					IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
					for (int slot = 0; slot < handler.getSlots(); slot++) {
						ItemStack itemStack = handler.getStackInSlot(slot);
						if (itemStack.getItem() == poppet && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("boundId")) {
							String uuid = itemStack.getTagCompound().getString("boundId");
							if (entity.getUniqueID().toString().equals(uuid)) {
								te.damageSlot(entity, slot);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public static ItemStack getRandomContract(Random rand) {
		List<Curse> contracts = GameRegistry.findRegistry(Curse.class).getValuesCollection().stream().filter(c -> c instanceof Contract).collect(Collectors.toList());
		Contract contract = (Contract) contracts.get(rand.nextInt(contracts.size()));
		ItemStack itemstack = new ItemStack(ModObjects.contract);
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setString("contract", contract.getRegistryName().toString());
		int mobCount = rand.nextInt(5) + 6;
		if (contract.requiresEntities()) itemstack.getTagCompound().setInteger("mobsTotal", mobCount);
		if (contract.requiresEntities()) itemstack.getTagCompound().setInteger("mobsComplete", 0);
		if (contract.requiresItems()) {
			NBTTagList list = new NBTTagList();
			for (Item item : contract.items) {
				NBTTagCompound couple = new NBTTagCompound();
				int amount = 6 + rand.nextInt(6);
				if (item == Items.WATER_BUCKET) amount = 1;
				couple.setString("item", item.getRegistryName().toString());
				couple.setInteger("amountTotal", amount);
				couple.setInteger("amountComplete", 0);
				list.appendTag(couple);
			}
			itemstack.getTagCompound().setTag("items", list);
		}
		return itemstack;
	}

	public static void registerAltarUpgradeItemStack(ItemStack stack, AltarUpgrade upgrade) {
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() == ModObjects.placed_item && s.getTileEntity() instanceof TileEntityPlacedItem && OreDictionary.itemMatches(stack, ((TileEntityPlacedItem) s.getTileEntity()).getInventories()[0].getStackInSlot(0), stack.getHasSubtypes()), upgrade);
	}

	public static void registerAltarUpgradeItem(Item item, AltarUpgrade upgrade) {
		registerAltarUpgradeItemStack(new ItemStack(item, 1, item.isDamageable() ? Short.MAX_VALUE : 0), upgrade);
	}

	public static void registerAltarUpgradeOreDict(String ore, AltarUpgrade upgrade) {
		for (ItemStack stack : OreDictionary.getOres(ore)) registerAltarUpgradeItemStack(stack, upgrade);
	}
}