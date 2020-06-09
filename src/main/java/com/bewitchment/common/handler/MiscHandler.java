package com.bewitchment.common.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.event.LockCheckEvent;
import com.bewitchment.api.event.WitchesCauldronEvent;
import com.bewitchment.api.message.DismountBroomMessage;
import com.bewitchment.api.registry.entity.EntityBroom;
import com.bewitchment.common.block.BlockBrazier;
import com.bewitchment.common.block.tile.entity.TileEntityBrazier;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.common.item.tool.ItemSkeletonKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"ConstantConditions", "unused"})
public class MiscHandler {
	//Credit due to individuals who helped me toil through my BS (mostly tslat in the MMD discord)
	
	Biome.SpawnListEntry cleaverSpawn = new Biome.SpawnListEntry(EntityCleaver.class, 5, 0, 1);
	Biome.SpawnListEntry bafometyrSpawn = new Biome.SpawnListEntry(EntityBafometyr.class, 5, 0, 1);
	Biome.SpawnListEntry wurmSpawn = new Biome.SpawnListEntry(EntityFeuerwurm.class, 8, 0, 3);
	Biome.SpawnListEntry hellhoundSpawn = new Biome.SpawnListEntry(EntityHellhound.class, 8, 0, 3);
	Biome.SpawnListEntry cambionSpawn = new Biome.SpawnListEntry(EntityCambion.class, 1, 0, 2);
	
	Biome.SpawnListEntry shadeSpawn = new Biome.SpawnListEntry(EntityShadowPerson.class, 1, 0, 1);
	Biome.SpawnListEntry ghostSpawn = new Biome.SpawnListEntry(EntityGhost.class, 4, 0, 1);
	Biome.SpawnListEntry dogeSpawn = new Biome.SpawnListEntry(EntityBlackDog.class, 6, 0, 1);
	
	@SubscribeEvent
	public void applyBrewingBuffs(WitchesCauldronEvent.CreatePotionEvent event) {
		if (BewitchmentAPI.hasAlchemistGear(event.getUser())) {
			event.setBoosted(true);
			event.setBottles(event.getBottles() + 1);
		}
		if (ExtendedWorld.playerPledgedToDemon(event.getUser().world, event.getUser(), "leonard")) {
			EntityPlayer player = event.getUser();
			List<Entity> entities = player.world.getEntitiesWithinAABB(ModEntityMob.class, new AxisAlignedBB(player.posX - 32, player.posY - 32, player.posZ - 32, player.posX + 32, player.posY + 32, player.posZ + 32), e -> e instanceof IPledgeable);
			for (Entity entity : entities) {
				if (entity instanceof EntityLeonard) {
					event.setAllowHigher(true);
					event.setBottles(event.getBottles() + 1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onNetherEntitySpawnsCheck(WorldEvent.PotentialSpawns ev) {
		WorldServer world = (WorldServer) ev.getWorld();
		
		if (ev.getType() == EnumCreatureType.MONSTER) {
			if (world.provider.getDimensionType() == DimensionType.NETHER && world.getChunkProvider().chunkGenerator.isInsideStructure(world, "Fortress", ev.getPos())) {
				ev.getList().add(cleaverSpawn);
				ev.getList().add(bafometyrSpawn);
				ev.getList().add(wurmSpawn);
				ev.getList().add(hellhoundSpawn);
				ev.getList().add(cambionSpawn);
			}
		}
	}
	
	@SubscribeEvent
	public void onStrongholdSpawnsCheck(WorldEvent.PotentialSpawns ev) {
		WorldServer world = (WorldServer) ev.getWorld();
		
		if (ev.getType() == EnumCreatureType.MONSTER) {
			if (world.provider.getDimensionType() == DimensionType.OVERWORLD && world.getChunkProvider().chunkGenerator.isInsideStructure(world, "Stronghold", ev.getPos())) {
				ev.getList().add(shadeSpawn);
				ev.getList().add(ghostSpawn);
				ev.getList().add(dogeSpawn);
			}
		}
	}
	
	@SubscribeEvent
	public void onMineshaftSpawnsCheck(WorldEvent.PotentialSpawns ev) {
		WorldServer world = (WorldServer) ev.getWorld();
		
		if (ev.getType() == EnumCreatureType.MONSTER) {
			if (world.provider.getDimensionType() == DimensionType.OVERWORLD && world.getChunkProvider().chunkGenerator.isInsideStructure(world, "Mineshaft", ev.getPos())) {
				ev.getList().add(shadeSpawn);
				ev.getList().add(ghostSpawn);
			}
		}
	}
	
	@SubscribeEvent
	public void handleSkeletonKey(LockCheckEvent.LockCheckedEvent event) {
		if (!event.isOpened() && !event.getUser().world.isRemote) {
			
			for (ItemStack stack : Bewitchment.proxy.getEntireInventory(event.getUser())) {
				if (stack.getItem() instanceof ItemSkeletonKey) {
					event.setSendMessage(false);
					IBlockState state = event.getUser().world.getBlockState(event.getLock());
					float mult = 1;
					if (state.getBlock() instanceof BlockContainer) {
						mult = 4;
					}
					else if (state.getBlock() instanceof BlockTrapDoor) {
						mult = 3;
					}
					else if (state.getBlock() instanceof BlockDoor) {
						mult = 2;
					}
					if (event.getUser().experienceLevel < 10) {
						event.getUser().sendStatusMessage(new TextComponentTranslation("skeleton_key.invalid.xp"), true);
						return;
					}
					if (MagicPower.attemptDrain(null, event.getUser(), Math.round(250 * mult))) {
						stack.damageItem(1, event.getUser());
						event.getUser().addExperienceLevel(-10);
						event.setResult(true);
					}
					else {
						event.getUser().sendStatusMessage(new TextComponentTranslation("skeleton_key.invalid.me"), true);
					}
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onFindAttackEntity(LivingSetAttackTargetEvent event) {
		if (!event.getEntity().world.isRemote) {
			if (event.getEntityLiving() instanceof ModEntityMob) {
				ModEntityMob mob = (ModEntityMob) event.getEntityLiving();
				if (mob.summoner != null && event.getTarget() != null && event.getTarget().getPersistentID() == mob.summoner) {
					mob.setAttackTarget(null);
				}
			}
			if (event.getEntityLiving() instanceof EntityTameable && event.getTarget() instanceof EntityDruden) {
				((EntityTameable) event.getEntityLiving()).setAttackTarget(null);
			}
			if (event.getEntityLiving() instanceof EntityGolem && event.getTarget() instanceof EntityCambion) {
				((EntityLiving) event.getEntityLiving()).setAttackTarget(null);
			}
			if (event.getEntityLiving() instanceof IPledgeable && event.getTarget() instanceof EntityPlayer && ExtendedWorld.playerPledgedToDemon(event.getEntityLiving().world, (EntityPlayer) event.getTarget(), ((IPledgeable) event.getEntityLiving()).getPledgeName())) {
				((EntityMob) event.getEntityLiving()).setAttackTarget(null);
			}
		}
	}
	
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt) {
		if (evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)) {
			evt.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryTable(new ResourceLocation(Bewitchment.MODID, "chests/nether_materials"), 5, 0, new LootCondition[0], "bewitchment_nether_materials_entry")}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_nether_materials_pool"));
		}
		if (evt.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON) || evt.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) || evt.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE) || evt.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH) || evt.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE) || evt.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID) || evt.getName().equals(LootTableList.CHESTS_IGLOO_CHEST) || evt.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE) || evt.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY) || evt.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)) { //Not sure if this is messy or better looking than the alternative.
			evt.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryTable(new ResourceLocation(Bewitchment.MODID, "chests/materials"), 5, 0, new LootCondition[0], "bewitchment_materials_entry")}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "bewitchment_materials_pool"));
		}
	}
	
	@SubscribeEvent
	public void attemptBrew(PotionBrewEvent.Pre event) {
		for (int i = 0; i < event.getLength() - 1; i++) {
			ItemStack stack = event.getItem(i);
			if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("bewitchment_brew")) {
				Item item = event.getItem(3).getItem();
				event.setItem(i, TileEntityWitchesCauldron.createPotion(PotionUtils.getEffectsFromStack(stack), item == Items.GUNPOWDER ? 1 : item == Items.DRAGON_BREATH ? 2 : 0));
				event.getItem(3).shrink(1);
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void explode(ExplosionEvent.Detonate event) {
		for (int i = event.getAffectedBlocks().size() - 1; i >= 0; i--) if (isNextToJuniperDoor(event.getWorld(), event.getAffectedBlocks().get(i))) event.getAffectedBlocks().remove(i);
	}
	
	@SubscribeEvent
	public void breakBlock(BlockEvent.BreakEvent event) {
		if (isNextToJuniperDoor(event.getWorld(), event.getPos())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed event) {
		if (isNextToJuniperDoor(event.getEntityPlayer().world, event.getPos())) event.setNewSpeed(0);
	}
	
	private boolean isNextToJuniperDoor(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() != ModObjects.juniper_door.door && world.getBlockState(pos.up()).getBlock() == ModObjects.juniper_door.door;
	}
	
	@SubscribeEvent
	public void onCollectFire(PlayerInteractEvent.RightClickBlock event) {
		Block block = event.getWorld().getBlockState(event.getPos().offset(Objects.requireNonNull(event.getFace()))).getBlock();
		EntityPlayer player = event.getEntityPlayer();
		if (block == ModObjects.hellfire) {
			if (!player.isSneaking() && player.getHeldItem(event.getHand()).getItem() == Items.GLASS_BOTTLE) {
				if (!event.getWorld().isRemote) {
					Util.replaceAndConsumeItem(player, event.getHand(), new ItemStack(ModObjects.bottled_hellfire));
					event.getWorld().setBlockToAir(event.getPos().offset(event.getFace()));
					event.getWorld().playSound(null, event.getPos().offset(event.getFace()), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1.75f);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void extinguishFire(PlayerInteractEvent.LeftClickBlock event) {
		BlockPos pos = event.getPos().offset(event.getFace());
		if (event.getWorld().getBlockState(pos).getBlock() == ModObjects.hellfire) {
			event.getWorld().playEvent(event.getEntityPlayer(), 1009, pos, 0);
			event.getWorld().setBlockToAir(pos);
		}
	}
	
	@SubscribeEvent
	public void wakeUp(PlayerWakeUpEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		List<Potion> potions = new ArrayList<>();
		int length = 600, strength = 0;
		for (BlockPos temp : BlockPos.getAllInBoxMutable(player.getPosition().add(-2, -2, -2), player.getPosition().add(2, 2, 2))) {
			if (world.getBlockState(temp).getBlock() instanceof BlockBrazier && world.getBlockState(temp).getValue(BlockBrazier.LIT)) {
				TileEntityBrazier te = (TileEntityBrazier) world.getTileEntity(temp);
				if (te.incense != null) {
					if (length == 600 && te.incense.getRegistryName().equals(new ResourceLocation(Bewitchment.MODID, "intensity"))) {
						strength++;
					}
					else if (strength == 0 && te.incense.getRegistryName().equals(new ResourceLocation(Bewitchment.MODID, "concentration"))) {
						length = 1200;
					}
					else {
						potions.addAll(te.incense.effects);
					}
				}
			}
		}
		strength = Math.min(strength, 2);
		for (Potion potion : potions) {
			if (potion == MobEffects.SATURATION) player.addPotionEffect(new PotionEffect(potion, 60, 1));
			else player.addPotionEffect(new PotionEffect(potion, 20 * length, strength));
		}
	}
	
	@SubscribeEvent
	public void takeBlood(LivingDeathEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (event.getEntityLiving() instanceof EntityAnimal || event.getEntityLiving() instanceof EntityPlayer || event.getEntityLiving() instanceof EntityVillager) {
				if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModObjects.athame && player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.GLASS_BOTTLE) {
					Util.replaceAndConsumeItem(player, EnumHand.OFF_HAND, new ItemStack(ModObjects.bottle_of_blood));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void depledgeGoats(LivingHurtEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof AbstractGreaterDemon && event.getSource().getTrueSource() instanceof EntityPlayer) {
			ExtendedWorld.depledgePlayerToDemon(event.getEntityLiving().world, (EntityPlayer) event.getSource().getTrueSource(), (IPledgeable) event.getEntityLiving());
		}
	}
	
	@SubscribeEvent
	public void dismountBroom(EntityMountEvent event) {
		if (event.isDismounting() && event.getEntityBeingMounted() instanceof EntityBroom) {
			if (!event.getEntityBeingMounted().world.isRemote && event.getEntityMounting() instanceof EntityPlayerMP) {
				Bewitchment.network.sendTo(new DismountBroomMessage(), (EntityPlayerMP) event.getEntityMounting());
			}
			if (event.getEntityBeingMounted().world.isRemote && event.getEntityMounting() instanceof EntityPlayerSP) {
				Bewitchment.network.sendToServer(new DismountBroomMessage());
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoined(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		if (player instanceof EntityPlayerMP) {
			ExtendedWorld.get(player.world).syncToClient((EntityPlayerMP) player);
		}
	}
}