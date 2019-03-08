package com.bewitchment.common.block.head;

import com.bewitchment.client.render.entity.model.ModelHellHoundAlphaHead;
import com.bewitchment.client.render.entity.model.ModelHellHoundHead;
import com.bewitchment.common.item.block.ItemBlockHeadType;
import com.bewitchment.common.tile.tiles.TileEntityHead;
import net.minecraft.client.model.ModelBase;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author its_meow
 *         <p>
 *         Mar 7, 2019
 */
public enum HeadTypes {


	// Double supplier avoids loading ModelBase which does not exist on the server
	HELLHOUND("hellhoundhead", true, 6, () -> () -> ModelHellHoundHead.class, type -> new TileEntityHead(type, -1.5F, HeadTextures.HELL_HOUND)),
	ALPHAHELLHOUND("alphahellhoundhead", true, 6, () -> () -> ModelHellHoundAlphaHead.class, type -> new TileEntityHead(type, -1.5F, HeadTextures.ALPHA_HELL_HOUND));


	public final String name;
	public final boolean allowFloor;
	public final int textureCount;
	public final Function<HeadTypes, TileEntityHead> teFactory;
	private final Supplier<Supplier<Class<? extends ModelBase>>> modelSupplier;
	private ArrayList<Pair<BlockGenericSkull, ItemBlockHeadType>> heads = new ArrayList<Pair<BlockGenericSkull, ItemBlockHeadType>>();
	private Set<ItemBlockHeadType> items = new HashSet<ItemBlockHeadType>();
	private Set<BlockGenericSkull> blocks = new HashSet<BlockGenericSkull>();

	HeadTypes(String name, boolean allowFloor, int texCount, Supplier<Supplier<Class<? extends ModelBase>>> modelSupplier,
	          Function<HeadTypes, TileEntityHead> teFactory) {
		this.name = name;
		this.allowFloor = allowFloor;
		this.teFactory = teFactory;
		this.textureCount = texCount;
		this.modelSupplier = modelSupplier;
		for (int i = 1; i <= texCount; i++) {
			BlockGenericSkull block = new BlockGenericSkull(this, i);
			ItemBlockHeadType item = new ItemBlockHeadType(block, this, i);
			heads.add(Pair.of(block, item));
			blocks.add(block);
			items.add(item);
		}
	}

	public BlockGenericSkull getBlock(int i) {
		return heads.get(i).getLeft();
	}

	public ItemBlockHeadType getItem(int i) {
		return heads.get(i).getRight();
	}

	public Set<ItemBlockHeadType> getItemSet() {
		return items;
	}

	public Set<BlockGenericSkull> getBlockSet() {
		return blocks;
	}

	public Supplier<Supplier<Class<? extends ModelBase>>> getModelSupplier() {
		return modelSupplier;
	}

}
