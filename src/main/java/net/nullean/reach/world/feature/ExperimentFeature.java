package net.nullean.reach.world.feature;


import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.world.feature.configurations.ExperimentConfig;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

import java.util.HashSet;

public class ExperimentFeature extends Feature<ExperimentConfig> {

    public ExperimentFeature(Codec<ExperimentConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ExperimentConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        ExperimentConfig config = context.config();
        HashSet<BlockPos> trigList = Sets.newHashSet();
        HashSet<BlockPos> clusterPos = Sets.newHashSet();
        boolean flag = false;
        int radiusCheck = config.xzRadius().sample(random) + 1;
        final int randomChance = random.nextInt(4);
        final int stepHeight = radiusCheck + 14 + Mth.nextInt(random, 10, 14);
        if (world.isStateAtPosition(blockPos.relative(config.crystal_direction().getDirection().getOpposite()),
                DripstoneUtils::isEmptyOrWaterOrLava)
                && world.getBlockState(blockPos).getBlock() == ReachBlocks.SOULSTONE.get()
                || world.getBlockState(blockPos).getBlock() == ReachBlocks.SOULSLATE.get()
                || world.getBlockState(blockPos).getBlock() == Blocks.SOUL_SOIL) {

            if (this.placeSpike(world, blockPos, radiusCheck, stepHeight, randomChance, trigList, config.crystal_direction().getDirection(), random)) {

                flag = placeCrystals(world, random, config, trigList, clusterPos, flag);

            }
        }

        return flag;
    }

    private boolean placeCrystals(WorldGenLevel world, RandomSource random, ExperimentConfig config, HashSet<BlockPos> trigList, HashSet<BlockPos> clusterPos, boolean flag) {
        for (BlockPos pos : trigList) {
            if (world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                this.setBlock(world, pos, config.crystal_state());
                //world.setBlock(pos, Blocks.SEA_LANTERN.defaultBlockState(), 2);
                clusterPos.add(pos);
                flag = true;
            }
        }
        for (BlockPos pos : clusterPos) {
            if (random.nextInt(6) == 0) {
                for (Direction direction : Direction.values()) {
                    BlockPos relative = pos.relative(direction);
                    if (random.nextBoolean() && world.isStateAtPosition(relative, DripstoneUtils::isEmptyOrWater)
                            && world.getBlockState(pos).equals(config.crystal_state())) {

                        BlockState blockState = random.nextFloat() > config.glinted_cluster_chance() ? config.cluster_state() : config.glinted_cluster();
                           //this.setBlock(world, relative, blockState.setValue(PollinatedClusterBlock.FACING, direction).setValue(PollinatedClusterBlock.WATERLOGGED, world.getFluidState(relative).getType() == Fluids.WATER));
                           //this.setBlock(pos, ReachBlocks.SHARD_WHITE.get(), 2);
                           // world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
                           this.setBlock(world, pos, config.glinted_cluster());
                    }
                }
            }
        }
        return flag;
    }

    public boolean placeSpike(LevelAccessor world, BlockPos blockPos, int startRadius, int height, int randomChance, HashSet<BlockPos> crystalPos, Direction direction, RandomSource random) {
        boolean flag = false;
        if (startRadius < 1) {
            return false;
        }
        for (int y = 0; y < height; y++) {
            int radius = startRadius - y / 2;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                    if (x * x + z * z <= radius * radius) {
                        if (direction == Direction.DOWN) {
                            if (world.isStateAtPosition(pos.below(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return placeSpike(world, blockPos.below(), startRadius / 2, height, randomChance, crystalPos, direction, random);
                            }
                        }
                        else if (direction == Direction.UP) {
                            BlockPos.MutableBlockPos mut = pos.mutable();
                            for (int i = 0; i < 10; i++) {
                                if (!world.isStateAtPosition(mut.above(), DripstoneUtils::isEmptyOrWaterOrLava)) break;
                                mut.move(Direction.UP);
                            }
                            pos = mut.immutable();

                            if (world.isStateAtPosition(pos.above(), DripstoneUtils::isEmptyOrWaterOrLava)) {
                                return false;
                            }
                        }
                        this.calciteBloom(world, pos.relative(direction), random, radius);
                        float delta = switch (randomChance) {
                            case 1 -> 11 * Mth.PI / 6;
                            case 2 -> Mth.PI / 6;
                            case 3 -> 7 * Mth.PI / 6;
                            case 0 -> 5 * Mth.PI / 6;
                            default -> throw new IllegalStateException("Unexpected value: " + randomChance);
                        };
                        float q = Mth.cos(delta) * y;
                        float k = Mth.sin(Mth.PI / 2) * y;
                        float l = Mth.sin(delta) * y;
                        float xx = direction == Direction.UP ? -q : q;
                        float yy = direction == Direction.UP ? -k : k;
                        float zz = direction == Direction.UP ? -l : l;
                        BlockPos trigPos = pos.offset(xx, yy, zz);
                        if (world.isStateAtPosition(trigPos, DripstoneUtils::isEmptyOrWaterOrLava)) {
                            crystalPos.add(trigPos);

                            flag = true;
                        } else {
                            crystalPos.remove(trigPos);
                        }
                    }
                }
            }
        }
        return flag;
    }

    private boolean calciteBloom(LevelAccessor world, BlockPos blockPos, RandomSource random, int crystalRadius) {
        int radius = crystalRadius / 5;
        int height = ConstantInt.of(1).sample(random);
        boolean flag = false;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -height; y <= height; y++) {
                    BlockPos pos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    for (Direction direction : Direction.values()) {
                        if (world.isStateAtPosition(pos.relative(direction), DripstoneUtils::isEmptyOrWaterOrLava)) {
                            world.setBlock(pos, ReachBlocks.SHARD_STONE.get().defaultBlockState(), 2);

                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }

}