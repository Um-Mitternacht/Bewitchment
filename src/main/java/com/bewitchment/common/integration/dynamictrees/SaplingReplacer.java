package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("deprecation")
public class SaplingReplacer {
	public SaplingReplacer() {
	}
	
	@SubscribeEvent
	public void onPlaceSaplingEvent(BlockEvent.PlaceEvent event) {
		IBlockState state = event.getPlacedBlock();
		Species species = null;
		if (state.getBlock() == ModObjects.cypress_sapling) {
			species = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "cypress"));
		}
		else if (state.getBlock() == ModObjects.dragons_blood_sapling) {
			species = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "dragonsblood"));
		}
		else if (state.getBlock() == ModObjects.elder_sapling) {
			species = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "elder"));
		}
		else if (state.getBlock() == ModObjects.juniper_sapling) {
			species = TreeRegistry.findSpecies(new ResourceLocation(Bewitchment.MODID, "juniper"));
		}
		
		if (species != null) {
			event.getWorld().setBlockToAir(event.getPos());
			if (!species.plantSapling(event.getWorld(), event.getPos())) {
				double x = (double) event.getPos().getX() + 0.5D;
				double y = (double) event.getPos().getY() + 0.5D;
				double z = (double) event.getPos().getZ() + 0.5D;
				EntityItem itemEntity = new EntityItem(event.getWorld(), x, y, z, species.getSeedStack(1));
				event.getWorld().spawnEntity(itemEntity);
			}
		}
	}
}
