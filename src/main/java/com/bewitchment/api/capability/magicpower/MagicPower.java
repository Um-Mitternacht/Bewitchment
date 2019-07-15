package com.bewitchment.api.capability.magicpower;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SyncStackCapability;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "SameReturnValue"})
public class MagicPower implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<MagicPower> {
	@CapabilityInject(MagicPower.class)
	public static final Capability<MagicPower> CAPABILITY = null;
	
	public int amount, maxAmount;
	
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<MagicPower> capability, MagicPower instance, EnumFacing face) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("amount", instance.amount);
		tag.setInteger("maxAmount", instance.maxAmount);
		return tag;
	}
	
	@Override
	public void readNBT(Capability<MagicPower> capability, MagicPower instance, EnumFacing face, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.amount = tag.getInteger("amount");
		instance.maxAmount = tag.getInteger("maxAmount");
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing face) {
		return capability == CAPABILITY ? CAPABILITY.cast(this) : null;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing face) {
		return getCapability(capability, null) != null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound) CAPABILITY.getStorage().writeNBT(CAPABILITY, this, null);
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		CAPABILITY.getStorage().readNBT(CAPABILITY, this, null, tag);
	}
	
	public static void syncToClient(EntityPlayer player, int slot) {
		if (!player.world.isRemote) Bewitchment.network.sendTo(new SyncStackCapability(Bewitchment.proxy.getEntireInventory(player).get(slot).getCapability(CAPABILITY, null).serializeNBT(), slot), (EntityPlayerMP) player);
	}
	
	public static boolean attemptDrain(TileEntity tile, EntityPlayer player, int amount) {
		World world;
		if (player != null) world = player.world;
		else world = tile.getWorld();
		boolean flag = attemptDrain(tile, player, amount, true);
		if (flag && !world.isRemote) attemptDrain(tile, player, amount, false);
		return flag;
	}
	
	public static boolean attemptDrain(TileEntity tile, EntityPlayer player, int amount, boolean simulate) {
		if (amount == 0) return true;
		if (tile instanceof TileEntityWitchesAltar && tile.getCapability(CAPABILITY, null).drain(amount, simulate)) return true;
		if (player != null) {
			List<ItemStack> inv = Bewitchment.proxy.getEntireInventory(player);
			for (int i = 0; i < inv.size(); i++) {
				ItemStack stack = inv.get(i);
				if (stack.getItem() instanceof ItemGrimoireMagia && stack.getCapability(CAPABILITY, null).drain(amount, simulate)) {
					syncToClient(player, i);
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean transfer(MagicPower to, MagicPower from, int amount, float loss) {
		if (to.amount - amount >= 0 && from.amount < from.maxAmount) return to.drain(amount) && from.fill((int) (amount * loss));
		return false;
	}
	
	public boolean drain(int amount) {
		return drain(amount, false);
	}
	
	public boolean drain(int amount, boolean simulate) {
		if (this.amount - amount >= 0) {
			if (!simulate) this.amount = Math.max(0, this.amount - amount);
			return true;
		}
		return false;
	}
	
	public boolean fill(int amount) {
		return fill(amount, false);
	}
	
	public boolean fill(int amount, boolean simulate) {
		if (this.amount < this.maxAmount) {
			if (!simulate) this.amount = Math.min(this.amount + amount, this.maxAmount);
			return true;
		}
		return false;
	}
}