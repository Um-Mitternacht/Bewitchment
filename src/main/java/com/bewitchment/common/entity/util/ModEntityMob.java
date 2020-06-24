package com.bewitchment.common.entity.util;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public abstract class ModEntityMob extends EntityMob {
    public static final DataParameter<Integer> SKIN = EntityDataManager.createKey(ModEntityMob.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> SPECTRAL = EntityDataManager.createKey(ModEntityMob.class, DataSerializers.BOOLEAN);

    private final ResourceLocation lootTableLocation;

    public int lifeTimeTicks = 0;
    public UUID summoner;

    protected ModEntityMob(World world, ResourceLocation lootTableLocation) {
        super(world);
        this.lootTableLocation = lootTableLocation;
        dataManager.set(SPECTRAL, false);
    }

    protected int getSkinTypes() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (dataManager.get(SPECTRAL)) {
                if (lifeTimeTicks <= 0) {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, 20, rand.nextGaussian() / 3, rand.nextGaussian() / 3, rand.nextGaussian() / 3, 0);
                    setDead();
                } else lifeTimeTicks--;
            }
            if (summoner != null && ticksElytraFlying % 60 == 0) {
                EntityPlayer player = world.getPlayerEntityByUUID(summoner);
                if (player != null && ((this.getAttackTarget() != player.getAttackingEntity() && this.getAttackTarget() != player.getLastAttackedEntity()) || this.getAttackTarget() == null)) {
                    this.setAttackTarget(player.getAttackingEntity() == null ? player.getLastAttackedEntity() : player.getAttackingEntity());
                }
            }
        }
    }

    @Override
    protected abstract boolean isValidLightLevel();

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(SKIN, 0);
        dataManager.register(SPECTRAL, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        tag.setInteger("skin", dataManager.get(SKIN));
        tag.setBoolean("spectral", dataManager.get(SPECTRAL));
        tag.setInteger("lifeTimeTick", lifeTimeTicks);
        tag.setString("summoner", summoner == null ? "" : summoner.toString());
        dataManager.setDirty(SKIN);
        dataManager.setDirty(SPECTRAL);
        super.writeEntityToNBT(tag);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        dataManager.set(SKIN, tag.getInteger("skin"));
        dataManager.set(SPECTRAL, tag.getBoolean("spectral"));
        lifeTimeTicks = tag.getInteger("lifeTimeTick");
        summoner = tag.getString("summoner").equals("") ? null : UUID.fromString(tag.getString("summoner"));
        super.readEntityFromNBT(tag);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return lootTableLocation;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
        dataManager.set(SKIN, rand.nextInt(getSkinTypes()));
        return super.onInitialSpawn(difficulty, data);
    }
}