package com.bewitchment.common.world.gen.village;

import com.bewitchment.common.village.VillagerTradeHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.List;
import java.util.Random;

public class VillageHedgeWitchHouse extends StructureVillagePieces.Village {
	public VillageHedgeWitchHouse() {
	}
	
	public VillageHedgeWitchHouse(StructureVillagePieces.Start villagePiece, int par2, Random random, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
		super(villagePiece, par2);
		this.setCoordBaseMode(facing);
		this.boundingBox = structureBoundingBox;
	}
	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		return false;
	}
	
	@Override
	protected VillagerRegistry.VillagerProfession chooseForgeProfession(int count, VillagerRegistry.VillagerProfession prof) {
		return VillagerTradeHandler.INSTANCE.hedgewitch;
	}
	
	public static class Manager implements VillagerRegistry.IVillageCreationHandler {
		
		@Override
		public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
			return new StructureVillagePieces.PieceWeight(VillageHedgeWitchHouse.class, 15, MathHelper.getInt(random, i, 1 + i));
		}
		
		@Override
		public Class<?> getComponentClass() {
			return VillageHedgeWitchHouse.class;
		}
		
		@Override
		public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight pieceWeight, StructureVillagePieces.Start start, List<StructureComponent> list, Random random, int i, int i1, int i2, EnumFacing enumFacing, int i3) {
			StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(i, i1, i2, 0, 0, 0, 11, 10, 9, enumFacing);
			return (!canVillageGoDeeper(box)) ? null : new VillageHedgeWitchHouse(start, i3, random, box, enumFacing);
		}
	}
}
