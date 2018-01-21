package com.bewitchment.common.core.net;

import com.bewitchment.api.capability.IBrewStorage;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.core.net.messages.BrewMessage;
import com.bewitchment.common.core.net.messages.EnergyMessage;
import com.bewitchment.common.core.net.messages.ParticleMessage;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * This class was created by Arekkuusu on 08/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class PacketHandler {

	public static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(LibMod.MOD_ID);

	private PacketHandler() {
	}

	public static void init() {
		int id = 0;
		HANDLER.registerMessage(ParticleMessage.ParticleMessageHandler.class, ParticleMessage.class, id++, Side.CLIENT);
		HANDLER.registerMessage(EnergyMessage.EnergyMessageHandler.class, EnergyMessage.class, id++, Side.CLIENT);
		HANDLER.registerMessage(BrewMessage.PotionMessageHandler.class, BrewMessage.class, id, Side.CLIENT);
	}

	public static void sendNear(EntityLivingBase entity, IBrewStorage storage) {
		int dim = entity.getEntityWorld().provider.getDimension();
		HANDLER.sendToAllAround(new BrewMessage(storage, entity), new NetworkRegistry.TargetPoint(dim, entity.posX, entity.posY, entity.posZ, 40));
	}

	public static void spawnParticle(ParticleF particleF, World world, double x, double y, double z, int amount, double xSpeed, double ySpeed, double zSpeed, int... args) {
		NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 10);
		HANDLER.sendToAllAround(new ParticleMessage(particleF, x, y, z, amount, xSpeed, ySpeed, zSpeed, args), point);
	}

	public static void sendTileUpdateNearbyPlayers(TileEntity tile) {
		final IBlockState state = tile.getWorld().getBlockState(tile.getPos());
		tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 8);
	}

	public static void updateToNearbyPlayers(World worldObj, BlockPos pos) {
		final IBlockState state = worldObj.getBlockState(pos);
		worldObj.notifyBlockUpdate(pos, state, state, 8);
	}
}
