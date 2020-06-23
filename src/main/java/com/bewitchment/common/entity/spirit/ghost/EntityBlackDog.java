package com.bewitchment.common.entity.spirit.ghost;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class EntityBlackDog extends ModEntityMob {
    public EntityBlackDog(World world) {
        super(world, new ResourceLocation(Bewitchment.MODID, "entities/black_dog"));
        setSize(1.08f, 1.53f);
        experienceValue = 35;
        isImmuneToFire = true;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.0F, 0.0F);
    }

    //Todo: Make regeneration and healing harm it, since it's a ghost
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != MobEffects.POISON && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
    }

    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return BewitchmentAPI.SPIRIT;
    }

    @Override
    protected int getSkinTypes() {
        return 5;
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    //Todo: Make it so they become more powerful if hit by lightning

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!world.isRemote && world.isDaytime() && !world.isRaining() && canDespawn()) setDead();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean flag = super.attackEntityFrom(source, amount);
        if (flag) {
            addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 50, 1, false, false));
            addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 1, false, false));
        }
        return flag;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.25);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
        tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
        tasks.addTask(3, new EntityAILookIdle(this));
        tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityVillager || e instanceof AbstractIllager || e instanceof EntityWitch || e instanceof EntityIronGolem || e instanceof EntitySheep || e instanceof EntityCow || e instanceof EntityChicken || e instanceof EntityLlama || e instanceof EntityPig || e instanceof EntityRabbit || e instanceof AbstractHorse));
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        PathNavigateGround path = new PathNavigateGround(this, world);
        path.setBreakDoors(true);
        return path;
    }

    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            --i;
            this.setAir(i);

            if (this.getAir() == -20) {
                this.setAir(300);
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    protected boolean canDespawn() {
        return !hasCustomName();
    }
}
