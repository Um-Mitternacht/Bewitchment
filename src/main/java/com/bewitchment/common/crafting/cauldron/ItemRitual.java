package com.bewitchment.common.crafting.cauldron;

import java.util.List;

import com.bewitchment.api.cauldron_ritual.CauldronRitualHolder;
import com.bewitchment.api.cauldron_ritual.ICauldronRitual;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.core.net.PacketHandler;
import com.bewitchment.common.tile.TileCauldron;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * This class was created by Arekkuusu on 21/05/2017.
 * It's distributed as split of Bewitchment under
 * the MIT license.
 */
public class ItemRitual implements ICauldronRitual<TileCauldron> {

	private final ItemStack stack;
	private final int cost;
	private final int split;

	public ItemRitual(int cost) {
		this.stack = ItemStack.EMPTY;
		this.cost = cost;
		this.split = cost / 10;
	}

	public ItemRitual(ItemStack stack, int cost) {
		this.stack = stack;
		this.cost = cost;
		this.split = cost / 10;
	}

	@Override
	public boolean canPerform(TileCauldron tile, World world, BlockPos pos) {
		return true;
	}

	@Override
	public void onUpdate(CauldronRitualHolder<TileCauldron> ritual, TileCauldron tile, World world, BlockPos pos) {
		if (getCost() > 0 && ritual.energy_left > 0 && ritual.ticks % 10 == 0) {
			List<EntityPlayer> list = EnergyHandler.getEnergySources(EntityPlayer.class, world, pos, 5);
			int taken = 100 * ((split / list.size()) + 1);
			for (int i = 0, size = list.size(); i < size; i++) {
				EntityPlayer player = list.get(i);
				if (EnergyHandler.addEnergy(player, -taken)) {
					ritual.energy_left -= taken;
				} else if (i + 1 >= size) {
					ritual.fail();
				}
			}
		}
		if (world instanceof WorldServer && ritual.ticks % 5 == 0) {
			double x = world.rand.nextFloat();
			double y = 0.2F + world.rand.nextFloat();
			double z = world.rand.nextFloat();
			((WorldServer) world).spawnParticle(EnumParticleTypes.SPELL_WITCH, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 4, x, y, z, 0D);
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onFinish(TileCauldron tile, World world, BlockPos pos) {
		for (int i = 0; i < 20; i++) {
			final float x = pos.getX() + 0.2F + MathHelper.clamp(world.rand.nextFloat(), 0F, 0.5F);
			final float y = pos.getY() + 0.2F + world.rand.nextFloat();
			final float z = pos.getZ() + 0.2F + MathHelper.clamp(world.rand.nextFloat(), 0F, 0.5F);

			PacketHandler.spawnParticle(ParticleF.STEAM, world, x, y, z, 10, 0, 0, 0);
		}
		if (!stack.isEmpty()) {
			if (tile.getContainer().isEmpty()) {
				tile.setContainer(stack);
			} else {
				spawnItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
			}
		}
		world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
		IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		handler.drain(1000, true);
	}

	@SuppressWarnings("ConstantConditions")
	private void spawnItem(World world, double x, double y, double z) {
		final EntityItem item = new EntityItem(world, x + 0.5D, y, z + 0.5D, stack);
		item.motionX = world.rand.nextDouble() * 2 - 1;
		item.motionZ = world.rand.nextDouble() * 2 - 1;
		item.motionY = 0.1D;
		world.spawnEntity(item);
	}

	@Override
	public int getCost() {
		return cost;
	}

	public ItemStack getStack() {
		return stack;
	}
}
