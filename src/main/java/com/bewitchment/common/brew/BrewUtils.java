package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.helper.NBTHelper;
import com.bewitchment.api.helper.RomanNumber;
import com.bewitchment.common.internalApi.BrewRegistry;
import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

/**
 * This class was created by BerciTheBeast on 27.3.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess"})
public class BrewUtils {

	public static final String BREW_COLOR = "brew_color";
	public static final String BREW_NAME = "brew_name";
	public static final String BREW_DESC = "brew_desc";

	public static final String BREW_DATA = "brew_data";
	public static final String BREW_ID = "brew_id";
	public static final String BREW_AMPLIFIER = "brew_amplifier";
	public static final String BREW_DURATION = "brew_duration";

	public static ItemStack createPotion(Item item, Collection<PotionEffect> effects) {
		return createPotion(new ItemStack(item), effects);
	}

	public static ItemStack createPotion(ItemStack stack, Collection<PotionEffect> effects) {
		PotionUtils.appendEffects(stack, effects);
		return stack;
	}

	public static ItemStack createPotion(Item in, PotionEffect... effects) {
		return createPotion(new ItemStack(in), effects);
	}

	public static ItemStack createPotion(ItemStack stack, PotionEffect... effects) {
		PotionUtils.appendEffects(stack, Arrays.asList(effects));
		return stack;
	}

	public static ItemStack createBrew(BrewRegistry.Brew enu, IBrew brew) {
		ItemStack stack = new ItemStack(enu.getItem());
		addBrewEffect(stack, BrewRegistry.getDefault(enu, brew));
		NBTHelper.setString(stack, BREW_NAME, "brew." + brew.getName() + ".name");
		NBTHelper.setString(stack, BREW_DESC, "brew." + brew.getName() + ".desc");
		NBTHelper.setInteger(stack, BREW_COLOR, brew.getColor());
		return stack;
	}

	public static ItemStack addBrewEffect(ItemStack stack, BrewEffect effect) {
		NBTTagList list = addBrewData(stack);
		NBTTagCompound tag = new NBTTagCompound();
		IBrew brew = effect.getBrew();
		tag.setString(BREW_ID, BrewRegistry.getBrewResource(brew).toString());
		tag.setInteger(BREW_AMPLIFIER, effect.getAmplifier());
		tag.setInteger(BREW_DURATION, effect.getDuration());
		list.appendTag(tag);
		return stack;
	}

	public static NBTTagList addBrewData(ItemStack stack) {
		if (!NBTHelper.hasTag(stack, BREW_DATA)) {
			NBTTagList list = new NBTTagList();
			NBTHelper.setNBT(stack, BREW_DATA, list);
			return list;
		}
		return NBTHelper.getNBT(stack, BREW_DATA);
	}

	public static ItemStack createBrew(BrewRegistry.Brew enu, BrewEffect... effects) {
		ItemStack stack = new ItemStack(enu.getItem());
		NBTTagList list = addBrewData(stack);
		for (BrewEffect effect : effects) {
			NBTTagCompound tag = new NBTTagCompound();
			IBrew brew = effect.getBrew();
			tag.setString(BREW_ID, BrewRegistry.getBrewResource(brew).toString());
			tag.setInteger(BREW_AMPLIFIER, effect.getAmplifier());
			tag.setInteger(BREW_DURATION, effect.getDuration());
			list.appendTag(tag);
		}
		return stack;
	}

	public static ItemStack addBrewInfo(ItemStack stack, IBrew brew) {
		NBTHelper.setString(stack, BREW_NAME, "brew." + brew.getName() + ".name");
		NBTHelper.setString(stack, BREW_DESC, "brew." + brew.getName() + ".desc");
		NBTHelper.setInteger(stack, BREW_COLOR, brew.getColor());

		return stack;
	}

	public static NBTTagCompound serialize(Collection<Object> collection) {
		List<BrewEffect> brewEffects = new ArrayList<>();
		List<PotionEffect> potionEffects = new ArrayList<>();
		for (Object brew : collection) {
			if (brew instanceof BrewEffect) {
				brewEffects.add((BrewEffect) brew);
			} else if (brew instanceof PotionEffect) {
				potionEffects.add((PotionEffect) brew);
			}
		}
		NBTTagCompound compound = new NBTTagCompound();

		appendPotions(compound, mixPotions(potionEffects));
		appendBrews(compound, mixBrews(brewEffects));

		return compound;
	}

	public static void appendPotions(NBTTagCompound tag, Collection<PotionEffect> effects) {
		NBTTagList tagList = tag.getTagList("CustomPotionEffects", 9);
		for (PotionEffect potioneffect : effects) {
			tagList.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
		}
		tag.setTag("CustomPotionEffects", tagList);
	}

	public static Collection<PotionEffect> mixPotions(Collection<PotionEffect> effects) {
		List<PotionEffect> list = new ArrayList<>();
		for (PotionEffect effect : effects) {
			effects.stream().filter(added -> added != effect && !list.contains(added) && !list.contains(effect)
					&& added.getPotion() == effect.getPotion()).forEach(added -> {
				effect.combine(added);
				list.add(added);
			});
		}
		effects.removeAll(list);
		return effects;
	}

	public static void appendBrews(NBTTagCompound tag, Collection<BrewEffect> effects) {
		NBTTagList list = new NBTTagList();
		tag.setTag(BREW_DATA, list);
		for (BrewEffect effect : effects) {
			NBTTagCompound compound = new NBTTagCompound();
			IBrew brew = effect.getBrew();
			compound.setString(BREW_ID, BrewRegistry.getBrewResource(brew).toString());
			compound.setInteger(BREW_AMPLIFIER, effect.getAmplifier());
			compound.setInteger(BREW_DURATION, effect.getDuration());
			list.appendTag(compound);
		}
	}

	public static Collection<BrewEffect> mixBrews(Collection<BrewEffect> effects) {
		List<BrewEffect> list = new ArrayList<>();
		for (BrewEffect effect : effects) {
			effects.stream().filter(added -> effect != added && effect.getBrew() == added.getBrew()).forEach(added -> {
				effect.combine(added);
				list.add(added);
			});
		}
		effects.removeAll(list);
		return effects;
	}

	public static Tuple<List<BrewEffect>, List<PotionEffect>> deSerialize(NBTTagCompound compound) {
		List<PotionEffect> potionEffects = PotionUtils.getEffectsFromTag(compound);
		List<BrewEffect> brewEffects = new ArrayList<>();
		Tuple<List<BrewEffect>, List<PotionEffect>> tuple = new Tuple<>(brewEffects, potionEffects);

		NBTTagList list = (NBTTagList) compound.getTag(BREW_DATA);
		for (int i = 0, size = list.tagCount(); i < size; i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			IBrew brew = BrewRegistry.getRegisteredBrew(tag.getString(BREW_ID));
			int duration = tag.getInteger(BREW_DURATION);
			int amplifier = tag.getInteger(BREW_AMPLIFIER);
			brewEffects.add(new BrewEffect(brew, duration, amplifier));
		}

		return tuple;
	}

	public static boolean hasBrewData(ItemStack stack) {
		return NBTHelper.hasTag(stack, BREW_DATA);
	}

	@SideOnly(Side.CLIENT)
	public static void addBrewTooltip(ItemStack stack, List<String> tooltip) {
		List<BrewEffect> brewsFromStack = BrewUtils.getBrewsFromStack(stack);
		for (BrewEffect effect : brewsFromStack) {
			IBrew brew = effect.getBrew();

			String name = " - " + I18n.format("brew." + brew.getName() + ".tooltip") + " ";
			String amplifier = (effect.getAmplifier() <= 0) ? "" : (RomanNumber.getRoman(effect.getAmplifier())) + " ";
			String duration = effect.isInstant() ? I18n.format("brew.instant") : StringUtils.ticksToElapsedTime(effect.getDuration());

			tooltip.add(TextFormatting.DARK_AQUA + "" + TextFormatting.ITALIC + name + amplifier + "(" + duration + ")");
		}
		if (brewsFromStack.isEmpty()) {
			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "---");
		}
	}

	public static List<BrewEffect> getBrewsFromStack(ItemStack stack) {
		List<BrewEffect> effects = new ArrayList<>();

		NBTTagList list = NBTHelper.getNBT(stack, BREW_DATA);
		for (int i = 0, size = list.tagCount(); i < size; i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			IBrew brew = BrewRegistry.getRegisteredBrew(tag.getString(BREW_ID));
			int duration = tag.getInteger(BREW_DURATION);
			int amplifier = tag.getInteger(BREW_AMPLIFIER);
			effects.add(new BrewEffect(brew, duration, amplifier));
		}

		return effects;
	}

	@SideOnly(Side.CLIENT)
	public static void addPotionTooltip(ItemStack itemIn, List<String> tooltip) {
		List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemIn);
		List<Tuple<String, AttributeModifier>> attributes = Lists.newArrayList();

		if (list.isEmpty()) {
			tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "---");
		} else {
			for (PotionEffect effect : list) {
				StringBuilder string = new StringBuilder();
				string.append(" - ").append(I18n.format(effect.getEffectName()).trim());
				Potion potion = effect.getPotion();
				Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

				if (!map.isEmpty()) {
					for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
						AttributeModifier attribute = entry.getValue();
						attribute = new AttributeModifier(attribute.getName(), potion.getAttributeModifierAmount(effect.getAmplifier(), attribute), attribute.getOperation());
						attributes.add(new Tuple<>(entry.getKey().getName(), attribute));
					}
				}

				if (effect.getAmplifier() > 0) {
					string.append(" ").append(RomanNumber.getRoman(effect.getAmplifier()));
				}

				if (effect.getDuration() > 20) {
					string.append(" (").append(Potion.getPotionDurationString(effect, 1.0F)).append(")");
				}

				if (potion.isBadEffect()) {
					tooltip.add(TextFormatting.DARK_RED + string.toString());
				} else {
					tooltip.add(TextFormatting.DARK_BLUE + string.toString());
				}
			}
		}

		if (!attributes.isEmpty()) {
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("potion.whenDrank"));

			for (Tuple<String, AttributeModifier> tuple : attributes) {
				AttributeModifier modifier = tuple.getSecond();
				double amount = modifier.getAmount();
				double newAmount;

				if (modifier.getOperation() != 1 && modifier.getOperation() != 2) {
					newAmount = modifier.getAmount();
				} else {
					newAmount = modifier.getAmount() * 100.0D;
				}

				if (amount > 0.0D) {
					tooltip.add(TextFormatting.BLUE + I18n.format("attribute.modifier.plus." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(newAmount), I18n.format("attribute.name." + tuple.getFirst())));
				} else if (amount < 0.0D) {
					newAmount = newAmount * -1.0D;
					tooltip.add(TextFormatting.RED + I18n.format("attribute.modifier.take." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(newAmount), I18n.format("attribute.name." + tuple.getFirst())));
				}
			}
		}
	}
}
