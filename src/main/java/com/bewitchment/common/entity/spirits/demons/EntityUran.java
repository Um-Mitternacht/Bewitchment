package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

/**
 * Created by Joseph on 10/2/2018.
 */

public class EntityUran extends EntityMultiSkin implements IMob {


	//Todo: Rewrite code, and implement weaknesses to water. Also implement a special potion effect that upon killing a target, spawns more uranids.
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/snake");
	//	private static final String[] names = {"David Hisslehoff", "Strangles", "Julius Squeezer", "William Snakespeare", "Medusa", "Sir Hiss", "Nagini", "Naga", "Slithers", "Rumplesnakeskin", "Monty the Python", "Shesha", "Nagaraja", "Stheno", "Euryale", "Vasuki", "Bakunawa", "Kaliya", "Karkotaka", "Manasa", "Mucalinda", "Padmavati", "Paravataksha", "Takshaka", "Ulupi", "Yulong", "Sir Booplesnoot", "Cobra", "Angus Snake", "Anguis", "Python", "Fafnir", "Echidna", "Anaconda", "Madame White Snake", "Meretseger", "Kaa", "Snape", "Solid Snake", "Apophis", "Ouroboros"};
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.CHICKEN, Items.BEEF, Items.MUTTON, Items.PORKCHOP, Items.FISH, ModItems.heart);
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityUran.class, DataSerializers.VARINT);
	private int timerRef = 0;

	public EntityUran(World worldIn) {
		super(worldIn);
		setSize(1.6F, 1.6F); //Todo: Figure out how to change the size of this properly
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return BewitchmentAPI.getAPI().DEMON;
	}


	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.aiSit != null) {
			this.aiSit.setSitting(false);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockGrass && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(3, new EntityAIMate(this, 1d));
		this.tasks.addTask(4, this.aiSit);
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityRabbit || e instanceof EntityChicken || e instanceof EntityBlindworm || e instanceof EntityLizard || e instanceof EntityCow || e instanceof EntityParrot || e instanceof EntitySheep || e instanceof EntityPig || e instanceof EntityVillager || e instanceof EntityPlayer || e instanceof EntityRaven || e instanceof EntityOwl || e instanceof EntityNewt || e instanceof EntityToad || e instanceof EntitySnake || e instanceof EntityHorse || e instanceof EntityDonkey || e instanceof EntityMule || e instanceof EntityLlama || e instanceof EntityWolf || e instanceof EntityOcelot || e instanceof EntityPolarBear || e.getClass().getName().equals("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat")));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}

	//Todo: Stalking behaviors
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55d);
	}

	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.DROWN, 1.0F);
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	public float getBrightness() {
		return 0.3F;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2000, 1, false, false));
			}
		}
		return flag;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!(otherAnimal instanceof EntityUran)) {
			return false;
		} else {
			EntityUran entityUran = (EntityUran) otherAnimal;
			return this.isInLove() && entityUran.isInLove();
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	public void resetTimer() {
		timerRef = 0;
	}

	public void addTimer(int n) {
		timerRef += n;
	}

	public int getTimer() {
		return timerRef;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == ModItems.heart;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityUran(world);
	}

	@Override
	public int getSkinTypes() {
		return 9;
	}
}
