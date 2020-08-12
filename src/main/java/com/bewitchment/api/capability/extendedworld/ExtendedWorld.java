package com.bewitchment.api.capability.extendedworld;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SyncExtendedWorld;
import com.bewitchment.common.entity.spirit.demon.AbstractGreaterDemon;
import com.bewitchment.common.entity.util.IPledgeable;
import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"ConstantConditions", "SameReturnValue", "WeakerAccess"})
@Data
public class ExtendedWorld extends WorldSavedData {
    private static final String TAG = Bewitchment.MODID + ".world_data";

    private final List<NBTTagCompound> storedCauldrons = new ArrayList<>();
    private final List<NBTTagCompound> storedPoppetShelves = new ArrayList<>();
    private final Map<String, Set<UUID>> demonPledgedPlayers = new HashMap<>(); //demon name - players
    private final Map<BlockPos, Biome> STORED_OVERRIDE_BIOMES = new ConcurrentHashMap<>();

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

    /**
     * Adds a biome to the map of overridden biomes, so it may be restored, should it be replaced.
     *
     * @param world
     * @param pos
     */
    public static void addOverriddenBiome(World world, BlockPos pos) {
        ExtendedWorld extendedWorld = get(world);
        extendedWorld.STORED_OVERRIDE_BIOMES.putIfAbsent(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), world.getBiome(pos));
        extendedWorld.markDirty();
    }

    public static void pledgePlayerToDemon(World world, EntityPlayer player, IPledgeable demon) {
        ExtendedWorld extendedWorld = get(world);
        Set<UUID> players = new HashSet<>();
        if (extendedWorld.demonPledgedPlayers.containsKey(demon.getPledgeName()))
            players.addAll(extendedWorld.demonPledgedPlayers.get(demon.getPledgeName()));
        players.add(player.getPersistentID());
        extendedWorld.demonPledgedPlayers.put(demon.getPledgeName(), players);
        if (demon instanceof AbstractGreaterDemon && player instanceof EntityPlayerMP) {
            ((AbstractGreaterDemon) demon).bossInfo.removePlayer((EntityPlayerMP) player);
        }
        extendedWorld.markDirty();
    }

    public static void depledgePlayerToDemon(World world, EntityPlayer player, IPledgeable demon) {
        ExtendedWorld extendedWorld = get(world);
        Set<UUID> players = new HashSet<>();
        if (extendedWorld.demonPledgedPlayers.containsKey(demon.getPledgeName()))
            players.addAll(extendedWorld.demonPledgedPlayers.get(demon.getPledgeName()));
        players.remove(player.getPersistentID());
        extendedWorld.demonPledgedPlayers.put(demon.getPledgeName(), players);
        if (demon instanceof AbstractGreaterDemon && player instanceof EntityPlayerMP) {
            ((AbstractGreaterDemon) demon).bossInfo.addPlayer((EntityPlayerMP) player);
        }
        extendedWorld.markDirty();
    }

    public static boolean playerPledgedToDemon(World world, EntityPlayer player, String demon) {
        ExtendedWorld extendedWorld = get(world);
        Set<UUID> players = extendedWorld.demonPledgedPlayers.get(demon);
        return players != null && players.contains(player.getPersistentID());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList storedCauldrons = nbt.getTagList("storedCauldrons", Constants.NBT.TAG_COMPOUND);
        NBTTagList storedPoppetShelves = nbt.getTagList("storedPoppetShelves", Constants.NBT.TAG_COMPOUND);
        for (NBTBase tag : nbt.getTagList("biome_id", Constants.NBT.TAG_COMPOUND)) {
            STORED_OVERRIDE_BIOMES.put(BlockPos.fromLong(((NBTTagCompound) tag).getLong("pos")), Biome.getBiome(((NBTTagCompound) tag).getInteger("biomeId")));
        }
        for (int i = 0; i < storedCauldrons.tagCount(); i++)
            this.storedCauldrons.add(storedCauldrons.getCompoundTagAt(i));
        for (int i = 0; i < storedPoppetShelves.tagCount(); i++)
            this.storedPoppetShelves.add(storedPoppetShelves.getCompoundTagAt(i));
        nbt.getTagList("demonPledges", Constants.NBT.TAG_COMPOUND).forEach(tag -> readPledge(demonPledgedPlayers, (NBTTagCompound) tag));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList storedCauldrons = new NBTTagList();
        NBTTagList storedPoppetShelves = new NBTTagList();
        NBTTagList demonPledges = new NBTTagList();
        for (NBTTagCompound cauldron : this.storedCauldrons) storedCauldrons.appendTag(cauldron);
        for (NBTTagCompound poppet : this.storedPoppetShelves) storedPoppetShelves.appendTag(poppet);
        demonPledgedPlayers.entrySet().forEach(entry -> this.writePledge(entry, demonPledges));
        nbt.setTag("storedCauldrons", storedCauldrons);
        nbt.setTag("storedPoppetShelves", storedPoppetShelves);
        nbt.setTag("demonPledges", demonPledges);
        NBTTagList biomeOverrides = new NBTTagList();
        STORED_OVERRIDE_BIOMES.forEach((pos, biome) -> {
            NBTTagCompound data = new NBTTagCompound();
            data.setLong("pos", pos.toLong());
            data.setInteger("biomeId", Biome.getIdForBiome(biome));
            biomeOverrides.appendTag(data);
        });
        nbt.setTag("biome_id", biomeOverrides);
        return nbt;
    }

    private void writePledge(Map.Entry<String, Set<UUID>> entry, NBTTagList list) {
        NBTTagCompound data = new NBTTagCompound();
        data.setString("demon", entry.getKey());
        NBTTagList players = new NBTTagList();
        for (UUID uuid : entry.getValue()) players.appendTag(new NBTTagString(uuid.toString()));
        data.setTag("players", players);
        list.appendTag(data);
    }

    private void readPledge(Map<String, Set<UUID>> map, NBTTagCompound tag) {
        Set<UUID> players = new HashSet<>();
        String demon = tag.getString("demon");
        for (NBTBase player : tag.getTagList("players", Constants.NBT.TAG_STRING))
            players.add(UUID.fromString(((NBTTagString) player).getString()));
        map.put(demon, players);
    }

    public void syncToClient(final EntityPlayerMP player) {
        Bewitchment.network.sendTo(new SyncExtendedWorld(this.writeToNBT(new NBTTagCompound())), player);
    }
}