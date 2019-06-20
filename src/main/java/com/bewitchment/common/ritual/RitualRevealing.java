package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockCandleBase;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.BlockLantern;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualRevealing extends Ritual {
	public RitualRevealing() {
		super(new ResourceLocation(Bewitchment.MODID, "revealing"), Arrays.asList(Util.get(ModObjects.ectoplasm), Util.get(Items.ENDER_EYE), Util.get(ModObjects.spectral_dust)), null, null, 3, 500, 20, BlockGlyph.ANY, BlockGlyph.ANY, BlockGlyph.ANY);
	}
	
	@Override
	public void onFinished(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, pos, caster, inventory);
		if (!world.isRemote) {
			for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-16, -16, -16), pos.add(16, 16, 16))) {
				if (world.getBlockState(pos0).getBlock() instanceof BlockLantern) {
					world.setBlockState(pos0, world.getBlockState(pos0).withProperty(BlockCandleBase.LIT, true));
				}
			}
		}
	}
}