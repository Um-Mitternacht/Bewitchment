package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.common.Bewitchment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class RenderMimicPlayer extends RenderLivingBase<AbstractClientPlayer> {
	private UUID mimicID;
	private String mimicName;
	private RenderPlayer renderPlayer;

	public RenderMimicPlayer(RenderManager renderManager, RenderPlayer renderPlayer, UUID mimicID, String mimicName) {
		this(renderManager, false, renderPlayer, mimicID, mimicName);
	}

	public RenderMimicPlayer(RenderManager renderManager, boolean useSmallArms, RenderPlayer renderPlayer, UUID mimicID, String mimicName) {
		super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
		this.renderPlayer = renderPlayer;
		this.mimicID = mimicID;
		this.mimicName = mimicName;
		this.addLayer(new LayerBipedArmor(renderPlayer));
		this.addLayer(new LayerHeldItem(renderPlayer));
		this.addLayer(new LayerArrow(renderPlayer));
		this.addLayer(new LayerDeadmau5Head(renderPlayer));
		this.addLayer(new LayerCape(renderPlayer));
		this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
		this.addLayer(new LayerElytra(renderPlayer));
		this.addLayer(new LayerEntityOnShoulder(renderManager));
	}

	/*
	 * Method unmodified from RenderPlayer#getMainModel
	 */
	@Override
	public ModelPlayer getMainModel() {
		return (ModelPlayer) super.getMainModel();
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
			double d0 = y;
			if (entity.isSneaking()) {
				d0 = y - 0.125D;
			}

			this.setModelVisibilities(entity);
			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
			super.doRender(entity, x, d0, z, entityYaw, partialTicks);
			GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		}
	}

	/*
	 * Method unmodified from RenderPlayer#setModelVisibilities
	 */
	private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		ModelPlayer modelplayer = this.getMainModel();
		if (clientPlayer.isSpectator()) {
			modelplayer.setVisible(false);
			modelplayer.bipedHead.showModel = true;
			modelplayer.bipedHeadwear.showModel = true;
		} else {
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			modelplayer.setVisible(true);
			modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
			modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
			modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
			modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
			modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
			modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
			modelplayer.isSneak = clientPlayer.isSneaking();
			ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
			ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
			EnumAction enumaction1;
			if (!itemstack.isEmpty()) {
				modelbiped$armpose = ModelBiped.ArmPose.ITEM;
				if (clientPlayer.getItemInUseCount() > 0) {
					enumaction1 = itemstack.getItemUseAction();
					if (enumaction1 == EnumAction.BLOCK) {
						modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
					} else if (enumaction1 == EnumAction.BOW) {
						modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (!itemstack1.isEmpty()) {
				modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
				if (clientPlayer.getItemInUseCount() > 0) {
					enumaction1 = itemstack1.getItemUseAction();
					if (enumaction1 == EnumAction.BLOCK) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
					} else if (enumaction1 == EnumAction.BOW) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
				modelplayer.rightArmPose = modelbiped$armpose;
				modelplayer.leftArmPose = modelbiped$armpose1;
			} else {
				modelplayer.rightArmPose = modelbiped$armpose1;
				modelplayer.leftArmPose = modelbiped$armpose;
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
		//TODO: find a way to get skin without referencing the player (allowing for mimicking to occur when vitim offline)
		AbstractClientPlayer newEntity = (AbstractClientPlayer) Minecraft.getMinecraft().world.getPlayerEntityByName(mimicName);
		if (newEntity == null) {
			Bewitchment.logger.info("Attempted mimicking but player not online. ");
			return entity.getLocationSkin();
		}
		return newEntity.getLocationSkin();
	}

	@Override
	public void transformHeldFull3DItemLayer() {
		this.renderPlayer.transformHeldFull3DItemLayer();
	}

	/*
	 * Method unmodified from RenderPlayer#preRenderCallback
	 */
	@Override
	protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
		float f = 0.9375F;
		GlStateManager.scale(f, f, f);
	}

	/*
	 * Method unmodified from RenderPlayer#renderEntityName
	 */
	@Override
	protected void renderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
		if (distanceSq < 100.0D) {
			Scoreboard scoreboard = entityIn.getWorldScoreboard();
			ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
			if (scoreobjective != null) {
				Score score = scoreboard.getOrCreateScore(entityIn.getName(), scoreobjective);
				this.renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
				y += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * 0.025F;
			}
		}

		super.renderEntityName(entityIn, x, y, z, name, distanceSq);
	}

	public void renderRightArm(AbstractClientPlayer clientPlayer) {
		this.renderPlayer.renderRightArm(clientPlayer);
	}

	public void renderLeftArm(AbstractClientPlayer clientPlayer) {
		this.renderPlayer.renderLeftArm(clientPlayer);
	}

	/*
	 * Method unmodified from RenderPlayer#renderLivingAt
	 */
	@Override
	protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
		if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
			super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
		} else {
			super.renderLivingAt(entityLivingBaseIn, x, y, z);
		}

	}

	/*
	 * Method unmodified from RenderPlayer#applyRotations
	 */
	@Override
	protected void applyRotations(AbstractClientPlayer entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping()) {
			GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		} else if (entityLiving.isElytraFlying()) {
			super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
			float f = entityLiving.getTicksElytraFlying() + partialTicks;
			float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
			Vec3d vec3d = entityLiving.getLook(partialTicks);
			double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
			double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;
			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = entityLiving.motionX * vec3d.z - entityLiving.motionZ * vec3d.x;
				GlStateManager.rotate((float) (Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
			}
		} else {
			super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
		}


	}

	public UUID getMimicID() {
		return mimicID;
	}

	public String getMimicName() {
		return mimicName;
	}
}
