package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class EntityDemon extends ModEntityMob implements IMerchant {
	public int attackTimer = 0;
	private MerchantRecipeList recipeList;
	private EntityPlayer buyer;
	private int careerID, careerLevel, wealth;
	
	public EntityDemon(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/demon" + world.rand.nextInt(4)));
		setSize(1.425f, 3.8f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.WATER, -1);
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 165;
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
				if (entity instanceof EntityPlayer) ((EntityPlayerMP) entity).connection.sendPacket(new SPacketEntityVelocity(entity));
			}
		}
		return flag;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.66);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(175);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8);
	}	@Override
	public BlockPos getPos() {
		return getPosition();
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> ((Util.hasBauble(e, ModObjects.hellish_bauble) ? world.rand.nextInt(4) == 0 : !e.isImmuneToFire())) && (!BewitchmentAPI.hasBesmirched(e))));
	}
	
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) attackTimer = 10;
		else super.handleStatusUpdate(id);
	}
	
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}
	
	@Override
	protected int getSkinTypes() {
		return 6;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}	@Override
	public EntityPlayer getCustomer() {
		return buyer;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("careerID", careerID);
		tag.setInteger("careerLevel", careerLevel);
		tag.setInteger("wealth", wealth);
		if (recipeList != null) tag.setTag("recipeList", recipeList.getRecipiesAsTags());
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		careerID = tag.getInteger("careerID");
		careerLevel = tag.getInteger("careerLevel");
		wealth = tag.getInteger("wealth");
		if (tag.hasKey("recipeList")) recipeList.readRecipiesFromTags((NBTTagCompound) tag.getTag("recipeList"));
		super.readEntityFromNBT(tag);
	}
	

	

	
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		return recipeList;
	}
	
	
	@Override
	public World getWorld() {
		return world;
	}
	
	
	@Override
	public void setCustomer(EntityPlayer player) {
		buyer = player;
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
	}
	
	//	@Override
	//	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
	//		setCustomNameTag((rand.nextInt(3) == 0 ? new TextComponentTranslation("demon_prefix_" + rand.nextInt(53)).getFormattedText() + " " : "") + new TextComponentTranslation("demon_name_" + rand.nextInt(326)).getFormattedText());
	//		return super.onInitialSpawn(difficulty, data);
	//	}
	
	
}