package com.bewitchment.common.entity.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SyncDragonsBloodBroom;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.common.item.ItemSigil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityDragonsBloodBroom extends EntityBroom {
	public ItemSigil sigil = null;
	
	public EntityDragonsBloodBroom(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.getWorldTime() % 100 == 0 && sigil != null && getControllingPassenger() instanceof EntityPlayer && world.getWorldTime() % 20 == 0) {
			sigil.applyEffects((EntityPlayer) getControllingPassenger());
		}
		if (!world.isRemote) for (EntityPlayer player : world.playerEntities)
			Bewitchment.network.sendTo(new SyncDragonsBloodBroom(this, sigil), (EntityPlayerMP) player);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() != null && getControllingPassenger() == null) {
			setDead();
			if (!world.isRemote) {
				ItemStack stack = item.copy();
				if (sigil != null) {
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setString("sigil", sigil.getRegistryName().toString());
				}
				InventoryHelper.spawnItemStack(world, posX, posY, posZ, stack);
			}
			return true;
		}
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		sigil = tag.getString("sigil").isEmpty() ? null : (ItemSigil) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("sigil")));
		super.readEntityFromNBT(tag);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setString("sigil", sigil == null ? "" : sigil.getRegistryName().toString());
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!player.world.isRemote && player.getHeldItem(hand).getItem() instanceof ItemSigil && sigil == null) {
			sigil = (ItemSigil) player.getHeldItem(hand).getItem();
			player.getHeldItem(hand).shrink(1);
			return true;
		}
		return super.processInitialInteract(player, hand);
	}
	
	@Override
	protected float getSpeed() {
		return 1.9f;
	}
	
	@Override
	protected float getMaxSpeed() {
		return 1.3f;
	}
	
	@Override
	protected float getThrust() {
		return 0.15f;
	}
	
	@Override
	protected int getMagicCost() {
		return 1;
	}
	
	public void setSigil(ItemSigil sigil) {
		this.sigil = sigil;
	}
}
