package net.nullean.reach.registry;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.world.feature.*;
import net.nullean.reach.world.feature.configurations.*;

public class ReachFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reach.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLEMISH_TOP_LAYER = FEATURES.register("blemish_top_layer", () -> new SnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<BlemishstoneClusterFeature> BLEMISHSTONE_CLUSTER = FEATURES.register("blemishstone_cluster", () -> new BlemishstoneClusterFeature(BlemishstoneClusterConfiguration.CODEC));

    public static final RegistryObject<BlemishVegetationFeature> BLEMISH_VEGETATION = FEATURES.register("reach_vegetation", () -> new BlemishVegetationFeature(ReachVegetationConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLEMISH_VINES = FEATURES.register("blemish_vines", () -> new BlemishVinesFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GOOP_FEATURE = FEATURES.register("goop", () -> new GoopFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<TreeConfiguration>> SNAG_TREE = FEATURES.register("snag_tree", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static final RegistryObject<Feature<ShardConfiguration1>> SHARD_BLUE_LARGE = FEATURES.register("shard_large_blue", () -> new ShardLargeFeature1(ShardConfiguration1.CODEC, ReachBlocks.SHARD_BLUE.get()));
    public static final RegistryObject<Feature<ShardConfiguration>> SHARD_GREEN_LARGE = FEATURES.register("shard_large_green", () -> new ShardLargeFeature(ShardConfiguration.CODEC, ReachBlocks.SHARD_GREEN.get()));
    public static final RegistryObject<Feature<ShardConfiguration2>> SHARD_RED_LARGE = FEATURES.register("shard_large_red", () -> new ShardLargeFeature2(ShardConfiguration2.CODEC, ReachBlocks.SHARD_RED.get()));

    public static final RegistryObject<Feature<ExperimentConfig>> XP_FLOOR = FEATURES.register("xp_floor", () -> new ExperimentFeature(ExperimentConfig.CODEC));
    public static final RegistryObject<Feature<ExperimentConfig>> XP_CEILING = FEATURES.register("xp_ceiling", () -> new ExperimentFeature(ExperimentConfig.CODEC));

    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_BLUE_SMALL = FEATURES.register("shard_small_blue", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_BLUE.get()));
    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_GREEN_SMALL = FEATURES.register("shard_small_green", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_GREEN.get()));
    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_RED_SMALL = FEATURES.register("shard_small_red", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_RED.get()));
    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_CYAN_SMALL = FEATURES.register("shard_small_cyan", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_CYAN.get()));
    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_MAGENTA_SMALL = FEATURES.register("shard_small_magenta", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_MAGENTA.get()));
    public static final RegistryObject<Feature<ColumnFeatureConfiguration>> SHARD_WHITE_SMALL = FEATURES.register("shard_small_white", () -> new ShardSmallFeature(ColumnFeatureConfiguration.CODEC, ReachBlocks.SHARD_WHITE.get()));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CALM_VINES = FEATURES.register("calm_vines", () -> new CalmVinesFeature(NoneFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<CloudConfiguration>> CLOUD = FEATURES.register("cloud", () -> new CloudFeature(CloudConfiguration.CODEC));

    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }
}
