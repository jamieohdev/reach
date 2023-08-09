package net.nullean.reach.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.nullean.reach.registry.ReachBlocks;

public class BlemishVinesFeature extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public BlemishVinesFeature(Codec<NoneFeatureConfiguration> p_67375_) {
        super(p_67375_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration>  p_160558_) {
        WorldGenLevel worldgenlevel = p_160558_.level();
        BlockPos blockpos = p_160558_.origin();
        if (isInvalidPlacementLocation(worldgenlevel, blockpos)) {
            return false;
        } else {
            RandomSource randomsource = p_160558_.random();
            int i = 8; // 8, 4, 8
            int j = 4;
            int k = 8;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = 0; l < i * i; ++l) {
                blockpos$mutableblockpos.set(blockpos).move(Mth.nextInt(randomsource, -i, i), Mth.nextInt(randomsource, -j, j), Mth.nextInt(randomsource, -i, i));
                if (findFirstAirBlockAboveGround(worldgenlevel, blockpos$mutableblockpos) && !isInvalidPlacementLocation(worldgenlevel, blockpos$mutableblockpos)) {
                    int i1 = Mth.nextInt(randomsource, 1, k);
                    if (randomsource.nextInt(6) == 0) {
                        i1 *= 2;
                    }

                    if (randomsource.nextInt(5) == 0) {
                        i1 = 1;
                    }

                    int j1 = 17;
                    int k1 = 25;
                    placeWeepingVinesColumn(worldgenlevel, randomsource, blockpos$mutableblockpos, i1, 17, 25);
                }
            }

            return true;
        }
    }

    private static boolean findFirstAirBlockAboveGround(LevelAccessor p_67294_, BlockPos.MutableBlockPos p_67295_) {
        do {
            p_67295_.move(0, -1, 0);
            if (p_67294_.isOutsideBuildHeight(p_67295_)) {
                return false;
            }
        } while(p_67294_.getBlockState(p_67295_).isAir());

        p_67295_.move(0, 1, 0);
        return true;
    }

    public static void placeWeepingVinesColumn(LevelAccessor p_225301_, RandomSource p_225302_, BlockPos.MutableBlockPos p_225303_, int p_225304_, int p_225305_, int p_225306_) {
        for(int i = 1; i <= p_225304_; ++i) {
            if (p_225301_.isEmptyBlock(p_225303_)) {
                if (i == p_225304_ || !p_225301_.isEmptyBlock(p_225303_.above())) {
                    p_225301_.setBlock(p_225303_, ReachBlocks.BLEMISH_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Integer.valueOf(Mth.nextInt(p_225302_, p_225305_, p_225306_))), 2);
                    break;
                }

                p_225301_.setBlock(p_225303_, ReachBlocks.BLEMISH_VINES_PLANT.get().defaultBlockState(), 2);
            }

            p_225303_.move(Direction.UP);
        }

    }

    private static boolean isInvalidPlacementLocation(LevelAccessor p_67297_, BlockPos p_67298_) {
        if (!p_67297_.isEmptyBlock(p_67298_)) {
            return true;
        } else {
            BlockState blockstate = p_67297_.getBlockState(p_67298_.below());
            return !blockstate.is(ReachBlocks.BLEMISH_BLOCK.get()) && !blockstate.is(ReachBlocks.BLEMISH.get()) && !blockstate.is(ReachBlocks.BLEMISH_ROOTED_BLOCK.get()) && !blockstate.is(ReachBlocks.BLEMISH_STONE.get());
        }
    }
}