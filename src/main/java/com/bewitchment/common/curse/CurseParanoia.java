package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;
import java.util.Random;

public class CurseParanoia extends Curse {


	//Curse plans
	//Random mob sounds
	//Blindness
	//Display random messages
	//Need to figure out capabilities so every player does not hear the same sound as the same time if there are multiples with the same curse. For now, this works.
	public int timer = 1150;

	public CurseParanoia() {
		super(new ResourceLocation(Bewitchment.MODID, "paranoia"), Arrays.asList(Util.get(Items.ENDER_EYE), Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.snake_venom), Util.get(Items.GHAST_TEAR), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.demonic_elixir), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 256, 3));
		Random rand = target.getRNG();
		World world = target.getEntityWorld();
		BlockPos pos = target.getPosition();
		int i = rand.nextInt(100);
		if (timer > 0) timer--;
		if (i < 10 && timer == 0) {
			switch (rand.nextInt(31)) {
				case 0:
					world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_SCREAM, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 1:
					world.playSound(null, pos, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 2:
					world.playSound(null, pos, SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 3:
					world.playSound(null, pos, ModSounds.WEREWOLF_HOWL, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 4:
					world.playSound(null, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 5:
					world.playSound(null, pos, SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 6:
					world.playSound(null, pos, SoundEvents.ENTITY_SPIDER_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 7:
					world.playSound(null, pos, SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 8:
					world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 9:
					world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 10:
					world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 11:
					world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 12:
					world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 13:
					world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 14:
					world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 15:
					world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 16:
					world.playSound(null, pos, SoundEvents.ENTITY_GHAST_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 17:
					world.playSound(null, pos, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 18:
					world.playSound(null, pos, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 19:
					world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 20:
					world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 21:
					world.playSound(null, pos, SoundEvents.BLOCK_GRASS_STEP, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 22:
					world.playSound(null, pos, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 23:
					world.playSound(null, pos, ModSounds.CHALK_SCRIBBLE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 24:
					world.playSound(null, pos, ModSounds.CLEAVER_IDLE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 25:
					world.playSound(null, pos, ModSounds.BAFOMETYR_IDLE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 26:
					world.playSound(null, pos, SoundEvents.ENTITY_SILVERFISH_HURT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 27:
					world.playSound(null, pos, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 28:
					world.playSound(null, pos, SoundEvents.ENTITY_EGG_THROW, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				case 29:
					world.playSound(null, pos, SoundEvents.ENTITY_ENDERDRAGON_AMBIENT, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
					break;
				default:
					world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_STARE, SoundCategory.HOSTILE, 1, 1);
					timer = 1150;
			}
		}
		return false;
	}
}
