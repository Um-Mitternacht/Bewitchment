package com.bewitchment.common.world.gen.structures;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.world.gen.ModWorldGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

@SuppressWarnings("NullableProblems")
public class WorldGenWickerman extends WorldGenerator {
	
	public WorldGenWickerman() {
		super();
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		boolean burned = rand.nextBoolean();
		
		WorldServer worldServer = (WorldServer) worldIn;
		MinecraftServer minecraftServer = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldServer.getStructureTemplateManager();
		Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(Bewitchment.MODID + (burned ? ":burnedwickerman" : ":wickerman")));
		
		if (ModWorldGen.canSpawnHere(template, worldServer, position)) {
			IBlockState iBlockState = worldIn.getBlockState(position);
			worldIn.notifyBlockUpdate(position, iBlockState, iBlockState, 3);
			PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.values()[rand.nextInt(Rotation.values().length)]).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);
			template.addBlocksToWorld(worldIn, burned ? position : position.add(0, 1, 0), placementsettings);
			if (!burned) {
				// Spawn Villager
				EntityVillager villager = new EntityVillager(worldIn);
				villager.setPosition(position.getX() + 6, position.getY() + 11, position.getZ() + 3);
				worldIn.spawnEntity(villager);
				// Spawn Random Animals
				int amount = rand.nextInt(5);
				for (int i = 0; i < amount; i++) {
					spawnAnimal(worldIn, position.getX() + 6, position.getY() + 7, position.getZ() + 3, rand);
				}
			}
			return true;
		}
		return false;
	}
	
	private void spawnAnimal(World world, int x, int y, int z, Random random) {
		EntityAnimal spawn;
		switch (random.nextInt(8)) {
			case 0:
				spawn = new EntityCow(world);
				break;
			case 1:
				spawn = new EntityPig(world);
				break;
			case 2:
				spawn = new EntitySheep(world);
				break;
			case 3:
				spawn = new EntityChicken(world);
				break;
			case 4:
				spawn = new EntityHorse(world);
				break;
			case 5:
				spawn = new EntityMule(world);
				break;
			case 6:
				spawn = new EntityDonkey(world);
				break;
			case 7:
				spawn = new EntityRabbit(world);
				break;
			default:
				spawn = null;
		}
		if (spawn != null) {
			spawn.setPosition(x + random.nextInt(1) - random.nextInt(1), y, z + random.nextInt(1) - random.nextInt(1));
			world.spawnEntity(spawn);
		}
	}
}
