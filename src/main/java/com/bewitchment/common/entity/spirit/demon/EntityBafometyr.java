package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 4/16/2020.
 */
public class EntityBafometyr extends ModEntityMob {
    public int flameTimer;

    public EntityBafometyr(World world) {
        super(world, new ResourceLocation(Bewitchment.MODID, "entities/bafometyr"));
        isImmuneToFire = true;
        setPathPriority(PathNodeType.LAVA, 8);
        setPathPriority(PathNodeType.DANGER_FIRE, 0);
        setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
        experienceValue = 25;
        setSize(0.8f, 2.0f);
    }

    public int getFlameTimer() {
        return flameTimer;
    }

    //Shameless rip of Baphomet's fireball code
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        flameTimer = (flameTimer + 1) % 8;
        if (getAttackTarget() != null) {
            EntityLivingBase player = getAttackTarget();
            boolean launchFireball = ticksExisted % 80 > 5;
            if (!launchFireball && getDistance(player) > 2) {
                double d0 = getDistanceSq(player);
                double d1 = player.posX - this.posX;
                double d2 = player.getEntityBoundingBox().minY + (double) (player.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
                double d3 = player.posZ - this.posZ;
                float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                world.playEvent(null, 1018, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
                EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, this, d1 + this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f);
                entitysmallfireball.posY = posY + (double) (height / 2.0F) + 0.5D;
                this.swingArm(EnumHand.MAIN_HAND);
                world.spawnEntity(entitysmallfireball);
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BAFOMETYR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BAFOMETYR_DEATH;
    }

    @Override
    protected void despawnEntity() {
        if (!hasCustomName()) {
            super.despawnEntity();
        }
    }

    @Override
    protected boolean canDespawn() {
        return !hasCustomName();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            player.setFire(25);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.66);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 0.75, false));
        tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
        tasks.addTask(3, new EntityAILookIdle(this));
        tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> (e instanceof EntityPlayer || e instanceof EntityHellhound || e instanceof EntityCleaver || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)) && !BewitchmentAPI.hasBesmirchedGear(e)));
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
    protected SoundEvent getAmbientSound() {
        return ModSounds.BAFOMETYR_IDLE;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }

    @Override
    protected int getSkinTypes() {
        return 1;
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return BewitchmentAPI.DEMON;
    }
}
