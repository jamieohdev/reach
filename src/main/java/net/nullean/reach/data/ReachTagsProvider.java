package net.nullean.reach.data;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.nullean.reach.registry.ReachBiomes;
import net.nullean.reach.registry.ReachTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ReachTagsProvider extends TagsProvider<Biome>
{
    protected ReachTagsProvider(PackOutput p_256596_, ResourceKey<? extends Registry<Biome>> p_255886_, CompletableFuture<HolderLookup.Provider> p_256513_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256596_, p_255886_, p_256513_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider context) {
        Optional<HolderLookup.RegistryLookup<Biome>> holdergetter = context.lookup(Registries.BIOME);
        Optional<HolderLookup.RegistryLookup<StructureTemplatePool>> holdergetter1 = context.lookup(Registries.TEMPLATE_POOL);
        this.tag(ReachTags.Biomes.IS_DEEP_OCEAN).add(ReachBiomes.CORAL_HAVEN).add(ReachBiomes.GLACIERS).add(ReachBiomes.KELP_FOREST).add(ReachBiomes.OCEAN);
        this.tag(ReachTags.Biomes.IS_OCEAN).addTag(ReachTags.Biomes.IS_DEEP_OCEAN).add(ReachBiomes.OCEAN_BOILING).add(ReachBiomes.OCEAN_HOT).add(ReachBiomes.OCEAN).add(ReachBiomes.OCEAN_COLD).add(ReachBiomes.OCEAN_FROZEN);
//        this.tag(ReachTags.Biomes.HAS_RUINED_PORTAL_OCEAN).addTag(ReachTags.Biomes.IS_OCEAN);
        this.tag(ReachTags.Biomes.REQUIRED_OCEAN_MONUMENT_SURROUNDING).addTag(ReachTags.Biomes.IS_OCEAN);
        this.tag(ReachTags.Biomes.HAS_OCEAN_RUIN_COLD).add(ReachBiomes.OCEAN).add(ReachBiomes.OCEAN_COLD).add(ReachBiomes.OCEAN_FROZEN);
        this.tag(ReachTags.Biomes.HAS_OCEAN_RUIN_WARM).add(ReachBiomes.OCEAN_BOILING).add(ReachBiomes.OCEAN_HOT).add(ReachBiomes.OCEAN);
        this.tag(ReachTags.Biomes.HAS_SHIPWRECK).addTag(ReachTags.Biomes.IS_OCEAN);
        this.tag(ReachTags.Biomes.HAS_OCEAN_MONUMENT).addTag(ReachTags.Biomes.IS_DEEP_OCEAN);
    }
}
