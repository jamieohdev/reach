package net.nullean.reach.data.resource;

import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.registry.ReachSounds;
import net.nullean.reach.world.biome.ReachBiomeDefaultFeatures;

public class ReachBiomeBuilders {

    public static Biome plainsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
        ReachBiomeDefaultFeatures.addFishSpawns(builder1);
        //ReachBiomeDefaultFeatures.addUnderGroundFeature(builder);
       // ReachBiomeDefaultFeatures.underGroundMonsterSpawns(builder1);
        return makeDefaultBiome(builder, builder1);
    }

    public static Biome warmBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
        ReachBiomeDefaultFeatures.addWarmFishSpawns(builder1);
        //ReachBiomeDefaultFeatures.addUnderGroundFeature(builder);
        // ReachBiomeDefaultFeatures.underGroundMonsterSpawns(builder1);
        return makeDefaultBiome(builder, builder1);
    }

    public static Biome frozenBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder1 = new MobSpawnSettings.Builder();
        ReachBiomeDefaultFeatures.addColdFishSpawns(builder1);
        ReachBiomeDefaultFeatures.addBlueIce(builder);
        ReachBiomeDefaultFeatures.addIcebergs(builder);
        ReachBiomeDefaultFeatures.addFrozenSprings(builder);
        //ReachBiomeDefaultFeatures.addUnderGroundFeature(builder);
        // ReachBiomeDefaultFeatures.underGroundMonsterSpawns(builder1);
        return makeDefaultBiome(builder, builder1);
    }


    public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting) {
        return makeDefaultBiome(builder, mobSpawnSetting, ReachSounds.REACH_EXAMPLE_BGM);
    }

    public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder, MobSpawnSettings.Builder mobSpawnSetting, RegistryObject<SoundEvent> soundEvent) {
        //ReachBiomeDefaultFeatures.addDefaultCarvers(builder);
        //ReachBiomeDefaultFeatures.addDefaultOres(builder);
        return fullDefinition(
                Biome.Precipitation.NONE,
                1.0F,
                0.6F,
                new BiomeSpecialEffects.Builder()
                        .fogColor(4630224)
                        .skyColor(7907327)
                        .waterColor(0x3f_76_e4)
                        .waterFogColor(0x05_05_33)
                        .grassColorOverride(7115607)
                        .foliageColorOverride(7115607)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(new Music(soundEvent.getHolder().orElseThrow(), 12000, 24000, true))
                        .build(),
                mobSpawnSetting.build(),
                builder.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome fullDefinition(Biome.Precipitation precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }

}
