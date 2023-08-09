package net.nullean.reach.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.world.feature.configurations.ReachVegetationConfig;

public class BlemishVegetationFeature extends Feature<ReachVegetationConfig> {
    public BlemishVegetationFeature(Codec<ReachVegetationConfig> p_66361_) {
        super(p_66361_);
    }

    public boolean place(FeaturePlaceContext<ReachVegetationConfig> p_160068_) {
        WorldGenLevel worldgenlevel = p_160068_.level();
        BlockPos blockpos = p_160068_.origin();
        BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
        ReachVegetationConfig netherforestvegetationconfig = p_160068_.config();
        RandomSource randomsource = p_160068_.random();
        if (!blockstate.is(ReachBlocks.BLEMISH_BLOCK.get()) || !blockstate.is(ReachBlocks.BLEMISH.get()) || !blockstate.is(ReachBlocks.BLEMISH_ROOTED_BLOCK.get()) ) {
            return false;
        } else {
            int i = blockpos.getY();
            if (i >= worldgenlevel.getMinBuildHeight() + 1 && i + 1 < worldgenlevel.getMaxBuildHeight()) {
                int j = 0;

                for(int k = 0; k < netherforestvegetationconfig.spreadWidth * netherforestvegetationconfig.spreadWidth; ++k) {
                    BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(netherforestvegetationconfig.spreadWidth) - randomsource.nextInt(netherforestvegetationconfig.spreadWidth), randomsource.nextInt(netherforestvegetationconfig.spreadHeight) - randomsource.nextInt(netherforestvegetationconfig.spreadHeight), randomsource.nextInt(netherforestvegetationconfig.spreadWidth) - randomsource.nextInt(netherforestvegetationconfig.spreadWidth));
                    BlockState blockstate1 = netherforestvegetationconfig.stateProvider.getState(randomsource, blockpos1);
                    if (worldgenlevel.isEmptyBlock(blockpos1) && blockpos1.getY() > worldgenlevel.getMinBuildHeight() && blockstate1.canSurvive(worldgenlevel, blockpos1)) {
                        worldgenlevel.setBlock(blockpos1, blockstate1, 2);
                        ++j;
                    }
                }

                return j > 0;
            } else {
                return false;
            }
        }
    }
}