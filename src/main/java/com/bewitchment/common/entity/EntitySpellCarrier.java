/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.entity;

import com.bewitchment.api.spell.ISpell;
import com.bewitchment.api.spell.ISpell.EnumSpellType;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.spell.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class EntitySpellCarrier extends EntityThrowable {

	private static final DataParameter<String> SPELL = EntityDataManager.<String>createKey(EntitySpellCarrier.class, DataSerializers.STRING);
	private static final DataParameter<String> CASTER = EntityDataManager.<String>createKey(EntitySpellCarrier.class, DataSerializers.STRING);

	public EntitySpellCarrier(World world) {
		super(world);
	}

	public EntitySpellCarrier(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void entityInit() {
		this.setEntityInvulnerable(true);
		this.setNoGravity(true);
		this.setSize(0.1f, 0.1f);
		this.getDataManager().register(SPELL, "");
		this.getDataManager().register(CASTER, "");
	}

	public void setSpell(ISpell spell) {
		setSpell(spell.getRegistryName().toString());
	}

	private void setSpell(String spell) {
		this.getDataManager().set(SPELL, spell);
		this.getDataManager().setDirty(SPELL);
	}

	private void setCaster(String uuid) {
		this.getDataManager().set(CASTER, uuid);
		this.getDataManager().setDirty(CASTER);
	}

	public void setCaster(EntityLivingBase player) {
		if (player != null) setCaster(player.getUniqueID().toString());
		else setCaster("");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("spell", getSpellName());
		compound.setString("caster", getCasterUUID());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setSpell(compound.getString("spell"));
		this.setCaster(compound.getString("caster"));
	}

	private String getSpellName() {
		return this.getDataManager().get(SPELL);
	}

	@Nullable
	public ISpell getSpell() {
		return Spell.SPELL_REGISTRY.getValue(new ResourceLocation(getSpellName()));
	}

	private String getCasterUUID() {
		return this.getDataManager().get(CASTER);
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (ticksExisted > 40) {
			this.setDead();
		}
	}

	@Nullable
	public EntityLivingBase getCaster() {
		String uuid = getCasterUUID();
		if (uuid == null || uuid.equals("")) return null;
		EntityLivingBase player = this.world.getPlayerEntityByUUID(UUID.fromString(uuid));
		if (player != null) return player;
		ArrayList<Entity> ent = new ArrayList<Entity>(world.getLoadedEntityList());
		for (Entity e : ent)
			if (e instanceof EntityLivingBase && uuid.equals(e.getUniqueID().toString())) return (EntityLivingBase) e;
		return null;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote) {
			ISpell spell = getSpell();
			EntityLivingBase caster = getCaster();
			if (spell != null) {
				if (result.typeOfHit != Type.ENTITY || result.entityHit != caster)
					spell.performEffect(result, caster, world);
				if (result.typeOfHit == Type.BLOCK && (spell.getType() == EnumSpellType.PROJECTILE_BLOCK || spell.getType() == EnumSpellType.PROJECTILE_ALL))
					this.setDead();
				if (result.typeOfHit == Type.ENTITY && (spell.getType() == EnumSpellType.PROJECTILE_ENTITY || spell.getType() == EnumSpellType.PROJECTILE_ALL) && result.entityHit != caster)
					this.setDead();
			} else Bewitchment.logger.warn("Spell is null for " + this + " with spell reg name of " + getSpellName());
		}
	}

	@Override
	public void setDead() {
		super.setDead();
	}

}
