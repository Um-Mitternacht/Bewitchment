package com.witchcraft.client.render.baubles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Zombie by Unknown
 */
@SideOnly(Side.CLIENT)
public class ModelHerbPouch extends ModelBase {

	private ModelRenderer Cuboid;
	private ModelRenderer Cuboid2;
	private ModelRenderer Cuboid3;
	private ModelRenderer Cuboid4;
	private ModelRenderer Cuboid5;

	public ModelHerbPouch() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.Cuboid = new ModelRenderer(this, 0, 4);
		this.Cuboid.setRotationPoint(-4.0F, 12.0F, -2.5F);
		this.Cuboid.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1);
		this.Cuboid2 = new ModelRenderer(this, 0, 8);
		this.Cuboid2.setRotationPoint(-3.75F, 12.0F, -2.5F);
		this.Cuboid2.addBox(0.0F, 0.0F, -3.0F, 8, 3, 3);
		this.Cuboid3 = new ModelRenderer(this, 0, 14);
		this.Cuboid3.setRotationPoint(-4.0F, 11.5F, -4.0F);
		this.Cuboid3.addBox(0.0F, 0.0F, 0.0F, 9, 1, 4);
		this.Cuboid4 = new ModelRenderer(this, 0, 0);
		this.Cuboid4.setRotationPoint(-4.0F, 12.0F, -4.5F);
		this.Cuboid4.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1);
		this.Cuboid5 = new ModelRenderer(this, 0, 15);
		this.Cuboid5.setRotationPoint(-2.25F, 13.1F, -5.2F);
		this.Cuboid5.addBox(0.0F, 0.0F, 0.6F, 1, 1, 1);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
		this.Cuboid.render(scale);
		this.Cuboid2.render(scale);
		this.Cuboid3.render(scale);
		this.Cuboid4.render(scale);
		this.Cuboid5.render(scale);
	}

	private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
