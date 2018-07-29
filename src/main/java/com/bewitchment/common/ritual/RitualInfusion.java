package com.bewitchment.common.ritual;

import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.api.infusion.IInfusionCapability;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.InfusionChangedMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualInfusion extends RitualImpl {

	IInfusion type;

	public RitualInfusion(ResourceLocation registryName, NonNullList<Ingredient> input, NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick, IInfusion type) {
		super(type.getRegistryName(), input, output, timeInTicks, circles, altarStartingPower, powerPerTick);
		this.type = type;
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
		if (player == null) {
			return;
		}
		player.getCapability(IInfusionCapability.CAPABILITY, null).setType(type);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new InfusionChangedMessage(player), (EntityPlayerMP) player);
		}
	}

}
