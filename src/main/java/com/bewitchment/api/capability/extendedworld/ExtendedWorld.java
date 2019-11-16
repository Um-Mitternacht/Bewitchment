package com.bewitchment.api.capability.extendedworld;

import com.bewitchment.Bewitchment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.*;

@SuppressWarnings({"ConstantConditions", "SameReturnValue", "NullableProblems", "WeakerAccess"})
public class ExtendedWorld extends WorldSavedData {
	private static final String TAG = Bewitchment.MODID + ".world_data";
	
	public final List<NBTTagCompound> storedCauldrons = new ArrayList<>();
	public final List<NBTTagCompound> storedPoppetShelves = new ArrayList<>();
	public final Map<String, List<UUID>> demonPledgedPlayers = new HashMap<>(); //demon name - players
	
	public ExtendedWorld(String name) {
		super(name);
	}
	
	public static ExtendedWorld get(World world) {
		ExtendedWorld data = (ExtendedWorld) world.getMapStorage().getOrLoadData(ExtendedWorld.class, TAG);
		if (data == null) {
			data = new ExtendedWorld(TAG);
			world.getMapStorage().setData(TAG, data);
		}
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = nbt.getTagList("storedCauldrons", Constants.NBT.TAG_COMPOUND);
		NBTTagList storedPoppetShelves = nbt.getTagList("storedPoppetShelves", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < storedCauldrons.tagCount(); i++) this.storedCauldrons.add(storedCauldrons.getCompoundTagAt(i));
		for (int i = 0; i < storedPoppetShelves.tagCount(); i++) this.storedPoppetShelves.add(storedPoppetShelves.getCompoundTagAt(i));
		nbt.getTagList("demonPledges", Constants.NBT.TAG_COMPOUND).forEach(tag -> readPledges(demonPledgedPlayers, (NBTTagCompound) tag));

		System.out.println(demonPledgedPlayers);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		System.out.println(demonPledgedPlayers);
		NBTTagList storedCauldrons = new NBTTagList();
		NBTTagList storedPoppetShelves = new NBTTagList();
		NBTTagList demonPledges = new NBTTagList();
		for (NBTTagCompound cauldron : this.storedCauldrons) storedCauldrons.appendTag(cauldron);
		for (NBTTagCompound poppet : this.storedPoppetShelves) storedPoppetShelves.appendTag(poppet);
		demonPledgedPlayers.entrySet().stream().forEach(entry -> this.writePledges(entry, demonPledges));
		nbt.setTag("storedCauldrons", storedCauldrons);
		nbt.setTag("storedPoppetShelves", storedPoppetShelves);
		nbt.setTag("demonPledges", demonPledges);
		return nbt;
	}

	private void writePledges(Map.Entry<String, List<UUID>> entry, NBTTagList list) {
		NBTTagCompound couple = new NBTTagCompound();
		couple.setString("demon", entry.getKey());
		NBTTagCompound players = new NBTTagCompound();
		entry.getValue().stream().forEach(uuid -> players.setString(uuid.toString(), uuid.toString()));
		couple.setTag("players", players);
		list.appendTag(couple);
	}

	private void readPledges(Map<String, List<UUID>> map, NBTTagCompound tag){
		List<UUID> players = new ArrayList<>();
		((NBTTagCompound) tag.getTag("players")).getKeySet().stream().forEach(s -> players.add(UUID.fromString(s)));
		map.put(tag.getString("demon"), players);
	}
}