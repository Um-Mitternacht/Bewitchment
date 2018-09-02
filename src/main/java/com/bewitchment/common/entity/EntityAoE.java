package com.bewitchment.common.entity;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.cauldron.BrewData.BrewEntry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAoE extends Entity {

	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityAoE.class, DataSerializers.VARINT);

	private BrewEntry entry = null;
	private int maxLife;

	public EntityAoE(World worldIn) {
		super(worldIn);
		this.setEntityInvulnerable(true);
		this.noClip = true;
		this.isImmuneToFire = true;
		this.width = 1;
		this.height = 1;
	}

	public EntityAoE(World world, BrewEntry data, BlockPos pos) {
		this(world);
		this.entry = data;
		dataManager.set(COLOR, entry.getPotion().getLiquidColor());
		this.width = entry.getModifierList().getLevel(DefaultModifiers.RADIUS).orElse(0) + 1;// FIXME not working
		this.height = width;
		maxLife = getMaxLife();
		this.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void onUpdate() {
		if (!world.isRemote) {
			this.firstUpdate = false;
			IBrewEffect pot = BewitchmentAPI.getAPI().getBrewFromPotion(entry.getPotion());
			if (pot instanceof IBrewEffectAoEOverTime) {
				IBrewEffectAoEOverTime eff = (IBrewEffectAoEOverTime) pot;
				world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox()).forEach(e -> {
					eff.performEffectAoEOverTime(e, entry.getModifierList());
				});

			} else {
				Bewitchment.logger.warn(entry.getPotion().getName() + " has no AoE Over Time effect associated");
				this.setDead();
			}

			if (this.ticksExisted >= maxLife) {
				this.setDead();
			}
		} else {
			for (int i = 0; i < 20; i++) {
				double pposX = this.posX + world.rand.nextGaussian() * this.width / 2;
				double pposY = this.posY + world.rand.nextDouble() * this.height;
				double pposZ = this.posZ + world.rand.nextGaussian() * this.width / 2;
				if (world.rand.nextBoolean()) {
					Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, pposX, pposY, pposZ, 0, 0, 0, dataManager.get(COLOR));
				} else {
					world.spawnParticle(EnumParticleTypes.END_ROD, pposX, pposY, pposZ, 0, 0, 0);
				}
			}
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(COLOR, Integer.valueOf(0));
	}

	private int getMaxLife() {
		return 100 * (1 + entry.getModifierList().getLevel(DefaultModifiers.GAS_CLOUD_DURATION).orElse(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		entry = new BrewEntry(tag.getCompoundTag("entry"));
		dataManager.set(COLOR, entry.getPotion().getLiquidColor());
		dataManager.setDirty(COLOR);
		maxLife = getMaxLife();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setTag("entry", entry.serializeNBT());
	}

	public static interface IBrewEffectAoEOverTime {
		public void performEffectAoEOverTime(Entity entity, IBrewModifierList modifiers);
	}
}
