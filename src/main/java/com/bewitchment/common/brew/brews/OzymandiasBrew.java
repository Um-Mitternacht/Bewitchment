package com.bewitchment.common.brew.brews;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import com.bewitchment.common.brew.BlockHitBrew;

import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class OzymandiasBrew extends BlockHitBrew {

	private final Map<Block, IBlockState> stateMap = new HashMap<>();

	@SuppressWarnings("deprecation")
	public OzymandiasBrew() {
		stateMap.put(Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL.getStateFromMeta(1));
		stateMap.put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		stateMap.put(Blocks.TALLGRASS, Blocks.DEADBUSH.getDefaultState());
		stateMap.put(Blocks.STONEBRICK, Blocks.STONEBRICK.getStateFromMeta(1));
		stateMap.put(Blocks.GRASS, Blocks.SAND.getDefaultState());
		stateMap.put(Blocks.MYCELIUM, Blocks.SAND.getDefaultState());
		stateMap.put(Blocks.DIRT, Blocks.SAND.getDefaultState());
	}

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
	}

	@Override
	public int getColor() {
		return 0x303E0C;
	}

	@Override
	public String getName() {
		return "ozymandias";
	}

	@Override
	public void safeImpact(BlockPos pos, @Nullable EnumFacing side, World world, int amplifier) {
		int box = 1 + (int) (amplifier / 2F);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			Block block = world.getBlockState(spot).getBlock();
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && stateMap.containsKey(block)) {
				world.setBlockState(spot, stateMap.get(block), 3);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
	}
}