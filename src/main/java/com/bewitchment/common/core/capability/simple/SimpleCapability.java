package com.bewitchment.common.core.capability.simple;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class SimpleCapability {
	
	private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<Class, Pair<Reader, Writer>>();
	private static final HashMap<Class, Field[]> fieldCache = new HashMap<Class, Field[]>();
	
	static {
		map(byte.class, SimpleCapability::readByte, SimpleCapability::writeByte);
		map(short.class, SimpleCapability::readShort, SimpleCapability::writeShort);
		map(int.class, SimpleCapability::readInt, SimpleCapability::writeInt);
		map(long.class, SimpleCapability::readLong, SimpleCapability::writeLong);
		map(float.class, SimpleCapability::readFloat, SimpleCapability::writeFloat);
		map(double.class, SimpleCapability::readDouble, SimpleCapability::writeDouble);
		map(boolean.class, SimpleCapability::readBoolean, SimpleCapability::writeBoolean);
		map(char.class, SimpleCapability::readChar, SimpleCapability::writeChar);
		map(String.class, SimpleCapability::readString, SimpleCapability::writeString);
		map(NBTTagCompound.class, SimpleCapability::readNBT, SimpleCapability::writeNBT);
		map(ItemStack.class, SimpleCapability::readItemStack, SimpleCapability::writeItemStack);
		map(BlockPos.class, SimpleCapability::readPos, SimpleCapability::writePos);
		map(UUID.class, SimpleCapability::readUUID, SimpleCapability::writeUUID);
		map(Vec3d.class, SimpleCapability::readVec3d, SimpleCapability::writeVec3d);
	}
	
	public static <C extends SimpleCapability> void preInit(Class<C> capabilityClass, String modId, Capability<C> capabilityObj, C capInstance) {
		CapabilityManager.INSTANCE.register(capabilityClass, new SimpleStorage<C>(), () -> capabilityClass.newInstance());
	}
	
	public static <C extends SimpleCapability> void init(Class<C> capabilityClass, String modId, Capability<C> capabilityObj, C capInstance) {
		MinecraftForge.EVENT_BUS.register(new CapabilityAttacher<C>(modId + capabilityClass.getName(), capabilityObj, capInstance));
	}
	
	private static Vec3d readVec3d(NBTTagCompound tag, String name) {
		return new Vec3d(tag.getCompoundTag(name).getDouble("x"), tag.getCompoundTag(name).getDouble("y"), tag.getCompoundTag(name).getDouble("z"));
	}
	
	private static void writeVec3d(Vec3d bt, NBTTagCompound tag, String name) {
		NBTTagCompound tag_pos = new NBTTagCompound();
		tag_pos.setDouble("x", bt.x);
		tag_pos.setDouble("y", bt.y);
		tag_pos.setDouble("z", bt.z);
		tag.setTag(name, tag_pos);
	}
	
	private static UUID readUUID(NBTTagCompound tag, String name) {
		return new UUID(tag.getCompoundTag(name).getLong("msb"), tag.getCompoundTag(name).getLong("lsb"));
	}
	
	private static void writeUUID(UUID bt, NBTTagCompound tag, String name) {
		NBTTagCompound tag_uuid = new NBTTagCompound();
		tag_uuid.setLong("msb", bt.getMostSignificantBits());
		tag_uuid.setLong("lsb", bt.getLeastSignificantBits());
		tag.setTag(name, tag_uuid);
	}
	
	private static BlockPos readPos(NBTTagCompound tag, String name) {
		return new BlockPos(tag.getCompoundTag(name).getInteger("x"), tag.getCompoundTag(name).getInteger("y"), tag.getCompoundTag(name).getInteger("z"));
	}
	
	private static void writePos(BlockPos bt, NBTTagCompound tag, String name) {
		NBTTagCompound tag_pos = new NBTTagCompound();
		tag_pos.setInteger("x", bt.getX());
		tag_pos.setInteger("y", bt.getY());
		tag_pos.setInteger("z", bt.getZ());
		tag.setTag(name, tag_pos);
	}
	
	private static ItemStack readItemStack(NBTTagCompound tag, String name) {
		return new ItemStack(tag.getCompoundTag(name));
	}
	
	private static void writeItemStack(ItemStack bt, NBTTagCompound tag, String name) {
		tag.setTag(name, bt.serializeNBT());
	}
	
	private static NBTTagCompound readNBT(NBTTagCompound tag, String name) {
		return tag.getCompoundTag(name);
	}
	
	private static void writeNBT(NBTTagCompound bt, NBTTagCompound tag, String name) {
		tag.setTag(name, bt);
	}
	
	private static String readString(NBTTagCompound tag, String name) {
		return tag.getString(name);
	}
	
	private static void writeString(String bt, NBTTagCompound tag, String name) {
		tag.setString(name, bt);
	}
	
	private static char readChar(NBTTagCompound tag, String name) {
		return (char) tag.getInteger(name);
	}
	
	private static void writeChar(char bt, NBTTagCompound tag, String name) {
		tag.setInteger(name, bt);
	}
	
	private static byte readByte(NBTTagCompound tag, String name) {
		return tag.getByte(name);
	}
	
	private static void writeByte(byte bt, NBTTagCompound tag, String name) {
		tag.setByte(name, bt);
	}
	
	private static short readShort(NBTTagCompound tag, String name) {
		return tag.getShort(name);
	}
	
	private static void writeShort(short bt, NBTTagCompound tag, String name) {
		tag.setShort(name, bt);
	}
	
	private static int readInt(NBTTagCompound tag, String name) {
		return tag.getInteger(name);
	}
	
	private static void writeInt(int bt, NBTTagCompound tag, String name) {
		tag.setInteger(name, bt);
	}
	
	private static long readLong(NBTTagCompound tag, String name) {
		return tag.getLong(name);
	}
	
	private static void writeLong(long bt, NBTTagCompound tag, String name) {
		tag.setLong(name, bt);
	}
	
	private static float readFloat(NBTTagCompound tag, String name) {
		return tag.getFloat(name);
	}
	
	private static void writeFloat(float bt, NBTTagCompound tag, String name) {
		tag.setFloat(name, bt);
	}
	
	private static double readDouble(NBTTagCompound tag, String name) {
		return tag.getDouble(name);
	}
	
	private static void writeDouble(double bt, NBTTagCompound tag, String name) {
		tag.setDouble(name, bt);
	}
	
	private static boolean readBoolean(NBTTagCompound tag, String name) {
		return tag.getBoolean(name);
	}
	
	private static void writeBoolean(boolean bt, NBTTagCompound tag, String name) {
		tag.setBoolean(name, bt);
	}
	
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
		if (pair == null) {
			throw new RuntimeException("No R/W handler for  " + clazz);
		}
		return pair;
	}
	
	private static boolean acceptField(Field f, Class<?> type) {
		int mods = f.getModifiers();
		if (Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) {
			return false;
		}
		return handlers.containsKey(type);
	}
	
	private static boolean syncField(Field f, Class<?> type) {
		return acceptField(f, type) && !Modifier.isVolatile(f.getModifiers());
	}
	
	private static <T extends Object> void map(Class<T> type, Reader<T> reader, Writer<T> writer) {
		handlers.put(type, Pair.of(reader, writer));
	}
	
	private final void writeField(Field f, Class clazz, NBTTagCompound buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		handler.getRight().write(f.get(this), buf, f.getName());
	}
	
	private final void readField(Field f, Class clazz, NBTTagCompound buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		f.set(this, handler.getLeft().read(buf, f.getName()));
	}
	
	public static interface Writer<T extends Object> {
		public void write(T t, NBTTagCompound buf, String field);
	}
	
	public static interface Reader<T extends Object> {
		public T read(NBTTagCompound buf, String name);
	}
	
	public final NBTBase serialize(NBTTagCompound tag) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (acceptField(f, type)) {
					writeField(f, type, tag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error at serializing capability " + this, e);
		}
		return tag;
	}
	
	public final void deserialize(NBTTagCompound tag) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (acceptField(f, type)) {
					readField(f, type, tag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error at deserializing capability " + this, e);
		}
	}
	
	public final void readSyncNBT(NBTTagCompound tag) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (syncField(f, type)) {
					readField(f, type, tag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("[Cap-Syn] Error at deserializing capability " + this, e);
		}
	}
	
	public final void writeSyncNBT(NBTTagCompound tag) {
		try {
			Class<?> clazz = getClass();
			Field[] clFields = getClassFields(clazz);
			for (Field f : clFields) {
				Class<?> type = f.getType();
				if (syncField(f, type)) {
					writeField(f, type, tag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("[Cap-Syn] Error at serializing capability " + this, e);
		}
	}
	
	public abstract boolean isRelevantFor(Entity object);
	
	public static class SimpleProvider<T extends SimpleCapability> implements ICapabilitySerializable<NBTBase> {
		
		private Capability<T> capability;
		private T deafault_instance;
		
		public SimpleProvider(Capability<T> capability, T default_instance) {
			this.capability = capability;
			this.deafault_instance = default_instance;
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == this.capability;
		}
		
		@Override
		public <V> V getCapability(Capability<V> capability, EnumFacing facing) {
			if (capability == this.capability) {
				return this.capability.cast(this.deafault_instance);
			}
			return null;
		}
		
		@Override
		public NBTBase serializeNBT() {
			return this.capability.getStorage().writeNBT(this.capability, this.deafault_instance, null);
		}
		
		@Override
		public void deserializeNBT(NBTBase nbt) {
			this.capability.getStorage().readNBT(this.capability, this.deafault_instance, null, nbt);
		}
	}
	
	public static class SimpleStorage<C extends SimpleCapability> implements IStorage<C> {
		
		@Override
		public NBTBase writeNBT(Capability<C> capability, C instance, EnumFacing side) {
			return instance.serialize(new NBTTagCompound());
		}
		
		@Override
		public void readNBT(Capability<C> capability, C instance, EnumFacing side, NBTBase nbt) {
			instance.deserialize((NBTTagCompound) nbt);
		}
		
	}
	
	public static class CapabilityAttacher<C extends SimpleCapability> {
		
		private final ResourceLocation name;
		private Capability<C> capability;
		private C default_instance;
		
		public CapabilityAttacher(String name, Capability<C> capability, C default_instance) {
			this.name = new ResourceLocation(name);
			this.capability = capability;
			this.default_instance = default_instance;
		}
		
		@SubscribeEvent
		public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> evt) {
			if (default_instance.isRelevantFor(evt.getObject())) {
				evt.addCapability(name, new SimpleProvider<C>(capability, default_instance));
			}
		}
	}
	
}
