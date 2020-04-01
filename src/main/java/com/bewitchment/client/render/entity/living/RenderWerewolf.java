package com.bewitchment.client.render.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.entity.living.ModelWerewolf;
import com.bewitchment.common.entity.living.EntityWerewolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/31/2020.
 */
@SideOnly(Side.CLIENT)
public class RenderWerewolf extends RenderLiving<EntityWerewolf> {
	private static final ResourceLocation ARCTIC = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_arctic.png");
	private static final ResourceLocation BLACK = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_black.png");
	private static final ResourceLocation BROWN = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_brown.png");
	private static final ResourceLocation GREY = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_grey.png");
	private static final ResourceLocation RED = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_red.png");
	private static final ResourceLocation TIMBER = new ResourceLocation(Bewitchment.MODID, "textures/entity/werewolf/werewolf_timber.png");
	
	public RenderWerewolf(RenderManager manager) {
		super(manager, new ModelWerewolf(), 0.3f);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityWerewolf entity) {
		switch (entity.getWerewolfType()) {
			case 0:
			default:
				return BROWN;
			case 1:
				return ARCTIC;
			case 2:
				return BLACK;
			case 3:
				return RED;
			case 4:
				return TIMBER;
			case 5:
				return GREY;
		}
	}
}
