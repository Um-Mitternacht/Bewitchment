package com.bewitchment.common.block.head;

import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author its_meow
 *         <p>
 *         Mar 7, 2019
 */
public class BlockGenericSkull extends BlockAnimalSkull {

	public final HeadTypes type;

	public BlockGenericSkull(HeadTypes type, int i) {
		super();
		this.setRegistryName(type.name + "_" + i);
		this.setTranslationKey(LibMod.MOD_ID + "." + type.name);
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		this.type = type;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return type.teFactory.apply(type);
	}

}
