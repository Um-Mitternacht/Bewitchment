package com.bewitchment.common.api;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.divination.IFortune;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.api.incantation.IIncantation;
import com.bewitchment.api.infusion.IInfusion;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.mp.IMagicPowerExpander;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.ritual.IRitual;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.actionbar.HotbarAction;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.content.crystalBall.Fortune;
import com.bewitchment.common.content.incantation.ModIncantations;
import com.bewitchment.common.content.infusion.ModInfusions;
import com.bewitchment.common.content.infusion.capability.InfusionCapability;
import com.bewitchment.common.content.ritual.AdapterIRitual;
import com.bewitchment.common.content.ritual.ModRituals;
import com.bewitchment.common.content.spell.Spell;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.content.transformation.vampire.CapabilityVampire;
import com.bewitchment.common.content.transformation.vampire.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.capability.energy.player.PlayerMPContainer;
import com.bewitchment.common.core.capability.energy.player.expansion.CapabilityMPExpansion;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.crafting.FrostFireRecipe;
import com.bewitchment.common.crafting.OvenSmeltingRecipe;
import com.bewitchment.common.crafting.SpinningThreadRecipe;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ApiInstance extends BewitchmentAPI {

	private ApiInstance() {
	}

	public static void initAPI() {
		BewitchmentAPI.setupAPI(new ApiInstance());
		BewitchmentAPI.getAPI().DEMON = EnumHelper.addCreatureAttribute("DEMON");
		BewitchmentAPI.getAPI().SPIRIT = EnumHelper.addCreatureAttribute("SPIRIT");
		BewitchmentAPI.getAPI().IMMUTABLE = Type.getType("IMMUTABLE");
		Bewitchment.logger.debug("API is ready!");
	}

	@Override
	public void registerHotbarAction(IHotbarAction action) {
		HotbarAction.registerNewAction(action);
	}

	@Override
	public void registerFortune(IFortune fortune) {
		Fortune.registerFortune(fortune);
	}

	@Override
	public void registerIncantation(String name, IIncantation incantation) {
		ModIncantations.registerIncantation(name, incantation);
	}

	@Override
	public void registerSpell(ISpell spell) {
		Spell.SPELL_REGISTRY.register(spell);
	}

	@Override
	public void registerInfusion(IInfusion infusion) {
		ModInfusions.REGISTRY.register(infusion);
	}

	@Override
	public IInfusion getPlayerInfusion(EntityPlayer player) {
		return player.getCapability(InfusionCapability.CAPABILITY, null).getType();
	}

	@Override
	public void setPlayerInfusion(EntityPlayer player, IInfusion infusion) {
		player.getCapability(InfusionCapability.CAPABILITY, null).setType(infusion);
	}

	/**
	 * @param player The player whose blood reserve is being modified
	 * @param amount The amount of blood to add (negative values will decrease the total)
	 * @return <i>When adding</i> blood this will return true if the value changed and false otherwise: this is <b>true</b> if there was
	 * even a little bit of space in the pool, but the blood added was greater than the amount that could be inserted,
	 * and <b>false</b> if the pool was maxed.<br>
	 * <i>When removing</i> blood this will return true if ALL the blood requested was drained.
	 * If the amount drained is greater than the amount available this will return false, and no blood will be drained from the pool
	 * @throws UnsupportedOperationException if the player is not a vampire
	 */
	@Override
	public boolean addVampireBlood(EntityPlayer player, int amount) {
		CapabilityVampire data = player.getCapability(CapabilityVampire.CAPABILITY, null);
		return data.addVampireBlood(amount, player);
	}

	@Override
	public void setTypeAndLevel(EntityPlayer player, ITransformation type, int level, boolean isClient) {
		CapabilityTransformation data = player.getCapability(CapabilityTransformation.CAPABILITY, null);
		IBloodReserve ibr = player.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		ITransformation oldTransformation = data.getType();
		data.setType(type);
		data.setLevel(level);
		if (oldTransformation != type && oldTransformation == DefaultTransformations.VAMPIRE) {
			player.getCapability(CapabilityVampire.CAPABILITY, null).setNightVision(false);
		}
		if ((type == DefaultTransformations.SPECTRE || type == DefaultTransformations.VAMPIRE)) {
			ibr.setMaxBlood(-1);
			player.removePotionEffect(ModPotions.bloodDrained);
		} else if (ibr.getMaxBlood() < 0) {
			ibr.setMaxBlood(480);
			ibr.setBlood(480);
		}
		if (isClient) {
			HotbarAction.refreshActions(player, player.world);
		} else {
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(player), (EntityPlayerMP) player);

		}
		MinecraftForge.EVENT_BUS.post(new TransformationModifiedEvent(player, type, level));
	}

	@Override
	public void registerCircleRitual(IRitual ritual) {
		AdapterIRitual.REGISTRY.register(new AdapterIRitual(ritual));
	}

	@Override
	public int getCirclesIntegerForRitual(EnumGlyphType small, EnumGlyphType medium, EnumGlyphType large) {
		return ModRituals.circles(small, medium, large);
	}

	@Override
	public void registerBrewModifier(IBrewModifier modifier) {
		CauldronRegistry.registerBrewModifier(modifier);
	}

	@Override
	public void registerBrewEffect(IBrewEffect effect, Potion potion, Ingredient ingredient) {
		CauldronRegistry.registerBrewIngredient(effect, ingredient);
		CauldronRegistry.bindPotionAndEffect(effect, potion);
	}

	@Override
	public Potion getPotionFromBrew(IBrewEffect effect) {
		return CauldronRegistry.getPotionFromBrew(effect);
	}

	@Override
	public IBrewEffect getBrewFromPotion(Potion potion) {
		return CauldronRegistry.getBrewFromPotion(potion);
	}

	@Override
	public void addSpinningThreadRecipe(ResourceLocation registryName, ItemStack output, Ingredient... inputs) {
		SpinningThreadRecipe.REGISTRY.register(new SpinningThreadRecipe(registryName, output, inputs));
	}

	@Override
	public void addOvenSmeltingRecipe(ResourceLocation registryName, ItemStack output, ItemStack byproduct, float byproductChance, Ingredient input) {
		OvenSmeltingRecipe.REGISTRY.register(new OvenSmeltingRecipe(registryName, input, output, byproduct, byproductChance));
	}

	@Override
	public void registerFrostfireSmelting(ResourceLocation name, Ingredient input, Supplier<ItemStack> output) {
		FrostFireRecipe.REGISTRY.register(new FrostFireRecipe(name, input, output));
	}

	@Override
	public void expandPlayerMP(IMagicPowerExpander expander, EntityPlayer player) {
		player.getCapability(CapabilityMPExpansion.CAPABILITY, null).expand(expander, player);
		((PlayerMPContainer) player.getCapability(IMagicPowerContainer.CAPABILITY, null)).markDirty();
	}

	@Override
	public void removeMPExpansion(IMagicPowerExpander expander, EntityPlayer player) {
		removeMPExpansion(expander.getID(), player);
	}

	@Override
	public void removeMPExpansion(ResourceLocation expander, EntityPlayer player) {
		player.getCapability(CapabilityMPExpansion.CAPABILITY, null).remove(expander);
		((PlayerMPContainer) player.getCapability(IMagicPowerContainer.CAPABILITY, null)).markDirty();
	}

	@Override
	public void drainBloodFromEntity(EntityPlayer player, EntityLivingBase entity) {
		IBloodReserve br = entity.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		CapabilityTransformation data = player.getCapability(CapabilityTransformation.CAPABILITY, null);
		if (br.getBlood() > 0 && br.getMaxBlood() > 0) {
			int transferred = (int) Math.min(br.getBlood(), br.getBlood() * 0.05 * data.getLevel());
			if (transferred > 0 && (BewitchmentAPI.getAPI().addVampireBlood(player, transferred) || player.isSneaking())) {
				br.setBlood(br.getBlood() - transferred);
				NetworkHandler.HANDLER.sendToAllAround(new EntityInternalBloodChanged(entity), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
			}
		}
	}

}
