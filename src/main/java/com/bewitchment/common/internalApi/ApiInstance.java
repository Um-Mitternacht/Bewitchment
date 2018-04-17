package com.bewitchment.common.internalApi;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.IEnergy;
import com.bewitchment.api.capability.IInfusion;
import com.bewitchment.api.cauldron.brew.IBrew;
import com.bewitchment.api.divination.IFortune;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.api.incantation.IIncantation;
import com.bewitchment.api.recipe.IBrewModifier;
import com.bewitchment.api.ritual.EnumGlyphType;
import com.bewitchment.api.ritual.IRitual;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.IBloodReserve;
import com.bewitchment.api.transformation.ITransformation;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.core.capability.transformation.blood.CapabilityBloodReserve;
import com.bewitchment.common.core.hotbar.HotbarAction;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.EntityInternalBloodChanged;
import com.bewitchment.common.core.net.messages.NightVisionStatus;
import com.bewitchment.common.core.net.messages.PlayerTransformationChangedMessage;
import com.bewitchment.common.core.net.messages.PlayerVampireBloodChanged;
import com.bewitchment.common.crafting.cauldron.CauldronFoodValue;
import com.bewitchment.common.crafting.cauldron.ItemRitual;
import com.bewitchment.common.divination.Fortune;
import com.bewitchment.common.incantation.ModIncantations;
import com.bewitchment.common.infusion.ModInfusions;
import com.bewitchment.common.potion.ModPotions;
import com.bewitchment.common.ritual.AdapterIRitual;
import com.bewitchment.common.ritual.ModRituals;
import com.bewitchment.common.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class ApiInstance extends BewitchmentAPI {

	private ApiInstance() {
	}

	public static void initAPI() {
		BewitchmentAPI.setupAPI(new ApiInstance());
		Bewitchment.logger.debug("API is ready!");
	}

	@Override
	public void registerBrew(IBrew brew, ResourceLocation name) {
		BrewRegistry.register(name, brew);
	}

	@Override
	public void registerBrewRecipe(ItemStack stack, Object... objects) {
		CauldronRegistry.registerBrewRecipe(stack, objects);
	}

	@Override
	public <T> void registerItemEffect(ItemStack stack, T effect, boolean strict) {
		CauldronRegistry.registerItemEffect(stack, effect, strict);
	}

	@Override
	public void registerItemModifier(ItemStack stack, IBrewModifier modifier, boolean strict) {
		CauldronRegistry.registerItemModifier(stack, modifier, strict);
	}

	@Override
	public void registerItemRitual(ItemStack stack, int cost, Object... objects) {
		CauldronRegistry.registerItemRitual(new ItemRitual(stack, cost), objects);
	}

	@Override
	public void registerFluidIngredient(Item item, FluidStack fluid) {
		CauldronRegistry.registerFluidIngredient(item, fluid);
	}

	@Override
	public void registerItemProcessing(Fluid fluid, ItemStack in, ItemStack out, boolean strict) {
		CauldronRegistry.registerItemProcessing(fluid, in, out, strict);
	}

	@Override
	public void registerFoodValue(ItemStack stack, int hunger, float saturation) {
		CauldronRegistry.registerFoodValue(stack, new CauldronFoodValue(hunger, saturation));
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
		Optional<IEnergy> iEnergy = EnergyHandler.getEnergy(player);
		if (iEnergy.isPresent()) {
			return iEnergy.get().getType();
		}
		return null;
	}

	@Override
	public void setPlayerInfusion(EntityPlayer player, IInfusion infusion) {
		Optional<IEnergy> iEnergy = EnergyHandler.getEnergy(player);
		if (iEnergy.isPresent()) {
			iEnergy.get().setType(infusion);
		}
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
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		boolean flag = data.addVampireBlood(amount);
		if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
		}
		return flag;
	}

	@Override
	public void setTypeAndLevel(EntityPlayer player, ITransformation type, int level, boolean isClient) {
		ITransformationData data = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
		IBloodReserve ibr = player.getCapability(CapabilityBloodReserve.CAPABILITY, null);
		data.setType(type);
		data.setLevel(level);
		data.setNightVision(data.isNightVisionActive() && (type == DefaultTransformations.WEREWOLF || type == DefaultTransformations.VAMPIRE));
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
			NetworkHandler.HANDLER.sendTo(new PlayerTransformationChangedMessage(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new PlayerVampireBloodChanged(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new EntityInternalBloodChanged(player), (EntityPlayerMP) player);
			NetworkHandler.HANDLER.sendTo(new NightVisionStatus(player.getCapability(CapabilityTransformationData.CAPABILITY, null).isNightVisionActive()), (EntityPlayerMP) player);

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

}
