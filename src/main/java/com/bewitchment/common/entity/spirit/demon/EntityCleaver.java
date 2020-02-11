package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 2/10/2020.
 */
public class EntityCleaver extends ModEntityMob {
	protected EntityCleaver(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/cleaver"));
		setSize(1.5f, 4.5f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 25;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	protected int getSkinTypes() {
		return 2;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.50);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.50);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(55);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
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
		}
		else {
			this.setAir(300);
		}
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
