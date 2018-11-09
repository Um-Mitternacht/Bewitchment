package com.bewitchment.common.core.net;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.core.capability.simple.CapabilityMessage;
import com.bewitchment.common.core.net.messages.*;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.state.IBlockState;
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
public final class NetworkHandler {

	public static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(LibMod.MOD_ID);

	private static int nextId = 0;

	private NetworkHandler() {
	}

	public static void init() {
		HANDLER.registerMessage(ParticleMessage.ParticleMessageHandler.class, ParticleMessage.class, next(), Side.CLIENT);
		HANDLER.registerMessage(TarotMessage.TarotMessageHandler.class, TarotMessage.class, next(), Side.CLIENT);
		//TODO <rustylocks79> remove
		//HANDLER.registerMessage(PlayerMimicDataChanged.PlayerMimicDataHandler.class, PlayerMimicDataChanged.class, next(), Side.CLIENT);
		registerSimpleMessage(PlayerTransformationChangedMessage.class, next(), Side.CLIENT);
		registerSimpleMessage(EntityInternalBloodChanged.class, next(), Side.CLIENT);
		registerSimpleMessage(WitchfireFlame.class, next(), Side.CLIENT);
		registerSimpleMessage(EnergySync.class, next(), Side.CLIENT);
		registerSimpleMessage(InfusionChangedMessage.class, next(), Side.CLIENT);
		registerSimpleMessage(SmokeSpawn.class, next(), Side.CLIENT);
		registerSimpleMessage(CapabilityMessage.class, next(), Side.CLIENT);

		registerSimpleMessage(PlayerUsedAbilityMessage.class, next(), Side.SERVER);
		registerSimpleMessage(WitchFireTP.class, next(), Side.SERVER);
		registerSimpleMessage(PlaceHeldItemMessage.class, next(), Side.SERVER);
	}

	private static <MSG extends SimpleMessage<MSG>> void registerSimpleMessage(Class<MSG> clazz, int id, Side side) {
		HANDLER.registerMessage(clazz, clazz, id, side);
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

	public static int next() {
		nextId++;
		return nextId - 1;
	}
}
