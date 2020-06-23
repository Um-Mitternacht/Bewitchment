package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelCleaver;
import com.bewitchment.common.entity.spirit.demon.EntityCleaver;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCleaver extends RenderLiving<EntityCleaver> {
	private static final ResourceLocation[] TEX = {new ResourceLocation(Bewitchment.MODID, "textures/entity/cleaver.png"), new ResourceLocation(Bewitchment.MODID, "textures/entity/cleaver_2.png")};
	
	public RenderCleaver(RenderManager manager) {
		super(manager, new ModelCleaver(1), 0.3f);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelBiped(0.5F); //add biped model here, but with parts in cleaver-size or so
				this.modelArmor = new ModelBiped(1);
			}
			
			@Override
			protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
				this.setModelVisible(p_188359_1_);
				
				switch (slotIn) {
					case HEAD:
						p_188359_1_.bipedHead.showModel = true;
						p_188359_1_.bipedHeadwear.showModel = true;
						
						p_188359_1_.bipedHead.offsetY = -0.40f;
						p_188359_1_.bipedHeadwear.offsetY = -0.40f;
						break;
					case CHEST:
						p_188359_1_.bipedBody.showModel = true;
						p_188359_1_.bipedRightArm.showModel = true;
						p_188359_1_.bipedLeftArm.showModel = true;
						
						p_188359_1_.bipedBody.offsetY = -0.30f;
						p_188359_1_.bipedRightArm.offsetY = -0.30f;
						p_188359_1_.bipedLeftArm.offsetY = -0.30f;
						break;
					case LEGS:
						p_188359_1_.bipedBody.showModel = false;
						p_188359_1_.bipedRightLeg.showModel = false;
						p_188359_1_.bipedLeftLeg.showModel = false;
						break;
					case FEET:
						p_188359_1_.bipedRightLeg.showModel = false;
						p_188359_1_.bipedLeftLeg.showModel = false;
				}
			}
		});
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCleaver entity) {
		return TEX[entity.getDataManager().get(ModEntityMob.SKIN)];
	}
	
	@Override
	protected void preRenderCallback(EntityCleaver entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.0, 1.0, 1.0);
	}
}