package com.bewitchment.client.render.tile;

import com.bewitchment.common.block.head.BlockAnimalSkull;
import com.bewitchment.common.block.head.HeadTypes;
import com.bewitchment.common.tile.tiles.TileEntityHead;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * @author its_meow
 *         <p>
 *         Mar 7, 2019
 */
public class TileRenderGenericHead extends TileEntitySpecialRenderer<TileEntityHead> {

	public static HashMap<HeadTypes, ModelBase> modelMap = new HashMap<HeadTypes, ModelBase>();

	@Override
	public void render(TileEntityHead te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		IBlockState iblockstate = te.getWorld().getBlockState(te.getPos());
		if (iblockstate == null) {
			return;
		}
		boolean flag = iblockstate.getBlock() instanceof BlockAnimalSkull;
		EnumFacing enumfacing = flag ? iblockstate.getValue(BlockAnimalSkull.FACING) : null;
		float rotation = -enumfacing.getHorizontalAngle();
		rotation = (enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH) ? enumfacing.getOpposite().getHorizontalAngle() : rotation;
		rotation = (enumfacing == EnumFacing.UP) ? te.getSkullRotation() : rotation;

		ModelBase model = modelMap.get(te.type);
		if (model == null) {
			ModelBase newModel = te.getModel();
			modelMap.put(te.type, newModel);
			model = newModel;
		}

		this.render((float) x, (float) y, (float) z, enumfacing, rotation, destroyStage, te.getTexture(), model, te.getOffset(), te.type);
	}

	public void render(float x, float y, float z, @Nullable EnumFacing facing, float skullRotation, int destroyStage, ResourceLocation texture, ModelBase model, float yOffset, HeadTypes type) {
		if (destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 2.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			this.bindTexture(texture);
		}

		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.translateHead(x, y, z, facing, 1.5F + yOffset, type);

		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		GlStateManager.enableAlpha();
		float rotX = 0F;
		if (facing != null) {
			rotX = facing == EnumFacing.UP ? -90F : 0.0F;
		}
		model.render((Entity) null, skullRotation, rotX, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}

	}

	private void translateHead(float x, float y, float z, EnumFacing face, float yOffset, HeadTypes type) {
		if (face == null) {
			GlStateManager.translate(x + 0.5F, y + 0.25F + yOffset + 0.3F, z + 1.0F);
			return;
		}
		switch (face) {
			case NORTH:
				GlStateManager.translate(x + 0.5F, y + 0.25F + yOffset + 0.3F, z + (type == HeadTypes.HELLHOUND || type == HeadTypes.ALPHAHELLHOUND ? 0.75F : 1.0F));
				break;
			case EAST:
				GlStateManager.translate(x + (type == HeadTypes.HELLHOUND || type == HeadTypes.ALPHAHELLHOUND ? 0.25F : 0.0F), y + 0.25F + yOffset + 0.3F, z + 0.5F);
				break;
			case SOUTH:
				GlStateManager.translate(x + 0.5F, y + 0.25F + yOffset + 0.3F, z + (type == HeadTypes.HELLHOUND || type == HeadTypes.ALPHAHELLHOUND ? 0.25F : 0.0F));
				break;
			case WEST:
				GlStateManager.translate(x + (type == HeadTypes.HELLHOUND || type == HeadTypes.ALPHAHELLHOUND ? 0.75F : 1.0F), y + 0.25F + yOffset + 0.3F, z + 0.5F);
				break;
			case UP:
				GlStateManager.translate(x + 0.5F, y + 0.18F + yOffset + 0.0F, z + 0.5F);
				break;
			default:
				GlStateManager.translate(x + 0.0F, y + 0.25F + yOffset + 0.3F, z + 0.5F);
				break;
		}
	}

}