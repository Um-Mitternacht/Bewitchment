package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class RitualHungryFlames extends Ritual {
	public RitualHungryFlames() {
		super(new ResourceLocation(Bewitchment.MODID, "hungry_flames"), Arrays.asList(Util.get(ModObjects.ectoplasm), Util.get(Items.BLAZE_POWDER), Util.get(new ItemStack(Items.COAL, 1, Short.MAX_VALUE))), null, null, 180, 333, 25, BlockGlyph.NETHER, -1, -1);
	}

	@Override
	public void onStarted(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onStarted(world, pos, caster, inventory);
		ModTileEntity.clear(inventory);
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.FLAME, effectivePos.getX() + 0.5 + world.rand.nextGaussian() * 2, effectivePos.getY() + world.rand.nextFloat() * 0.5, effectivePos.getZ() + 0.5 + world.rand.nextGaussian() * 2, 0.05 * world.rand.nextFloat(), 0.1 * world.rand.nextFloat(), 0.05 * world.rand.nextFloat()), world.provider.getDimension());
		if (world.getTotalWorldTime() % 40 == 0) {
			List<EntityItem> smeltables = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(effectivePos).grow(3), entity -> !FurnaceRecipes.instance().getSmeltingResult(entity.getItem()).isEmpty());
			for (EntityItem entity : smeltables) {
				ItemStack stack = entity.getItem().copy();
				if (!stack.getItem().hasContainerItem(stack)) {
					if (world.rand.nextDouble() < 0.7)
						InventoryHelper.spawnItemStack(world, entity.posX, entity.posY, entity.posZ, FurnaceRecipes.instance().getSmeltingResult(stack.splitStack(1)).copy());
					else {
						world.spawnEntity(new EntityXPOrb(world, entity.posX, entity.posY, entity.posZ, 2));
						stack.shrink(1);
					}
				}
				entity.setItem(stack);
			}
		}
	}
}