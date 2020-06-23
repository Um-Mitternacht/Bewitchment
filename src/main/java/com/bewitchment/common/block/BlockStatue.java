package com.bewitchment.common.block;

import com.bewitchment.client.misc.Statues;
import com.bewitchment.common.block.tile.entity.TileEntityStatue;
import com.bewitchment.common.block.util.ModBlock;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockStatue extends ModBlockContainer {
    public final Statues.Statue statue;

    public BlockStatue(Statues.Statue statue) {
        super(null, statue.getName(), statue.getBase(), -1);
        this.statue = statue;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityStatue();
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        for (int i = 0; i < this.statue.getHeight(); i++) {
            world.setBlockToAir(pos.up(i));
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        for (int i = 0; i < this.statue.getHeight() - 1; i++) {
            world.setBlockState(pos.up().up(i), ModObjects.filler.getDefaultState().withProperty(BlockFiller.HEIGHT, i));
        }
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityStatue) {
            TileEntityStatue te = (TileEntityStatue) world.getTileEntity(pos);
            te.name = statue.getName();
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return 3.5f;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return FULL_BLOCK_AABB.expand(0, statue.getHeight() - 1, 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing face) {
        BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
        for (int i = 0; i < this.statue.getHeight(); i++) {
            if (!canPlaceBlockAt(world, pos0.up(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return false;
    }

    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return false;
    }

    public static class BlockFiller extends ModBlock {
        public static final PropertyInteger HEIGHT = PropertyInteger.create("height", 0, 3);

        public BlockFiller() {
            super("statue_filler", Blocks.GOLD_BLOCK);
            setDefaultState(getBlockState().getBaseState().withProperty(HEIGHT, 0));
        }

        private int getHeightAbove(BlockPos pos, World world) {
            int height = 0;
            while (world.getBlockState(pos.up()).getBlock() instanceof BlockFiller) {
                height++;
                pos = pos.up();
            }
            return height;
        }

        private BlockPos getStatuePos(World world, BlockPos pos) {
            for (int i = 0; i < 5; i++) {
                if (world.getBlockState(pos.down(i)).getBlock() instanceof BlockStatue) return pos.down(i);
            }
            return null;
        }

        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public EnumBlockRenderType getRenderType(IBlockState state) {
            return EnumBlockRenderType.INVISIBLE;
        }


        @Override
        public int quantityDropped(Random random) {
            return 0;
        }

        @Override
        public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public IBlockState getStateFromMeta(int meta) {
            return getDefaultState().withProperty(HEIGHT, meta);
        }

        @Override
        public int getMetaFromState(IBlockState state) {
            return state.getValue(HEIGHT);
        }

        @Override
        protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, HEIGHT);
        }

        @SuppressWarnings("deprecation")
        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
            return BlockFaceShape.UNDEFINED;
        }

        @Override
        public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
            int height = 1;
            if (source instanceof World) height = getHeightAbove(pos, (World) source);
            return new AxisAlignedBB(0, -state.getValue(HEIGHT) - 1, 0, 1, height + 1, 1);
        }

        @Override
        public void breakBlock(World world, BlockPos pos, IBlockState state) {
            BlockPos statuePos = getStatuePos(world, pos);
            if (statuePos != null) {
                InventoryHelper.spawnItemStack(world, statuePos.getX(), statuePos.getY(), statuePos.getZ(), new ItemStack(world.getBlockState(statuePos).getBlock()));
                world.getBlockState(statuePos).getBlock().breakBlock(world, statuePos, world.getBlockState(statuePos));
            } else super.breakBlock(world, pos, state);
        }

        @SuppressWarnings("deprecation")
        @Override
        public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
            BlockPos statuePos = getStatuePos(worldIn, pos);
            if (statuePos != null) {
                return new ItemStack(worldIn.getBlockState(statuePos).getBlock());
            }
            return ItemStack.EMPTY;
        }

        @Override
        public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
            return false;
        }

        @Override
        public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
            return false;
        }
    }


}
