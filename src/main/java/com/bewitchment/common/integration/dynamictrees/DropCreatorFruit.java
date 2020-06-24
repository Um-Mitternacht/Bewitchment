package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class DropCreatorFruit extends DropCreator {
	private final Item fruit;
	private final int chance;

	public DropCreatorFruit(Item item, int chance) {
		super(new ResourceLocation(Bewitchment.MODID, item.getRegistryName().getPath()));
		this.fruit = item;
		this.chance = chance;
	}

	public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
		return this.getLeafDrops(world, species, leafPos, random, dropList, fortune);
	}

	public List<ItemStack> getVoluntaryDrop(World world, Species species, BlockPos rootPos, Random random, List<ItemStack> dropList, int soilLife) {
		BlockPos treePos = rootPos.up();
		IBlockState trunk = world.getBlockState(treePos);
		BlockBranch branch = TreeHelper.getBranch(trunk);
		if (branch != null && branch.getRadius(trunk) >= 8 && 0.33F > random.nextFloat()) {
			dropList.add(new ItemStack(this.fruit, 1, 0));
		}

		return dropList;
	}

	public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
		return this.getLeafDrops(access, species, breakPos, random, dropList, fortune);
	}

	protected List<ItemStack> getLeafDrops(IBlockAccess access, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int fortune) {
		int chance = this.chance;
		if (fortune > 0) {
			chance -= fortune;
			if (chance < 2) {
				chance = 2;
			}
		}

		if (random.nextInt(chance) == 0) {
			dropList.add(new ItemStack(this.fruit, 1, 0));
		}

		return dropList;
	}
}