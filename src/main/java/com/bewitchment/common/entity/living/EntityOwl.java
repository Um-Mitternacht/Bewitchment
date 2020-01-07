package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class EntityOwl extends EntityRaven {
	public EntityOwl(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/owl"), Items.RABBIT, Items.CHICKEN);
		setSize(0.4f, 0.9f);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.OWL_HOOT;
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && player.getHeldItem(hand).getItem() instanceof ItemBoline && shearTimer == 0) {
			InventoryHelper.spawnItemStack(world, posX, posY, posZ, new ItemStack(Items.FEATHER, 1 + world.rand.nextInt(3)));
			shearTimer = 12000;
			return true;
		}
		else return super.processInteract(player, hand);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		tasks.addTask(1, new EntityAIFleeSun(this, 1));
		targetTasks.addTask(2, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, e -> e instanceof EntityBat || e instanceof EntityChicken || e instanceof EntityLizard || e instanceof EntityParrot || e instanceof EntityRabbit || e.getClass().getName().endsWith("Rat")));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
		getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getHealth() < this.getMaxHealth() && !(ticksExisted % 200 > 5)) this.heal(2);
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
}
