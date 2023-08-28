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

public class ReachBiomes {
    public static final MultiNoiseBiomeSource.Preset REACH_BIOMESOURCE = new MultiNoiseBiomeSource.Preset(new ResourceLocation("reach"), (p_255954_) -> {
        return new Climate.ParameterList<>(ImmutableList.of(
                Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(ReachBiomes.SOUL_PLAINS)),
                Pair.of(Climate.parameters(1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(ReachBiomes.PILLARS)),
                Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(Biomes.CRIMSON_FOREST)),
                Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), p_255954_.getOrThrow(Biomes.WARPED_FOREST)),
                Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), p_255954_.getOrThrow(Biomes.BASALT_DELTAS)),
                Pair.of(Climate.parameters(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), p_255954_.getOrThrow(ReachBiomes.BLEMISH))));
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
     * Jelly
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
    }

    private static ResourceKey<Biome> register(String p_48229_) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Reach.MOD_ID, p_48229_));
    }

    public static void addBiomeTypes() {
    }
}
