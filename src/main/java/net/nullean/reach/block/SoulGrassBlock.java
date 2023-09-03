package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.nullean.reach.block.SoulPlantBlock;
import net.nullean.reach.registry.ReachBlocks;

public class SoulGrassBlock extends SoulPlantBlock implements BonemealableBlock, net.minecraftforge.common.IForgeShearable {
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public SoulGrassBlock(BlockBehaviour.Properties p_57318_) {
        super(p_57318_);
    }

    public VoxelShape getShape(BlockState p_57336_, BlockGetter p_57337_, BlockPos p_57338_, CollisionContext p_57339_) {
        return SHAPE;
    }

    public boolean isValidBonemealTarget(LevelReader p_255692_, BlockPos p_57326_, BlockState p_57327_, boolean p_57328_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_222583_, RandomSource p_222584_, BlockPos p_222585_, BlockState p_222586_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_222578_, RandomSource p_222579_, BlockPos p_222580_, BlockState p_222581_) {
        DoublePlantBlock doubleplantblock = (DoublePlantBlock)(p_222581_.is(Blocks.FERN) ? Blocks.LARGE_FERN : ReachBlocks.SOUL_GRASS_PLANT);
        if (doubleplantblock.defaultBlockState().canSurvive(p_222578_, p_222580_) && p_222578_.isEmptyBlock(p_222580_.above())) {
            DoublePlantBlock.placeAt(p_222578_, doubleplantblock.defaultBlockState(), p_222580_, 2);
        }

    }
}
