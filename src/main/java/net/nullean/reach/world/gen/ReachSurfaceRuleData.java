package net.nullean.reach.world.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.nullean.reach.registry.ReachBiomes;

public class ReachSurfaceRuleData {
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK.defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT.defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE.defaultBlockState().getBlock());

    private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SANDSTONE);

    private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
        return SurfaceRules.state(p_194811_.defaultBlockState());
    }

    public static SurfaceRules.RuleSource reachRealm() {
        return frostrealmLike(true, true, true);
    }

    public static SurfaceRules.RuleSource frostrealmLike(boolean p_198381_, boolean p_198382_, boolean p_198383_) {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();

            builder.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
            builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(25)), BEDROCK));


        SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterBlockCheck(0, 0);

        SurfaceRules.RuleSource surfacerules$rulesource5 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D), SurfaceRules.ifTrue(surfacerules$conditionsource8, POWDER_SNOW));
        SurfaceRules.RuleSource powderSnow = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(SurfaceRules.isBiome(ReachBiomes.GLACIERS), SurfaceRules.sequence(surfacerules$rulesource5, SurfaceRules.ifTrue(surfacerules$conditionsource8, SNOW_BLOCK))));

        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK),
                DIRT);

        SurfaceRules.RuleSource overworldLike = SurfaceRules.ifTrue(SurfaceRules.isBiome(ReachBiomes.PLAINS,
                ReachBiomes.FOREST), SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        ));

        SurfaceRules.RuleSource surfacerules$rulesource9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), overworldLike);

        builder.add(surfacerules$rulesource9);
        builder.add(powderSnow);

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));

    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
    }
}
