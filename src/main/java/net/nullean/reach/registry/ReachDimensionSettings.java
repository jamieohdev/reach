package net.nullean.reach.registry;

import com.mojang.serialization.DataResult;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.nullean.reach.Reach;
import net.nullean.reach.world.gen.ReachNoiseRouterData;
import net.nullean.reach.world.gen.ReachSurfaceRuleData;

import java.util.List;
import java.util.OptionalLong;

public class ReachDimensionSettings {
    public static final ResourceLocation EFFECTS = new ResourceLocation(Reach.MOD_ID, "renderer");

    static final NoiseSettings Reach_NOISE_SETTINGS = create(-80, 384, 1, 2);

    public static final ResourceKey<NoiseGeneratorSettings> REACH_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(Reach.MOD_ID, "reach_noise"));

    public static NoiseGeneratorSettings reachNoise(BootstapContext<NoiseGeneratorSettings> p_256365_) {
        return new NoiseGeneratorSettings(Reach_NOISE_SETTINGS, Blocks.STONE.defaultBlockState(), Blocks.WATER.defaultBlockState(),
                ReachNoiseRouterData.reachRealm(p_256365_.lookup(Registries.DENSITY_FUNCTION), p_256365_.lookup(Registries.NOISE), false, false),
                ReachSurfaceRuleData.reachRealm(), List.of(), 64, false, true, false,
                false);
    }


    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> p_256365_) {
        p_256365_.register(REACH_NOISE, reachNoise(p_256365_));
    }

    public static void bootstrapDimensionType(BootstapContext<DimensionType> p_256376_) {
        p_256376_.register(ReachDimensions.REACH_TYPE, ReachDimType());
    }

    private static DimensionType ReachDimType() {
        return new DimensionType(
                OptionalLong.empty(),
                true, //skylight
                true, //ceiling
                false, //ultrawarm
                true, //natural
                1.0D, //coordinate scale
                true, //bed works
                false, //respawn anchor works
                -64,
                384,
                384, // Logical Height
                BlockTags.INFINIBURN_OVERWORLD, //infiburn
                Reach.prefix("renderer"), // DimensionRenderInfo
                0.2F, // Wish this could be set to -0.05 since it'll make the world truly blacked out if an area is not sky-lit (see: Dark Forests) Sadly this also messes up night vision so it gets 0
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 15)
        );
    }

    private static DataResult<NoiseSettings> guardY(NoiseSettings p_158721_) {
        if (p_158721_.minY() + p_158721_.height() > DimensionType.MAX_Y + 1) {
            return DataResult.error("min_y + height cannot be higher than: " + (DimensionType.MAX_Y + 1));
        } else if (p_158721_.height() % 16 != 0) {
            return DataResult.error("height has to be a multiple of 16");
        } else {
            return p_158721_.minY() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(p_158721_);
        }
    }

    public static NoiseSettings create(int p_224526_, int p_224527_, int p_224528_, int p_224529_) {
        NoiseSettings noisesettings = new NoiseSettings(p_224526_, p_224527_, p_224528_, p_224529_);
        guardY(noisesettings).error().ifPresent((p_158719_) -> {
            throw new IllegalStateException(p_158719_.message());
        });
        return noisesettings;
    }
}
