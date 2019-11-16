package com.bewitchment.client.render.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.spirit.demon.ModelBaphomet;
import com.bewitchment.common.entity.spirit.demon.EntityBaphomet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderBiped;
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
		this.addLayer(new LayerHeldWeapon(this));
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityBaphomet entityBaphomet) {
		return TEX;
	}
	
	@Override
	protected void preRenderCallback(EntityBaphomet entity, float partialTickTime) {
		super.preRenderCallback(entity, partialTickTime);
		GlStateManager.scale(1.6, 1.6, 1.6);
	}

	@SideOnly(Side.CLIENT)
	public static class LayerHeldWeapon implements LayerRenderer<EntityLivingBase> {

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
					float f = 0.5F;
					GlStateManager.translate(0.0F, 0.75F, 0.0F);
					GlStateManager.scale(0.5F, 0.5F, 0.5F);
				}
				this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
				this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
				GlStateManager.popMatrix();
			}
		}

		private void renderHeldItem(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, EnumHandSide handSide)
		{
			if (!itemStack.isEmpty()) {
				GlStateManager.pushMatrix();
				if (entityLivingBase.isSneaking()) {
					GlStateManager.translate(0.0F, 0.2F, 0.0F);
				}
				this.translateToHand(handSide);
				GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
				boolean flag = handSide == EnumHandSide.LEFT;
				GlStateManager.translate((float)(flag ? -1 : -0.25) / 4, -0.125F, 1 / 8F);
				Minecraft.getMinecraft().getItemRenderer().renderItemSide(entityLivingBase, itemStack, transformType, flag);
				GlStateManager.popMatrix();
			}
		}

		void translateToHand(EnumHandSide p_191361_1_) {
			((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_191361_1_);
		}

		public boolean shouldCombineTextures()
		{
			return false;
		}
	}
}
