package com.bewitchment.common.core.event;

import com.bewitchment.api.capability.transformations.EnumTransformationType;
import com.bewitchment.api.capability.transformations.ITransformationData;
import com.bewitchment.api.capability.transformations.TransformationHelper;
import com.bewitchment.api.event.HotbarActionCollectionEvent;
import com.bewitchment.api.event.HotbarActionTriggeredEvent;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.common.abilities.ModAbilities;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.helper.RayTraceHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.NightVisionStatus;
import com.bewitchment.common.entity.EntityBatSwarm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.oredict.OreIngredient;

import java.util.UUID;

public class VampireAbilityHandler {

	public static final DamageSource SUN_DAMAGE = new DamageSource("sun_on_vampire").setDamageBypassesArmor().setDamageIsAbsolute().setFireDamage();

	public static final Ingredient WOOD_WEAPON = new OreIngredient("weaponWood");
	public static final Ingredient SILVER_WEAPON = new OreIngredient("weaponSilver");

	public static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("c73f6d26-65ed-4ba5-ada8-9a96f8712424");

	/**
	 * Modifies damage depending on the type. Fire and explosion make it 150%of the original,
	 * all the other types make it 10% of the original provided there's blood in the pool
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void incomingDamageModifier(LivingHurtEvent evt) {
		if (!evt.getEntity().world.isRemote && evt.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
			ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
			if (data.getType() == EnumTransformationType.VAMPIRE) {
				evt.setCanceled(false); // No immunity for you
				if (evt.getSource() == SUN_DAMAGE) {
					return;
				}
				float multiplier = getMultiplier(evt); // A multiplier greater 1 makes the damage not be reduced
				if (multiplier > 1) {
					evt.setAmount(evt.getAmount() * multiplier);
				} else if (data.getBlood() > 0) { // Don't mitigate damage when there is no blood in the pool
					evt.setAmount(evt.getAmount() * 0.1f);
					if (evt.getAmount() > 5f) {
						evt.setAmount(5f);
					}
				}
			}
		}
	}

	// NO-SUBSCRIBE
	private float getMultiplier(LivingHurtEvent evt) {
		float mult = 1;
		if (evt.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer source = (EntityPlayer) evt.getSource().getTrueSource();
			ITransformationData data = source.getCapability(CapabilityTransformationData.CAPABILITY, null);
			if (data.getType() == EnumTransformationType.HUNTER) {
				mult += 0.8;
			} else if (data.getType() != EnumTransformationType.NONE) {
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
	public void tickChecks(PlayerTickEvent evt) {
		ITransformationData data = evt.player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (data.getType() == EnumTransformationType.VAMPIRE && evt.side.isServer()) {

			// Check sun damage
			if (evt.player.world.getTotalWorldTime() % 40 == 0 && evt.player.world.canBlockSeeSky(evt.player.getPosition()) && evt.player.world.isDaytime() && !evt.player.world.isRainingAt(evt.player.getPosition())) {
				if (data.getLevel() < 5 || !TransformationHelper.addVampireBlood(evt.player, -(13 + data.getLevel()))) {
					evt.player.attackEntityFrom(SUN_DAMAGE, 11 - data.getLevel());
				}
			}

			// Replace hunger mechanics with blood mechanics
			if (evt.player.ticksExisted % 80 == 0) {
				evt.player.getFoodStats().addStats(20, 3000);
				if (data.getBlood() == 0) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 2, false, false));
					evt.player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 2, false, false));
				}

				// Hunger drains blood
				PotionEffect effect = evt.player.getActivePotionEffect(MobEffects.HUNGER);
				if (effect != null) {
					TransformationHelper.addVampireBlood(evt.player, -effect.getAmplifier() * 5);
				}

				// Fire resistance becomes hunger
				PotionEffect pe = evt.player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE);
				if (pe != null) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, pe.getDuration(), pe.getAmplifier()));
					evt.player.removePotionEffect(MobEffects.FIRE_RESISTANCE);
				}
			}
		}
	}

	@SubscribeEvent
	public void sleepBed(RightClickBlock evt) {
		ITransformationData data = evt.getEntityPlayer().getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (data.getType() == EnumTransformationType.VAMPIRE && evt.getEntityPlayer().world.getBlockState(evt.getPos()).getBlock() == Blocks.BED) {
			evt.setCancellationResult(EnumActionResult.FAIL);
			evt.setCanceled(true);
			evt.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("vampire.bed_blocked"), true);
		}
	}

	@SubscribeEvent
	public void foodEaten(LivingEntityUseItemEvent.Finish evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer && evt.getItem().getItem() instanceof ItemFood) {
			ITransformationData data = ((EntityPlayer) evt.getEntityLiving()).getCapability(CapabilityTransformationData.CAPABILITY, null);
			if (data.getType() == EnumTransformationType.VAMPIRE) {
				evt.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 2, false, true));
			}
		}
	}

	@SubscribeEvent
	public void healControl(LivingHealEvent evt) {
		if (evt.getEntityLiving() instanceof EntityPlayer) {
			ITransformationData data = ((EntityPlayer) evt.getEntityLiving()).getCapability(CapabilityTransformationData.CAPABILITY, null);
			if (data.getType() == EnumTransformationType.VAMPIRE) {
				if (!TransformationHelper.addVampireBlood((EntityPlayer) evt.getEntityLiving(), (int) evt.getAmount() * -3))
					evt.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void attachHotbarAbilities(HotbarActionCollectionEvent evt) {
		ITransformationData data = evt.player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (data.getType() == EnumTransformationType.VAMPIRE) {
			evt.getList().add(ModAbilities.DRAIN_BLOOD);
			if (data.getLevel() > 5) {
				evt.getList().add(ModAbilities.NIGHT_VISION);
			} else {
				data.setNightVision(false);
			}
			if (data.getLevel() > 6) {
				evt.getList().add(ModAbilities.BAT_SWARM);
			}
		} else {
			data.setNightVision(false);
		}

	}

	@SubscribeEvent
	public void onHotbarAbilityToggled(HotbarActionTriggeredEvent evt) {
		ITransformationData data = evt.player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		if (evt.action == ModAbilities.NIGHT_VISION) {
			boolean newStatus = !data.isNightVisionActive();
			data.setNightVision(newStatus);
			if (evt.player instanceof EntityPlayerMP) {
				NetworkHandler.HANDLER.sendTo(new NightVisionStatus(newStatus), (EntityPlayerMP) evt.player);
			}
		} else if (evt.action == ModAbilities.DRAIN_BLOOD) {

			RayTraceResult rt = RayTraceHelper.rayTraceResult(evt.player, RayTraceHelper.fromLookVec(evt.player, evt.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue()), true, true);
			if (rt != null && rt.typeOfHit == Type.ENTITY) {
				if (rt.entityHit instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase) rt.entityHit;
					if (canDrainBloodFrom(evt.player, entity)) {
						TransformationHelper.drainBloodFromEntity(evt.player, entity);
					} else {
						entity.attackEntityAsMob(evt.player);
					}
				}
			}
		} else if (evt.action == ModAbilities.BAT_SWARM) {
			if (!(evt.player.getRidingEntity() instanceof EntityBatSwarm) && TransformationHelper.addVampireBlood(evt.player, -150)) {
				EntityBatSwarm bs = new EntityBatSwarm(evt.player.world);
				float pitch = (Math.abs(evt.player.rotationPitch) < 7) ? 0 : evt.player.rotationPitch;
				bs.setPositionAndRotation(evt.player.posX, evt.player.posY + evt.player.getEyeHeight(), evt.player.posZ, evt.player.rotationYaw, pitch);
				evt.player.world.spawnEntity(bs);
				evt.player.startRiding(bs);
			}
		}
	}

	private boolean canDrainBloodFrom(EntityPlayer player, EntityLivingBase entity) {
		if (player.getLastAttackedEntity() == entity || entity.getAttackingEntity() == player)
			return false;
		// TODO check if frontal, in case return false and damage the entity
		return true;
	}

	@SubscribeEvent
	public void abilityHandler(PlayerTickEvent evt) {
		if (evt.phase == Phase.START && !evt.player.world.isRemote) {
			PotionEffect nv = evt.player.getActivePotionEffect(MobEffects.NIGHT_VISION);
			if ((nv == null || nv.getDuration() <= 220) && evt.player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()) {
				if (TransformationHelper.addVampireBlood(evt.player, -2)) {
					evt.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, false));
				} else {
					evt.player.sendStatusMessage(new TextComponentTranslation("vampire.nightvision.low_blood"), true);
					evt.player.getCapability(CapabilityTransformationData.CAPABILITY, null).setNightVision(false);
				}
			}
		}
	}

	@SubscribeEvent
	public void refreshModifiers(TransformationModifiedEvent evt) {
		ITransformationData data = evt.getEntityPlayer().getCapability(CapabilityTransformationData.CAPABILITY, null);
		IAttributeInstance attack_speed = evt.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier modifier = attack_speed.getModifier(ATTACK_SPEED_MODIFIER_UUID);
		if (modifier != null) {
			attack_speed.removeModifier(modifier);
		}
		if (data.getType() == EnumTransformationType.VAMPIRE) {
			attack_speed.applyModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Vampire Atk Speed", evt.level / 10, 0));
		}
	}

}
