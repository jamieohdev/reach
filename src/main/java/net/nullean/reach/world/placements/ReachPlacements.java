package net.nullean.reach.world.placements;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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

        PlacementUtils.register(context, BLEMISH_TOP_LAYER, configuredFeature.getOrThrow(ModConfiguredFeatures.BLEMISH_TOP_LAYER), BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_RAFFLESIA, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_RAFFLESIA), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_BLEMISH_ROOTS, configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_BLEMISH_ROOTS), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, BLEMISH_TOP_LAYER, configuredFeature.getOrThrow(ModConfiguredFeatures.BLEMISH_TOP_LAYER), BiomeFilter.biome());


        //PlacementUtils.register(context, SHARD_LARGE_BLUE, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_BLUE_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_BLUE, SHARD_BLUE_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        //PlacementUtils.register(context, SHARD_LARGE_GREEN, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_GREEN_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_GREEN, SHARD_GREEN_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        //PlacementUtils.register(context, SHARD_LARGE_RED, configuredFeature.getOrThrow(ModConfiguredFeatures.SHARD_RED_LARGE_FEATURE), NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        //register(context, ReachPlacements.SHARD_LARGE_RED, SHARD_RED_LARGE_PLACEMENT, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

        register(context, ReachPlacements.SHARD_SMALL_BLUE, SHARD_BLUE_SMALL_PLACEMENT, List.of(CountPlacement.of(100), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_GREEN, SHARD_GREEN_SMALL_PLACEMENT, List.of(CountPlacement.of(100), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, ReachPlacements.SHARD_SMALL_RED, SHARD_RED_SMALL_PLACEMENT, List.of(CountPlacement.of(100), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

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
}
