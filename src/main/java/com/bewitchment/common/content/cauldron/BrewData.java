package com.bewitchment.common.content.cauldron;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewData;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.helper.ColorHelper;
import com.bewitchment.common.entity.EntityLingeringBrew;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BrewData implements INBTSerializable<NBTTagList>, IBrewData {

	private ArrayList<IBrewEntry> effects = new ArrayList<>();

	public static BrewData fromStack(ItemStack stack) {
		BrewData data = new BrewData();
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("brew")) {
				data.deserializeNBT(stack.getTagCompound().getTagList("brew", NBT.TAG_COMPOUND));
			}
		}
		return data;
	}

	public void addEntry(BrewEntry entry) {
		effects.add(entry);
	}

	public void saveToStack(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		tag.setTag("brew", this.serializeNBT());
	}

	@Override
	public List<IBrewEntry> getEffects() {
		return ImmutableList.copyOf(effects);
	}

	@Override
	public NBTTagList serializeNBT() {
		NBTTagList list = new NBTTagList();
		effects.forEach(ef -> list.appendTag(((BrewEntry) ef).serializeNBT()));
		return list;
	}

	@Override
	public void deserializeNBT(NBTTagList nbt) {
		effects.clear();
		nbt.forEach(nbtb -> effects.add(new BrewEntry((NBTTagCompound) nbtb)));
	}

	public int getColor() {
		return this.getEffects().stream().map(be -> getColorFromEntry(be)).reduce((a, b) -> ColorHelper.blendColor(a, b, 0.5f + (0.5f / getEffects().size()))).orElse(TileEntityCauldron.DEFAULT_COLOR);
	}

	private int getColorFromEntry(IBrewEntry be) {
		if (be.getPotion() != null) {
			Optional<Integer> color = be.getModifierList().getLevel(DefaultModifiers.COLOR);
			if (color.isPresent()) {
				return color.get();
			}
			return be.getPotion().getLiquidColor();
		}
		return TileEntityCauldron.DEFAULT_COLOR;
	}

	public void applyToEntity(EntityLivingBase entity, Entity indirectSource, Entity thrower, ApplicationType type) {
		this.getEffects().stream()
				.filter(be -> !be.getModifierList().getLevel(DefaultModifiers.SUPPRESS_ENTITY_EFFECT).isPresent())
				.forEach(be -> applyEffect(be, entity, indirectSource, thrower, type));
	}

	private void applyEffect(IBrewEntry be, EntityLivingBase entity, Entity carrier, Entity thrower, ApplicationType type) {
		if (be.getPotion() != null) {
			IBrewEffect brew = BewitchmentAPI.getAPI().getBrewFromPotion(be.getPotion());
			if (!be.getModifierList().getLevel(DefaultModifiers.SUPPRESS_ENTITY_EFFECT).isPresent()) {
				int duration = (int) (0.5d * getDuration(type, brew)) * (be.getPotion().isInstant() ? 0 : 1);
				duration *= 1 + (be.getModifierList().getLevel(DefaultModifiers.DURATION).orElse(0) * 0.3);
				int amplifier = be.getModifierList().getLevel(DefaultModifiers.POWER).orElse(0);
				boolean particles = !be.getModifierList().getLevel(DefaultModifiers.SUPPRESS_PARTICLES).isPresent();
				PotionEffect pe = new PotionEffect(be.getPotion(), duration, amplifier, false, particles);
				pe = brew.onApplyToEntity(entity, pe, be.getModifierList(), thrower);
				if (be.getPotion().isInstant()) {
					be.getPotion().affectEntity(carrier, thrower, entity, pe.getAmplifier(), 1D);
				} else {
					entity.addPotionEffect(pe);
				}
			}
		} else {
			Bewitchment.logger.error("No potion associated with the brew!");
		}
	}

	private int getDuration(ApplicationType type, IBrewEffect brew) {
		switch (type) {
			case ARROW:
				return brew.getArrowDuration();
			case GENERAL:
				return brew.getDefaultDuration();
			case LINGERING:
				return brew.getLingeringDuration();
			default:
				return brew.getDefaultDuration();
		}
	}

	public void setupLingeringCloud(EntityLingeringBrew entBrew) {
		float radius = (float) this.effects.stream().mapToDouble(be -> be.getModifierList().getLevel(DefaultModifiers.RADIUS).orElse(0)).average().orElse(0);
		int duration = (int) (1 + this.effects.stream().mapToInt(be -> be.getModifierList().getLevel(DefaultModifiers.GAS_CLOUD_DURATION).orElse(0)).average().orElse(0));
		entBrew.setRadius(1f + radius).setDuration(100 * duration).setRadiusPerTick(0.01f * radius);
	}

	public void applyInWorld(World world, double x, double y, double z, EnumFacing side, EntityLivingBase thrower) {
		this.getEffects().stream().filter(be -> !be.getModifierList().getLevel(DefaultModifiers.SUPPRESS_IN_WORLD_EFFECT).isPresent()).forEach(be -> {
			try {
				CauldronRegistry.getBrewFromPotion(be.getPotion()).applyInWorld(world, new BlockPos(x, y, z), side, be.getModifierList(), thrower);
			} catch (NoSuchElementException exc) {
				Bewitchment.logger.error(exc.getMessage());
			}
		});
	}

	public static enum ApplicationType {
		GENERAL, ARROW, LINGERING
	}

	public static class BrewEntry implements INBTSerializable<NBTTagCompound>, IBrewEntry {

		private Potion pot;
		private BrewModifierListImpl mods;

		public BrewEntry(Potion potion, BrewModifierListImpl modifiers) {
			this.pot = potion;
			this.mods = modifiers;
		}

		public BrewEntry(NBTTagCompound tag) {
			deserializeNBT(tag);
		}

		@Override
		@Nullable
		public Potion getPotion() {
			return pot;
		}

		@Override
		public IBrewModifierList getModifierList() {
			return mods;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			if (pot != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("potion", pot.getRegistryName().toString());
				tag.setTag("modifiers", mods.serializeNBT());
				return tag;
			}
			return new NBTTagCompound();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			pot = ForgeRegistries.POTIONS.getValue(new ResourceLocation(nbt.getString("potion")));
			if (pot != null) {
				mods = new BrewModifierListImpl();
				mods.deserializeNBT(nbt.getTagList("modifiers", NBT.TAG_COMPOUND));
			}
		}

	}
}
