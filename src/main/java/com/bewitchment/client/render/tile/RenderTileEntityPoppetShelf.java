package com.bewitchment.client.render.tile;

import com.bewitchment.common.block.tile.entity.TileEntityPoppetShelf;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RenderTileEntityPoppetShelf extends TileEntitySpecialRenderer<TileEntityPoppetShelf> {

    @Override
    public void render(TileEntityPoppetShelf te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            if (!handler.getStackInSlot(slot).isEmpty())
                renderPoppetInSlot(handler.getStackInSlot(slot), te.getPos(), slot, x, y, z);
        }
    }

    private void renderPoppetInSlot(ItemStack poppet, BlockPos pos, int slot, double x, double y, double z) {
        IBlockState shelf = Minecraft.getMinecraft().world.getBlockState(pos);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        switch (shelf.getValue(BlockHorizontal.FACING).getHorizontalIndex()) {
            case 0: //South
                switch (slot) {
                    case 0:
                        GlStateManager.translate(3 / 16d, 13 / 16d, 0.2);
                        break;
                    case 1:
                        GlStateManager.translate(8 / 16d, 13 / 16d, 0.2);
                        break;
                    case 2:
                        GlStateManager.translate(13 / 16d, 13 / 16d, 0.2);
                        break;
                    case 3:
                        GlStateManager.translate(3 / 16d, 0.5, 0.2);
                        break;
                    case 4:
                        GlStateManager.translate(8 / 16d, 0.5, 0.2);
                        break;
                    case 5:
                        GlStateManager.translate(13 / 16d, 0.5, 0.2);
                        break;
                    case 6:
                        GlStateManager.translate(3 / 16d, 3 / 16d, 0.2);
                        break;
                    case 7:
                        GlStateManager.translate(8 / 16d, 3 / 16d, 0.2);
                        break;
                    case 8:
                        GlStateManager.translate(13 / 16d, 3 / 16d, 0.2);
                        break;
                }
                break;
            case 1: //West
                GlStateManager.rotate(90, 0, 1, 0);
                switch (slot) {
                    case 0:
                        GlStateManager.translate(-3 / 16d, 13 / 16d, 0.8);
                        break;
                    case 1:
                        GlStateManager.translate(-8 / 16d, 13 / 16d, 0.8);
                        break;
                    case 2:
                        GlStateManager.translate(-13 / 16d, 13 / 16d, 0.8);
                        break;
                    case 3:
                        GlStateManager.translate(-3 / 16d, 0.5, 0.8);
                        break;
                    case 4:
                        GlStateManager.translate(-8 / 16d, 0.5, 0.8);
                        break;
                    case 5:
                        GlStateManager.translate(-13 / 16d, 0.5, 0.8);
                        break;
                    case 6:
                        GlStateManager.translate(-3 / 16d, 3 / 16d, 0.8);
                        break;
                    case 7:
                        GlStateManager.translate(-8 / 16d, 3 / 16d, 0.8);
                        break;
                    case 8:
                        GlStateManager.translate(-13 / 16d, 3 / 16d, 0.8);
                        break;
                }
                break;
            case 2: //North
                switch (slot) {
                    case 0:
                        GlStateManager.translate(13 / 16d, 13 / 16d, 0.8);
                        break;
                    case 1:
                        GlStateManager.translate(8 / 16d, 13 / 16d, 0.8);
                        break;
                    case 2:
                        GlStateManager.translate(3 / 16d, 13 / 16d, 0.8);
                        break;
                    case 3:
                        GlStateManager.translate(13 / 16d, 0.5, 0.8);
                        break;
                    case 4:
                        GlStateManager.translate(8 / 16d, 0.5, 0.8);
                        break;
                    case 5:
                        GlStateManager.translate(3 / 16d, 0.5, 0.8);
                        break;
                    case 6:
                        GlStateManager.translate(13 / 16d, 3 / 16d, 0.8);
                        break;
                    case 7:
                        GlStateManager.translate(8 / 16d, 3 / 16d, 0.8);
                        break;
                    case 8:
                        GlStateManager.translate(3 / 16d, 3 / 16d, 0.8);
                        break;
                }
                break;
            case 3: //East
                GlStateManager.rotate(90, 0, 1, 0);
                switch (slot) {
                    case 0:
                        GlStateManager.translate(-13 / 16d, 13 / 16d, 0.2);
                        break;
                    case 1:
                        GlStateManager.translate(-8 / 16d, 13 / 16d, 0.2);
                        break;
                    case 2:
                        GlStateManager.translate(-3 / 16d, 13 / 16d, 0.2);
                        break;
                    case 3:
                        GlStateManager.translate(-13 / 16d, 0.5, 0.2);
                        break;
                    case 4:
                        GlStateManager.translate(-8 / 16d, 0.5, 0.2);
                        break;
                    case 5:
                        GlStateManager.translate(-3 / 16d, 0.5, 0.2);
                        break;
                    case 6:
                        GlStateManager.translate(-13 / 16d, 3 / 16d, 0.2);
                        break;
                    case 7:
                        GlStateManager.translate(-8 / 16d, 3 / 16d, 0.2);
                        break;
                    case 8:
                        GlStateManager.translate(-3 / 16d, 3 / 16d, 0.2);
                        break;
                }
        }
        GlStateManager.scale(0.25, 0.25, 0.25);
        Minecraft.getMinecraft().getRenderItem().renderItem(poppet, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
