package net.nullean.reach.world.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CloudConfiguration(int bounds) implements FeatureConfiguration {
    public static final Codec<CloudConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("bounds").forGetter(CloudConfiguration::bounds)
    ).apply(instance, CloudConfiguration::new));
}