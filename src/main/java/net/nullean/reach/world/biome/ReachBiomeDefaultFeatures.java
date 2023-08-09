package net.nullean.reach.world.biome;

import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ReachBiomeDefaultFeatures
{
    public static void addDefaultCarvers(BiomeGenerationSettings.Builder builder)
    {

    }

    public static void addMonsterSpawns(MobSpawnSettings.Builder builder)
    {

    }

    public static void addAnimalSpawns(MobSpawnSettings.Builder builder)
    {

    }

    public static void addFrozenSprings(BiomeGenerationSettings.Builder p_194732_) {
        p_194732_.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA_FROZEN);
    }

    public static void addIcebergs(BiomeGenerationSettings.Builder p_126768_) {
        p_126768_.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_PACKED);
        p_126768_.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_BLUE);
    }

    public static void addBlueIce(BiomeGenerationSettings.Builder p_126770_) {
        p_126770_.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.BLUE_ICE);
    }

    public static void addFishSpawns(MobSpawnSettings.Builder builder)
    {
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 5, 1, 4));
        builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 25, 2, 8));
    }

    public static void addColdFishSpawns(MobSpawnSettings.Builder builder)
    {
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 5, 1, 4));
        builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 25, 2, 8));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 8));
    }

    public static void addWarmFishSpawns(MobSpawnSettings.Builder builder)
    {
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 10, 1, 2));
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 5, 1, 4));
        builder.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
  }

    public static void addMiscSpawns(MobSpawnSettings.Builder builder)
    {

    }

    public static void addDefaultOres(BiomeGenerationSettings.Builder builder)
    {

    }

    public static void addOceanFeatures(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION);
    }
}
