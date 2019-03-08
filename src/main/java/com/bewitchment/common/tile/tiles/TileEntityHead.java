package com.bewitchment.common.tile.tiles;

import com.bewitchment.common.block.head.HeadTypes;
import net.minecraft.client.model.ModelBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

/**
 * @author its_meow
 *         <p>
 *         Mar 7, 2019
 */
public class TileEntityHead extends TileEntity {

	public HeadTypes type = null;
	public HashMap<Integer, ResourceLocation> textures;
	protected int typeNum = 0;
	private Class<? extends ModelBase> modelT = null;
	private float offset;
	private float rotation = 0;
	private boolean shouldDrop = true;
	private Function<Integer, ResourceLocation> textureFunc = null;

	public TileEntityHead() {
	}

	public TileEntityHead(HeadTypes type, float yOffset,
	                      ResourceLocation... textureList) {
		this(type, yOffset, null, textureList);
	}

	public TileEntityHead(HeadTypes type, float yOffset, Function<Integer, ResourceLocation> textureFunc,
	                      ResourceLocation... textureList) {
		this.type = type;
		this.modelT = type.getModelSupplier().get().get();
		this.textures = new HashMap<>();
		int i = 1;
		for (ResourceLocation texture : textureList) {
			this.textures.put(i, texture);
			i++;
		}
		if (!this.getTileData().hasKey("TYPENUM")) {
			this.setType(new Random().nextInt(type.textureCount) + 1);
			this.markDirty();
		}
		this.offset = yOffset;
		this.textureFunc = textureFunc;
	}

	public static void disableDrop(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if ((te instanceof TileEntityHead)) {
			((TileEntityHead) te).shouldDrop = false;
		}
	}

	public ModelBase getModel() {
		try {
			return this.modelT.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.getPos().add(-1, -1, -1), this.getPos().add(2, 2, 2));
	}

	public ResourceLocation getTexture() {
		if (textureFunc == null) {
			return this.textures.get(this.typeNum);
		} else {
			ResourceLocation rl = textureFunc.apply(this.typeNum);
			if (rl == null || rl.toString().equals("")) {
				rl = this.textures.get(this.typeNum);
			}
			return rl;
		}
	}

	public void setType(int i) {
		this.typeNum = i;
		this.markDirty();
	}

	public int typeValue() {
		return this.typeNum;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("TYPENUM")) {
			this.typeNum = compound.getInteger("TYPENUM");
		} else {
			this.setType(new Random().nextInt(this.textures.size()) + 1);
		}

		if (compound.hasKey("rotation")) {
			this.rotation = compound.getFloat("rotation");
		}
		if (compound.hasKey("GENERIC_TYPE")) {
			this.type = HeadTypes.valueOf(compound.getString("GENERIC_TYPE"));

			// Create with proper constructor for type
			TileEntityHead te2 = type.teFactory.apply(type);
			// Copy from TE
			this.modelT = te2.modelT;
			this.textures = te2.textures;
			this.offset = te2.offset;
			this.textureFunc = te2.textureFunc;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("TYPENUM", this.typeNum);
		compound.setFloat("rotation", rotation);
		compound.setString("GENERIC_TYPE", type.name());
		return compound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
		world.scheduleUpdate(this.pos, this.blockType, 100);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public float getOffset() {
		return this.offset;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		this.markDirty();
	}

	public float getSkullRotation() {
		return this.rotation;
	}

	public boolean shouldDrop() {
		return shouldDrop;
	}

}