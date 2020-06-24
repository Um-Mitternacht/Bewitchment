package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.common.item.ItemContract;
import com.bewitchment.common.village.VillagerTradeHandler;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class EntityDemon extends ModEntityMob implements IMerchant {
	public int attackTimer = 0;
	public EntityPlayer buyer, lastBuyer;
	private MerchantRecipeList recipeList;
	private int careerId, careerLevel, timeUntilReset;
	private boolean needsInitilization;

	public EntityDemon(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/demon" + world.rand.nextInt(4)));
		setSize(1.425f, 3.8f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.WATER, -1);
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 75;
		enablePersistence();
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != MobEffects.POISON && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (attackTimer > 0) attackTimer--;
		if (ticksExisted % 20 == 0 && isInLava()) heal(6);
		if (getAttackTarget() != null) {
			EntityLivingBase player = getAttackTarget();
			boolean launchFireball = ticksExisted % 80 > 5;
			if (!launchFireball && getDistance(player) > 2) {
				double d0 = getDistanceSq(player);
				double d1 = player.posX - this.posX;
				double d2 = player.getEntityBoundingBox().minY + (double) (player.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
				double d3 = player.posZ - this.posZ;
				float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
				world.playEvent(null, 1018, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
				EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, this, d1 + this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f);
				entitysmallfireball.posY = posY + (double) (height / 2.0F) + 0.5D;
				this.swingArm(EnumHand.MAIN_HAND);
				world.spawnEntity(entitysmallfireball);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			attackTimer = 10;
			world.setEntityState(this, (byte) 4);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1, false, false));
				entity.setFire(25);
				entity.motionY += 0.6;
				if (entity instanceof EntityPlayer)
					((EntityPlayerMP) entity).connection.sendPacket(new SPacketEntityVelocity(entity));
			}
		}
		return flag;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.66);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(3.33);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(195);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.7D);
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new DemonAITradePlayer(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> ((Util.hasBauble(e, ModObjects.hellish_bauble) ? world.rand.nextInt(4) == 0 : !e.isImmuneToFire())) && (!BewitchmentAPI.hasBesmirchedGear(e))));
	}

	public void onEntityUpdate() {
		int i = this.getAir();
		super.onEntityUpdate();

		if (this.isEntityAlive() && !this.isInWater()) {
			--i;
			this.setAir(i);

			if (this.getAir() == -20) {
				this.setAir(300);
			}
		} else {
			this.setAir(300);
		}
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) attackTimer = 10;
		else super.handleStatusUpdate(id);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void updateAITasks() {
		if (!this.isTrading() && this.timeUntilReset > 0) {
			--this.timeUntilReset;
			if (this.timeUntilReset <= 0) {
				if (this.needsInitilization) {
					for (MerchantRecipe merchantrecipe : this.recipeList) {
						if (!(merchantrecipe.getItemToSell().getItem() instanceof ItemContract) && merchantrecipe.isRecipeDisabled()) {
							merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
						}
					}
					this.populateBuyingList();
					this.needsInitilization = false;
				}
				this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
			}
		}
		super.updateAITasks();
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (itemstack.getItem() == Items.NAME_TAG || itemstack.getItem() == Items.LEAD) {
			itemstack.interactWithEntity(player, this, hand);
			return true;
		} else if (this.isEntityAlive() && !this.isTrading() && !this.isChild() && !player.isSneaking()) {
			if (!world.isRemote) this.setCustomer(player);
			if (this.recipeList == null) {
				this.populateBuyingList();
			}
			if (hand == EnumHand.MAIN_HAND) {
				player.addStat(StatList.TALKED_TO_VILLAGER);
			}
			if (!this.world.isRemote && !this.recipeList.isEmpty()) {
				player.displayVillagerTradeGui(this);
			} else if (this.recipeList.isEmpty()) {
				return super.processInteract(player, hand);
			}
			return true;
		} else {
			return super.processInteract(player, hand);
		}
	}

	public ITextComponent getDisplayName() {
		Team team = this.getTeam();
		String s = this.getCustomNameTag();

		if (s != null && !s.isEmpty()) {
			TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.formatPlayerName(team, s));
			textcomponentstring.getStyle().setHoverEvent(this.getHoverEvent());
			textcomponentstring.getStyle().setInsertion(this.getCachedUniqueIdString());
			return textcomponentstring;
		} else {
			TextComponentString itextcomponent = new TextComponentString(this.getName());
			itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
			itextcomponent.getStyle().setInsertion(this.getCachedUniqueIdString());

			if (team != null) {
				itextcomponent.getStyle().setColor(team.getColor());
			}
			return itextcomponent;
		}
	}

	@Override
	protected int getSkinTypes() {
		return 4;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("careerId", careerId);
		tag.setInteger("careerLevel", careerLevel);
		if (recipeList != null) tag.setTag("recipeList", recipeList.getRecipiesAsTags());
		super.writeEntityToNBT(tag);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		careerId = tag.getInteger("careerId");
		careerLevel = tag.getInteger("careerLevel");
		if (tag.hasKey("recipeList")) {
			NBTTagCompound compound = tag.getCompoundTag("recipeList");
			this.recipeList = new MerchantRecipeList(compound);
		}
		super.readEntityFromNBT(tag);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
		this.setCustomNameTag((rand.nextInt(3) == 0 ? new TextComponentTranslation("entity.bewitchment.prefix." + rand.nextInt(102)).getFormattedText() + " " : "") + new TextComponentTranslation("entity.bewitchment.given_name." + rand.nextInt(503)).getFormattedText());
		return super.onInitialSpawn(difficulty, data);
	}

	public VillagerRegistry.VillagerProfession getProfessionForge() {
		return VillagerTradeHandler.INSTANCE.demon;
	}

	private void populateBuyingList() {
		if (this.careerId != 0 && this.careerLevel != 0) {
			++this.careerLevel;
		} else {
			this.careerId = this.getProfessionForge().getRandomCareer(this.rand) + 1;
			this.careerLevel = 1;
		}
		if (this.recipeList == null) {
			this.recipeList = new MerchantRecipeList();
		}
		int i = this.careerId - 1;
		int j = this.careerLevel - 1;
		List<EntityVillager.ITradeList> trades = this.getProfessionForge().getCareer(i).getTrades(j);
		if (trades != null && !trades.isEmpty()) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int t = 0; t < trades.size(); t++) list.add(t);
			Collections.shuffle(list);
			trades.get(list.get(0)).addMerchantRecipe(this, this.recipeList, this.rand);
			if (j <= 3 && list.size() > 1) trades.get(list.get(1)).addMerchantRecipe(this, this.recipeList, this.rand);
		}
	}

	public boolean isTrading() {
		return this.buyer != null;
	}

	@Override
	public BlockPos getPos() {
		return getPosition();
	}

	@Override
	public EntityPlayer getCustomer() {
		return buyer;
	}

	@Override
	public void setCustomer(EntityPlayer player) {
		buyer = player;
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		if (this.recipeList == null) this.populateBuyingList();
		return this.recipeList;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public void setRecipes(MerchantRecipeList recipeList) {
		this.recipeList = recipeList;
	}

	@Override
	public void verifySellingItem(ItemStack stack) {
	}

	@Override
	public void useRecipe(MerchantRecipe recipe) {
		recipe.incrementToolUses();
		int i = 3 + this.rand.nextInt(4);
		if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
			this.timeUntilReset = 40;
			this.needsInitilization = true;
			if (this.buyer != null) this.lastBuyer = this.buyer;
			else this.lastBuyer = null;
			i += 5;
		}
		if (recipe.getRewardsExp()) {
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX + 0.5d, this.posY, this.posZ, i));
		}
	}

	private class DemonAITradePlayer extends EntityAIBase {
		private final EntityDemon demon;

		public DemonAITradePlayer(EntityDemon demon) {
			this.demon = demon;
			this.setMutexBits(4);
		}

		public boolean shouldExecute() {
			if (!this.demon.isEntityAlive()) {
				return false;
			} else if (this.demon.isInWater()) {
				return false;
			} else if (!this.demon.onGround) {
				return false;
			} else if (this.demon.velocityChanged) {
				return false;
			} else {
				EntityPlayer entityplayer = this.demon.getCustomer();

				if (entityplayer == null) {
					return false;
				} else if (this.demon.getDistanceSq(entityplayer) > 16.0D) {
					return false;
				} else {
					return entityplayer.openContainer != null;
				}
			}
		}

		public void resetTask() {
			this.demon.setCustomer(null);
		}

		public void updateTask() {
			this.demon.getNavigator().clearPath();
		}
	}


}
