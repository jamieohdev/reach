package net.nullean.reach.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.nullean.reach.Reach;

public interface ReachDimensions
{
    public static final ResourceKey<DimensionType> REACH_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(Reach.MOD_ID, "reach"));
    public static final ResourceKey<Level> REACH_LEVEL = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Reach.MOD_ID, "reach"));
    public static final ResourceKey<LevelStem> REACH_LEVELSTEM = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(Reach.MOD_ID, "reach"));
}
