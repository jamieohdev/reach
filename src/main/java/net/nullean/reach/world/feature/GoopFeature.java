package net.nullean.reach.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.nullean.reach.registry.ReachBlocks;

public class GoopFeature extends Feature<NoneFeatureConfiguration> {
    public GoopFeature(Codec<NoneFeatureConfiguration> p_65865_) {
        super(p_65865_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159861_) {
        WorldGenLevel worldgenlevel = p_159861_.level();
        BlockPos blockpos = p_159861_.origin();
        RandomSource randomsource = p_159861_.random();
        if (!worldgenlevel.isEmptyBlock(blockpos)) {
            return false;
        } else {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos.above());
            if (!blockstate.is(ReachBlocks.SOULSLATE.get()) && !blockstate.is(ReachBlocks.SOULSTONE.get()) && !blockstate.is(ReachBlocks.GOOP.get())) {
                return false;
            } else {
                worldgenlevel.setBlock(blockpos, ReachBlocks.GOOP.get().defaultBlockState(), 2);

                for(int i = 0; i < 1500; ++i) {
                    BlockPos blockpos1 = blockpos.offset(
                            randomsource.nextInt(8) - randomsource.nextInt(8),
                            -randomsource.nextInt(12),
                            randomsource.nextInt(8) - randomsource.nextInt(8));
                    if (worldgenlevel.getBlockState(blockpos1).isAir()) {
                        int j = 0;

                        for(Direction direction : Direction.values()) {
                            if (worldgenlevel.getBlockState(blockpos1.relative(direction)).is( ReachBlocks.GOOP.get())) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            worldgenlevel.setBlock(blockpos1,  ReachBlocks.GOOP.get().defaultBlockState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}