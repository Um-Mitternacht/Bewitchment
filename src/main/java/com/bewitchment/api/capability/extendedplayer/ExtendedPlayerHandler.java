package com.bewitchment.api.capability.extendedplayer;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Contract;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.item.ItemContract;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.UUID;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ExtendedPlayerHandler {
	private static final ResourceLocation LOC = new ResourceLocation(Bewitchment.MODID, "extended_player");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) event.addCapability(LOC, new ExtendedPlayer());
	}
	
	@SubscribeEvent
	public void clonePlayer(PlayerEvent.Clone event) {
		event.getEntityPlayer().getCapability(ExtendedPlayer.CAPABILITY, null).deserializeNBT(event.getOriginal().getCapability(ExtendedPlayer.CAPABILITY, null).serializeNBT());
	}
	
	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END) {
			ExtendedPlayer cap = event.player.getCapability(ExtendedPlayer.CAPABILITY, null);
			if (cap.fortune != null) {
				if (event.player.world.getTotalWorldTime() % 20 == 0) cap.fortuneTime--;
				if (cap.fortuneTime == 0) {
					if (cap.fortune.apply(event.player)) cap.fortune = null;
					else cap.fortuneTime = (event.player.getRNG().nextInt(cap.fortune.maxTime - cap.fortune.minTime) + cap.fortune.minTime);
					ExtendedPlayer.syncToClient(event.player);
				}
			}
			if (cap.curses != null) {  //check "curse conditions"
				List<Curse> curses = cap.getCurses();
				for (Curse curse : curses) {
					if (curse.getCurseCondition() == Curse.CurseCondition.EXIST && event.player.getRNG().nextDouble() < curse.chance) curse.doCurse(event, event.player);
					if (curse.getCurseCondition() == Curse.CurseCondition.INSTANT) {
						curse.doCurse(event, event.player);
						cap.removeCurse(curse);
					}
				}
				if (event.player.world.getWorldTime() % 20 == 0) { //todo also count in sleeping/other time skips
					cap.updateCurses();
				}
			}
			if (cap.ritualDisabledTime > 0) {
				cap.ritualDisabledTime--;
				cap.canRitual = false;
				ExtendedPlayer.syncToClient(event.player);
			}
			else {
				cap.canRitual = true;
				ExtendedPlayer.syncToClient(event.player);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			ExtendedPlayer ep = player.getCapability(ExtendedPlayer.CAPABILITY, null);
			if (!event.getEntityLiving().isNonBoss()) {
				NBTTagList list = ep.uniqueDefeatedBosses;
				String name = EntityRegistry.getEntry(event.getEntityLiving().getClass()).getName();
				boolean found = false;
				for (int i = 0; i < list.tagCount(); i++) {
					if (list.getStringTagAt(i).equals(name)) {
						found = true;
						break;
					}
				}
				if (!found) {
					list.appendTag(new NBTTagString(name));
					ExtendedPlayer.syncToClient(player);
				}
			}
			if (ep.curses != null) {
				for (Curse curse : ep.getCurses()) {
					if (curse.getCurseCondition() == Curse.CurseCondition.KILL) curse.doCurse(event, player);
				}
			}
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack.getItem() instanceof ItemContract && !((ItemContract) stack.getItem()).complete(stack) && stack.hasTagCompound() && stack.getTagCompound().hasKey("mobsTotal") && stack.getTagCompound().hasKey("mobsComplete") && stack.getTagCompound().hasKey("boundId")) {
					if (Util.findPlayer(stack.getTagCompound().getString("boundId")) == player) {
						Contract contract = (Contract) GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(stack.getTagCompound().getString("contract")));
						if (contract.entities.test(event.getEntityLiving())) {
							stack.getTagCompound().setInteger("mobsComplete", stack.getTagCompound().getInteger("mobsComplete") + 1);
							break;
						}
					}
				}
			}
			if (event.getEntityLiving() instanceof EntityMob) {
				player.getCapability(ExtendedPlayer.CAPABILITY, null).mobsKilled++;
				ExtendedPlayer.syncToClient(player);
			}
			if (event.getEntityLiving() instanceof IEntityOwnable) {
				NBTTagCompound nbt = event.getEntityLiving().serializeNBT();
				if (event.getEntityLiving().serializeNBT().getString("OwnerUUID") != null) {
					// Doesn't work when owner is offline
					Util.findPlayer(UUID.fromString(event.getEntityLiving().serializeNBT().getString("OwnerUUID"))).getCapability(ExtendedPlayer.CAPABILITY, null).pets--;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBreakBlock(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote && event.getPlayer().hasCapability(ExtendedPlayer.CAPABILITY, null)) {
			ExtendedPlayer ep = event.getPlayer().getCapability(ExtendedPlayer.CAPABILITY, null);
			if (ep.curses != null) {
				for (Curse curse : ep.getCurses()) {
					if (curse.getCurseCondition() == Curse.CurseCondition.BLOCK_BREAK) curse.doCurse(event, event.getPlayer());
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockDrop(BlockEvent.HarvestDropsEvent event) {
		if (!event.getWorld().isRemote && event.getHarvester() != null && event.getHarvester().hasCapability(ExtendedPlayer.CAPABILITY, null)) {
			ExtendedPlayer ep = event.getHarvester().getCapability(ExtendedPlayer.CAPABILITY, null);
			if (ep.curses != null) {
				for (Curse curse : ep.getCurses()) {
					if (curse.getCurseCondition() == Curse.CurseCondition.BLOCK_DROP) curse.doCurse(event, event.getHarvester());
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (!event.getEntityLiving().world.isRemote) {
			// damage, when player attacks
			if (event.getSource().getTrueSource() instanceof EntityPlayer && event.getSource().getTrueSource().hasCapability(ExtendedPlayer.CAPABILITY, null)) {
				ExtendedPlayer ep = event.getSource().getTrueSource().getCapability(ExtendedPlayer.CAPABILITY, null);
				if (ep.curses != null) {
					for (Curse curse : ep.getCurses()) {
						if (curse.getCurseCondition() == Curse.CurseCondition.DAMAGE) curse.doCurse(event, (EntityPlayer) event.getSource().getTrueSource());
					}
				}
			}
			// hurt, when player is hurt by something
			if (event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().hasCapability(ExtendedPlayer.CAPABILITY, null)) {
				ExtendedPlayer ep = event.getEntityLiving().getCapability(ExtendedPlayer.CAPABILITY, null);
				if (ep.curses != null) {
					for (Curse curse : ep.getCurses()) {
						if (curse.getCurseCondition() == Curse.CurseCondition.HURT) curse.doCurse(event, (EntityPlayer) event.getEntityLiving());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onMobTamed(AnimalTameEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getTamer() instanceof EntityPlayer) {
			EntityPlayer player = event.getTamer();
			player.getCapability(ExtendedPlayer.CAPABILITY, null).pets++;
		}
	}
	
	@SubscribeEvent
	public void pickUpItems(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			boolean found = false;
			if (stack.getItem() instanceof ItemContract && stack.hasTagCompound() && stack.getTagCompound().hasKey("contract") && stack.getTagCompound().hasKey("boundId") && stack.getTagCompound().hasKey("items") && !((ItemContract) stack.getItem()).complete(stack)) {
				if (stack.getTagCompound().getString("boundId").equals(player.getPersistentID().toString())) {
					NBTTagList list = stack.getTagCompound().getTagList("items", Constants.NBT.TAG_COMPOUND);
					for (int t = 0; t < list.tagCount(); t++) {
						NBTTagCompound tag = list.getCompoundTagAt(t);
						if (event.getItem().getItem().getItem() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item")))) {
							int complete = tag.getInteger("amountComplete");
							int gained = event.getItem().getItem().getCount();
							int toShrink = Math.min(tag.getInteger("amountTotal") - complete, gained);
							event.getItem().getItem().shrink(toShrink);
							tag.setInteger("amountComplete", complete + toShrink);
							if (toShrink > 0) found = true;
							break;
						}
					}
					if (found) break;
				}
			}
		}
	}
}