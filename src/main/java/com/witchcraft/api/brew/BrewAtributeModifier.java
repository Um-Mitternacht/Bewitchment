package com.witchcraft.api.brew;

import com.google.common.collect.Maps;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.Map;
import java.util.UUID;

/**
 * This class was created by Arekkuusu on 10/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public abstract class BrewAtributeModifier implements IBrew {

	private final Map<IAttribute, AttributeModifier> modifierMap = Maps.newHashMap();

	public BrewAtributeModifier() {
		initAtributes();
	}

	protected abstract void initAtributes();

	public void applyAttributeModifiers(AbstractAttributeMap attributeMap, int amplifier) {
		for (Map.Entry<IAttribute, AttributeModifier> entry : modifierMap.entrySet()) {
			IAttributeInstance attribute = attributeMap.getAttributeInstance(entry.getKey());
			if (attribute == null) continue;

			AttributeModifier modifier = entry.getValue();
			attribute.removeModifier(modifier);
			attribute.applyModifier(new AttributeModifier(modifier.getID(), this.getName() + " " + amplifier, modifier.getAmount() * (double) (amplifier + 1), modifier.getOperation()));
		}
	}

	public void removeAttributeModifiers(AbstractAttributeMap attributeMapIn, int amplifier) {
		for (Map.Entry<IAttribute, AttributeModifier> entry : modifierMap.entrySet()) {
			IAttributeInstance attribute = attributeMapIn.getAttributeInstance(entry.getKey());
			if (attribute == null) continue;

			attribute.removeModifier(entry.getValue());
		}
	}

	protected BrewAtributeModifier register(IAttribute attribute, String uuid, double ammount, int operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), getName(), ammount, operation);
		modifierMap.put(attribute, attributemodifier);
		return this;
	}
}
