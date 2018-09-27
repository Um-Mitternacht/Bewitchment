package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.tile.ModTileEntity;
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

import java.util.HashMap;

public class TileEntityGemBowl extends ModTileEntity {

	private static final HashMap<String, Integer> gainMap = new HashMap<>();
	private static final String GEM_TAG_NAME = "gem";
	private static final String DIRECTION_TAG_NAME = "facing";

	static {
		gainMap.put("gemDiamond", 4);
		gainMap.put("gemEmerald", 4);
		gainMap.put("gemPsi", 4);
		gainMap.put("gemPrismarine", 2);
		gainMap.put("gemLifeCrystal", 2);
		gainMap.put("gemQuartz", 1);
		gainMap.put("gemLapis", 1);
		gainMap.put("dustRedstone", 1);
		gainMap.put("gemBloodstone", 2);
		gainMap.put("gemNuummite", 2);
		gainMap.put("gemGarnet", 2);
		gainMap.put("gemTourmaline", 2);
		gainMap.put("gemJasper", 2);
		gainMap.put("gemTigersEye", 2);
		gainMap.put("gemMalachite", 2);
		gainMap.put("gemAmethyst", 2);
		gainMap.put("gemAlexandrite", 2);
		gainMap.put("gemAquamarine", 1);
		gainMap.put("gemRuby", 1);
		gainMap.put("gemSapphire", 1);
		gainMap.put("gemPeridot", 1);
		gainMap.put("gemAmber", 1);
		gainMap.put("gemApatite", 1);
		gainMap.put("gemTopaz", 1);
		gainMap.put("gemJet", 1);
		gainMap.put("gemTanzanite", 1);
		gainMap.put("gemPearl", 1);
		gainMap.put("gemOpal", 1);
		gainMap.put("gemZanite", 1);
		gainMap.put("gemCrimsonMiddleGem", 1);
		gainMap.put("gemAquaMiddleGem", 1);
		gainMap.put("gemGreenMiddleGem", 1);
		gainMap.put("gemZircon", 1);
		gainMap.put("gemAzurite", 1);
		gainMap.put("gemEudialyte", 1);
		gainMap.put("gemRime", 1);
		gainMap.put("gemAgate", 1);
		gainMap.put("gemJade", 1);
		gainMap.put("gemOnyx", 1);
		gainMap.put("gemEnderBiotite", 1);
		gainMap.put("gemBurnium", 1);
		gainMap.put("gemEndimium", 1);
		gainMap.put("gemHephaestite", 1);
		gainMap.put("gemScarlite", 1);
		gainMap.put("gemAether", 1);
		gainMap.put("gemSerpentine", 1);
		gainMap.put("gemPetoskeyStone", 1);
		gainMap.put("gemValonite", 1);
		gainMap.put("gemRhodochrosite", 1);
		gainMap.put("gemBoronNitride", 1);
		gainMap.put("gemFluorite", 1);
		gainMap.put("gemVilliaumite", 1);
		gainMap.put("gemCarobbiite", 1);
		gainMap.put("gemMoon", 1);
		gainMap.put("gemRedstone", 1);
		gainMap.put("gemVinteum", 1);
		gainMap.put("gemQuartzite", 1);
		gainMap.put("gemGlass", 1);
		gainMap.put("gemQuartzBlock", 1);
		gainMap.put("gemAlmandine", 1);
		gainMap.put("gemBlueTopaz", 1);
		gainMap.put("gemCinnabar", 1);
		gainMap.put("gemGreenSapphire", 1);
		gainMap.put("gemRutile", 1);
		gainMap.put("gemLazurite", 1);
		gainMap.put("gemSodalite", 1);
		gainMap.put("gemCertusQuartz", 1);
		gainMap.put("gemOlivine", 1);
		gainMap.put("gemLignite", 1);
		gainMap.put("gemGarnetRed", 1);
		gainMap.put("gemGarnetYellow", 1);
		gainMap.put("gemMonazite", 1);
		gainMap.put("gemDominicanAmber", 1);
		gainMap.put("gemScarabBlue", 1);
		gainMap.put("gemScarab", 1);
		gainMap.put("gemMoldavite", 2);
		gainMap.put("gemVioletSapphire", 1);
		gainMap.put("gemCatsEye", 1);
		gainMap.put("gemAmmolite", 1);
		gainMap.put("gemSpinel", 1);
		gainMap.put("gemMoonstone", 1);
		gainMap.put("gemSunstone", 1);
		gainMap.put("gemPyrope", 1);
		gainMap.put("gemRoseQuartz", 1);
		gainMap.put("gemKunzite", 1);
		gainMap.put("gemKyanite", 1);
		gainMap.put("gemChrysoprase", 1);
		gainMap.put("gemBlackDiamond", 1);
		gainMap.put("gemTektite", 1);
		gainMap.put("gemMorganite", 1);
		gainMap.put("gemLepidolite", 1);
		gainMap.put("gemCoral", 1);
		gainMap.put("gemCarnelian", 1);
		gainMap.put("gemGoldenBeryl", 1);
		gainMap.put("gemHeliodor", 1);
		gainMap.put("gemIndicolite", 1);
		gainMap.put("gemIolite", 1);
		gainMap.put("gemTurquoise", 1);
		gainMap.put("materialCoraliumPearl", 1);
		gainMap.put("gemShadow", 1);
	}

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
			for (String acceptedName : gainMap.keySet()) {
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

	public int getGemValue() {
		if (gem.isEmpty()) {
			return 0;
		}
		for (int oreID : OreDictionary.getOreIDs(gem)) {
			String oreName = OreDictionary.getOreName(oreID);
			if (gainMap.containsKey(oreName)) {
				return gainMap.get(oreName);
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
