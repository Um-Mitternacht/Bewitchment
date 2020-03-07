package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.IPledgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 2/21/2020.
 */
public class EntityMoloch extends AbstractGreaterDemon implements IPledgeable {
	public EntityMoloch(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/moloch"));
	}
	
	@Override
	public String getPledgeName() {
		return "moloch";
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
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
		return false;
	}
}
