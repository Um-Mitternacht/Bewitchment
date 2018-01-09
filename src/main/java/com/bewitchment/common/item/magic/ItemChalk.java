
package com.bewitchment.common.item.magic;
	
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.tools.BlockCircleGlyph;
import com.bewitchment.common.item.ItemMod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
	
public class ItemChalk extends ItemMod {
	
	private static final int MAX_USES = 40;
	
	public ItemChalk(String id) {
		super(id);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() > BlockCircleGlyph.GlyphType.values().length)
			return super.getUnlocalizedName(stack) + "_" + BlockCircleGlyph.GlyphType.values()[0].name().toLowerCase();
		return super.getUnlocalizedName(stack) + "_" + BlockCircleGlyph.GlyphType.values()[stack.getMetadata()].name().toLowerCase();
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
	
	@Override
	public boolean canItemEditBlocks() {
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("usesLeft", MAX_USES);
		}
		int usesLeft = stack.getTagCompound().getInteger("usesLeft");
		return 1d - ((double) usesLeft / (double) MAX_USES);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0;
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (this.isInCreativeTab(itemIn)) {
			for (int i = 0; i < 4; i++) {
				tab.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean isReplacing = worldIn.getBlockState(pos).getBlock().equals(ModBlocks.glyph);
		if (!worldIn.isRemote && (facing == EnumFacing.UP && ModBlocks.glyph.canPlaceBlockAt(worldIn, pos.up()) || isReplacing)) {
			ItemStack chalk = player.getHeldItem(hand);
			if (!chalk.hasTagCompound()) {
				chalk.setTagCompound(new NBTTagCompound());
				chalk.getTagCompound().setInteger("usesLeft", MAX_USES);
			}
			int type = chalk.getItemDamage();
			if (!player.isCreative()) {
				int usesLeft = chalk.getTagCompound().getInteger("usesLeft") - 1;
				chalk.getTagCompound().setInteger("usesLeft", usesLeft);
				if (usesLeft < 1)
					chalk.setCount(0);
			}
			IBlockState state = ModBlocks.glyph.getExtendedState(ModBlocks.glyph.getDefaultState(), worldIn, pos);
			state = state.withProperty(BlockCircleGlyph.FACING, EnumFacing.HORIZONTALS[(int) (Math.random() * 4)]);
			state = state.withProperty(BlockCircleGlyph.TYPE, BlockCircleGlyph.GlyphType.values()[type]);
			worldIn.setBlockState(isReplacing ? pos : pos.up(), state, 2);
			}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
	
	@Override
	public void registerModel() {
		for (int meta = 0; meta < 4; meta++) {
			ResourceLocation rl = new ResourceLocation(getRegistryName() + "_" + BlockCircleGlyph.GlyphType.values()[meta].name().toLowerCase());
			ModelResourceLocation mrl = new ModelResourceLocation(rl, "inventory");
			ModelLoader.setCustomModelResourceLocation(this, meta, mrl);
		}
	}
}
