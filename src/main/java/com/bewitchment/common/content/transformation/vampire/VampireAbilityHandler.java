package com.bewitchment.common.content.transformation.vampire;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.event.HotbarActionCollectionEvent;
import com.bewitchment.api.event.HotbarActionTriggeredEvent;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.helper.AttributeModifierModeHelper;
import com.bewitchment.common.entity.EntityBatSwarm;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.world.biome.ModBiomes;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;
import java.util.UUID;

@Mod.EventBusSubscriber
public class VampireAbilityHandler {

	public static final DamageSource SUN_DAMAGE = new DamageSource("sun_on_vampire").setDamageBypassesArmor().setDamageIsAbsolute().setFireDamage();

	public static final Ingredient WOOD_WEAPON = new OreIngredient("weaponWood");
	public static final Ingredient SILVER_WEAPON = new OreIngredient("weaponSilver");

	public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("c73f6d26-65ed-4ba5-ada8-9a96f8712424");
	// TODO Check: can two different living hurt events run down the subscriber list in parallel?
	// If so this doesn't work and one entity might end up receiving the damage amount meant for someone else in some cases
	public static float lastDamage = 0;

	/**
	 * Modifies damage depending on the type. Fire and explosion make it 150%of the original,
	 * all the other types make it 10% of the original provided there's blood in the pool
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void incomingDamageModifier(LivingHurtEvent evt) {
		if (!evt.getEntity().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
			CapabilityTransformation data = player.getCapability(CapabilityTransformation.CAPABILITY, null);
			if (data.getType() == DefaultTransformations.VAMPIRE) {
				if (evt.getSource() == SUN_DAMAGE) {
					evt.setCanceled(false); // No immunity for you
					player.dismountRidingEntity();
					return; // No multipliers for sun
				}
				float multiplier = getMultiplier(evt); // A multiplier greater 1 makes the damage not be reduced
				evt.setCanceled(false); // No forms of immunity for vampires
				evt.setAmount(lastDamage); // No reductions either
				if (multiplier > 1) {
					evt.setAmount(evt.getAmount() * multiplier);
				} else if (player.getCapability(CapabilityVampire.CAPABILITY, null).getBlood() > 0) { // Don't mitigate damage when there is no blood in the pool
					evt.setAmount(evt.getAmount() * (1f - 0.1f * data.getLevel()));
				}
				if (player.getRidingEntity() instanceof EntityBatSwarm) {
					evt.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public static void dropInvalidGear(LivingHurtEvent evt) {
		if (!evt.getEntity().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer) {
			CapabilityTransformation data = evt.getEntityLiving().getCapability(CapabilityTransformation.CAPABILITY, null);
			if (data.getType() == DefaultTransformations.VAMPIRE) {
				lastDamage = evt.getAmount();
			}
		}
	}

	// NO-SUBSCRIBE
	private static float getMultiplier(LivingHurtEvent evt) {
		float mult = 1;
		if (evt.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer source = (EntityPlayer) evt.getSource().getTrueSource();
			CapabilityTransformation data = source.getCapability(CapabilityTransformation.CAPABILITY, null);
			if (data.getType() == DefaultTransformations.HUNTER) {
				mult += 0.8;
			} else if (data.getType() != DefaultTransformations.NONE) {
				mult += 0.1; // All transformations basically (except hunter)
			}
			if (WOOD_WEAPON.apply(source.getHeldItemMainhand())) {
				mult += 0.1;
			} else if (SILVER_WEAPON.apply(source.getHeldItemMainhand())) {
				mult += 0.3;
			}
		}
		if (evt.getSource().isFireDamage() || evt.getSource().isExplosion() || evt.getSource().canHarmInCreative()) {
			mult += 0.5;
			if (evt.isCanceled()) {
				mult += 1; // Attempts to prevent damage are bad
			}
		}
		return mult;
	}

	@SubscribeEvent
	public static void tickChecks(PlayerTickEvent evt) {
		CapabilityTransformation data = evt.player.getCapability(CapabilityTransformation.CAPABILITY, null);
		if (data.getType() == DefaultTransformations.VAMPIRE && evt.side.isServer()) {

			// Check sun damage
			if (evt.player.world.getTotalWorldTime() % 40 == 0 && evt.player.world.canBlockSeeSky(evt.player.getPosition()) && evt.player.world.isDaytime() && !evt.player.world.isRainingAt(evt.player.getPosition()) && evt.player.getActivePotionEffect(ModPotions.sun_ward) == null && evt.player.world.getBiome(evt.player.getPosition()) != ModBiomes.VAMPIRE_LAIR) {
				if (data.getLevel() < 5 || !BewitchmentAPI.getAPI().addVampireBlood(evt.player, -(13 + data.getLevel()))) {
					evt.player.attackEntityFrom(SUN_DAMAGE, 11 - data.getLevel());
				}
			}

			// Replace hunger mechanics with blood mechanics
			if (evt.player.ticksExisted % 30 == 0) {
				evt.player.getFoodStats().setFoodLevel(10); // No healing from food
				if (evt.player.getCapability(CapabilityVampire.CAPABILITY, null).getBlood() == 0) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 2, false, false));
					evt.player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 2, false, false));
				} else {
					if (evt.player.getHealth() < evt.player.getMaxHealth() && BewitchmentAPI.getAPI().addVampireBlood(evt.player, -20)) {
						evt.player.heal(1);
					}
				}

				// Hunger drains blood
				PotionEffect effect = evt.player.getActivePotionEffect(MobEffects.HUNGER);
				if (effect != null) {
					BewitchmentAPI.getAPI().addVampireBlood(evt.player, -effect.getAmplifier() * 5);
				}

				// Fire resistance becomes hunger
				PotionEffect pe = evt.player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE);
				if (pe != null) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, pe.getDuration(), pe.getAmplifier()));
					evt.player.removePotionEffect(MobEffects.FIRE_RESISTANCE);
				}

				// No need for air
				evt.player.setAir(150);

			}
		}
	}

	@SubscribeEvent
	public static void sleepBed(RightClickBlock evt) {
		CapabilityTransformation data = evt.getEntityPlayer().getCapability(CapabilityTransformation.CAPABILITY, null);
		if (data.getType() == DefaultTransformations.VAMPIRE && evt.getEntityPlayer().world.getBlockState(evt.getPos()).getBlock() == Blocks.BED) {
			evt.setCancellationResult(EnumActionResult.FAIL);
			evt.setCanceled(true);
			evt.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("vampire.bed_blocked"), true);
		}
	}

	@SubscribeEvent
	public static void foodEaten(LivingEntityUseItemEvent.Finish evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer && evt.getItem().getItem() instanceof ItemFood) {
			CapabilityTransformation data = ((EntityPlayer) evt.getEntityLiving()).getCapability(CapabilityTransformation.CAPABILITY, null);
			if (data.getType() == DefaultTransformations.VAMPIRE) {
				evt.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 2, false, true));
			}
		}
	}

	@SubscribeEvent
	public static void attachHotbarAbilities(HotbarActionCollectionEvent evt) {
		CapabilityTransformation data = evt.player.getCapability(CapabilityTransformation.CAPABILITY, null);
		if (data.getType() == DefaultTransformations.VAMPIRE) {
			evt.getList().add(ModAbilities.DRAIN_BLOOD);
			if (data.getLevel() > 5) {
				evt.getList().add(ModAbilities.NIGHT_VISION_VAMPIRE);
			} else {
				evt.player.getCapability(CapabilityVampire.CAPABILITY, null).setNightVision(false);
			}
			if (data.getLevel() > 5) {
				evt.getList().add(ModAbilities.BAT_SWARM);
				evt.getList().add(ModAbilities.MESMERIZE);
			}
			if (data.getLevel() > 7) {
				evt.getList().add(ModAbilities.HYPNOTIZE);
			}
		} else {
			evt.player.getCapability(CapabilityVampire.CAPABILITY, null).setNightVision(false);
		}
	}

	@SubscribeEvent
	public static void onHotbarAbilityToggled(HotbarActionTriggeredEvent evt) {
		CapabilityTransformation data = evt.player.getCapability(CapabilityTransformation.CAPABILITY, null);
		CapabilityVampire vampire = evt.player.getCapability(CapabilityVampire.CAPABILITY, null);
		if (data.getType() != DefaultTransformations.VAMPIRE) {
			return;
		}
		if (evt.action == ModAbilities.NIGHT_VISION_VAMPIRE) {
			vampire.setNightVision(!vampire.nightVision);
		} else if (evt.action == ModAbilities.DRAIN_BLOOD) {

			if (evt.focusedEntity instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) evt.focusedEntity;
				if (canDrainBloodFrom(evt.player, entity)) {
					BewitchmentAPI.getAPI().drainBloodFromEntity(evt.player, entity);
				} else {
					entity.attackEntityAsMob(evt.player);
				}
			}
		} else if (evt.action == ModAbilities.BAT_SWARM) {
			if (!(evt.player.getRidingEntity() instanceof EntityBatSwarm) && (evt.player.isCreative() || BewitchmentAPI.getAPI().addVampireBlood(evt.player, -20))) {
				EntityBatSwarm bs = new EntityBatSwarm(evt.player.world);
				float pitch = (Math.abs(evt.player.rotationPitch) < 7) ? 0 : evt.player.rotationPitch;
				bs.setPositionAndRotation(evt.player.posX, evt.player.posY + evt.player.getEyeHeight(), evt.player.posZ, evt.player.rotationYaw, pitch);
				evt.player.world.spawnEntity(bs);
				evt.player.startRiding(bs);
			}
		} else if (evt.action == ModAbilities.MESMERIZE) {
			if (evt.focusedEntity instanceof EntityLivingBase) {
				if (BewitchmentAPI.getAPI().addVampireBlood(evt.player, -150)) {
					((EntityLivingBase) evt.focusedEntity).addPotionEffect(new PotionEffect(ModPotions.mesmerized, 100, 0, false, true));
				}
			}
		} else if (evt.action == ModAbilities.HYPNOTIZE) {
			evt.player.sendStatusMessage(new TextComponentString("Hypnotize ability not available yet"), true);
		}
	}

	private static boolean canDrainBloodFrom(EntityPlayer player, EntityLivingBase entity) {
		if (entity.getActivePotionEffect(ModPotions.mesmerized) != null) {
			return true;
		}
		if (player.getLastAttackedEntity() == entity || entity.getAttackingEntity() == player) {
			return false;
		}
		return Math.abs(player.rotationYawHead - entity.rotationYawHead) < 30;
	}

	@SubscribeEvent
	public static void abilityHandler(PlayerTickEvent evt) {
		if (evt.phase == Phase.START && !evt.player.world.isRemote && evt.player.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
			PotionEffect nv = evt.player.getActivePotionEffect(MobEffects.NIGHT_VISION);
			if ((nv == null || nv.getDuration() <= 220) && evt.player.getCapability(CapabilityVampire.CAPABILITY, null).nightVision) {
				if (evt.player.isCreative() || BewitchmentAPI.getAPI().addVampireBlood(evt.player, -2)) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, false));
				} else {
					evt.player.sendStatusMessage(new TextComponentTranslation("vampire.nightvision.low_blood"), true);
					evt.player.getCapability(CapabilityVampire.CAPABILITY, null).setNightVision(false);
				}
			}
		}
	}

	@SubscribeEvent
	public static void refreshModifiers(TransformationModifiedEvent evt) {
		CapabilityTransformation data = evt.getEntityPlayer().getCapability(CapabilityTransformation.CAPABILITY, null);
		IAttributeInstance attack_speed = evt.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier modifier = attack_speed.getModifier(ATTACK_SPEED_MODIFIER_UUID);
		if (modifier != null) {
			attack_speed.removeModifier(modifier);
		}
		if (data.getType() == DefaultTransformations.VAMPIRE) {
			attack_speed.applyModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Vampire Atk Speed", evt.level / 10, AttributeModifierModeHelper.ADD));
		}
	}

	@SubscribeEvent
	public static void onLivingJoinWorld(EntityJoinWorldEvent evt) {
		if (evt.getEntity() instanceof EntityLiving) {
			EntityLiving living = (EntityLiving) evt.getEntity();
			ArrayList<EntityAITaskEntry> tasks = Lists.newArrayList();
			living.tasks.taskEntries.stream()
					.filter(t -> t.action instanceof EntityAIWatchClosest)
					.forEach(t -> tasks.add(t));
			tasks.forEach(t -> living.tasks.taskEntries.remove(t));
			tasks.forEach(t -> living.tasks.taskEntries.add(living.tasks.new EntityAITaskEntry(t.priority, new AIWatchClosestWrapper((EntityAIWatchClosest) t.action))));
		}
	}

	@SubscribeEvent
	public static void checkHealing(LivingHealEvent evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer && !evt.getEntityLiving().world.isRemote) {
			if (evt.getEntityLiving().getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
				if (evt.getEntityLiving().getCapability(CapabilityVampire.CAPABILITY, null).getBlood() == 0) {
					evt.setCanceled(true);
				}
			}
		}
	}

}
