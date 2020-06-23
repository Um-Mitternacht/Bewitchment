package com.bewitchment.common.item.tool;

import com.bewitchment.Util;
import com.bewitchment.registry.ModObjects;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings({"deprecation", "ConstantConditions", "NullableProblems"})
public class ItemBoline extends ItemShears {
	public ItemBoline() {
		super();
		Util.registerItem(this, "boline");
		setMaxDamage(ModObjects.TOOL_SILVER.getMaxUses());
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("biome_id", Biome.getIdForBiome(world.getBiome(player.getPosition())));
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!target.world.isRemote) stack.damageItem(1, attacker);
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("biome_id")) tooltip.add(Biome.getBiome(stack.getTagCompound().getInteger("biome_id")).getBiomeName());
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ModObjects.TOOL_SILVER.getAttackDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4, 0));
		}
		return multimap;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
		if (!entity.world.isRemote) {
			if (entity instanceof IShearable) {
				IShearable target = (IShearable) entity;
				if (target.isShearable(stack, entity.world, entity.getPosition())) {
					List<ItemStack> drops = target.onSheared(stack, entity.world, entity.getPosition(), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
					for (ItemStack stack0 : drops) {
						EntityItem item = entity.entityDropItem(entity instanceof EntitySheep && stack0.getItem() == Item.getItemFromBlock(Blocks.WOOL) ? new ItemStack(Items.STRING, 4) : stack0, 1);
						item.motionY += entity.getRNG().nextFloat() * 0.05F;
						item.motionX += (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.1f;
						item.motionZ += (entity.getRNG().nextFloat() - entity.getRNG().nextFloat()) * 0.1f;
					}
					stack.damageItem(1, entity);
				}
				return true;
			}
		}
		return false;
	}
}