package com.bewitchment.common.integration.thaumcraft;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.misc.Weakness;
import com.bewitchment.registry.ModObjects;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ScanOreDictionary;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.golems.EntityThaumcraftGolem;

import java.util.List;
import java.util.function.BooleanSupplier;

@SuppressWarnings({"deprecation", "WeakerAccess", "unused"})
public class ThaumcraftCompat implements IConditionFactory {
	public static final Aspect SUN = getOrCreateAspect("sol", 0xffd300, new Aspect[]{Aspect.FIRE, Aspect.LIGHT}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/sol.png"));
	public static final Aspect MOON = getOrCreateAspect("luna", 0x808080, new Aspect[]{Aspect.EARTH, Aspect.DARKNESS}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/luna.png"));
	public static final Aspect STAR = getOrCreateAspect("stellae", 0x73c2fb, new Aspect[]{SUN, Aspect.VOID}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/stellae.png"));
	public static final Aspect DEMON = getOrCreateAspect("diabolus", 0x960018, new Aspect[]{Aspect.SOUL, Aspect.AVERSION}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/diabolus.png"));

	//TODO: NEW ICON FOR SPIRITUAL_WARD
	public static final EnumGolemTrait SPIRITUAL_WARD = EnumHelper.addEnum(EnumGolemTrait.class, "SPIRITUAL_WARD", new Class[]{ResourceLocation.class}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/golems/tag_blessed.png"));
	public static final EnumGolemTrait UNCANNY = EnumHelper.addEnum(EnumGolemTrait.class, "UNCANNY", new Class[]{ResourceLocation.class}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/golems/tag_uncanny.png"));
	public static final EnumGolemTrait BLESSED = EnumHelper.addEnum(EnumGolemTrait.class, "BLESSED", new Class[]{ResourceLocation.class}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/golems/tag_blessed.png"));

	public static void init() {
		ThaumcraftApi.registerResearchLocation(new ResourceLocation(Bewitchment.MODID, "tc/research/bewitchment"));
		ScanningManager.addScannableThing(new ScanOreDictionary("f_MATCOLDIRON", "ingotColdIron", "blockColdIron", "nuggetColdIron", "plateColdIron", "dustColdIron"));
		ScanningManager.addScannableThing(new ScanOreDictionary("f_MATDRAGONSBLOOD", "resinDragonsBlood", "blockDragonsBloodResin"));
		ScanningManager.addScannableThing(new ScanOreDictionary("f_MATSILVER", "ingotSilver", "blockSilver", "plateSilver", "dustSilver", "nuggetSilver", "oreSilver"));
		GolemMaterial.register(new GolemMaterial("COLDIRON", new String[]{"MATSTUDCOLDIRON"}, new ResourceLocation("bewitchment", "textures/entity/coldirongolem.png"), 2699070, 20, 8, 3, new ItemStack(ModObjects.cold_iron_plate), new ItemStack(ItemsTC.mechanismSimple), new EnumGolemTrait[]{EnumGolemTrait.HEAVY, EnumGolemTrait.FIREPROOF, EnumGolemTrait.BLASTPROOF, SPIRITUAL_WARD}));
		GolemMaterial.register(new GolemMaterial("DRAGONSBLOOD", new String[]{"MATSTUDDRAGONSBLOOD"}, new ResourceLocation("bewitchment", "textures/entity/dragonsbloodgolem.png"), 4786944, 10, 1, 1, new ItemStack(ModObjects.dragons_blood_resin), new ItemStack(ItemsTC.mechanismSimple), new EnumGolemTrait[]{EnumGolemTrait.FRAGILE, EnumGolemTrait.CLUMSY, EnumGolemTrait.LIGHT, UNCANNY}));
		GolemMaterial.register(new GolemMaterial("SILVER", new String[]{"MATSTUDSILVER"}, new ResourceLocation("bewitchment", "textures/entity/silvergolem.png"), 10922156, 14, 3, 2, new ItemStack(ModObjects.silver_plate), new ItemStack(ItemsTC.mechanismSimple), new EnumGolemTrait[]{EnumGolemTrait.LIGHT, BLESSED}));

	}

	private static Aspect getOrCreateAspect(String tag, int color, Aspect[] components, ResourceLocation image) {
		Aspect a = Aspect.getAspect(tag);
		if (a != null) return a;
		return new Aspect(tag, color, components, image, 1);
	}

	public static boolean isColdIronGolem(EntityLivingBase golem) {
		return golem instanceof EntityThaumcraftGolem && ((EntityThaumcraftGolem) golem).getProperties().hasTrait(SPIRITUAL_WARD);
	}

	public static boolean isSilverGolem(EntityLivingBase golem) {
		return golem instanceof EntityThaumcraftGolem && ((EntityThaumcraftGolem) golem).getProperties().hasTrait(BLESSED);
	}

	public static boolean isDragonsBloodGolem(EntityLivingBase golem) {
		return golem instanceof EntityThaumcraftGolem && ((EntityThaumcraftGolem) golem).getProperties().hasTrait(UNCANNY);
	}

	private static float getDamage(float initialDamage, Weakness weakness, EntityLivingBase target, EntityLivingBase attacker) {
		float amount = weakness.get(target);

		if (amount > 1.0F && (isColdIronGolem(attacker) || isSilverGolem(attacker)))
			return initialDamage * amount * 2;

		amount = weakness.get(attacker);

		if (amount > 1.0F && (isColdIronGolem(target) || isSilverGolem(target) || isDragonsBloodGolem(target))) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(target), 4.0F);
			return initialDamage * 0.4F;
		}

		return initialDamage;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerItemsLater(RegistryEvent.Register<Item> event) {
		try {
			Class.forName("thaumcraft.common.golems.GolemProperties");
		} catch (Exception ex) {
			System.out.println("GolemProperties appears to be missing. Please report this issue to the dev of BW, for someone has done something they shouldn't!");
		}
	}

	@SubscribeEvent
	public void handleDragonsBloodGolems(LivingEvent.LivingUpdateEvent event) {
		if (isDragonsBloodGolem(event.getEntityLiving()) && event.getEntityLiving().getRNG().nextInt(20) == 0) {
			EntityThaumcraftGolem golem = (EntityThaumcraftGolem) event.getEntityLiving();
			List<EntityMob> mobsNearby = golem.world.getEntitiesWithinAABB(EntityMob.class, golem.getEntityBoundingBox().grow(8));
			mobsNearby.forEach(mob -> {
				Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(golem, 16, 7, new Vec3d(mob.posX, mob.posY, mob.posZ));
				if (vec3d != null && mob.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) >= mob.getDistanceSq(golem)) {
					Path path = mob.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
					if (path != null) mob.getNavigator().setPath(path, 1);
				}
			});
		}
	}

	@SubscribeEvent
	public void handleGolems(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();

		if (!target.world.isRemote) {
			Entity source = event.getSource().getImmediateSource();

			if (source instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) source;

				{
					float damage = getDamage(event.getAmount(), BewitchmentAPI.SILVER_WEAKNESS, target, attacker);
					event.setAmount(damage);
				}

				if (isDragonsBloodGolem(target)) {
					attacker.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 0));
				}


				if (isDragonsBloodGolem(attacker)) {
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 0));
				}

				{
					float damage = getDamage(event.getAmount(), BewitchmentAPI.COLD_IRON_WEAKNESS, target, attacker);
					event.setAmount(damage);
				}
			}
		}
	}

	@SubscribeEvent
	public void aspectRegistrationEvent(AspectRegistryEvent event) {
		//Technical
		event.register.registerObjectTag(new ItemStack(ModObjects.salt_barrier), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.EARTH, 5));
		//Tiles
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_oven), new AspectList().add(Aspect.FIRE, 25).add(Aspect.METAL, 30).add(Aspect.CRAFT, 30));
		event.register.registerObjectTag(new ItemStack(ModObjects.spinning_wheel), new AspectList().add(Aspect.PLANT, 30).add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 25));
		event.register.registerObjectTag(new ItemStack(ModObjects.crystal_ball), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		//Altar Blocks
		event.register.registerObjectTag(new ItemStack(ModObjects.goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.VOID, 15));
		event.register.registerObjectTag(new ItemStack(ModObjects.filled_goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.ALCHEMY, 15));
		//Extra Blocks
		event.register.registerObjectTag(new ItemStack(ModObjects.purifying_earth), new AspectList().add(Aspect.EARTH, 5).add(SUN, 5));

		event.register.registerObjectTag(new ItemStack(ModObjects.greenwitch_fleece), new AspectList().add(Aspect.CRAFT, 4).add(SUN, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.alchemists_fleece), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.ALCHEMY, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.besmirched_fleece), new AspectList().add(Aspect.CRAFT, 4).add(DEMON, 4));
		//Ores
		event.register.registerObjectTag(new ItemStack(ModObjects.amethyst_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.ALCHEMY, 4).add(Aspect.LIFE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.garnet_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4).add(STAR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.opal_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.MAGIC, 4).add(MOON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.silver_ore), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(Aspect.EARTH, 5).add(MOON, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.salt_ore), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		//Misc Blocks
		for (Block block : ModObjects.coquina)
			event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		for (Block block : ModObjects.nethersteel)
			event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.METAL, 5).add(DEMON, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.perpetual_ice), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.embittered_bricks), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));
		for (Block block : ModObjects.scorned_bricks)
			event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10).add(DEMON, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.cracked_scorned_bricks), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10).add(DEMON, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.cracked_embittered_bricks), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));

		for (Block block : ModObjects.block_of_cold_iron)
			event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.AVERSION, 30).add(Aspect.COLD, 20).add(Aspect.METAL, 67));
		for (Block block : ModObjects.block_of_silver)
			event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.METAL, 67).add(Aspect.DESIRE, 30).add(MOON, 20));

		//Plants
		event.register.registerObjectTag(new ItemStack(ModObjects.embergrass), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.AVERSION, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.spanish_moss), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 2).add(Aspect.MAGIC, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.torchwood), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.frostflower), new AspectList().add(Aspect.PLANT, 8).add(Aspect.COLD, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.blue_ink_cap), new AspectList().add(Aspect.SENSES, 4).add(Aspect.DARKNESS, 4));

		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_allium), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_azure_bluet), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_blue_orchid), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_dandelion), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_oxeye_daisy), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_poppy), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_tulip_orange), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_tulip_pink), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_tulip_red), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.flower_siphoning_tulip_white), new AspectList().add(Aspect.PLANT, 8).add(Aspect.DARKNESS, 8));
		//Saplings
		event.register.registerObjectTag(new ItemStack(ModObjects.cypress_sapling), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.DEATH, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.elder_sapling), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MIND, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_sapling), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.dragons_blood_sapling), new AspectList().add(Aspect.PLANT, 15).add(Aspect.FIRE, 5).add(Aspect.ENERGY, 3));
		//Resins, logs, barks, etc
		event.register.registerObjectTag(new ItemStack(ModObjects.dragons_blood_resin), new AspectList().add(Aspect.PLANT, 3).add(Aspect.FIRE, 3).add(Aspect.ENERGY, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.dragons_blood_wood), new AspectList().add(Aspect.PLANT, 10).add(Aspect.FIRE, 10).add(Aspect.ENERGY, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_wood), new AspectList().add(Aspect.PLANT, 10).add(Aspect.LIFE, 10).add(Aspect.MAGIC, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.elder_wood), new AspectList().add(Aspect.PLANT, 10).add(Aspect.LIFE, 10).add(Aspect.MIND, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.cypress_wood), new AspectList().add(Aspect.PLANT, 10).add(Aspect.LIFE, 10).add(Aspect.DEATH, 10));

		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_key), new AspectList().add(Aspect.PLANT, 3).add(Aspect.LIFE, 3).add(Aspect.MAGIC, 3));

		event.register.registerObjectTag(new ItemStack(ModObjects.aconitum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.belladonna), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.garlic), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.AVERSION, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2).add(DEMON, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.mandrake_root), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.EARTH, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.white_sage), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.SOUL, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.wormwood), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SOUL, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.aconitum_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.belladonna_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1).add(Aspect.MAGIC, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.garlic_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.AVERSION, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellebore_seeds), new AspectList().add(Aspect.PLANT, 1).add(DEMON, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.mandrake_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.MAGIC, 1).add(Aspect.EARTH, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.white_sage_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1).add(Aspect.SOUL, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.wormwood_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SOUL, 1));
		//Equipment
		event.register.registerObjectTag(new ItemStack(ModObjects.girdle_of_the_dryads), new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIFE, 20).add(Aspect.PROTECT, 20).add(Aspect.MAGIC, 20));
		event.register.registerObjectTag(new ItemStack(ModObjects.horseshoe), new AspectList().add(Aspect.METAL, 8).add(Aspect.BEAST, 8).add(Aspect.PROTECT, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.nazar), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.METAL, 8).add(Aspect.CRYSTAL, 8).add(Aspect.PROTECT, 8).add(Aspect.MAGIC, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.grimoire_magia), new AspectList().add(Aspect.MIND, 8).add(Aspect.MAGIC, 8).add(Aspect.AURA, 8));
		//Loom
		event.register.registerObjectTag(new ItemStack(ModObjects.diabolical_vein), new AspectList().add(Aspect.CRAFT, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.pure_filament), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.AURA, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_stitching), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.MAGIC, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.sanguine_cloth), new AspectList().add(Aspect.CRAFT, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.golden_thread), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.DESIRE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.spirit_string), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SOUL, 4));

		event.register.registerObjectTag(new ItemStack(ModObjects.witches_cowl), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(MOON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_hat), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(MOON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_robes), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(MOON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_pants), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(MOON, 6));

		event.register.registerObjectTag(new ItemStack(ModObjects.alchemist_cowl), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.ALCHEMY, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.alchemist_hat), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.ALCHEMY, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.alchemist_robes), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.ALCHEMY, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.alchemist_pants), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.ALCHEMY, 6));

		event.register.registerObjectTag(new ItemStack(ModObjects.green_witch_cowl), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.green_witch_hat), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.green_witch_robes), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.green_witch_pants), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));

		event.register.registerObjectTag(new ItemStack(ModObjects.besmirched_cowl), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(DEMON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.besmirched_hat), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(DEMON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.besmirched_robes), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(DEMON, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.besmirched_pants), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(DEMON, 6));
		//Materials
		event.register.registerObjectTag(new ItemStack(ModObjects.amethyst), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ALCHEMY, 4).add(Aspect.LIFE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.garnet), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4).add(STAR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.opal), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 4).add(MOON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.silver_ingot), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(MOON, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.cold_iron_ingot), new AspectList().add(Aspect.AVERSION, 10).add(Aspect.COLD, 3).add(Aspect.METAL, 15));
		event.register.registerObjectTag(new ItemStack(ModObjects.salt), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		//Food
		event.register.registerObjectTag(new ItemStack(ModObjects.elderberries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MIND, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_berries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAGIC, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.stew_of_the_grotesque), new AspectList().add(DEMON, 3).add(Aspect.MAGIC, 3).add(Aspect.SENSES, 3).add(MOON, 3));
		//Mob Drops
		event.register.registerObjectTag(new ItemStack(ModObjects.lizard_leg), new AspectList().add(Aspect.MOTION, 4).add(Aspect.EARTH, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.owlets_wing), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(MOON, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.ravens_feather), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(Aspect.DARKNESS, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.adders_fork), new AspectList().add(Aspect.DEATH, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.toe_of_frog), new AspectList().add(Aspect.WATER, 4).add(Aspect.ALCHEMY, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellhound_horn), new AspectList().add(DEMON, 6).add(Aspect.AVERSION, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		event.register.registerObjectTag(new ItemStack(ModObjects.demon_heart), new AspectList().add(DEMON, 6).add(Aspect.AVERSION, 6).add(Aspect.FIRE, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.snake_venom), new AspectList().add(Aspect.DEATH, 9).add(Aspect.CRYSTAL, 9));
		//Vanilla Mob Drops
		event.register.registerObjectTag(new ItemStack(ModObjects.hoof), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MOTION, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.eye_of_old), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2).add(Aspect.ELDRITCH, 2).add(STAR, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.tongue_of_dog), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4).add(Aspect.DEATH, 4));
		//Usable Misc
		event.register.registerObjectTag(new ItemStack(ModObjects.focal_chalk), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(SUN, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.ritual_chalk), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.fiery_chalk), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.phasing_chalk), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(Aspect.ELDRITCH, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.taglock), new AspectList().add(Aspect.SOUL, 8).add(Aspect.LIFE, 8));

		//Sigil
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_battle), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_binding), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_cleansing), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_disorientation), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_failure), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_luck), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_mending), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_purity), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_ruin), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_sentinel), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.sigil_shrieking), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));

		event.register.registerObjectTag(new ItemStack(ModObjects.garlic_grilled), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.CRAFT, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_tea), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAGIC, 3).add(Aspect.WATER, 3).add(Aspect.VOID, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.elderberry_jelly), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MIND, 3).add(Aspect.ALCHEMY, 3));

		event.register.registerObjectTag(new ItemStack(ModObjects.cleaver_sword), new AspectList().add(DEMON, 6).add(Aspect.AVERSION, 6).add(Aspect.FIRE, 6));

		//Fumes
		event.register.registerObjectTag(new ItemStack(ModObjects.demonic_elixir), new AspectList().add(DEMON, 6).add(Aspect.FIRE, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.cleansing_balm), new AspectList().add(Aspect.PROTECT, 6).add(Aspect.AURA, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.ebb_of_death), new AspectList().add(Aspect.DEATH, 6).add(Aspect.ENTROPY, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.acacia_resin), new AspectList().add(Aspect.PLANT, 6).add(Aspect.AURA, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.fiery_unguent), new AspectList().add(Aspect.FIRE, 6).add(Aspect.ENERGY, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.spruce_heart), new AspectList().add(Aspect.PLANT, 6).add(Aspect.COLD, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.liquid_witchcraft), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.essence_of_vitality), new AspectList().add(Aspect.PLANT, 6).add(Aspect.LIFE, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.droplet_of_wisdom), new AspectList().add(Aspect.MIND, 6).add(Aspect.PLANT, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.cloudy_oil), new AspectList().add(Aspect.ENTROPY, 6).add(Aspect.DARKNESS, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.oak_spirit), new AspectList().add(Aspect.PLANT, 6).add(Aspect.SOUL, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.unfired_jar), new AspectList().add(Aspect.VOID, 6).add(Aspect.EARTH, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.empty_jar), new AspectList().add(Aspect.VOID, 6).add(Aspect.EARTH, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.bottled_frostfire), new AspectList().add(Aspect.FIRE, 6).add(Aspect.COLD, 6).add(Aspect.EXCHANGE, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.bottled_hellfire), new AspectList().add(Aspect.FIRE, 6).add(DEMON, 6).add(Aspect.DARKNESS, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.birch_soul), new AspectList().add(Aspect.PLANT, 6).add(Aspect.SOUL, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.swirl_of_depths), new AspectList().add(Aspect.WATER, 6).add(Aspect.DARKNESS, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.oil_of_vitriol), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.AVERSION, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.otherworldly_tears), new AspectList().add(STAR, 6).add(Aspect.WATER, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.heaven_extract), new AspectList().add(SUN, 6).add(Aspect.AIR, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.stone_ichor), new AspectList().add(Aspect.EARTH, 6).add(Aspect.ENTROPY, 6));

		event.register.registerObjectTag(new ItemStack(ModObjects.hellfire), new AspectList().add(Aspect.FIRE, 6).add(DEMON, 6).add(Aspect.DARKNESS, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.frostfire), new AspectList().add(Aspect.FIRE, 6).add(Aspect.COLD, 6).add(Aspect.EXCHANGE, 6));

		//Candles
		event.register.registerObjectTag(new ItemStack(ModObjects.black_candle), new AspectList().add(Aspect.DARKNESS, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.cyan_candle), new AspectList().add(Aspect.LIFE, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.white_candle), new AspectList().add(Aspect.AURA, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.purple_candle), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.blue_candle), new AspectList().add(Aspect.WATER, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.green_candle), new AspectList().add(Aspect.PLANT, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.pink_candle), new AspectList().add(Aspect.PROTECT, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.yellow_candle), new AspectList().add(Aspect.MIND, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.orange_candle), new AspectList().add(Aspect.FIRE, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.red_candle), new AspectList().add(DEMON, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.brown_candle), new AspectList().add(Aspect.EARTH, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.gray_candle), new AspectList().add(Aspect.VOID, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.light_gray_candle), new AspectList().add(Aspect.COLD, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.magenta_candle), new AspectList().add(Aspect.EXCHANGE, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.lime_candle), new AspectList().add(Aspect.ALCHEMY, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.light_blue_candle), new AspectList().add(Aspect.AIR, 10).add(Aspect.LIGHT, 10).add(Aspect.CRAFT, 10));

		//Brooms
		event.register.registerObjectTag(new ItemStack(ModObjects.cypress_broom), new AspectList().add(Aspect.PLANT, 35).add(Aspect.MAGIC, 35).add(Aspect.FLIGHT, 30).add(MOON, 25).add(STAR, 25));
		event.register.registerObjectTag(new ItemStack(ModObjects.elder_broom), new AspectList().add(Aspect.PLANT, 35).add(Aspect.MAGIC, 35).add(Aspect.FLIGHT, 30).add(MOON, 25).add(STAR, 25));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_broom), new AspectList().add(Aspect.PLANT, 35).add(Aspect.MAGIC, 35).add(Aspect.FLIGHT, 30).add(MOON, 25).add(STAR, 25));
		event.register.registerObjectTag(new ItemStack(ModObjects.dragons_blood_broom), new AspectList().add(Aspect.PLANT, 35).add(Aspect.MAGIC, 35).add(Aspect.FLIGHT, 30).add(MOON, 25).add(STAR, 25));

		//Misc
		event.register.registerObjectTag(new ItemStack(ModObjects.vampiric_amulet), new AspectList().add(Aspect.MAGIC, 16).add(Aspect.DARKNESS, 16).add(MOON, 16));

		event.register.registerObjectTag(new ItemStack(ModObjects.triskelion), new AspectList().add(Aspect.DESIRE, 16).add(Aspect.AVERSION, 16).add(SUN, 16));
		event.register.registerObjectTag(new ItemStack(ModObjects.fortunes_favor), new AspectList().add(Aspect.DESIRE, 16).add(Aspect.EARTH, 16).add(Aspect.MAGIC, 16));
		event.register.registerObjectTag(new ItemStack(ModObjects.gluttons_sash), new AspectList().add(Aspect.DESIRE, 16).add(DEMON, 16).add(Aspect.MAGIC, 16));

		event.register.registerObjectTag(new ItemStack(ModObjects.skeleton_key), new AspectList().add(Aspect.DESIRE, 16).add(DEMON, 16).add(Aspect.MAGIC, 16));

		event.register.registerObjectTag(new ItemStack(ModObjects.hecates_visage), new AspectList().add(Aspect.AURA, 16).add(Aspect.FIRE, 16).add(Aspect.MAGIC, 16));

		event.register.registerObjectTag(new ItemStack(ModObjects.dimensional_sand), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DARKNESS, 4).add(STAR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.ectoplasm), new AspectList().add(Aspect.SOUL, 5).add(Aspect.DEATH, 5));
		event.register.registerObjectTag(new ItemStack(ModObjects.oak_apple_gall), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.ENTROPY, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.bone_needle), new AspectList().add(Aspect.DEATH, 2).add(Aspect.CRAFT, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.spectral_dust), new AspectList().add(Aspect.VOID, 4).add(Aspect.SOUL, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.tallow), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.ALCHEMY, 8).add(Aspect.BEAST, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.bottle_of_blood), new AspectList().add(Aspect.WATER, 3).add(Aspect.LIFE, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.bottle_of_vampire_blood), new AspectList().add(Aspect.WATER, 3).add(Aspect.DEATH, 3).add(DEMON, 3));

		event.register.registerObjectTag(new ItemStack(ModObjects.box_of_sealed_evil), new AspectList().add(Aspect.DESIRE, 15).add(DEMON, 15).add(Aspect.DARKNESS, 15).add(Aspect.VOID, 15));

		event.register.registerObjectTag(new ItemStack(ModObjects.leonards_wand), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.ALCHEMY, 15).add(DEMON, 15).add(Aspect.ENERGY, 15));
		event.register.registerObjectTag(new ItemStack(ModObjects.caduceus), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.FIRE, 15).add(DEMON, 15).add(Aspect.DARKNESS, 15));

		event.register.registerObjectTag(new ItemStack(ModObjects.thyrsus), new AspectList().add(SUN, 16).add(Aspect.AVERSION, 16).add(Aspect.PLANT, 16));

		event.register.registerObjectTag(new ItemStack(ModObjects.cleaver_sword), new AspectList().add(DEMON, 16).add(Aspect.AVERSION, 16).add(Aspect.FIRE, 16));

		//Add some of our aspects to existing items in vanilla
		//Use this sparingly. Please run over any future additions to this part of the file with Sunconure11.
		//If new aspects must be added to an item from vanilla, try and preserve as much of the original aspects as possible.
		//The same applies if you try and add aspects to items from other mods.
		event.register.registerObjectTag(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new AspectList().add(Aspect.PLANT, 5).add(Aspect.SENSES, 5).add(SUN, 3).add(Aspect.AIR, 1).add(Aspect.LIFE, 1));
		event.register.registerObjectTag(new ItemStack(Items.GOLD_INGOT), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 10).add(SUN, 5));

		//Entities
		ThaumcraftApi.registerEntityTag("bewitchment.owl", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(MOON, 8));
		ThaumcraftApi.registerEntityTag("bewitchment.snake", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AVERSION, 10).add(DEMON, 8));
		ThaumcraftApi.registerEntityTag("bewitchment.raven", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(Aspect.DARKNESS, 8));
		ThaumcraftApi.registerEntityTag("bewitchment.toad", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.ALCHEMY, 8));

		ThaumcraftApi.registerEntityTag("bewitchment.lizard", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(SUN, 8));

		ThaumcraftApi.registerEntityTag("bewitchment.black_dog", new AspectList().add(Aspect.SOUL, 25).add(Aspect.BEAST, 25).add(Aspect.AVERSION, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.hellhound", new AspectList().add(Aspect.BEAST, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.feuerwurm", new AspectList().add(Aspect.BEAST, 25).add(DEMON, 25).add(Aspect.DARKNESS, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.demon", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.demoness", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.imp", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.druden", new AspectList().add(Aspect.PLANT, 25).add(DEMON, 25).add(Aspect.DARKNESS, 16));
		ThaumcraftApi.registerEntityTag("bewitchment.shadow_person", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.DARKNESS, 25));
		ThaumcraftApi.registerEntityTag("bewitchment.cleaver", new AspectList().add(Aspect.AVERSION, 25).add(DEMON, 25).add(Aspect.BEAST, 25));
		ThaumcraftApi.registerEntityTag("bewitchment.cambion", new AspectList().add(Aspect.MIND, 25).add(DEMON, 25).add(Aspect.BEAST, 25));
		ThaumcraftApi.registerEntityTag("bewitchment.bafometyr", new AspectList().add(Aspect.AVERSION, 25).add(DEMON, 25).add(Aspect.BEAST, 25).add(Aspect.MAGIC, 25));

		ThaumcraftApi.registerEntityTag("bewitchment.ghost", new AspectList().add(Aspect.SOUL, 25).add(Aspect.DEATH, 25).add(Aspect.MIND, 16));

		ThaumcraftApi.registerEntityTag("bewitchment.leonard", new AspectList().add(Aspect.SOUL, 45).add(DEMON, 45).add(Aspect.ALCHEMY, 45));
		ThaumcraftApi.registerEntityTag("bewitchment.baphomet", new AspectList().add(Aspect.SOUL, 45).add(DEMON, 45).add(Aspect.FIRE, 45));
	}


	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> Loader.isModLoaded("thaumcraft");
	}
}
