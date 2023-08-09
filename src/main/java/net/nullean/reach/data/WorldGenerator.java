package net.nullean.reach.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachBiomes;
import net.nullean.reach.registry.ReachDimensionSettings;
import net.nullean.reach.registry.ReachDimensions;
import net.nullean.reach.world.carver.ReachConfiguredWorldCarvers;
import net.nullean.reach.world.feature.ModConfiguredFeatures;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenerator extends DatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.NOISE, (context) -> {
            })
            .add(Registries.DENSITY_FUNCTION, (context) -> {
            })
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrapConfiguredFeature)
            .add(Registries.PLACED_FEATURE, ModConfiguredFeatures::bootstrapPlacedFeature)
            .add(Registries.STRUCTURE, ModConfiguredFeatures::bootstrapStructures)
            .add(Registries.CONFIGURED_CARVER, ReachConfiguredWorldCarvers::bootstrap)
            .add(Registries.NOISE_SETTINGS, ReachDimensionSettings::bootstrapNoise)
            .add(Registries.DIMENSION_TYPE, ReachDimensionSettings::bootstrapDimensionType)
            .add(Registries.BIOME, ReachBiomes::bootstrap);
    ;


    public WorldGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries.thenApply(r -> createLookup()), Set.of(Reach.MOD_ID));
    }

    public static HolderLookup.Provider createLookup() {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
    }

    public static DataProvider createLevelStem(PackOutput output, ExistingFileHelper helper) {
        HolderLookup.Provider registry = createLookup();
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registry);
        HolderGetter<Biome> biomeRegistry = registry.lookupOrThrow(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = registry.lookupOrThrow(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = registry.lookupOrThrow(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator =
                new NoiseBasedChunkGenerator(
                        ReachBiomes.REACH_BIOMESOURCE.biomeSource(biomeRegistry, true),
                        noiseGenSettings.getOrThrow(ReachDimensionSettings.REACH_NOISE));

        LevelStem stem = new LevelStem(
                dimTypes.getOrThrow(ReachDimensions.REACH_TYPE),
                wrappedChunkGenerator);
        return new JsonCodecProvider<>(output, helper, Reach.MOD_ID, ops, PackType.SERVER_DATA, Registries.LEVEL_STEM.location().getPath(), LevelStem.CODEC, Map.of(ReachDimensions.REACH_LEVELSTEM.location(), stem));
    }
}
