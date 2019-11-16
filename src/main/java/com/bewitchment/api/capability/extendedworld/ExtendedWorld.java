package com.bewitchment.api.capability.extendedworld;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.IPledgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.*;

@SuppressWarnings({"ConstantConditions", "SameReturnValue", "NullableProblems", "WeakerAccess"})
public class ExtendedWorld extends WorldSavedData {
	private static final String TAG = Bewitchment.MODID + ".world_data";
	
	public final List<NBTTagCompound> storedCauldrons = new ArrayList<>();
	public final List<NBTTagCompound> storedPoppetShelves = new ArrayList<>();
	public final Map<String, Collection<UUID>> demonPledgedPlayers = new HashMap<>(); //demon name - players
	
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

	public static void pledgePlayerToDemon(World world, EntityPlayer player, IPledgeable demon){
		ExtendedWorld extendedWorld = get(world);
		List<UUID> players = new ArrayList<>();
		if (extendedWorld.demonPledgedPlayers.containsKey(demon.getPledgeName()))
			players.addAll(extendedWorld.demonPledgedPlayers.get(demon.getPledgeName()));
		players.add(player.getUniqueID());
		extendedWorld.demonPledgedPlayers.put(demon.getPledgeName(), players);
		extendedWorld.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = nbt.getTagList("storedCauldrons", Constants.NBT.TAG_COMPOUND);
		NBTTagList storedPoppetShelves = nbt.getTagList("storedPoppetShelves", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < storedCauldrons.tagCount(); i++) this.storedCauldrons.add(storedCauldrons.getCompoundTagAt(i));
		for (int i = 0; i < storedPoppetShelves.tagCount(); i++) this.storedPoppetShelves.add(storedPoppetShelves.getCompoundTagAt(i));
		//todo properly store NBT
		nbt.getTagList("demonPledges", Constants.NBT.TAG_COMPOUND).forEach(tag -> readPledges(demonPledgedPlayers, (NBTTagCompound) tag));
		//demonPledgedPlayers.put("bapy", Arrays.asList(UUID.fromString("cc6d4703-5257-360a-b167-332b467f63af")));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList storedCauldrons = new NBTTagList();
		NBTTagList storedPoppetShelves = new NBTTagList();
		NBTTagList demonPledges = new NBTTagList();
		for (NBTTagCompound cauldron : this.storedCauldrons) storedCauldrons.appendTag(cauldron);
		for (NBTTagCompound poppet : this.storedPoppetShelves) storedPoppetShelves.appendTag(poppet);
		demonPledgedPlayers.entrySet().stream().forEach(entry -> this.writePledges(entry, demonPledges));
		nbt.setTag("storedCauldrons", storedCauldrons);
		nbt.setTag("storedPoppetShelves", storedPoppetShelves);
		nbt.setTag("demonPledges", demonPledges);
		System.out.println(demonPledges);
		return nbt;
	}

	private void writePledges(Map.Entry<String, Collection<UUID>> entry, NBTTagList list) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("demon", entry.getKey());
		NBTTagCompound players = new NBTTagCompound();
		for(UUID uuid : entry.getValue()){
			players.setString(uuid.toString(), uuid.toString());
		}
		data.setTag("players", players);
		list.appendTag(data);
	}

	private void readPledges(Map<String, Collection<UUID>> map, NBTTagCompound tag){
		List<UUID> players = new ArrayList<>();
		tag.getCompoundTag("players").getKeySet().forEach(key -> {
			System.out.println(key);
			players.add(UUID.fromString(tag.getCompoundTag("players").getString(key)));
		});
		map.put(tag.getString("demon"), players);
		System.out.println(tag);
	}
}