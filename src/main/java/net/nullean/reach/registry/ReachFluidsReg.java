package net.nullean.reach.registry;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.world.material.fluids.ReachFluids;
import net.nullean.reach.world.material.fluids.SoulLavaFluid;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.nullean.reach.world.material.fluids.ReachFluids.*;

import static net.nullean.reach.Reach.MOD_ID;

public class ReachFluidsReg
{
    public static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister.create(Registries.FLUID, MOD_ID);
    public static final DeferredRegister<FluidType> FORGE_FLUID_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MOD_ID);

    public static void setup()
    {
        registerFluids();
    }

    public static void registerFluids()
    {
        ReachFluids.FLOWING_SOUL_LAVA = registerFluid(SoulLavaFluid.Flowing::new, "soul_lava_flowing");
        ReachFluids.SOUL_LAVA = registerFluid(SoulLavaFluid.Source::new, "soul_lava");

        ReachFluids.SOUL_LAVA_TYPE = registerFluidType(() ->
                new FluidType(FluidType.Properties.create()
                        .descriptionId("block.reach.soul_lava")
                        .fallDistanceModifier(0F)
                        .canExtinguish(false)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                        .density(3000)
                        .viscosity(6000))
                {
                    @Override
                    public @Nullable BlockPathTypes getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog)
                    {
                        return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
                    }

                    @Override
                    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
                    {
                        consumer.accept(new IClientFluidTypeExtensions()
                        {
                            private static final ResourceLocation SOUL_LAVA_UNDERWATER = new ResourceLocation("reach:textures/block/soul_lava.png"),
                                    SOUL_LAVA_STILL = new ResourceLocation("reach:block/soul_lava_still"),
                                    SOUL_LAVA_FLOW = new ResourceLocation("reach:block/soul_lava_flow");

                            @Override
                            public ResourceLocation getStillTexture()
                            {
                                return SOUL_LAVA_STILL;
                            }

                            @Override
                            public ResourceLocation getFlowingTexture() { return SOUL_LAVA_FLOW; }

                            @Override
                            public ResourceLocation getRenderOverlayTexture(Minecraft mc) { return SOUL_LAVA_UNDERWATER; }

                            @Override
                            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor)
                            {
                                return new Vector3f(0.407F, 0.121F, 0.137F);
                            }

                            @Override
                            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape)
                            {
                                RenderSystem.setShaderFogStart(0.125F);
                                RenderSystem.setShaderFogEnd(1.5F);
                            }
                        });
                    }
                }, "soul_lava");
    }

    public static RegistryObject<Fluid> registerFluid(Supplier<Fluid> fluidSupplier, String name)
    {
        return FLUID_REGISTER.register(name, fluidSupplier);
    }

    public static RegistryObject<FluidType> registerFluidType(Supplier<FluidType> fluidSupplier, String name)
    {
        return FORGE_FLUID_REGISTER.register(name, fluidSupplier);
    }

    public static void registerFluidInteractions()
    {
        for (Map.Entry<ResourceKey<FluidType>, FluidType> fluidType : ForgeRegistries.FLUID_TYPES.get().getEntries())
        {
            if (fluidType.getValue() != ForgeMod.EMPTY_TYPE.get() && fluidType.getValue() != ReachFluids.SOUL_LAVA_TYPE.get())
            {
                FluidInteractionRegistry.addInteraction(fluidType.getValue(), new FluidInteractionRegistry.InteractionInformation(
                        ReachFluids.SOUL_LAVA_TYPE.get(),
                        fluidState -> fluidState.isSource() ? Blocks.GLOWSTONE.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState()
                ));
            }
        }
    }
}
