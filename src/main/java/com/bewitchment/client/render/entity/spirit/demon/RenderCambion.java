package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelCambion;
import com.bewitchment.client.model.entity.spirit.demon.ModelCambionSlim;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 3/30/2020.
 */
public class RenderCambion extends RenderLiving<EntityCambion> {
	
	private static final ResourceLocation MALE_1 = new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_0.png");
	private static final ResourceLocation MALE_2 = new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_1.png");
	private static final ResourceLocation FEMALE_1 = new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_2.png");
	private static final ResourceLocation FEMALE_2 = new ResourceLocation(Bewitchment.MODID, "textures/entity/cambion_3.png");
	private static final ModelBase slimModel = new ModelCambionSlim();
	private static final ModelBase regModel = new ModelCambion();
	
	public RenderCambion(RenderManager manager) {
		super(manager, new ModelCambion(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelBiped(0.5F);
				this.modelArmor = new ModelBiped(1);
			}
			
			@Override
			protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
				this.setModelVisible(p_188359_1_);
				
				switch (slotIn) {
					case HEAD:
						p_188359_1_.bipedHead.showModel = true;
						p_188359_1_.bipedHeadwear.showModel = true;
						
						p_188359_1_.bipedHead.offsetY = -0.10f;
						p_188359_1_.bipedHeadwear.offsetY = -0.10f;
						break;
					case CHEST:
						p_188359_1_.bipedBody.showModel = true;
						p_188359_1_.bipedRightArm.showModel = true;
						p_188359_1_.bipedLeftArm.showModel = true;
						
						p_188359_1_.bipedBody.offsetY = -0.10f;
						p_188359_1_.bipedRightArm.offsetY = -0.10f;
						p_188359_1_.bipedLeftArm.offsetY = -0.10f;
						break;
					case LEGS:
						p_188359_1_.bipedBody.showModel = true;
						p_188359_1_.bipedRightLeg.showModel = true;
						p_188359_1_.bipedLeftLeg.showModel = true;
						break;
					case FEET:
						p_188359_1_.bipedRightLeg.showModel = true;
						p_188359_1_.bipedLeftLeg.showModel = true;
				}
			}
		});
	}
	
	@Override
	public void doRender(EntityCambion entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity.getCambionType() > 1) {
			this.mainModel = slimModel;
		}
		else this.mainModel = regModel;
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCambion entity) {
		switch (entity.getCambionType()) {
			case 0:
			default:
				return MALE_1;
			case 1:
				return MALE_2;
			case 2:
				return FEMALE_1;
			case 3:
				return FEMALE_2;
		}
	}
	
	@Override
	protected void preRenderCallback(EntityCambion entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.0, 1.0, 1.0);
	}
}
