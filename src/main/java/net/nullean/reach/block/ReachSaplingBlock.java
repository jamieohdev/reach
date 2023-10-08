package net.nullean.reach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.nullean.reach.registry.ReachBlocks;

public class ReachSaplingBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private final AbstractTreeGrower treeGrower;

    public ReachSaplingBlock(AbstractTreeGrower p_55978_, BlockBehaviour.Properties p_55979_) {
        super(p_55979_);
        this.treeGrower = p_55978_;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
    }

    public boolean canSurvive(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) {
        BlockPos blockpos = p_51030_.below();
        if (p_51028_.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return p_51029_.getBlockState(blockpos).canSustainPlant(p_51029_, blockpos, Direction.UP, this);
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
    }


    @Override
    protected boolean mayPlaceOn(BlockState p_53623_, BlockGetter p_53624_, BlockPos p_53625_) {
        return
                p_53623_.is(BlockTags.DIRT) ||
                        p_53623_.is(Blocks.MYCELIUM) ||
                        p_53623_.is(ReachBlocks.SOUL_GRASS_BLOCK.get()) ||
                        p_53623_.is(Blocks.SOUL_SOIL) ||
                        super.mayPlaceOn(p_53623_, p_53624_, p_53625_);
    }

    public VoxelShape getShape(BlockState p_56008_, BlockGetter p_56009_, BlockPos p_56010_, CollisionContext p_56011_) {
        return SHAPE;
    }

    public void randomTick(BlockState p_222011_, ServerLevel p_222012_, BlockPos p_222013_, RandomSource p_222014_) {
        if (!p_222012_.isAreaLoaded(p_222013_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (p_222012_.getMaxLocalRawBrightness(p_222013_.above()) >= 9 && p_222014_.nextInt(7) == 0) {
            this.advanceTree(p_222012_, p_222013_, p_222011_, p_222014_);
        }

    }

    public void advanceTree(ServerLevel p_222001_, BlockPos p_222002_, BlockState p_222003_, RandomSource p_222004_) {
        if (p_222003_.getValue(STAGE) == 0) {
            p_222001_.setBlock(p_222002_, p_222003_.cycle(STAGE), 4);
        } else {
            this.treeGrower.growTree(p_222001_, p_222001_.getChunkSource().getGenerator(), p_222002_, p_222003_, p_222004_);
        }

    }

    public boolean isValidBonemealTarget(LevelReader p_256124_, BlockPos p_55992_, BlockState p_55993_, boolean p_55994_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_222006_, RandomSource p_222007_, BlockPos p_222008_, BlockState p_222009_) {
        return (double)p_222006_.random.nextFloat() < 0.45D;
    }

    public void performBonemeal(ServerLevel p_221996_, RandomSource p_221997_, BlockPos p_221998_, BlockState p_221999_) {
        this.advanceTree(p_221996_, p_221998_, p_221999_, p_221997_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56001_) {
        p_56001_.add(STAGE);
    }
}
