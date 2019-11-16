package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelLeonard;
import com.bewitchment.common.entity.spirit.demon.EntityLeonard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class RenderLeonard extends RenderLiving<EntityLeonard> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/leonard.png");
	
	public RenderLeonard(RenderManager manager) {
		super(manager, new ModelLeonard(), 0.3f);
		this.addLayer(new RenderBaphomet.LayerHeldWeapon(this));
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityLeonard entityLeonard) {
		return TEX;
	}
	
	@Override
	protected void preRenderCallback(EntityLeonard entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.6, 1.6, 1.6);
	}
}
