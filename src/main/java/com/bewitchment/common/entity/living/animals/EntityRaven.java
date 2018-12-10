package com.bewitchment.common.entity.living.animals;

import com.bewitchment.common.core.statics.ModSounds;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

public class EntityRaven extends EntityMultiSkin {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/raven");
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.GOLD_NUGGET, ModItems.silver_nugget);
	private static final Set<Item> FODDER_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, Items.NETHER_WART, ModItems.seed_aconitum, ModItems.seed_asphodel, ModItems.seed_belladonna, ModItems.seed_chrysanthemum, ModItems.seed_garlic, ModItems.seed_ginger, ModItems.seed_hellebore, ModItems.seed_kelp, ModItems.seed_kenaf, ModItems.seed_lavender, ModItems.seed_mandrake, ModItems.seed_mint, ModItems.seed_sagebrush, ModItems.seed_silphium, ModItems.seed_thistle, ModItems.seed_tulsi, ModItems.seed_white_sage, ModItems.seed_wormwood);
	//	private static final String[] names = {"Huginn", "Muninn", "Morrigan", "Bhusunda", "Pallas", "Qrow", "Nevermore", "Corvus", "Apollo", "Odin", "Badhbh", "Bran", "Crowe", "Scarecrow", "Santa Caws", "Valravn", "Cain", "Mabel", "Grip", "Harbinger", "Shani", "Diablo", "Raven", "Charlie", "Unidan", "Yatagarasu", "Samjokgo", "Ischys"}; //I'm trash lmao
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityRaven.class, DataSerializers.VARINT);

	public EntityRaven(World worldIn) {
		super(worldIn);
		this.setSize(0.4f, 0.4f);
		this.moveHelper = new EntityFlyHelper(this);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.85d);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	protected void setupTamedAI() {
		this.tasks.addTask(5, new EntityAIFollowOwnerFlying(this, 2, 10, 2));
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIPanic(this, 0.7D));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAIMate(this, 0.8d));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAILookIdle(this));
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
		pathnavigateflying.setCanOpenDoors(false);
		pathnavigateflying.setCanFloat(false);
		pathnavigateflying.setCanEnterDoors(true);
		return pathnavigateflying;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
			if (!player.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			if (!this.isSilent()) {
				this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			}

			if (!this.world.isRemote) {
				if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.setTamedBy(player);
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityRaven(world);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.RAVEN_CRY;
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == FODDER_ITEMS;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntityRaven)) {
			return false;
		} else {
			EntityRaven raven = (EntityRaven) otherAnimal;

			if (!raven.isTamed()) {
				return false;
			} else if (raven.isSitting()) {
				return false;
			} else {
				return this.isInLove() && raven.isInLove();
			}
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog || block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.aiSit != null) {
			this.aiSit.setSitting(false);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public int getSkinTypes() {
		return 1;
	}

}
