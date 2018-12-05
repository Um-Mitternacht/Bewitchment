/*
 * Slightly modified version of https://gist.github.com/Vazkii/13e0ce45577bbae49362, by Vazkii
 */

package com.bewitchment.common.core.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("rawtypes")
public class SimpleMessage<REQ extends SimpleMessage> implements IMessage, IMessageHandler<REQ, IMessage> {

	private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<Class, Pair<Reader, Writer>>();
	private static final HashMap<Class, Field[]> fieldCache = new HashMap<Class, Field[]>();

	static {
		map(byte.class, SimpleMessage::readByte, SimpleMessage::writeByte);
		map(short.class, SimpleMessage::readShort, SimpleMessage::writeShort);
		map(int.class, SimpleMessage::readInt, SimpleMessage::writeInt);
		map(long.class, SimpleMessage::readLong, SimpleMessage::writeLong);
		map(float.class, SimpleMessage::readFloat, SimpleMessage::writeFloat);
		map(double.class, SimpleMessage::readDouble, SimpleMessage::writeDouble);
		map(boolean.class, SimpleMessage::readBoolean, SimpleMessage::writeBoolean);
		map(char.class, SimpleMessage::readChar, SimpleMessage::writeChar);
		map(String.class, SimpleMessage::readString, SimpleMessage::writeString);
		map(NBTTagCompound.class, SimpleMessage::readNBT, SimpleMessage::writeNBT);
		map(ItemStack.class, SimpleMessage::readItemStack, SimpleMessage::writeItemStack);
		map(BlockPos.class, SimpleMessage::readBlockPos, SimpleMessage::writeBlockPos);
		map(UUID.class, SimpleMessage::readUUID, SimpleMessage::writeUUID);
		map(Vec3d.class, SimpleMessage::readVec3d, SimpleMessage::writeVec3d);
		map(ResourceLocation.class, SimpleMessage::readResourceLocation, SimpleMessage::writeResourceLocation);
	}

	@SuppressWarnings("unlikely-arg-type")
	private static Field[] getClassFields(Class<?> clazz) {
		if (fieldCache.containsValue(clazz)) {
			return fieldCache.get(clazz);
		}
		Field[] fields = clazz.getFields();
		Arrays.sort(fields, (Field f1, Field f2) -> {
			return f1.getName().compareTo(f2.getName());
		});
		fieldCache.put(clazz, fields);
		return fields;
	}

	private static Pair<Reader, Writer> getHandler(Class<?> clazz) {
		Pair<Reader, Writer> pair = handlers.get(clazz);
		if (pair == null)
			throw new RuntimeException("No R/W handler for  " + clazz);
		return pair;
	}

	private static boolean acceptField(Field f, Class<?> type) {
		int mods = f.getModifiers();
		if (Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods))
			return false;

		return handlers.containsKey(type);
	}

	private static <T extends Object> void map(Class<T> type, Reader<T> reader, Writer<T> writer) {
		handlers.put(type, Pair.of(reader, writer));
	}

	public static byte readByte(ByteBuf buf) {
		return buf.readByte();
	}

	public static void writeByte(byte b, ByteBuf buf) {
		buf.writeByte(b);
	}

	public static short readShort(ByteBuf buf) {
		return buf.readShort();
	}

	public static void writeShort(short s, ByteBuf buf) {
		buf.writeShort(s);
	}

	public static int readInt(ByteBuf buf) {
		return buf.readInt();
	}

	public static void writeInt(int i, ByteBuf buf) {
		buf.writeInt(i);
	}

	public static long readLong(ByteBuf buf) {
		return buf.readLong();
	}

	public static void writeLong(long l, ByteBuf buf) {
		buf.writeLong(l);
	}

	public static float readFloat(ByteBuf buf) {
		return buf.readFloat();
	}

	public static void writeFloat(float f, ByteBuf buf) {
		buf.writeFloat(f);
	}

	public static double readDouble(ByteBuf buf) {
		return buf.readDouble();
	}

	public static void writeDouble(double d, ByteBuf buf) {
		buf.writeDouble(d);
	}

	public static boolean readBoolean(ByteBuf buf) {
		return buf.readBoolean();
	}

	public static void writeBoolean(boolean b, ByteBuf buf) {
		buf.writeBoolean(b);
	}

	public static void writeUUID(UUID id, ByteBuf buf) {
		if (id == null) {
			buf.writeLong(0);
			buf.writeLong(0);
		} else {
			buf.writeLong(id.getMostSignificantBits());
			buf.writeLong(id.getLeastSignificantBits());
		}
	}

	public static UUID readUUID(ByteBuf buf) {
		long msb = buf.readLong();
		long lsb = buf.readLong();
		if (msb == 0 && lsb == 0)
			return null;
		return new UUID(msb, lsb);
	}

	public static char readChar(ByteBuf buf) {
		return buf.readChar();
	}

	public static void writeChar(char c, ByteBuf buf) {
		buf.writeChar(c);
	}

	public static String readString(ByteBuf buf) {
		return ByteBufUtils.readUTF8String(buf);
	}

	public static void writeString(String s, ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, s);
	}

	public static NBTTagCompound readNBT(ByteBuf buf) {
		return ByteBufUtils.readTag(buf);
	}

	public static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {
		ByteBufUtils.writeTag(buf, cmp);
	}

	public static ItemStack readItemStack(ByteBuf buf) {
		return ByteBufUtils.readItemStack(buf);
	}

	public static void writeItemStack(ItemStack stack, ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
	}

	public static BlockPos readBlockPos(ByteBuf buf) {
		return BlockPos.fromLong(buf.readLong());
	}

	public static void writeBlockPos(BlockPos pos, ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}

	public static Vec3d readVec3d(ByteBuf buf) {
		return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	public static void writeVec3d(Vec3d vec, ByteBuf buf) {
		buf.writeDouble(vec.x);
		buf.writeDouble(vec.y);
		buf.writeDouble(vec.z);
	}

	public static void writeResourceLocation(ResourceLocation rl, ByteBuf buf) {
		writeString(rl.toString(), buf);
	}

	public static ResourceLocation readResourceLocation(ByteBuf buf) {
		return new ResourceLocation(readString(buf));
	}

	public IMessage handleMessage(MessageContext context) {
		return null;
	}

	@Override
	public final IMessage onMessage(REQ message, MessageContext context) {
		return message.handleMessage(context);
	}

	@Override
	public final void fromBytes(ByteBuf buf) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (acceptField(f, type))
					readField(f, type, buf);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error at reading packet " + this, e);
		}
	}

	@Override
	public final void toBytes(ByteBuf buf) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (acceptField(f, type))
					writeField(f, type, buf);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error at writing packet " + this, e);
		}
	}

	@SuppressWarnings("unchecked")
	private final void writeField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		handler.getRight().write(f.get(this), buf);
	}

	private final void readField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		f.set(this, handler.getLeft().read(buf));
	}

	public static interface Writer<T extends Object> {
		public void write(T t, ByteBuf buf);
	}

	public static interface Reader<T extends Object> {
		public T read(ByteBuf buf);
	}

}