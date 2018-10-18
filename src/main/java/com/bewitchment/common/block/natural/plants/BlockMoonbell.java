package com.bewitchment.common.block.natural.plants;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

import java.util.ArrayList;
import java.util.Random;

@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", modid = "thaumcraft")
public class BlockMoonbell extends BlockModFlower implements IInfusionStabiliserExt {

	public static final PropertyBool placed = PropertyBool.create("placed");
	private static ArrayList<Biome> validBiomesMoonBell = new ArrayList<Biome>();

	public BlockMoonbell() {
		super("moonbell");
		MinecraftForge.EVENT_BUS.register(this);
		this.setLightOpacity(16).setLightLevel(0.5f).setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(placed, false));
		this.setSoundType(SoundType.PLANT);
	}

	public static void addValidMoonbellBiome(Biome b) {
		validBiomesMoonBell.add(b);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(placed, meta == 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (state.getValue(placed)) return 0;
		return 1;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, placed);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getStateFromMeta(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextDouble() < 0.2)
			worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5 + rand.nextGaussian() * 0.2, 0.1 + pos.getY() + rand.nextGaussian() * 0.2, pos.getZ() + 0.5 + rand.nextGaussian() * 0.2, 0, 0.1, 0);
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		if (player == null) return world.getBlockState(pos).getValue(placed);
		return super.canHarvestBlock(world, pos, player) && ((!player.world.isDaytime() && player.world.getMoonPhase() == 4) || world.getBlockState(pos).getValue(placed));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random r) {
		if (!state.getValue(placed) && (world.isDaytime() || world.getMoonPhase() != 4)) {
			world.setBlockToAir(pos);
			for (int i = 0; i < 7; i++) {
				world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + r.nextDouble(), pos.getY() + r.nextDouble(), pos.getZ() + r.nextDouble(), r.nextGaussian() * 0.01, r.nextDouble() * 0.01, r.nextGaussian() * 0.01);
			}
		}
	}

	@SubscribeEvent
	public void spawnFlowers(PlayerTickEvent evt) {
		if (evt.side == Side.SERVER && evt.phase == Phase.START) {
			World w = evt.player.world;
			if (w.getTotalWorldTime() % 20 == 0 && validBiomesMoonBell.contains(w.getBiome(evt.player.getPosition()))) {
				Random r = evt.player.getRNG();
				if (w.provider.getDimension() == 0 && w.provider.getMoonPhase(w.getWorldTime()) == 4 && !w.isDaytime() && evt.player.getRNG().nextDouble() < 0.2) {
					int dx = (r.nextInt(7) - 3) * 10;
					int dz = (r.nextInt(7) - 3) * 10;
					MutableBlockPos pos = new MutableBlockPos(evt.player.getPosition().add(dx, 0, dz));
					tryAndSpawn(w, pos);
				}
			}
		}
	}

	private void tryAndSpawn(World w, MutableBlockPos p) {
		int oy = p.getY();
		for (int dy = -5; dy <= 5; dy++) {
			p.setY(oy + dy);
			if ((w.isAirBlock(p) || w.getBlockState(p).getBlock().isReplaceable(w, p)) && w.getBlockState(p.down()).getBlock() == Blocks.DIRT) {
				w.setBlockState(p, this.getDefaultState().withProperty(placed, false), 3);
				return;
			}
		}
	}

	@Override
	@Optional.Method(modid = "thaumcraft")
	public boolean canStabaliseInfusion(World world, BlockPos pos) {
		return true;
	}

	@Override
	@Optional.Method(modid = "thaumcraft")
	public float getStabilizationAmount(World world, BlockPos pos) {
		return 0;
	}

}
