package com.bewitchment.client.render.entity.model;

import net.minecraft.client.model.ModelRenderer;

public class ModelHelper {
	public static void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
