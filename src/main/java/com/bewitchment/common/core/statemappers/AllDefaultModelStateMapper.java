package com.bewitchment.common.core.statemappers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Joseph on 1/2/2019. Original code by Zabi94.
 */
public class AllDefaultModelStateMapper implements IStateMapper {

	private final Map<IBlockState, ModelResourceLocation> map = Maps.newHashMap();

	public AllDefaultModelStateMapper(Block block) {
		ModelResourceLocation mrl = new ModelResourceLocation(block.getRegistryName(), "normal");
		this.variateKeyAndAdd(mrl, 0, Lists.newArrayList(block.getDefaultState().getPropertyKeys()), block.getDefaultState());
	}

	private void variateKeyAndAdd(ModelResourceLocation mrl, int i, List<IProperty<?>> properties, IBlockState iBlockState) {
		if (i >= properties.size()) {
			this.map.put(iBlockState, mrl);
		} else {
			IBlockState current = iBlockState;
			Iterator<?> it = properties.get(i).getAllowedValues().iterator();
			while (it.hasNext()) {
				it.next();
				current = current.cycleProperty(properties.get(i));
				this.variateKeyAndAdd(mrl, i + 1, properties, current);
			}
		}
	}

	@Override
	public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
		return this.map;
	}
}