package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelLeonard;
import com.bewitchment.common.entity.spirit.demon.EntityLeonard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
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
		this.addLayer(new LayerHeldWeapon(this));
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

	@SideOnly(Side.CLIENT)
	private static class LayerHeldWeapon implements LayerRenderer<EntityLivingBase> {

		final RenderLivingBase<?> livingEntityRenderer;

		LayerHeldWeapon(RenderLivingBase<?> livingEntityRendererIn)
		{
			this.livingEntityRenderer = livingEntityRendererIn;
		}

		public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
			ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
			ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
			if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
				GlStateManager.pushMatrix();
				if (this.livingEntityRenderer.getMainModel().isChild) {
					GlStateManager.translate(0.0F, 0.75F, 0.0F);
					GlStateManager.scale(1.5F, 1.5F, 1.5F);
				}
				this.renderHeldItem(entitylivingbaseIn, itemstack1);
				GlStateManager.popMatrix();
			}
		}

		private void renderHeldItem(EntityLivingBase entityLivingBase, ItemStack itemStack) {
			if (!itemStack.isEmpty()) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(-0.5, 0.625, -0.375);
				GlStateManager.rotate(220.0F, -1.0F, 0.0F, 0.0F);
				GlStateManager.scale(1.5, 1.5, 1.5);
				Minecraft.getMinecraft().getItemRenderer().renderItemSide(entityLivingBase, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false);
				GlStateManager.popMatrix();
			}
		}

		public boolean shouldCombineTextures() {
			return false;
		}
	}
}
