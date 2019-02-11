package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 1/14/2019.
 */
public class EntityDemon extends EntityDemonBase implements IAnimatedEntity, IMob {

	public static final Animation ANIMATION_TOSS = Animation.create(20, 10);
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/demon");
	private static final String[] names = {"Bael", "Agares", "Vassago", "Samigina", "Marbas", "Valefor", "Amon", "Barbatos", "Paimon", "Buer", "Gusion", "Sitri", "Beleth", "Leraje", "Eligos", "Zepar", "Botis", "Bathin", "Sallos", "Purson", "Marax", "Ipos", "Aim", "Naberius", "Glasya-Labolas", "Buné", "Ronové", "Berith", "Astaroth", "Forneus", "Asmodeus", "Asmoday", "Gäap", "Furfur", "Marchosias", "Stolas", "Phenex", "Halphas", "Malphas", "Räum", "Focalor", "Vepar", "Sabnock", "Shax", "Viné", "Bifrons", "Vual", "Haagenti", "Crocell", "Furcas", "Balam", "Alloces", "Caim", "Murmur", "Orobas", "Gremory", "Ose", "Amy", "Orias", "Vapula", "Zagan", "Valac", "Andras", "Flauros", "Andrealphus", "Kimaris", "Amdusias", "Belial", "Decarabia", "Seere", "Dantalion", "Andromalius", "Amaymon", "Corson", "Ziminiar", "Moloch", "Pruflas", "Satanachia", "Sargatanas", "Beelzebub", "Agaliarept", "Tarihimal", "Qandisa", "Mbwiri", "Shezmu", "Ammit", "Apep", "Pañcika", "Hariti", "Mara", "Azazel", "Zalambur", "Al-‘Uzzá", "Pazuzu", "Asag", "Koka", "Vikoka", "Jalandhara", "Ravana", "Chedipe", "Kali", "Kalanemi", "Tārakāsura", "Durgamasur", "Bhandasura", "Arunasura", "Sumbha", "Nisumbha", "Yaldabaoth", "Horaios", "Elaios", "Adonaios", "Astaphanos", "Sabaoth", "Iao", "Aka-Manah", "Aeshma", "Bushyasta", "Angra-Mainyu", "Apaosha", "Jahi", "Div-e Sepid", "Zarik", "Ahriman", "Eisheth", "Bagdana", "Berith", "Baal", "Agrat bat Mahlat", "Abyzou", "A'arab Zaraq", "Naamah", "Šulak", "Tanin'iver", "Golachab", "Ghagiel", "Gha'agsheblah", "Gamaliel", "Sathariel", "Thagirion", "Thaumiel", "Odei", "Nasnas", "Guayota", "Nybbas", "Melchom", "Ukobach", "Zaebos", "Anamelech", "Adrammelech", "Zahhak", "Mamitu", "Lakhey", "Kroni", "Titivillus", "Belphegor", "Chort", "Anzû", "Grendel", "Dusios", "Alphito", "Ala", "Bies", "Boruta", "Bukavac", "Chernobog", "Reeri Yakseya", "Maha Sona", "Samca", "Seteh", "Baigujing", "Yin", "Xiong Shanjun", "Techu Shi", "Zhenyuanzi", "Hu A'qi", "Ruyi", "Sai Tai Sui", "Samyaza", "Yen Lo Wang", "Pipa Jing", "Jiutou Zhiji Jing", "Dzoavits", "Átahsaia", "Chullachaki", "Guahaioque", "Kigatilik", "Okeus", "Gualichu", "Sonneillon", "Gressil", "Verrine", "Rosier", "Oeillet", "Carnivale", "Carreau", "Olivier", "Luvart", "Verrier", "Mammon", "Legion", "Tartaruchi", "Demogorgon", "Alastor", "Surgat", "Asbeel", "Chazaqiel", "Iscaraon", "Madeste", "Zhuanlun", "Pingdeng", "Dushi", "Taishan", "Biancheng", "Yanluo", "Wuguan", "Songdi", "Chujiang", "Qinguang", "Ox-Head", "Horse-Face", "Saklas", "Wanyudo", "Bašmu", "Kulullû", "Kusarikku", "Lamashtu", "Mukīl rēš lemutti", "Mušmaḫḫū", "Ugallu", "Umū dabrūtu", "Uridimmu", "Ušumgallu", "Prahasta", "Kirmira", "Hidimba", "Hidimbi", "Bazil", "Azi-Dahaka", "Indrajit", "Khara", "Kumbhakarana", "Maricha", "Subahu", "Tataka", "Vibishana", "Vatapi", "Daruka", "Mandodari", "Apollyon", "Bakasura", "Kabandha", "Druj", "Caspiel", "Carnesiel", "Amenadiel", "Demoriel", "Pamersiel", "Garadiel", "Buriel", "Hydriel", "Barachus", "Symiel", "Raysiel", "Pirichiel", "Emoniel", "Icosiel", "Soleviel", "Menadiel", "Macariel", "Bidiel", "Dorochiel", "Malgaras", "Dorochiel", "Masaeriel", "Barmiel", "Gedial", "Asyriel", "Aseliel", "Camuel", "Padiel", "Ramiel", "Tamiel", "Kokabiel", "Arakiel", "Danel", "Chazaqiel", "Baraqiel", "Asahel", "Armaros", "Batariel", "Ananiel", "Zaqiel", "Shamsiel", "Turiel", "Yomiel", "Sariel", "Abraxas", "Chaigidel", "Gamchicoth", "Golachab", "Harab Serapel", "Urieus", "Egin", "Mahazael", "Nebro", "Lucifuge Rofocale", "Alrinach", "Bucon", "Tenebrion", "Verdelet", "Leges", "Mulciber", "Fleruty", "Abbadon", "Alastor","Morax", "Baphomet", "Circle Guy", "Lair Guy", "Zozo", "Faust", "The Old Man", "The Smoke Man", "Mephistopheles", "Behemoth"};
	private int animationTick;
	private Animation currentAnimation;

	public EntityDemon(World worldIn) {
		super(worldIn);
		setSize(1.8F, 4.6F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 165;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(175.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.25D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.66D);
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.targetTasks.addTask(9, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityUran || e instanceof EntityHellhound || e instanceof EntityHellhoundAlpha));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(8, new EntityAIAttackMelee(this, 1.0D, false));
	}

	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		if (potioneffectIn.getPotion() == MobEffects.WITHER || potioneffectIn.getPotion() == MobEffects.POISON || potioneffectIn.getPotion() == ModPotions.rotting) {
			net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
		}
		return super.isPotionApplicable(potioneffectIn);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase && this.getAnimation() != ANIMATION_TOSS) {
				{
					this.setAnimation(ANIMATION_TOSS);
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 2000, 1, false, false));
					setFire(500);
					entity.motionY += 0.6000000059604645D;
					this.applyEnchantments(this, entity);
				}
			}
			return flag;
		}
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		AnimationHandler.INSTANCE.updateAnimations(this);
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityDemon.ANIMATION_TOSS};
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.75F;
	}
}
