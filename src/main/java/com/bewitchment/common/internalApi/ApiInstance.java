package com.bewitchment.common.internalApi;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.cauldron.brew.IBrew;
import com.bewitchment.api.divination.IFortune;
import com.bewitchment.api.hotbar.IHotbarAction;
import com.bewitchment.api.incantation.IIncantation;
import com.bewitchment.api.recipe.IBrewModifier;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.hotbar.HotbarAction;
import com.bewitchment.common.crafting.cauldron.CauldronFoodValue;
import com.bewitchment.common.crafting.cauldron.ItemRitual;
import com.bewitchment.common.divination.Fortune;
import com.bewitchment.common.incantation.ModIncantations;
import com.bewitchment.common.spell.Spell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

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

}
