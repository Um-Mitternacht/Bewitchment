package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.equipment.baubles.ItemGrimoireMagia;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	
	public int color, gain;
	
	private Map<IBlockState, Integer> map = new HashMap<>();
	private BlockPos.MutableBlockPos checking = new BlockPos.MutableBlockPos();
	private int counter;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		return super.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		super.readFromNBT(tag);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY ? MagicPower.CAPABILITY.cast(magicPower) : super.getCapability(capability, face);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		boolean flag = super.shouldRefresh(world, pos, oldState, newState) || !newState.getValue(BlockWitchesAltar.TYPE).equals(oldState.getValue(BlockWitchesAltar.TYPE));
		if (!world.isRemote && flag) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 0.75, pos.getZ(), new ItemStack(Blocks.CARPET, 1, color - 1));
		return flag;
	}
	
	@Override
	public ItemStackHandler[] getInventories() {
		ItemStackHandler fin = new ItemStackHandler();
		fin.insertItem(0, new ItemStack(Blocks.CARPET, 1, color - 1), false);
		return new ItemStackHandler[]{fin};
	}
	
	@Override
	public void onLoad() {
		world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 5, 0);
	}
	
	@Override
	public void update() {
		if (world.getTotalWorldTime() % 100 == 0) {
			for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(25))) {
				for (ItemStack stack : Util.getEntireInventory(player))
					if (stack.getItem() instanceof ItemGrimoireMagia && magicPower.drain(100)) {
						stack.getCapability(MagicPower.CAPABILITY, null).fill(25);
						break;
					}
			}
		}
		if (!world.isRemote) {
			if (magicPower.amount > magicPower.maxAmount) magicPower.amount = magicPower.maxAmount;
			if (world.getTotalWorldTime() % 20 == 0) magicPower.fill(gain * 16);
			scan(Bewitchment.proxy.config.altarScansPerTick);
		}
	}
	
	public void forceScan() {
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
		counter = 0;
		map.clear();
		scan(4096);
	}
	
	protected void scan(int times) {
		for (int i = 0; i < times; i++) {
			counter = ++counter % Short.MAX_VALUE;
			int x = counter & 15;
			int y = (counter >> 4) & 15;
			int z = (counter >> 8) & 15;
			checking.setPos(pos.getX() + x - 8, pos.getY() + y - 8, pos.getZ() + z - 8);
			registerToMap(world.getBlockState(checking));
			if (counter == 4095) {
				boolean foundStone = false, foundCup = false, foundPentacle = false, foundSword = false, foundWand = false;
				gain = 1;
				double multiplier = 1;
				int maxPower = 0;
				for (int val : map.values()) maxPower += val;
				for (int cx = -1; cx <= 1; cx++) {
					for (int cz = -1; cz <= 1; cz++) {
						BlockPos pos0 = pos.add(cx, 0, cz);
						if (world.getBlockState(pos0).getBlock() instanceof BlockWitchesAltar) {
							if (world.getBlockState(pos0.up()).getBlock() == ModObjects.blessed_stone) foundStone = true;
							if (!foundStone) {
								AltarUpgrade upgrade = BewitchmentAPI.getAltarUpgrade(world, pos0.up());
								if (upgrade != null) {
									AltarUpgrade.Type type = upgrade.type;
									if (type == AltarUpgrade.Type.CUP && !foundCup) {
										gain += upgrade.upgrade1;
										multiplier *= upgrade.upgrade2;
										foundCup = true;
									}
									if (type == AltarUpgrade.Type.PENTACLE && !foundPentacle) {
										gain += upgrade.upgrade1;
										foundPentacle = true;
									}
									if (type == AltarUpgrade.Type.SWORD && !foundSword) {
										multiplier *= upgrade.upgrade2;
										foundSword = true;
									}
									if (type == AltarUpgrade.Type.WAND && !foundWand) {
										maxPower += 128 * upgrade.upgrade2;
										foundWand = true;
									}
								}
							}
						}
					}
				}
				if (foundStone) {
					gain = Short.MAX_VALUE;
					maxPower = Short.MAX_VALUE;
					multiplier = 1;
				}
				if (gain < 0) gain = 0;
				magicPower.maxAmount = (int) (maxPower * multiplier);
				counter = 0;
				map.clear();
			}
		}
	}
	
	protected void registerToMap(IBlockState state) {
		if (isNatural(state)) {
			int amount = 1;
			IBlockState state0 = convert(state);
			if (Loader.isModLoaded("dynamictrees")) {
			}
			map.put(state0, map.getOrDefault(state0, 0) + amount);
			map.put(state0, Math.max(map.keySet().size() * 2, map.get(state0)));
		}
	}
	
	protected boolean isNatural(IBlockState state) {
		if (Loader.isModLoaded("dynamictrees")) {
		}
		return (!(state.getBlock() instanceof BlockGrass)) && (state.getBlock() instanceof IGrowable || state.getBlock() instanceof IPlantable || state.getBlock() instanceof BlockMelon || state.getBlock() instanceof BlockPumpkin || state.getBlock() instanceof BlockLeaves || (state.getBlock() instanceof BlockRotatedPillar && state.getMaterial() == Material.WOOD));
	}
	
	protected IBlockState convert(IBlockState state) {
		if (Loader.isModLoaded("dynamictrees")) {
			return state;
		}
		if (state.getBlock() instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
		else if (state.getBlock() instanceof BlockRotatedPillar) state = state.getBlock().getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
		else if (state.getBlock() instanceof BlockLeaves) state = state.withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, false);
		else if (!(state.getBlock() instanceof BlockFlower)) state = state.getBlock().getDefaultState();
		return state;
	}
}