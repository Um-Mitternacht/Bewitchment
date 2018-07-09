package com.bewitchment.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityGemBowl extends ModTileEntity {
	private static final String[] ACCEPTED_ORE_NAMES = {"gemDiamond", "gemEmerald", "gemQuartz", "gemLapis",
			"dustRedstone", "gemBloodstone", "gemMoldavite", "gemNuummite", "gemGarnet", "gemTourmaline",
			"gemTigersEye", "gemJasper", "gemMalachite", "gemAmethyst", "gemAlexandrite", "gemAquamarine",
			"gemRuby", "gemSapphire", "gemPeridot", "gemAmber", "gemApatite"};
	private ItemStack gem;

	public TileEntityGemBowl() {
		gem = new ItemStack(Items.AIR);
	}

	public void onBowlRightClicked(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack held = playerIn.getHeldItem(hand);
			if (held.isEmpty()) {
				if (this.hasGem()) {
					playerIn.setHeldItem(hand, gem);
					gem = new ItemStack(Items.AIR);
					this.markDirty();
					this.syncToClient();
				}
			} else {
				for (String acceptedName : ACCEPTED_ORE_NAMES) {
					for (int oreID : OreDictionary.getOreIDs(held)) {
						if (OreDictionary.getOreName(oreID).equals(acceptedName)) {
							ItemStack previousGem = gem;
							if (held.getCount() == 1) {
								gem = held;
								playerIn.setHeldItem(hand, previousGem);
							} else {
								gem = new ItemStack(held.getItem(), 1, held.getMetadata());
								held.setCount(held.getCount() - 1);
								if (!previousGem.isEmpty()) {
									ItemHandlerHelper.giveItemToPlayer(playerIn, previousGem);
								}
							}
							this.markDirty();
							this.syncToClient();
						}
					}
				}
			}
		}
	}

	@Override
	void readAllModDataNBT(NBTTagCompound cmp) {
		gem = new ItemStack(cmp.getCompoundTag("gem"));
	}

	@Override
	void writeAllModDataNBT(NBTTagCompound cmp) {
		cmp.setTag("gem", gem.writeToNBT(new NBTTagCompound()));
	}

	@Override
	void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setTag("gem", gem.writeToNBT(new NBTTagCompound()));
	}

	@Override
	void readModSyncDataNBT(NBTTagCompound tag) {
		gem = new ItemStack(tag.getCompoundTag("gem"));
	}

	public boolean hasGem() {
		return !gem.isEmpty();
	}

	public ItemStack getGem() {
		return gem;
	}

	public int getGain() {
		if (gem.isEmpty()) {
			return 0;
		}
		for (int oreID : OreDictionary.getOreIDs(gem)) {
			String oreName = OreDictionary.getOreName(oreID);
			if (oreName.equals("gemDiamond")) {
				return 325;
			} else if (oreName.equals("gemEmerald")) {
				return 275;
			} else if (oreName.equals("gemMoldavite")) {
				return 225;
			} else if (oreName.equals("gemAlexandrite")) {
				return 225;
			} else if (oreName.equals("gemNuummite")) {
				return 225;
			} else if (oreName.equals("gemGarnet")) {
				return 225;
			} else if (oreName.equals("gemAmethyst")) {
				return 225;
			} else if (oreName.equals("gemTourmaline")) {
				return 225;
			} else if (oreName.equals("gemTigersEye")) {
				return 225;
			} else if (oreName.equals("gemMalachite")) {
				return 225;
			} else if (oreName.equals("gemBloodstone")) {
				return 225;
			} else if (oreName.equals("gemJasper")) {
				return 225;
			} else if (oreName.equals("gemLapis")) {
				return 225;
			} else if (oreName.equals("gemQuartz")) {
				return 225;
			} else if (oreName.equals("gemAquamarine")) {
				return 225;
			} else if (oreName.equals("gemRuby")) {
				return 225;
			} else if (oreName.equals("gemSapphire")) {
				return 225;
			} else if (oreName.equals("gemPeridot")) {
				return 225;
			} else if (oreName.equals("gemAmber")) {
				return 225;
			} else if (oreName.equals("gemApatite")) {
				return 225;
			} else if (oreName.equals("dustRedstone")) {
				return 200;
			}
		}
		return 0;
	}
}
