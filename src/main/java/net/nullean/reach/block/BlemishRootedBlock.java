package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.nullean.reach.registry.ReachBlocks;

public class BlemishRootedBlock extends Block
{
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    protected BlemishRootedBlock(BlockBehaviour.Properties p_56817_) {
        super(p_56817_);
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, Boolean.valueOf(false)));
    }

    public BlockState updateShape(BlockState p_56644_, Direction p_56645_, BlockState p_56646_, LevelAccessor p_56647_, BlockPos p_56648_, BlockPos p_56649_) {
        return p_56645_ == Direction.UP ? p_56644_.setValue(SNOWY, Boolean.valueOf(isSnowySetting(p_56646_))) : super.updateShape(p_56644_, p_56645_, p_56646_, p_56647_, p_56648_, p_56649_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_56642_) {
        BlockState blockstate = p_56642_.getLevel().getBlockState(p_56642_.getClickedPos().above());
        return this.defaultBlockState().setValue(SNOWY, Boolean.valueOf(isSnowySetting(blockstate)));
    }

    private static boolean isSnowySetting(BlockState p_154649_) {
        return p_154649_.is(BlockTags.SNOW);
    }

    private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
        BlockPos blockpos = p_56826_.above();
        BlockState blockstate = p_56825_.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
            return i < p_56825_.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
        BlockPos blockpos = p_56830_.above();
        return canBeGrass(p_56828_, p_56829_, p_56830_) && !p_56829_.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState p_222508_, ServerLevel p_222509_, BlockPos p_222510_, RandomSource p_222511_) {
        if (!canBeGrass(p_222508_, p_222509_, p_222510_)) {
            if (!p_222509_.isAreaLoaded(p_222510_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            p_222509_.setBlockAndUpdate(p_222510_, ReachBlocks.BLEMISH_BLOCK.get().defaultBlockState());
        } else {
            if (!p_222509_.isAreaLoaded(p_222510_, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (p_222509_.getMaxLocalRawBrightness(p_222510_.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = p_222510_.offset(p_222511_.nextInt(3) - 1, p_222511_.nextInt(5) - 3, p_222511_.nextInt(3) - 1);
                    if (p_222509_.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, p_222509_, blockpos)) {
                        p_222509_.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, p_222509_.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                    }
                }
            }

        }
    }
}