package com.bewitchment.common.ritual;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards.
 */
//public class RitualBiomeShift extends Ritual {
//	public RitualBiomeShift() {
//		super(new ResourceLocation(Bewitchment.MODID, "biome_shift"), Arrays.asList(Util.get("treeSapling"), Util.get("logWood"), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.boline))), null, null, 200, 1300, 33, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.NORMAL);
//	}
//
//	@Override
//	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
//		NonNullList<ItemStack> recipe = Ritual.getItemsUsedForInput(data);
//		int id = Biome.getIdForBiome(Biomes.PLAINS);
//		for (ItemStack is : recipe) {
//			if (is.getItem() == ModObjects.boline && is.hasTagCompound() && is.getTagCompound().hasKey("biome_id")) {
//				id = is.getTagCompound().getInteger("biome_id");
//			}
//		}
//		int radius = (int) MathHelper.sqrt(64 * (1 + 2) * (1 + 2));
//		BiomeChangingUtils.BiomeChangerWalker walker = new BiomeChangingUtils.BiomeChangerWalker(id);
//		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
//		mpos.setPos(effectivePos.getX() - radius, 0, effectivePos.getZ() - radius);
//		for (int x = -radius; x <= radius; x++) {
//			for (int z = -radius; z <= radius; z++) {
//				if (x * x + z * z <= radius * radius) {
//					walker.visit(world, mpos);
//				}
//				mpos.move(EnumFacing.SOUTH);
//			}
//			mpos.move(EnumFacing.NORTH, 2 * radius + 1);
//			mpos.move(EnumFacing.EAST);
//		}
//		walker.complete();
//	}
//
//}
