package net.nullean.reach.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.nullean.reach.registry.ReachBlocks;

public class CalmVinesFeature  extends Feature<NoneFeatureConfiguration> {
    public CalmVinesFeature(Codec<NoneFeatureConfiguration> p_67337_) {
        super(p_67337_);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_160628_) {
        WorldGenLevel worldgenlevel = p_160628_.level();
        BlockPos blockpos = p_160628_.origin();
        p_160628_.config();
        if (!worldgenlevel.isEmptyBlock(blockpos)) {
            return false;
        } else {
            for(Direction direction : Direction.values()) {
                if (direction != Direction.DOWN && VineBlock.isAcceptableNeighbour(worldgenlevel, blockpos.relative(direction), direction)) {
                    worldgenlevel.setBlock(blockpos, ReachBlocks.CALM_VINE.get().defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), Boolean.valueOf(true)), 2);
                    return true;
                }
            }

            return false;
        }
    }
}