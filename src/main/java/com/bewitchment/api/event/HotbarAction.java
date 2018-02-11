package com.bewitchment.api.event;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class HotbarAction {

	private static final ArrayList<HotbarAction> ACTIONS = new ArrayList<>();

	private ResourceLocation name;
	private ResourceLocation DEFAULT_ICON_TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/abilities.png");
	private int xIconIndex = 0, yIconIndex = 0;

	public HotbarAction(ResourceLocation name) {
		this.name = name;
		ACTIONS.add(this);
	}

	public static void refreshActions(EntityPlayer player, World world) {
		HotbarActionCollectionEvent evt = new HotbarActionCollectionEvent(player, world);
		MinecraftForge.EVENT_BUS.post(evt);
		player.getCapability(CapabilityTransformationData.CAPABILITY, null).loadAvailableHotbarActions(evt.getList());
	}

	public static HotbarAction getFromRegistryName(String name) {
		for (HotbarAction act : ACTIONS) {
			if (act.getName().toString().equals(name))
				return act;
		}
		throw new RuntimeException(name + " was never created as an HotbarAction");
	}

	public ResourceLocation getName() {
		return name;
	}

	@SideOnly(Side.CLIENT)
	public int getIconIndexX(EntityPlayer player) {
		return xIconIndex;
	}

	@SideOnly(Side.CLIENT)
	public int getIconIndexY(EntityPlayer player) {
		return yIconIndex;
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getIcon(EntityPlayer player) {
		return DEFAULT_ICON_TEXTURE;
	}

	public HotbarAction setIconIndexes(int x, int y) {
		this.xIconIndex = x;
		this.yIconIndex = y;
		return this;
	}

}
