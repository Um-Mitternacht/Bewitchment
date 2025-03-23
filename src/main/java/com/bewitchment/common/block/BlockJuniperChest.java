package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntityJuniperChest;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class BlockJuniperChest extends ModBlockContainer {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375);

    public BlockJuniperChest() {
        super(null, "juniper_chest", Blocks.CHEST, -1);
        setResistance(Integer.MAX_VALUE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityJuniperChest();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking() || player.getHeldItem(hand).isEmpty()) {
            if (!world.getBlockState(pos.up()).doesSideBlockChestOpening(world, pos.up(), EnumFacing.DOWN)) {
                if (ItemJuniperKey.checkAccess(world, pos, player, true))
                    player.openGui(Bewitchment.instance, GuiHandler.ModGui.JUNIPER_CHEST.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (placer instanceof EntityPlayer)
            Util.giveItem((EntityPlayer) placer, ((ItemJuniperKey) ModObjects.juniper_key).setTags(world, pos, new ItemStack(ModObjects.juniper_key)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return BOX;
    }

    @Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
        if (ItemJuniperKey.checkAccess(world, pos, player, false))
            return super.getPlayerRelativeBlockHardness(state, player, world, pos);
        return -1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockHorizontal.FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
        return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
    }
}
