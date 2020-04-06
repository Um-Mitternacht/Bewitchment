package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.world.BiomeChangingUtils;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.animation.ModelBlockAnimation;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards.
 */
public class RitualBiomeShift extends Ritual {
	public RitualBiomeShift() {
		super(new ResourceLocation(Bewitchment.MODID, "biome_shift"), Arrays.asList(Util.get("treeSapling"), Util.get("logWood"), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.boline))), null, null, 200, 1300, 33, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.NORMAL);
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		List<Ingredient> recipe = input;
		EnumHand hand = EnumHand.MAIN_HAND;
		ItemStack stack = caster.getHeldItem(hand);
		int id = Biome.getIdForBiome(Biomes.PLAINS);
		for (Ingredient is : recipe) {
			if (stack.getItem() == ModObjects.boline && stack.hasTagCompound() && stack.getTagCompound().hasKey("biome_id")) {
				id = stack.getTagCompound().getInteger("biome_id");
			}
		}
		int radius = (int) MathHelper.sqrt(64 * (1 + 2) * (1 + 2));
		BiomeChangingUtils.BiomeChangerWalker walker = new BiomeChangingUtils.BiomeChangerWalker(id);
		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
		mpos.setPos(effectivePos.getX() - radius, 0, effectivePos.getZ() - radius);
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (x * x + z * z <= radius * radius) {
					walker.visit(world, mpos);
				}
				mpos.move(EnumFacing.SOUTH);
			}
			mpos.move(EnumFacing.NORTH, 2 * radius + 1);
			mpos.move(EnumFacing.EAST);
		}
		walker.complete();
	}
	
}
