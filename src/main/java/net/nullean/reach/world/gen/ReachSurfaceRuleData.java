package net.nullean.reach.world.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.nullean.reach.registry.ReachBiomes;
import net.nullean.reach.registry.ReachBlocks;

public class ReachSurfaceRuleData {
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK.defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT.defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE.defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource CALMSTONE = makeStateRule(ReachBlocks.CALMSTONE.get().defaultBlockState().getBlock());
    private static final SurfaceRules.RuleSource WHITESTONE = makeStateRule(ReachBlocks.WHITESTONE.get().defaultBlockState().getBlock());

    private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SANDSTONE);

    private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
        return SurfaceRules.state(p_194811_.defaultBlockState());
    }

    public static SurfaceRules.RuleSource reachRealm() {
        return reachLike(true, true, true);
    }

    public static SurfaceRules.RuleSource reachLike(boolean p_198381_, boolean p_198382_, boolean p_198383_) {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();

            builder.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
            builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(25)), BEDROCK));


        SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.RuleSource surfacerules$rulesource3 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ReachBiomes.CALM),
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125D, 0.0125D),
                        WHITESTONE), CALMSTONE)));

        SurfaceRules.RuleSource surfacerules$rulesource5 = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D), SurfaceRules.ifTrue(surfacerules$conditionsource8, POWDER_SNOW));

        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK),
                DIRT);



        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));

    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
    }
}
