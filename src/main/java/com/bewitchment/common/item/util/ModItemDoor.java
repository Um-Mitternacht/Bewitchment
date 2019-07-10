package com.bewitchment.common.item.util;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings("NullableProblems")
public class ModItemDoor extends ItemDoor {
	public final ModBlockDoor door;
	
	public ModItemDoor(String name, Block base, String... oreDictionaryNames) {
		this(name, new ModBlockDoor("block_" + name, base), oreDictionaryNames);
	}
	
	private ModItemDoor(String name, ModBlockDoor door, String... oreDictionaryNames) {
		super(door);
		Util.registerItem(this, name, oreDictionaryNames);
		this.door = door;
		this.door.drop = new ItemStack(this);
		ForgeRegistries.BLOCKS.register(door);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		EnumActionResult flag = super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
		if (flag != EnumActionResult.FAIL && this == ModObjects.juniper_door) Util.giveItem(player, ItemJuniperKey.setTags(world, pos.offset(face), new ItemStack(ModObjects.juniper_key)));
		return flag;
	}
	
	@SuppressWarnings({"NullableProblems", "ConstantConditions", "deprecation"})
	public static class ModBlockDoor extends BlockDoor {
		private ItemStack drop;
		
		private ModBlockDoor(String name, Block base) {
			super(base.getDefaultState().getMaterial());
			Util.registerBlock(this, name, base);
			setCreativeTab(null);
			if (base == ModObjects.juniper_planks) setResistance(Integer.MAX_VALUE);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getRenderLayer() {
			return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
		}
		
		@Override
		public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
			return drop;
		}
		
		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
			return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : drop.getItem();
		}
		
		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
			if (this == ModObjects.juniper_door.door) {
				boolean found = false;
				for (ItemStack stack : Bewitchment.proxy.getEntireInventory(player)) {
					if (ItemJuniperKey.canAccess(world, pos, world.provider.getDimension(), stack)) {
						found = true;
						break;
					}
				}
				if (!found) {
					if (!world.isRemote) player.sendStatusMessage(new TextComponentTranslation("juniper_key.invalid"), true);
					return true;
				}
			}
			return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
		}
		
		@Override
		public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
			float val = super.getPlayerRelativeBlockHardness(state, player, world, pos);
			if (this == ModObjects.juniper_door.door) {
				for (ItemStack stack : Bewitchment.proxy.getEntireInventory(player)) if (ItemJuniperKey.canAccess(world, pos, player.dimension, stack)) return val;
				return -1;
			}
			return val;
		}
	}
}