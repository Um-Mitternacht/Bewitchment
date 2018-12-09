package com.bewitchment.common.content.crystalBall;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;

public abstract class Fortune implements IFortune {

	public static final IForgeRegistry<IFortune> REGISTRY = new RegistryBuilder<IFortune>().setName(new ResourceLocation(LibMod.MOD_ID, "fortunes")).setType(IFortune.class).setIDRange(0, 200).create();

	private int weight;
	private ResourceLocation name;

	/**
	 * @param weight see {@link #getDrawingWeight()}
	 * @param name   registry name of the fortune, without the domain
	 * @param modid  registry domain of the fortune, typically this is the mod id
	 */
	public Fortune(int weight, @Nonnull String name, @Nonnull String modid) {
		this.setRegistryName(new ResourceLocation(modid, name));
		this.weight = weight;
	}

	public static void registerFortune(IFortune fortune) {
		REGISTRY.register(fortune);
	}

	/**
	 * This works like a lottery ticket extraction, all the (applicable) fortunes get this number of tickets each.
	 * One is then extracted, choosing what fortune will be read from the crystal ball to the player
	 *
	 * @return The number of tickets this fortune should account for. The higher the number the higher the chance
	 */
	@Override
	public int getDrawingWeight() {
		return weight;
	}

	/**
	 * This method is called when a player is being told their future to decide what fortunes are
	 * available to them. Use this to exclude profecies that only appear once or in similar particular cases
	 *
	 * @param player the player the fortunes are being read to
	 * @return true if this fortune is available to be read, false otherwise
	 */
	@Override
	public abstract boolean canBeUsedFor(@Nonnull EntityPlayer player);

	/**
	 * Used to determine if now is a good moment to execute the profecy.
	 * Don't use this to do stuff linked to the profecy, as this method
	 * gets called multiple times per second. Use {@link #apply(EntityPlayer)} for that.
	 *
	 * @param player the player this fortune is being applied to
	 * @return true if this fortune can and should be applied right now
	 */
	@Override
	public abstract boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player);

	/**
	 * Execute the logic of the profecy
	 * On "instant" profecies this should return true.
	 * On event dependent profecies this should mark the fortune as active in the capability,
	 * while the removable part should be set after the event has happened succesfully
	 * Only called server side
	 *
	 * @param player the player this fortune is being applied to
	 * @return true if the fortune should be automatically removed after this
	 */
	@Override
	public abstract boolean apply(@Nonnull EntityPlayer player);

	/**
	 * @return true if the fortune can be considered negative
	 */
	@Override
	public abstract boolean isNegative();

	@Override
	public String getTranslationKey() {
		return "fortunes." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath() + ".name";
	}

	@Override
	public IFortune setRegistryName(ResourceLocation nameIn) {
		this.name = nameIn;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return name;
	}

	@Override
	public Class<IFortune> getRegistryType() {
		return IFortune.class;
	}
}

