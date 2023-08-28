package net.nullean.reach.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.HolderSet;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.worldgen.*;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.DripstoneClusterFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.*;
import net.nullean.reach.Reach;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.registry.ReachFeatures;
import net.nullean.reach.registry.ReachTags;
import net.nullean.reach.world.feature.configurations.*;
import net.nullean.reach.world.placements.ReachPlacements;

import java.util.List;
import java.util.Map;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEMISH_TOP_LAYER = registerKey("blemish_top_layer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEMISHSTONE_CLUSTER = registerKey("blemishstone_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEMISH_VEGETATION = registerKey("reach_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  BLEMISH_VINES = registerKey("blemish_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>>  GOOP_FEATURE = registerKey("goop");

    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_BLUE_LARGE_FEATURE = registerKey("shard_large_blue");
    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_GREEN_LARGE_FEATURE = registerKey("shard_large_green");
    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_RED_LARGE_FEATURE = registerKey("shard_large_red");
    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_BLUE_SMALL_FEATURE = registerKey("shard_small_blue");
    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_GREEN_SMALL_FEATURE = registerKey("shard_small_green");
    public static final ResourceKey<ConfiguredFeature<? , ?>> SHARD_RED_SMALL_FEATURE = registerKey("shard_small_red");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLEMISH_ROOTS = registerKey("patch_blemish_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RAFFLESIA = registerKey("patch_rafflesia");

    public static void bootstrapConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context) {
       //ReachTreeFeatures.bootstrap(context);
       //ReachConfiguredFeatures.bootstrap(context);
    }

    public static void bootstrapPlacedFeature(BootstapContext<PlacedFeature> context) {
        //ReachOrePlacements.bootstrap(context);
        ReachPlacements.bootstrap(context);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> p_256015_, Map<MobCategory, StructureSpawnOverride> p_256297_, GenerationStep.Decoration p_255729_, TerrainAdjustment p_255865_) {
        return new Structure.StructureSettings(p_256015_, p_256297_, p_255729_, p_255865_);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> p_255731_, GenerationStep.Decoration p_256551_, TerrainAdjustment p_256463_) {
        return structure(p_255731_, Map.of(), p_256551_, p_256463_);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> p_256501_, TerrainAdjustment p_255704_) {
        return structure(p_256501_, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, p_255704_);
    }

    public static void bootstrapStructures(BootstapContext<Structure> context) {
        HolderGetter<Biome> holdergetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> holdergetter1 = context.lookup(Registries.TEMPLATE_POOL);
        context.register(BuiltinStructures.SHIPWRECK, new ShipwreckStructure(structure(holdergetter.getOrThrow(ReachTags.Biomes.HAS_SHIPWRECK), TerrainAdjustment.NONE), false));
        context.register(BuiltinStructures.OCEAN_MONUMENT, new OceanMonumentStructure(structure(holdergetter.getOrThrow(ReachTags.Biomes.HAS_OCEAN_MONUMENT), Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN, 1, 2, 4))), MobCategory.UNDERGROUND_WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST), MobCategory.AXOLOTLS, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST)), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
        context.register(BuiltinStructures.OCEAN_RUIN_COLD, new OceanRuinStructure(structure(holdergetter.getOrThrow(ReachTags.Biomes.HAS_OCEAN_RUIN_COLD), TerrainAdjustment.NONE), OceanRuinStructure.Type.COLD, 0.3F, 0.9F));
        context.register(BuiltinStructures.OCEAN_RUIN_WARM, new OceanRuinStructure(structure(holdergetter.getOrThrow(ReachTags.Biomes.HAS_OCEAN_RUIN_WARM), TerrainAdjustment.NONE), OceanRuinStructure.Type.WARM, 0.3F, 0.9F));
        // context.register(BuiltinStructures.RUINED_PORTAL_OCEAN, new RuinedPortalStructure(structure(holdergetter.getOrThrow(ReachTags.Biomes.HAS_RUINED_PORTAL_OCEAN), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR, 0.0F, 0.8F, false, false, true, false, 1.0F)));
    }
    
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        FeatureUtils.register(context, BLEMISH_TOP_LAYER, ReachFeatures.BLEMISH_TOP_LAYER.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, BLEMISHSTONE_CLUSTER, ReachFeatures.BLEMISHSTONE_CLUSTER.get(), new BlemishstoneClusterConfiguration(12, UniformInt.of(3, 6), UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F), ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));

        FeatureUtils.register(context, BLEMISH_VINES, ReachFeatures.BLEMISH_VINES.get(), new NoneFeatureConfiguration());
        FeatureUtils.register(context, GOOP_FEATURE, ReachFeatures.GOOP_FEATURE.get(), new NoneFeatureConfiguration());

        FeatureUtils.register(context, SHARD_BLUE_LARGE_FEATURE, ReachFeatures.SHARD_BLUE_LARGE.get(), new ShardConfiguration1(30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F));
        FeatureUtils.register(context, SHARD_GREEN_LARGE_FEATURE, ReachFeatures.SHARD_GREEN_LARGE.get(), new ShardConfiguration(30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F));
        FeatureUtils.register(context, SHARD_RED_LARGE_FEATURE, ReachFeatures.SHARD_RED_LARGE.get(), new ShardConfiguration2 (30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), UniformFloat.of(0.0F, 0.3F), 4, 0.6F));

        FeatureUtils.register(context, SHARD_BLUE_SMALL_FEATURE, ReachFeatures.SHARD_BLUE_SMALL.get(), new ColumnFeatureConfiguration(UniformInt.of(2, 3), UniformInt.of(1, 5)));
        FeatureUtils.register(context, SHARD_GREEN_SMALL_FEATURE, ReachFeatures.SHARD_GREEN_SMALL.get(), new ColumnFeatureConfiguration(ConstantInt.of(1), UniformInt.of(1, 4)));
        FeatureUtils.register(context, SHARD_RED_SMALL_FEATURE, ReachFeatures.SHARD_RED_SMALL.get(), new ColumnFeatureConfiguration(UniformInt.of(1, 2), UniformInt.of(1, 5)));

        WeightedStateProvider weightedstateprovider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ReachBlocks.BLEMISH_ROOTS.get().defaultBlockState(), 87));
        FeatureUtils.register(context, BLEMISH_VEGETATION, ReachFeatures.BLEMISH_VEGETATION.get(), new ReachVegetationConfig(weightedstateprovider, 8, 4));

        FeatureUtils.register(context, PATCH_BLEMISH_ROOTS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ReachBlocks.BLEMISH_ROOTS.get()), 64));
        FeatureUtils.register(context, PATCH_RAFFLESIA, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ReachBlocks.RAFFLESIA.get()), 32));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Reach.MOD_ID, name));
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
