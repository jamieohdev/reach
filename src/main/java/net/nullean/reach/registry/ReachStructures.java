package net.nullean.reach.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.nullean.reach.Reach;
import net.nullean.reach.block.ReachBlockStateProperties;
import net.nullean.reach.world.structures.CloudStructure;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class ReachStructures
{
    public static final ResourceKey<Structure> LARGE_CLOUD = createKey("large_cloud");
    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Reach.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        Map<MobCategory, StructureSpawnOverride> mobSpawnsBox = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create())));

        Map<MobCategory, StructureSpawnOverride> mobSpawnsPiece = Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap((category) -> category, (category) -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create())));

        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        context.register(LARGE_CLOUD, new CloudStructure(
                ReachStructureBuilders.structure(biomes.getOrThrow(ReachTags.Biomes.HAS_LARGE_CLOUD), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                BlockStateProvider.simple(ReachBlocks.CLOUD_WHITE.get().defaultBlockState().setValue(ReachBlockStateProperties.DOUBLE_DROPS, true)),
                3, 32));
    }
}
