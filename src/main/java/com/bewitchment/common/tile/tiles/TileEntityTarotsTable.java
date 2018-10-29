package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.helper.PlayerHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.TarotMessage;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TileEntityTarotsTable extends ModTileEntity {

	private static final int READ_COST = 2000;
	private IMagicPowerConsumer altarTracker = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	public void read(@Nonnull ItemStack tarotDeck, @Nonnull EntityPlayer reader) {
		if (!reader.world.isRemote) {
			if (checkDeck(tarotDeck) && altarTracker.drainAltarFirst(reader, pos, world.provider.getDimension(), READ_COST)) {
				reader.openGui(Bewitchment.instance, LibGui.TAROT.ordinal(), reader.world, pos.getX(), pos.getY(), pos.getZ());
				EntityPlayerMP readee = (EntityPlayerMP) PlayerHelper.getPlayerAcrossDimensions(UUID.fromString(tarotDeck.getTagCompound().getString("read_id")));
				if (readee != null) {
					NetworkHandler.HANDLER.sendTo(new TarotMessage(readee), (EntityPlayerMP) reader);
				} else {
					reader.sendStatusMessage(new TextComponentTranslation("item.tarots.error_reading"), true);
				}
			} else {
				reader.sendStatusMessage(new TextComponentTranslation("item.tarots.error_reading"), true);
			}
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(altarTracker);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == IMagicPowerConsumer.CAPABILITY || super.hasCapability(capability, facing);
	}

	private boolean checkDeck(ItemStack tarotDeck) {
		return (tarotDeck.getItem() == ModItems.tarots && tarotDeck.hasTagCompound() && tarotDeck.getTagCompound().hasKey("read_id") && tarotDeck.getTagCompound().hasKey("read_name"));
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("altar", altarTracker.writeToNbt());
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		altarTracker.readFromNbt(tag.getCompoundTag("altar"));
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}
}
