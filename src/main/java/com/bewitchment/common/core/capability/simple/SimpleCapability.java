/**
 * Class created by Zabi94, May 13th, 2018
 * <p>
 * Distributed under MIT license, included in the Bewitchment mod
 */

package com.bewitchment.common.core.capability.simple;

import com.bewitchment.common.core.helper.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class SimpleCapability {

	private static final boolean sc_dbg = System.getProperty("simplecapabilitydebug") != null;

	private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<Class, Pair<Reader, Writer>>();
	private static final HashMap<Class, Field[]> fieldCache = new HashMap<Class, Field[]>();
	private static final ArrayList<Tuple<SimpleCapability, Capability<? extends SimpleCapability>>> capabilities = new ArrayList<>();
	private static int nextId = 0;
	private static SimpleNetworkWrapper net = null;

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

	@Ignore
	protected byte dirty = 0;
	@Ignore
	protected int id = -1;

	//Call this somewhere during startup, passing your mods' network handler
	public static void setup(SimpleNetworkWrapper netHandler) {
		net = netHandler;
	}

	public static <C extends SimpleCapability> void preInit(Class<C> capabilityClass) {
		Objects.requireNonNull(capabilityClass);
		CapabilityManager.INSTANCE.register(capabilityClass, new SimpleStorage<C>(), () -> capabilityClass.newInstance());

	}

	public static <C extends SimpleCapability> void init(Class<C> capabilityClass, String modId, Capability<C> capabilityObj, C capInstance) {
		Objects.requireNonNull(capabilityClass);
		Objects.requireNonNull(capabilityObj);
		Objects.requireNonNull(capInstance);
		Objects.requireNonNull(modId);
		capInstance.id = nextId++;
		capabilities.add(new Tuple<>(capInstance, capabilityObj));
		if (capabilities.get(capInstance.id).getFirst() != capInstance) {
			throw new IllegalStateException("SimpleCapability " + capabilityObj.getName() + " internal ID doesn't match the lookup id. Things won't work!");
		}
		CapabilityEventListener<C> cel = new CapabilityEventListener<C>(modId + capabilityClass.getName(), capabilityObj, capInstance);
		MinecraftForge.EVENT_BUS.register(cel);
	}

	private static Vec3d readVec3d(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name)) {
			return null;
		}
		return new Vec3d(tag.getCompoundTag(name).getDouble("x"), tag.getCompoundTag(name).getDouble("y"), tag.getCompoundTag(name).getDouble("z"));
	}

	private static void writeVec3d(Vec3d bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			NBTTagCompound tag_pos = new NBTTagCompound();
			tag_pos.setDouble("x", bt.x);
			tag_pos.setDouble("y", bt.y);
			tag_pos.setDouble("z", bt.z);
			tag.setTag(name, tag_pos);
		}
	}

	private static UUID readUUID(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name)) {
			return null;
		}
		return new UUID(tag.getCompoundTag(name).getLong("msb"), tag.getCompoundTag(name).getLong("lsb"));
	}

	private static void writeUUID(UUID bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			NBTTagCompound tag_uuid = new NBTTagCompound();
			tag_uuid.setLong("msb", bt.getMostSignificantBits());
			tag_uuid.setLong("lsb", bt.getLeastSignificantBits());
			tag.setTag(name, tag_uuid);
		}
	}

	private static BlockPos readPos(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name)) {
			return null;
		}
		return new BlockPos(tag.getCompoundTag(name).getInteger("x"), tag.getCompoundTag(name).getInteger("y"), tag.getCompoundTag(name).getInteger("z"));
	}

	private static void writePos(BlockPos bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			NBTTagCompound tag_pos = new NBTTagCompound();
			tag_pos.setInteger("x", bt.getX());
			tag_pos.setInteger("y", bt.getY());
			tag_pos.setInteger("z", bt.getZ());
			tag.setTag(name, tag_pos);
		}
	}

	private static ItemStack readItemStack(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name)) {
			return ItemStack.EMPTY;
		}
		return new ItemStack(tag.getCompoundTag(name));
	}

	private static void writeItemStack(ItemStack bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			tag.setTag(name, bt.serializeNBT());
		}
	}

	private static NBTTagCompound readNBT(NBTTagCompound tag, String name) {
		return tag.getCompoundTag(name);
	}

	private static void writeNBT(NBTTagCompound bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			tag.setTag(name, bt);
		}
	}

	private static String readString(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name)) {
			return null;
		}
		return tag.getString(name);
	}

	private static void writeString(String bt, NBTTagCompound tag, String name) {
		if (bt != null) {
			tag.setString(name, bt);
		}
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
		if (pair == null) {
			throw new RuntimeException("No R/W handler for  " + clazz);
		}
		return pair;
	}

	private static boolean acceptField(Field f, Class<?> type) {
		int mods = f.getModifiers();
		if (f.isAnnotationPresent(Ignore.class) || Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) {
			return false;
		}
		return handlers.containsKey(type);
	}

	private static boolean syncField(Field f, Class<?> type) {
		return acceptField(f, type) && !f.isAnnotationPresent(DontSync.class);
	}

	private static <T extends Object> void map(Class<T> type, Reader<T> reader, Writer<T> writer) {
		handlers.put(type, Pair.of(reader, writer));
	}

	public static void messageReceived(NBTTagCompound tag, int capabilityId, int entityID, byte dirt) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().world != null) {
				Capability capability = capabilities.get(capabilityId).getSecond();
				log("message received for  " + capability.getName() + ", entity: " + entityID);
				Entity e = Minecraft.getMinecraft().world.getEntityByID(entityID);
				try {
					log("EntityName: " + e);
				} catch (Exception ex) {
				}
				if (e != null && e.hasCapability(capability, null)) {
					((SimpleCapability) e.getCapability(capability, null)).readSyncNBT(tag);
					((SimpleCapability) e.getCapability(capability, null)).onSyncMessage(dirt);
				} else {
					log("message dropped: " + e + ", " + capability.getName());
				}
			}
		});
	}

	private static void log(Object o) {
		if (sc_dbg) {
			Log.i(o);
		}
	}

	private final void writeField(Field f, Class clazz, NBTTagCompound buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		handler.getRight().write(f.get(this), buf, f.getName());
	}

	private final void readField(Field f, Class clazz, NBTTagCompound buf) throws IllegalArgumentException, IllegalAccessException {
		Pair<Reader, Writer> handler = getHandler(clazz);
		f.set(this, handler.getLeft().read(buf, f.getName()));
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

	@SideOnly(Side.CLIENT)
	public void onSyncMessage(byte mode) {
		//NO-OP
	}

	public boolean shouldSyncToPlayersAround() {
		return true;
	}

	public boolean shouldSyncToOwnerPlayer() {
		return true;
	}

	public abstract SimpleCapability getNewInstance();

	public void markDirty(byte mode) {
		this.dirty = mode;
	}

	public static interface Writer<T extends Object> {
		public void write(T t, NBTTagCompound buf, String field);
	}

	public static interface Reader<T extends Object> {
		public T read(NBTTagCompound buf, String name);
	}

	/**
	 * All fields marked with this interface will be kept server-side only
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface DontSync {
	}

	/**
	 * All fields marked with this interface won't be serialized by the capability
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Ignore {
	}

	public static class SimpleProvider<T extends SimpleCapability> implements ICapabilitySerializable<NBTBase> {

		private Capability<T> capability;
		private T deafault_instance;

		public SimpleProvider(Capability<T> capability, T default_instance) {
			this.capability = capability;
			this.deafault_instance = default_instance;
		}

		@Override
		public boolean hasCapability(Capability<?> capabilityIn, EnumFacing facing) {
			return capabilityIn == this.capability;
		}

		@Override
		public <V> V getCapability(Capability<V> capabilityIn, EnumFacing facing) {
			if (capabilityIn == this.capability) {
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

	public static class CapabilityEventListener<C extends SimpleCapability> {

		private final ResourceLocation name;
		private final Capability<C> capability;
		private final SimpleCapability default_instance;

		public CapabilityEventListener(String name, Capability<C> capability, SimpleCapability default_instance) {
			this.name = new ResourceLocation(name);
			this.capability = capability;
			this.default_instance = default_instance;
		}


		@SubscribeEvent
		public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> evt) {
			if (default_instance.isRelevantFor(evt.getObject())) {
				log("Capability " + this.name + " attached");
				evt.addCapability(name, new SimpleProvider(capability, default_instance.getNewInstance()));
			}
		}

		@SubscribeEvent
		public void onEntityTracking(StartTracking evt) {
			if (default_instance.shouldSyncToPlayersAround()) {
				Entity e = evt.getTarget();
				log("start tracking: " + e);
				if (!e.world.isRemote && e.hasCapability(capability, null) && evt.getEntityPlayer() instanceof EntityPlayerMP) {
					log("start tracking - succeded");
					NBTTagCompound tag = new NBTTagCompound();
					e.getCapability(capability, null).writeSyncNBT(tag);
					net.sendTo(new CapabilityMessage(default_instance.id, tag, e.getEntityId(), (byte) 0), (EntityPlayerMP) evt.getEntityPlayer());
				}
			}
		}

		@SubscribeEvent
		public void onEntityUpdate(LivingUpdateEvent evt) {
			if (!evt.getEntityLiving().world.isRemote && evt.getEntityLiving().hasCapability(capability, null)) {
				C instance = evt.getEntityLiving().getCapability(capability, null);
				if (instance.dirty != 0) {
					log("Cleaning instance of " + capability.getName() + " for " + evt.getEntityLiving());
					NBTTagCompound tag = new NBTTagCompound();
					evt.getEntityLiving().getCapability(capability, null).writeSyncNBT(tag);
					CapabilityMessage msg = new CapabilityMessage(this.default_instance.id, tag, evt.getEntityLiving().getEntityId(), instance.dirty);
					if (default_instance.shouldSyncToOwnerPlayer() && evt.getEntityLiving() instanceof EntityPlayerMP) {
						net.sendTo(msg, (EntityPlayerMP) evt.getEntityLiving());
					}
					if (default_instance.shouldSyncToPlayersAround()) {
						net.sendToAllTracking(msg, evt.getEntityLiving());
					}
					instance.dirty = 0;
				}
			}
		}

		@SubscribeEvent
		public void onWorldJoin(EntityJoinWorldEvent evt) {
			if (default_instance.shouldSyncToOwnerPlayer()) {
				if (evt.getEntity() instanceof EntityPlayerMP) {
					log("onWorldJoin - player");
					EntityPlayerMP entity = (EntityPlayerMP) evt.getEntity();
					NBTTagCompound tag = new NBTTagCompound();
					evt.getEntity().getCapability(capability, null).writeSyncNBT(tag);
					net.sendTo(new CapabilityMessage(this.default_instance.id, tag, entity.getEntityId(), (byte) 0), entity);
				}
			}
		}

	}
}
