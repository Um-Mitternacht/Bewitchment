package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.TileEntityGlyph;
import com.bewitchment.common.item.ItemTaglock;
import com.bewitchment.common.item.ItemWaystone;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Collections;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class RitualTeleport extends Ritual {
	public RitualTeleport() {
		super(new ResourceLocation(Bewitchment.MODID, "teleport"), Collections.singletonList(Util.get(ModObjects.waystone, ModObjects.taglock)), null, null, false, 5, 450, 20, BlockGlyph.ENDER, -1, -1);
	}

	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.invalid_location";
	}

	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		if (world.getTileEntity(pos) instanceof TileEntityGlyph) {
			for (int i = 0; i < inventory.getSlots(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack.getItem() instanceof ItemWaystone)
					return stack.hasTagCompound() && stack.getTagCompound().hasKey("location") && stack.getTagCompound().hasKey("dimension");
				if (stack.getItem() instanceof ItemTaglock && stack.hasTagCompound())
					for (Entity entity : world.loadedEntityList)
						if (entity.getPersistentID().equals(UUID.fromString(stack.getTagCompound().getString("boundId"))))
							return true;
			}
		}
		return false;
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		world.playSound(null, effectivePos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.7f, 0.7f);
		BlockPos pos0 = null;
		int dimension = 0;
		if (world.getTileEntity(effectivePos) instanceof TileEntityGlyph) {
			for (int i = 0; i < inventory.getSlots(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack.getItem() instanceof ItemWaystone) {
					pos0 = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
					dimension = stack.getTagCompound().getInteger("dimension");
					stack.damageItem(1, caster);
					break;
				}
				if (stack.getItem() instanceof ItemTaglock) {
					for (Entity entity : world.loadedEntityList) {
						if (entity.getPersistentID().equals(UUID.fromString(stack.getTagCompound().getString("boundId")))) {
							pos0 = entity.getPosition();
							dimension = entity.dimension;
							stack.shrink(1);
							break;
						}
					}
				}
			}
		}
		if (pos0 == null || dimension != world.provider.getDimension()) return;
		for (Entity entity : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(effectivePos).grow(3))) {
			world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
			if (entity instanceof EntityPlayer)
				Util.teleportPlayer((EntityPlayer) entity, pos0.getX() + 0.5, pos0.getY(), pos0.getZ() + 0.5);
			else entity.setPositionAndUpdate(pos0.getX() + 0.5, pos0.getY(), pos0.getZ() + 0.5);
			world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
		}
	}
}