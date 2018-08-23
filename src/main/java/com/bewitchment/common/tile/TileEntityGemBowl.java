package com.bewitchment.common.tile;

import com.bewitchment.common.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
			"gemRuby", "gemSapphire", "gemPeridot", "gemAmber", "gemApatite", "gemTopaz", "gemJet",
			"gemTanzanite", "gemPearl", "gemOpal", "gemZanite", "gemPsi", "gemCrimsonMiddleGem", "gemAquaMiddleGem",
			"gemGreenMiddleGem", "gemZircon", "gemAzurite", "gemEudialyte", "gemRime", "gemAgate", "gemJade",
			"gemPrismarine", "gemOnyx", "gemEnderBiotite", "gemBurnium", "gemEndimium", "gemHephaestite", "gemScarlite", "gemAether",
			"gemSerpentine", "gemPetoskeyStone", "gemLifeCrystal", "gemValonite", "gemRhodochrosite", "gemBoronNitride",
			"gemFluorite", "gemVilliaumite", "gemCarobbiite", "gemMoon", "gemRedstone", "gemVinteum", "gemQuartzite",
			"gemGlass", "gemQuartzBlock", "gemAlmandine", "gemBlueTopaz", "gemCinnabar", "gemGreenSapphire", "gemRutile",
			"gemLazurite", "gemSodalite", "gemCertusQuartz", "gemOlivine", "gemLignite", "gemGarnetRed", "gemGarnetYellow",
			"gemMonazite"};

	private static final String GEM_TAG_NAME = "gem";
	private static final String DIRECTION_TAG_NAME = "facing";

	private ItemStack gem;
	private EnumFacing direction;

	public TileEntityGemBowl() {
		gem = new ItemStack(Items.AIR);
		direction = EnumFacing.UP;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		if (worldIn.isRemote) {
			return true;
		}

		ItemStack held = playerIn.getHeldItem(hand);
		if (held.isEmpty()) {
			if (this.hasGem()) {
				playerIn.setHeldItem(hand, gem);
				gem = new ItemStack(Items.AIR);
				direction = EnumFacing.UP;
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
						direction = EnumFacing.fromAngle(playerIn.rotationYaw).getOpposite();
						this.markDirty();
						this.syncToClient();
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockHarvested(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (worldIn.isRemote || player.isCreative()) {
			return;
		}
		EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), gem);
		worldIn.spawnEntity(item);
		EntityItem block = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.gem_bowl));
		worldIn.spawnEntity(block);
	}

	public boolean hasGem() {
		return !gem.isEmpty();
	}

	public ItemStack getGem() {
		return gem;
	}

	public EnumFacing getDirection() {
		return direction;
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
				return 255;
			} else if (oreName.equals("gemQuartz")) {
				return 245;
			} else if (oreName.equals("gemAquamarine")) {
				return 240;
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
			} else if (oreName.equals("gemTopaz")) {
				return 225;
			} else if (oreName.equals("gemJet")) {
				return 225;
			} else if (oreName.equals("gemTanzanite")) {
				return 225;
			} else if (oreName.equals("gemPearl")) {
				return 225;
			} else if (oreName.equals("gemOpal")) {
				return 225;
			} else if (oreName.equals("gemZanite")) {
				return 225;
			} else if (oreName.equals("gemPsi")) {
				return 240;
			} else if (oreName.equals("gemCrimsonMiddleGem")) {
				return 240;
			} else if (oreName.equals("gemAquaMiddleGem")) {
				return 240;
			} else if (oreName.equals("gemGreenMiddleGem")) {
				return 240;
			} else if (oreName.equals("gemZircon")) {
				return 225;
			} else if (oreName.equals("gemAzurite")) {
				return 225;
			} else if (oreName.equals("gemEudialyte")) {
				return 225;
			} else if (oreName.equals("gemRime")) {
				return 225;
			} else if (oreName.equals("gemAgate")) {
				return 225;
			} else if (oreName.equals("gemJade")) {
				return 225;
			} else if (oreName.equals("gemPrismarine")) {
				return 250;
			} else if (oreName.equals("gemOnyx")) {
				return 225;
			} else if (oreName.equals("gemEnderBiotite")) {
				return 225;
			} else if (oreName.equals("gemBurnium")) {
				return 225;
			} else if (oreName.equals("gemEndimium")) {
				return 225;
			} else if (oreName.equals("gemHephaestite")) {
				return 225;
			} else if (oreName.equals("gemScarlite")) {
				return 225;
			} else if (oreName.equals("gemAether")) {
				return 225;
			} else if (oreName.equals("gemSerpentine")) {
				return 225;
			} else if (oreName.equals("gemPetoskeyStone")) {
				return 225;
			} else if (oreName.equals("gemLifeCrystal")) {
				return 225;
			} else if (oreName.equals("gemValonite")) {
				return 225;
			} else if (oreName.equals("gemRhodochrosite")) {
				return 225;
			} else if (oreName.equals("gemBoronNitride")) {
				return 225;
			} else if (oreName.equals("gemFluorite")) {
				return 225;
			} else if (oreName.equals("gemVilliaumite")) {
				return 225;
			} else if (oreName.equals("gemCarobbiite")) {
				return 225;
			} else if (oreName.equals("gemMoon")) {
				return 225;
			} else if (oreName.equals("gemRedstone")) {
				return 225;
			} else if (oreName.equals("gemVinteum")) {
				return 225;
			} else if (oreName.equals("gemQuartzite")) {
				return 225;
			} else if (oreName.equals("gemGlass")) {
				return 225;
			} else if (oreName.equals("gemQuartzBlock")) {
				return 225;
			} else if (oreName.equals("gemAlmandine")) {
				return 225;
			} else if (oreName.equals("gemBlueTopaz")) {
				return 225;
			} else if (oreName.equals("gemCinnabar")) {
				return 225;
			} else if (oreName.equals("gemGreenSapphire")) {
				return 225;
			} else if (oreName.equals("gemRutile")) {
				return 225;
			} else if (oreName.equals("gemLazurite")) {
				return 225;
			} else if (oreName.equals("gemSodalite")) {
				return 225;
			} else if (oreName.equals("gemCertusQuartz")) {
				return 225;
			} else if (oreName.equals("gemOlivine")) {
				return 225;
			} else if (oreName.equals("gemLignite")) {
				return 225;
			} else if (oreName.equals("gemGarnetRed")) {
				return 225;
			} else if (oreName.equals("gemGarnetYellow")) {
				return 225;
			} else if (oreName.equals("gemMonazite")) {
				return 225;
			} else if (oreName.equals("dustRedstone")) {
				return 200;
			}
		}
		return 0;
	}

	public int getMultiplier() {
		if (gem.isEmpty()) {
			return 0;
		}
		for (int oreID : OreDictionary.getOreIDs(gem)) {
			String oreName = OreDictionary.getOreName(oreID);
			if (oreName.equals("gemDiamond")) {
				return 125;
			} else if (oreName.equals("gemEmerald")) {
				return 100;
			} else if (oreName.equals("gemMoldavite")) {
				return 75;
			} else if (oreName.equals("gemAlexandrite")) {
				return 75;
			} else if (oreName.equals("gemNuummite")) {
				return 75;
			} else if (oreName.equals("gemGarnet")) {
				return 75;
			} else if (oreName.equals("gemAmethyst")) {
				return 75;
			} else if (oreName.equals("gemTourmaline")) {
				return 75;
			} else if (oreName.equals("gemTigersEye")) {
				return 75;
			} else if (oreName.equals("gemMalachite")) {
				return 75;
			} else if (oreName.equals("gemBloodstone")) {
				return 75;
			} else if (oreName.equals("gemJasper")) {
				return 75;
			} else if (oreName.equals("gemLapis")) {
				return 75;
			} else if (oreName.equals("gemQuartz")) {
				return 75;
			} else if (oreName.equals("gemAquamarine")) {
				return 75;
			} else if (oreName.equals("gemRuby")) {
				return 75;
			} else if (oreName.equals("gemSapphire")) {
				return 75;
			} else if (oreName.equals("gemPeridot")) {
				return 75;
			} else if (oreName.equals("gemAmber")) {
				return 75;
			} else if (oreName.equals("gemApatite")) {
				return 75;
			} else if (oreName.equals("gemTopaz")) {
				return 75;
			} else if (oreName.equals("gemJet")) {
				return 75;
			} else if (oreName.equals("gemTanzanite")) {
				return 75;
			} else if (oreName.equals("gemPearl")) {
				return 75;
			} else if (oreName.equals("gemOpal")) {
				return 75;
			} else if (oreName.equals("gemZanite")) {
				return 75;
			} else if (oreName.equals("gemPsi")) {
				return 75;
			} else if (oreName.equals("gemCrimsonMiddleGem")) {
				return 75;
			} else if (oreName.equals("gemAquaMiddleGem")) {
				return 75;
			} else if (oreName.equals("gemGreenMiddleGem")) {
				return 75;
			} else if (oreName.equals("gemZircon")) {
				return 75;
			} else if (oreName.equals("gemAzurite")) {
				return 75;
			} else if (oreName.equals("gemEudialyte")) {
				return 75;
			} else if (oreName.equals("gemRime")) {
				return 75;
			} else if (oreName.equals("gemAgate")) {
				return 75;
			} else if (oreName.equals("gemJade")) {
				return 75;
			} else if (oreName.equals("gemPrismarine")) {
				return 75;
			} else if (oreName.equals("gemOnyx")) {
				return 75;
			} else if (oreName.equals("gemEnderBiotite")) {
				return 75;
			} else if (oreName.equals("gemBurnium")) {
				return 75;
			} else if (oreName.equals("gemEndimium")) {
				return 75;
			} else if (oreName.equals("gemHephaestite")) {
				return 75;
			} else if (oreName.equals("gemScarlite")) {
				return 75;
			} else if (oreName.equals("gemAether")) {
				return 75;
			} else if (oreName.equals("gemSerpentine")) {
				return 75;
			} else if (oreName.equals("gemPetoskeyStone")) {
				return 75;
			} else if (oreName.equals("gemLifeCrystal")) {
				return 75;
			} else if (oreName.equals("gemValonite")) {
				return 75;
			} else if (oreName.equals("gemRhodochrosite")) {
				return 75;
			} else if (oreName.equals("gemBoronNitride")) {
				return 75;
			} else if (oreName.equals("gemFluorite")) {
				return 75;
			} else if (oreName.equals("gemVilliaumite")) {
				return 75;
			} else if (oreName.equals("gemCarobbiite")) {
				return 75;
			} else if (oreName.equals("gemMoon")) {
				return 75;
			} else if (oreName.equals("gemRedstone")) {
				return 75;
			} else if (oreName.equals("gemVinteum")) {
				return 75;
			} else if (oreName.equals("gemQuartzite")) {
				return 75;
			} else if (oreName.equals("gemGlass")) {
				return 75;
			} else if (oreName.equals("gemQuartzBlock")) {
				return 75;
			} else if (oreName.equals("gemAlmandine")) {
				return 75;
			} else if (oreName.equals("gemBlueTopaz")) {
				return 75;
			} else if (oreName.equals("gemCinnabar")) {
				return 75;
			} else if (oreName.equals("gemGreenSapphire")) {
				return 75;
			} else if (oreName.equals("gemRutile")) {
				return 75;
			} else if (oreName.equals("gemLazurite")) {
				return 75;
			} else if (oreName.equals("gemSodalite")) {
				return 75;
			} else if (oreName.equals("gemCertusQuartz")) {
				return 75;
			} else if (oreName.equals("gemOlivine")) {
				return 75;
			} else if (oreName.equals("gemLignite")) {
				return 75;
			} else if (oreName.equals("gemGarnetRed")) {
				return 75;
			} else if (oreName.equals("gemGarnetYellow")) {
				return 75;
			} else if (oreName.equals("gemMonazite")) {
				return 75;
			} else if (oreName.equals("dustRedstone")) {
				return 50;
			}
		}
		return 0;
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound cmp) {
		gem = new ItemStack(cmp.getCompoundTag(GEM_TAG_NAME));
		direction = EnumFacing.byName(cmp.getString(DIRECTION_TAG_NAME));
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound cmp) {
		cmp.setTag(GEM_TAG_NAME, gem.writeToNBT(new NBTTagCompound()));
		cmp.setString(DIRECTION_TAG_NAME, direction.getName());
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setTag(GEM_TAG_NAME, gem.writeToNBT(new NBTTagCompound()));
		tag.setString(DIRECTION_TAG_NAME, direction.getName());
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		gem = new ItemStack(tag.getCompoundTag(GEM_TAG_NAME));
		direction = EnumFacing.byName(tag.getString(DIRECTION_TAG_NAME));
	}
}
