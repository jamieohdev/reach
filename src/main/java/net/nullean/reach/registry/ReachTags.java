package net.nullean.reach.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.nullean.reach.Reach;

public class ReachTags
{
    public static class Biomes
    {
        public static final TagKey<Biome> IS_DEEP_OCEAN = create("is_deep_ocean");
        public static final TagKey<Biome> IS_OCEAN = create("is_ocean");
        public static final TagKey<Biome> REQUIRED_OCEAN_MONUMENT_SURROUNDING = create("required");
        public static final TagKey<Biome> HAS_OCEAN_MONUMENT = create("has_structure/ocean_monument");
        public static final TagKey<Biome> HAS_OCEAN_RUIN_COLD = create("has_structure/ocean_ruin_cold");
        public static final TagKey<Biome> HAS_OCEAN_RUIN_WARM = create("has_structure/ocean_ruin_warm");
        public static final TagKey<Biome> HAS_SHIPWRECK = create("has_structure/shipwreck");

        private static TagKey<Biome> create(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(name));
        }
    }

    public static class Blocks
    {
        public static final TagKey<Block> BASE_STONE_REACH = tag("base_stone_reach");
        public static final TagKey<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
        public static final TagKey<Block> HOT_SOURCE = tag("hot_source");
        public static final TagKey<Block> WORLD_CARVER_REPLACEABLE = tag("world_replaceable");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Reach.MOD_ID, name));
        }
    }

    public static class EntityTypes
    {
        public static final TagKey<EntityType<?>> COLD_WEATHER_IMMUNE = tag("cold_weather_immune");

        private static TagKey<EntityType<?>> tag(String p_203849_) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Reach.MOD_ID, p_203849_));
        }
    }

    public static void setup()
    {
        Fluids.setup();
    }

    public static class Fluids
    {
        private static void setup() {}

        public static final TagKey<Fluid> BLOOD = FluidTags.create(new ResourceLocation(Reach.MOD_ID, "soul_lava"));
    }
}
