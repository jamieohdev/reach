package net.nullean.reach.registry;

import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.world.feature.BlemishVegetationFeature;
import net.nullean.reach.world.feature.BlemishVinesFeature;
import net.nullean.reach.world.feature.BlemishstoneClusterFeature;
import net.nullean.reach.world.feature.GoopFeature;
import net.nullean.reach.world.feature.configurations.BlemishstoneClusterConfiguration;
import net.nullean.reach.world.feature.configurations.ReachVegetationConfig;

public class ReachFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reach.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLEMISH_TOP_LAYER = FEATURES.register("blemish_top_layer", () -> new SnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<BlemishstoneClusterFeature> BLEMISHSTONE_CLUSTER = FEATURES.register("blemishstone_cluster", () -> new BlemishstoneClusterFeature(BlemishstoneClusterConfiguration.CODEC));

    public static final RegistryObject<BlemishVegetationFeature> BLEMISH_VEGETATION = FEATURES.register("reach_vegetation", () -> new BlemishVegetationFeature(ReachVegetationConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLEMISH_VINES = FEATURES.register("blemish_vines", () -> new BlemishVinesFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GOOP_FEATURE = FEATURES.register("goop", () -> new GoopFeature(NoneFeatureConfiguration.CODEC));

}
