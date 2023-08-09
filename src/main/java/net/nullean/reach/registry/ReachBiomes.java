package net.nullean.reach.registry;


import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.nullean.reach.Reach;
import net.nullean.reach.data.resource.ReachBiomeBuilders;
import net.nullean.reach.world.biome.ReachBiomeBuilder;

public class ReachBiomes {
    public static final MultiNoiseBiomeSource.Preset REACH_BIOMESOURCE = new MultiNoiseBiomeSource.Preset(new ResourceLocation("reach"), (p_255954_) -> {
        return new Climate.ParameterList<>(ImmutableList.of(
                Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(ReachBiomes.SOUL_PLAINS)),
                Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(Biomes.CRIMSON_FOREST)),
                Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), p_255954_.getOrThrow(Biomes.WARPED_FOREST)),
                Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), p_255954_.getOrThrow(Biomes.BASALT_DELTAS)),
                Pair.of(Climate.parameters(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(ReachBiomes.BLEMISH))));
    });
    public static final MultiNoiseBiomeSource.Preset REACH_BIOMESOURCE1 = new MultiNoiseBiomeSource.Preset(Reach.prefix("reach"), (p_187108_) -> {
        ImmutableList.Builder<Pair<Climate.ParameterPoint, Holder<Biome>>> builder = ImmutableList.builder();
        (new ReachBiomeBuilder()).addBiomes((p_204279_) -> {
            builder.add(p_204279_.mapSecond(p_187108_::getOrThrow));
        });
        return new Climate.ParameterList<>(builder.build());
    });

    /**
     * BIOMES:
     *
     * UpsideDown (Trees/Vegetation)
     * Shards/Barrens (Big glasssy shards)
     * Pillars? (Like just super weird pillars everywhere)
     * Soul Plains (Vegetation)
     * Infected (Biome overrun with infected stuff)
     * Heaven/Calm
     */

    // public static final ResourceKey<Biome> EXAMPLE_BIOME = register("example");

    // Sky-based Biomes
    public static final ResourceKey<Biome> SKY = register("sky");
    public static final ResourceKey<Biome> SKY_2 = register("sky2");


    public static final ResourceKey<Biome> BLEMISH = register("blemish");
    public static final ResourceKey<Biome> SOUL_PLAINS = register("soul_plains");;
    public static final ResourceKey<Biome> BARRENS = register("barrens");
    public static final ResourceKey<Biome> PILLARS = register("pillars");
    public static final ResourceKey<Biome> UPSIDEDOWN = register("upsidedown");
    public static final ResourceKey<Biome> CALM = register("calm");

    // Land-based Biomes
    public static final ResourceKey<Biome> PLAINS = register("plains");
    public static final ResourceKey<Biome> FOREST = register("forest");
    public static final ResourceKey<Biome> HILLS = register("hills");
    public static final ResourceKey<Biome> SWAMP = register("swamp");
    public static final ResourceKey<Biome> MUSHROOMS = register("mushrooms");

    // Transitional Biomes
    public static final ResourceKey<Biome> RIVER = register("river");
    public static final ResourceKey<Biome> BEACH = register("beach");
    // Generic Oceans with tempts for testing
    public static final ResourceKey<Biome> OCEAN_BOILING = register("boiling");
    public static final ResourceKey<Biome> OCEAN_HOT= register("hot");
    public static final ResourceKey<Biome> OCEAN = register("ocean");
    public static final ResourceKey<Biome> OCEAN_COLD = register("cold");
    public static final ResourceKey<Biome> OCEAN_FROZEN = register("frozen");
    // Actual Biome Ideas
    public static final ResourceKey<Biome> KELP_FOREST = register("kelpforest");
    public static final ResourceKey<Biome> CORAL_HAVEN = register("coralhaven");
    public static final ResourceKey<Biome> TOPSY_TURVY = register("topsyturvy");
    public static final ResourceKey<Biome> GLACIERS = register("glaciers");       // temporary name
    public static final ResourceKey<Biome> VILLAGE = register("village");
    public static final ResourceKey<Biome> DANKNESS = register("dankness");

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
// TODO: USE <EXAMPLE>BIOME(PAR1, PAR2) INSTEAD OF PLAINSBIOME
        context.register(BLEMISH, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SOUL_PLAINS, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(BARRENS, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(PILLARS, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(UPSIDEDOWN, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(CALM, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));

        /**context.register(PLAINS, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(FOREST, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(HILLS, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SWAMP, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(MUSHROOMS, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));

        context.register(RIVER, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(BEACH, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));

        context.register(OCEAN_BOILING, ReachBiomeBuilders.warmBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(OCEAN_HOT, ReachBiomeBuilders.warmBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(OCEAN, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(OCEAN_COLD, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(OCEAN_FROZEN, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));

        context.register(KELP_FOREST, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(CORAL_HAVEN, ReachBiomeBuilders.warmBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(TOPSY_TURVY, ReachBiomeBuilders.plainsBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(GLACIERS, ReachBiomeBuilders.frozenBiome(placedFeatures, vanillaConfiguredCarvers));**/
    }

    private static ResourceKey<Biome> register(String p_48229_) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Reach.MOD_ID, p_48229_));
    }

    public static void addBiomeTypes() {
    }
}
