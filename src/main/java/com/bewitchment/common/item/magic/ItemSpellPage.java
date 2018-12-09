/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.item.magic;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.mp.IMagicPowerUsingItem;
import com.bewitchment.api.spell.ISpell;
import com.bewitchment.api.spell.ISpell.EnumSpellType;
import com.bewitchment.common.content.spell.Spell;
import com.bewitchment.common.entity.EntitySpellCarrier;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemSpellPage extends ItemMod {

	public ItemSpellPage(String id) {
		super(id);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new IBehaviorDispenseItem() {

			@Override
			public ItemStack dispense(IBlockSource source, ItemStack stack) {
				ISpell s = ItemSpellPage.getSpellFromItemStack(stack);
				if (s != null) {
					EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING);
					Vec3d lookVect = new Vec3d(enumfacing.getDirectionVec());
					if (s.canBeUsed(source.getWorld(), source.getBlockPos().offset(enumfacing), null)) {
						if (s.getType() == EnumSpellType.INSTANT)
							s.performEffect(new RayTraceResult(Type.MISS, lookVect, EnumFacing.UP, source.getBlockPos()), null, source.getWorld());
						else {
							EntitySpellCarrier car = new EntitySpellCarrier(source.getWorld(), source.getBlockPos().getX() + 1.5 * lookVect.x + 0.5, source.getBlockPos().getY() + 0.5d + lookVect.y, source.getBlockPos().getZ() + 1.5 * lookVect.z + 0.5);
							car.setSpell(s);
							car.setCaster(null);
							car.shoot(car, 0, enumfacing.getHorizontalAngle(), 0, 1f, 0);
							source.getWorld().spawnEntity(car);
						}
						return ItemStack.EMPTY;
					}
				}
				return stack;
			}
		});

	}

	@Nullable
	public static ISpell getSpellFromItemStack(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("spell")) {
			return Spell.SPELL_REGISTRY.getValue(new ResourceLocation(stack.getTagCompound().getString("spell")));
		}
		return null;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (ISpell s : Spell.SPELL_REGISTRY) {
				items.add(getStackFor(s));
			}
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("spell"))
			return super.getTranslationKey(stack) + "." + stack.getTagCompound().getString("spell").replace(':', '.');
		return super.getTranslationKey(stack);
	}

	public ItemStack getStackFor(ISpell s) {
		ItemStack stack = new ItemStack(this);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("spell", s.getRegistryName().toString());
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ISpell s = getSpellFromItemStack(playerIn.getHeldItem(handIn));
		if (s != null && s.canBeUsed(worldIn, playerIn.getPosition(), playerIn) && playerIn.getCapability(IMagicPowerContainer.CAPABILITY, null).getAmount() >= s.getCost()) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		ISpell spell = getSpellFromItemStack(stack);
		if (spell != null && !worldIn.isRemote) {
			if (entityLiving instanceof EntityPlayer) {
				int spellCost = spell.getCost() * 80;
				IMagicPowerContainer mpc = entityLiving.getCapability(IMagicPowerContainer.CAPABILITY, null);
				if (mpc.drain(spellCost)) {
					if (spell.getType() == EnumSpellType.INSTANT)
						spell.performEffect(new RayTraceResult(Type.MISS, entityLiving.getLookVec(), EnumFacing.UP, entityLiving.getPosition()), entityLiving, worldIn);
					else {
						EntitySpellCarrier car = new EntitySpellCarrier(worldIn, entityLiving.posX + entityLiving.getLookVec().x, entityLiving.posY + entityLiving.getEyeHeight() + entityLiving.getLookVec().y, entityLiving.posZ + entityLiving.getLookVec().z);
						car.setSpell(spell);
						car.setCaster(entityLiving);
						car.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0, 2f, 0);
						worldIn.spawnEntity(car);
					}
				}
			}
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ICapabilityProvider() {

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
				if (capability == IMagicPowerUsingItem.CAPABILITY) {
					return true;
				}
				return false;
			}

			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
				return null;
			}

		};
	}

}
