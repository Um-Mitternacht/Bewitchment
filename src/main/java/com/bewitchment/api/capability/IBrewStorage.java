package com.bewitchment.api.capability;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.bewitchment.api.cauldron.brew.IBrew;
import com.bewitchment.common.brew.BrewEffect; //TODO remove references from everything that is not under api

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IBrewStorage {

	public void addBrew(EntityLivingBase entity, BrewEffect effect);

	public void removeBrew(EntityLivingBase entity, IBrew brew);

	public Map<IBrew, BrewEffect> getBrewMap();

	public void setBrewMap(Map<IBrew, BrewEffect> effect);

	public void syncToNear(EntityLivingBase target);

	public BrewEffect getBrew(IBrew brew);

	public Collection<BrewEffect> getBrewEffects();

	public Set<IBrew> getBrews();

	public void syncTo(EntityPlayerMP target);
}
