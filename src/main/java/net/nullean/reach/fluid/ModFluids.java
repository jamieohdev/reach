package net.nullean.reach.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.fluid.ModFluidTypes;
import net.nullean.reach.registry.ReachBlocks;
import net.nullean.reach.registry.ReachItems;

public class ModFluids
{
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Reach.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_SOUL_LAVA = FLUIDS.register("soul_lava",
            () -> new ForgeFlowingFluid.Source(ModFluids.SOUL_LAVA_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SOUL_LAVA = FLUIDS.register("flowing_soul_lava",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.SOUL_LAVA_WATER_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties SOUL_LAVA_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SOUL_LAVA_FLUID_TYPE, SOURCE_SOUL_LAVA, FLOWING_SOUL_LAVA)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ReachBlocks.SOUL_LAVA)
            .bucket(ReachItems.SOUL_LAVA_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

}
