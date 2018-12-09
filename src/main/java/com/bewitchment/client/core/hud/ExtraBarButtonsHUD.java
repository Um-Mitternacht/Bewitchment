package com.bewitchment.client.core.hud;

import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.client.handler.Keybinds;
import com.bewitchment.common.content.actionbar.HotbarAction;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlayerUsedAbilityMessage;
import com.bewitchment.common.core.statics.ModConfig;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ExtraBarButtonsHUD extends HudComponent {

	public static final ResourceLocation TEXTURE = new ResourceLocation(LibMod.MOD_ID, "textures/gui/ability_selector.png");
	public static final ExtraBarButtonsHUD INSTANCE = new ExtraBarButtonsHUD();
	private static final IHotbarAction arrows = new IHotbarAction() {

		@Override
		public ResourceLocation getName() {
			return null;
		}

		@Override
		public int getIconIndexY() {
			return 3;
		}

		@Override
		public int getIconIndexX() {
			return 3;
		}

		@Override
		public ResourceLocation getIcon() {
			return HotbarAction.DEFAULT_ICON_TEXTURE;
		}
	};
	public IHotbarAction[] actionScroller = new IHotbarAction[3];// 0: current, 1: prev, 2: next
	// TODO reset these when the user closes the game, either MP or SP
	int slotSelected = -1;
	int cooldown = 0;
	int selectedItemTemp = 0;
	List<IHotbarAction> actions = new ArrayList<IHotbarAction>();
	private boolean isInExtraBar = false;

	private ExtraBarButtonsHUD() {
		super(70, 16);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public boolean isInExtraBar() {
		return isInExtraBar;
	}

	@SubscribeEvent
	public void RMBHijacker(MouseEvent evt) {
		if (evt.isButtonstate() && slotSelected >= 0 && evt.getButton() == 1) {
			evt.setCanceled(true);
			if (cooldown == 0) {
				int e_id = -1;
				if (Minecraft.getMinecraft().pointedEntity != null) {
					e_id = Minecraft.getMinecraft().pointedEntity.getEntityId();
				}
				NetworkHandler.HANDLER.sendToServer(new PlayerUsedAbilityMessage(actions.get(slotSelected).getName().toString(), e_id));
				cooldown = 5;
			}
		}
	}

	@SubscribeEvent
	public void handRender(RenderHandEvent evt) {
		if (isInExtraBar && ModConfig.CLIENT.ACTION_BAR_HUD.hideHandWithAbility) {
			evt.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent evt) {
		if (cooldown > 0) {
			cooldown--;
		}
	}

	@SubscribeEvent
	public void scrollWheelHijacker(MouseEvent evt) {
		int dir = evt.getDwheel() == 0 ? 0 : evt.getDwheel() > 0 ? -1 : 1;
		if (dir == 0 || (!Minecraft.getMinecraft().player.isSneaking() && !ModConfig.CLIENT.ACTION_BAR_HUD.autoJumpToBar && !isInExtraBar))
			return;
		int curItm = Minecraft.getMinecraft().player.inventory.currentItem;
		int max = actions.size();
		if (Minecraft.getMinecraft().currentScreen == null) {// Don't mess with scroll wheels if a gui is open, only when playing
			IHotbarAction lastSelected = refreshSelected();
			evt.setCanceled(isInExtraBar || (dir < 0 && slotSelected == 0) || (dir > 0 && curItm == 8 && max > 0));
			if (evt.isCanceled()) {
				if (dir > 0) {
					if (max > 0) {
						isInExtraBar = true;
						slotSelected++;
						if (slotSelected >= max) {
							slotSelected = max - 1;
						}
					}
				} else if (dir < 0) {
					slotSelected--;
					if (slotSelected < 0) {
						isInExtraBar = false;
					}
				}
			}
			if (slotSelected < -1) {
				slotSelected = -1;
			}
			IHotbarAction current = refreshSelected();
			if (current != null && current != lastSelected) {
				Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentTranslation(current.getName().getNamespace() + "." + current.getName().getPath()), true);
			}
		}
	}

	private IHotbarAction refreshSelected() {
		if (slotSelected >= 0) {
			actionScroller[0] = actions.get(slotSelected);
		} else {
			actionScroller = new HotbarAction[3];
			if (actions.size() > 0) {
				actionScroller[0] = actions.get(0);
				if (actions.size() > 1) {
					actionScroller[2] = actions.get(1);
				}
			}
			return null;
		}
		if (slotSelected > 0) {
			actionScroller[1] = actions.get(slotSelected - 1);
		} else {
			actionScroller[1] = null;
		}
		if (slotSelected + 1 < actions.size()) {
			actionScroller[2] = actions.get(slotSelected + 1);
		} else {
			actionScroller[2] = null;
		}
		return actionScroller[0];
	}

	public void setList(List<IHotbarAction> list) {
		this.actions = list;
		this.slotSelected = Math.min(slotSelected, list.size() - 1);
		refreshSelected();
	}

	@SubscribeEvent
	public void keybordInput(KeyInputEvent evt) {
		if (Keybinds.gotoExtraBar.isPressed()) {
			if (actions.size() > 0) {
				int lastSelected = slotSelected;
				slotSelected = 0;
				isInExtraBar = true;
				IHotbarAction current = refreshSelected();
				if (current != null && lastSelected != slotSelected) {
					Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentTranslation(current.getName().getNamespace() + "." + current.getName().getPath()), true);
				}
			}
		}
		if (Keybinds.alwaysEnableBar.isPressed()) {
			ModConfig.CLIENT.ACTION_BAR_HUD.autoJumpToBar = !ModConfig.CLIENT.ACTION_BAR_HUD.autoJumpToBar;
			ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1) || Keyboard.isKeyDown(Keyboard.KEY_2) || Keyboard.isKeyDown(Keyboard.KEY_3) || Keyboard.isKeyDown(Keyboard.KEY_4) || Keyboard.isKeyDown(Keyboard.KEY_5) || Keyboard.isKeyDown(Keyboard.KEY_6) || Keyboard.isKeyDown(Keyboard.KEY_7) || Keyboard.isKeyDown(Keyboard.KEY_8) || Keyboard.isKeyDown(Keyboard.KEY_9)) {
			slotSelected = -1;
			isInExtraBar = false;
			refreshSelected();
		}
	}

	@SubscribeEvent
	public void restoreVanillaItemSelector(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {
			Minecraft.getMinecraft().player.inventory.currentItem = selectedItemTemp;
		}
	}

	@SubscribeEvent
	public void removeVanillaItemSelector(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && isInExtraBar) {
			selectedItemTemp = Minecraft.getMinecraft().player.inventory.currentItem;
			Minecraft.getMinecraft().player.inventory.currentItem = 100;// Render overlay to the right (increase to something like 100 to make it disappear, if we decide to use a custom selection indicator)
		}
	}

	@Override
	public boolean isActive() {
		return !ModConfig.CLIENT.ACTION_BAR_HUD.deactivate;
	}

	@Override
	public void setHidden(boolean hidden) {
		ModConfig.CLIENT.ACTION_BAR_HUD.deactivate = hidden;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public double getX() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.ACTION_BAR_HUD.h_anchor.dataToPixel(ModConfig.CLIENT.ACTION_BAR_HUD.x, getWidth(), sr.getScaledWidth());
	}

	@Override
	public double getY() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		return ModConfig.CLIENT.ACTION_BAR_HUD.v_anchor.dataToPixel(ModConfig.CLIENT.ACTION_BAR_HUD.y, getHeight(), sr.getScaledHeight());
	}

	@Override
	public void setRelativePosition(double x, double y, EnumHudAnchor horizontal, EnumHudAnchor vertical) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		ModConfig.CLIENT.ACTION_BAR_HUD.v_anchor = vertical;
		ModConfig.CLIENT.ACTION_BAR_HUD.h_anchor = horizontal;
		ModConfig.CLIENT.ACTION_BAR_HUD.x = horizontal.pixelToData(x, getWidth(), sr.getScaledWidth());
		ModConfig.CLIENT.ACTION_BAR_HUD.y = vertical.pixelToData(y, getHeight(), sr.getScaledHeight());
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public void resetConfig() {
		ModConfig.CLIENT.ACTION_BAR_HUD.v_anchor = EnumHudAnchor.END_ABSOLUTE;
		ModConfig.CLIENT.ACTION_BAR_HUD.h_anchor = EnumHudAnchor.CENTER_ABSOLUTE;
		ModConfig.CLIENT.ACTION_BAR_HUD.x = 130;
		ModConfig.CLIENT.ACTION_BAR_HUD.y = 2;
		ModConfig.CLIENT.ACTION_BAR_HUD.deactivate = false;
		ConfigManager.sync(LibMod.MOD_ID, Type.INSTANCE);
	}

	@Override
	public EnumHudAnchor getAnchorHorizontal() {
		return ModConfig.CLIENT.ACTION_BAR_HUD.h_anchor;
	}

	@Override
	public EnumHudAnchor getAnchorVertical() {
		return ModConfig.CLIENT.ACTION_BAR_HUD.v_anchor;
	}

	@Override
	public String getTooltip(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public void onClick(int mouseX, int mouseY) {
	}

	@Override
	public void render(ScaledResolution sr, float partialTicks, boolean renderDummy) {
		if (renderDummy) {
			arrows.render(getX(), getY(), 16, 16, 1f);
			ModAbilities.DRAIN_BLOOD.render(getX() + 18, getY(), 16, 16, 0.4f);
			ModAbilities.BAT_SWARM.render(getX() + 36, getY(), 16, 16, 1f);
			ModAbilities.HOWL.render(getX() + 54, getY(), 16, 16, 0.4f);
			renderSelectionBox(getX() + 35, getY() - 1);
		} else {
			if (actionScroller[0] != null) {
				actionScroller[0].render(getX() + 36, getY(), 16, 16, slotSelected < 0 ? 0.4f : 1f);
				if (isInExtraBar) {
					renderSelectionBox(getX() + 35, getY() - 1);
				}
			}
			if (actionScroller[2] != null) {
				actionScroller[2].render(getX() + 54, getY(), 16, 16, 0.4f);
			}
			if (actionScroller[1] != null) {
				actionScroller[1].render(getX() + 18, getY(), 16, 16, 0.4f);
			}
			if (slotSelected < 0 && actions.size() > 0 && ModConfig.CLIENT.ACTION_BAR_HUD.showArrowsInBar) {
				arrows.render(getX(), getY(), 16, 16, ((Minecraft.getMinecraft().player.isSneaking() || ModConfig.CLIENT.ACTION_BAR_HUD.autoJumpToBar) ? 1 : 0.2f));
			}
		}
	}

	private void renderSelectionBox(double x, double y) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buff = tessellator.getBuffer();

		buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buff.pos(x, y + 18, 0).tex(0, 1).endVertex();
		buff.pos(x + 18, y + 18, 0).tex(1, 1).endVertex();
		buff.pos(x + 18, y, 0).tex(1, 0).endVertex();
		buff.pos(x, y, 0).tex(0, 0).endVertex();

		tessellator.draw();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}
