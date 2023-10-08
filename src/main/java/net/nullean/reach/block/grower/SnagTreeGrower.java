package net.nullean.reach.block.grower;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.nullean.reach.registry.ReachFeatures;
import net.nullean.reach.world.feature.ModConfiguredFeatures;

public class SnagTreeGrower  extends AbstractTreeGrower {
    public SnagTreeGrower() {
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_255637_, boolean p_255764_) {
        return ModConfiguredFeatures.SNAG;
    }

}
