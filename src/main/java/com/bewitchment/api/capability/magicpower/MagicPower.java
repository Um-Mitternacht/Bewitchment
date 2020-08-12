package com.bewitchment.api.capability.magicpower;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.bewitchment.common.item.tool.ItemBastardsGrimoire;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
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
@Data
public class MagicPower implements ICapabilitySerializable<NBTTagCompound>, Capability.IStorage<MagicPower> {
    @CapabilityInject(MagicPower.class)
    public static final Capability<MagicPower> CAPABILITY = null;

    private int amount;
    private int maxAmount;

    public MagicPower() {
        maxAmount = ModConfig.misc.maxGrimoirePower;
    }

    public static boolean attemptDrain(TileEntity tile, EntityPlayer player, int amount) {
        World world = null;
        if (player != null) world = player.world;
        else if (tile != null) world = tile.getWorld();
        if (world == null) return false;
        boolean flag = attemptDrain(tile, player, amount, true);
        if (flag && !world.isRemote) attemptDrain(tile, player, amount, false);
        return flag;
    }

    public static boolean attemptDrain(TileEntity tile, EntityPlayer player, int amount, boolean simulate) {
        if (amount == 0) return true;
        if (tile instanceof TileEntityWitchesAltar && tile.getCapability(CAPABILITY, null).drain(amount, simulate))
            return true;
        if (player != null) {
            List<ItemStack> inv = Bewitchment.proxy.getEntireInventory(player);
            for (ItemStack stack : inv) {
                if ((stack.getItem() instanceof ItemGrimoireMagia || stack.getItem() instanceof ItemBastardsGrimoire) && stack.hasTagCompound()) {
                    NBTTagCompound tag = stack.getTagCompound();
                    MagicPower cap = new MagicPower();
                    cap.amount = tag.getInteger("amount");
                    cap.maxAmount = tag.getInteger("maxAmount");
                    if (cap.drain(amount, simulate)) {
                        if (!simulate) tag.setInteger("amount", cap.amount);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean transfer(MagicPower to, MagicPower from, int amount, float loss) {
        if (to.amount - amount >= 0 && from.amount < from.maxAmount)
            return to.drain(amount) && from.fill((int) (amount * loss));
        return false;
    }

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

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing face) {
        return getCapability(capability, null) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing face) {
        return capability == CAPABILITY ? CAPABILITY.cast(this) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) CAPABILITY.getStorage().writeNBT(CAPABILITY, this, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, this, null, tag);
    }

    public boolean drain(int amount) {
        return drain(amount, false);
    }

    public boolean drain(int amount, boolean simulate) {
        if (this.amount - amount >= 0) {
            if (!simulate) this.amount -= amount;
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