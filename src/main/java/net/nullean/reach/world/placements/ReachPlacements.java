package net.nullean.reach.world.placements;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.world.carver.ReachConfiguredWorldCarvers;
import net.nullean.reach.world.feature.ModConfiguredFeatures;

import java.util.List;

public class ReachPlacements
{
    private static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final ResourceKey<PlacedFeature> PATCH_BLEMISH_ROOTS = PlacementUtils.createKey("patch_blemish_roots");
    public static final ResourceKey<PlacedFeature> PATCH_RAFFLESIA = PlacementUtils.createKey("patch_rafflesia");
    public static final ResourceKey<PlacedFeature> BLEMISH_TOP_LAYER = registerKey("blemish_top_layer");
    public static final ResourceKey<PlacedFeature> SHARD_LARGE_BLUE = registerKey("shard_large_blue");
    public static final ResourceKey<PlacedFeature> SHARD_LARGE_GREEN = registerKey("shard_large_green");
    public static final ResourceKey<PlacedFeature> SHARD_LARGE_RED = registerKey("shard_large_red");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_BLUE = registerKey("shard_large_blue");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_GREEN = registerKey("shard_small_green");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_RED = registerKey("shard_small_red");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_CYAN = registerKey("shard_large_cyan");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_MAGENTA = registerKey("shard_small_magebnta");
    public static final ResourceKey<PlacedFeature> SHARD_SMALL_WHITE = registerKey("shard_small_white");

    public static final ResourceKey<PlacedFeature> SNAG_CHECKED = registerKey("snag_checked");
    public static final ResourceKey<PlacedFeature> SNAG_TREE = registerKey("trees_snag_snag");

    public static final ResourceKey<PlacedFeature> PATCH_GRASS = registerKey("patch_grass");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS = registerKey("patch_tall_grass");
    public static final ResourceKey<PlacedFeature> PATCH_PUMPKIN = registerKey("patch_pumpkin");


    public static final ResourceKey<PlacedFeature> CALM_VINES = registerKey("calm_vines");
    public static final ResourceKey<PlacedFeature> CLOUD = registerKey("cloud");

    public static final ResourceKey<PlacedFeature> XP_FLOOR = registerKey("xp_floor");
    //, GConfiguredFeatures.ALLURITE_CRYSTAL_FLOOR.getHolder().get(), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final ResourceKey<PlacedFeature> XP_CEILING = registerKey("xp_ceiling");//, GConfiguredFeatures.LUMIERE_CRYSTAL_FLOOR.getHolder().get(), CountPlacement.of(UniformInt.of(180, 200)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());


    public static String prefix(String name) {
        return Reach.MOD_ID + ":" + name;
    }


    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Reach.prefix(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        final Holder<ConfiguredFeature<?, ?>> SHARD_BLUE_LARGE_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_BLUE_LARGE_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_GREEN_LARGE_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_GREEN_LARGE_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_RED_LARGE_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_RED_LARGE_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_BLUE_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_BLUE_SMALL_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_GREEN_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_GREEN_SMALL_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_RED_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_RED_SMALL_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_CYAN_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_CYAN_SMALL_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_MAGENTA_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_MAGENTA_SMALL_FEATURE);
        final Holder<ConfiguredFeature<?, ?>> SHARD_WHITE_SMALL_PLACEMENT = configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_WHITE_SMALL_FEATURE);

        PlacementUtils.register(context, BLEMISH_TOP_LAYER, configuredFeature.getOrThrow(ModConfiguredFeatures.BLEMISH_TOP_LAYER), BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_RAFFLESIA, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_RAFFLESIA), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_BLEMISH_ROOTS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_BLEMISH_ROOTS), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, CALM_VINES, configuredFeature.getOrThrow(ModConfiguredFeatures.CALM_VINES), BiomeFilter.biome());

        PlacementUtils.register(context, CLOUD, configuredFeature.getOrThrow(ModConfiguredFeatures.CLOUD), BiomeFilter.biome());

        Holder<ConfiguredFeature<?, ?>> holder0 = configuredFeature.getOrThrow(TreeFeatures.PINE);
        PlacementUtils.register(context, SNAG_CHECKED, holder0, PlacementUtils.filteredByBlockSurvival(ReachBlocks.SNAG_SAPLING.get()));
        PlacementUtils.register(context, SNAG_TREE, configuredFeature.getOrThrow(ModConfiguredFeatures.SNAG_TREE), PlacementUtils.filteredByBlockSurvival(ReachBlocks.SNAG_SAPLING.get()));

        PlacementUtils.register(context, PATCH_PUMPKIN, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_PUMPKIN), RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_GRASS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_GRASS), worldSurfaceSquaredWithCount(125));
        PlacementUtils.register(context, PATCH_TALL_GRASS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_TALL_GRASS), RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());

        //PlacementUtils.register(context, SHARD_LARGE_BLUE, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_BLUE_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_BLUE, SHARD_BLUE_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        //PlacementUtils.register(context, SHARD_LARGE_GREEN, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_GREEN_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_GREEN, SHARD_GREEN_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        //PlacementUtils.register(context, SHARD_LARGE_RED, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_RED_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_RED, SHARD_RED_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

        register(context, ReachPlacements.SHARD_SMALL_BLUE, SHARD_BLUE_SMALL_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_GREEN, SHARD_GREEN_SMALL_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_RED, SHARD_RED_SMALL_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_CYAN, SHARD_CYAN_SMALL_PLACEMENT, List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_MAGENTA, SHARD_MAGENTA_SMALL_PLACEMENT, List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_WHITE, SHARD_WHITE_SMALL_PLACEMENT, List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

        PlacementUtils.register(context, XP_FLOOR, configuredFeature.getOrThrow(ModConfiguredFeatures.XP_FLOOR), CountPlacement.of(UniformInt.of(10, 20)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, XP_CEILING, configuredFeature.getOrThrow(ModConfiguredFeatures.XP_CEILING), CountPlacement.of(UniformInt.of(10, 20)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers)
    {
        register(context, placedFeatureKey, configuredFeature, List.of(modifiers));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers)
    {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }

        private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier p_195485_) {
        return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier p_195480_) {
        return treePlacementBase(p_195480_).build();
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier p_195482_, Block p_195483_) {
        return treePlacementBase(p_195482_).add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(p_195483_.defaultBlockState(), BlockPos.ZERO))).build();
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int p_195475_) {
        return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }
}
