package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelBaphomet;
import com.bewitchment.common.entity.spirit.demon.EntityBaphomet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class RenderBaphomet extends RenderLiving<EntityBaphomet> {
	private static final ResourceLocation TEX = new ResourceLocation(Bewitchment.MODID, "textures/entity/baphomet.png");
	
	public RenderBaphomet(RenderManager manager) {
		super(manager, new ModelBaphomet(), 0.3f);
		this.addLayer(new LayerHeldItem(this));
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityBaphomet entityBaphomet) {
		return TEX;
	}
	
	@Override
	protected void preRenderCallback(EntityBaphomet entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.25, 1.25, 1.25);
	}
}
