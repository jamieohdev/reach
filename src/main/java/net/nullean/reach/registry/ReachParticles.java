package net.nullean.reach.registry;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.particles.BlemishBlockParticle;
import net.nullean.reach.particles.ReachSuspendedParticle;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReachParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reach.MOD_ID);

    private static String prefix(String path) {
        return Reach.MOD_ID + "." + path;
    }

    public static final RegistryObject<SimpleParticleType> LARGE_FLAME = PARTICLES.register("large_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BLEMISH_BUBBLE = PARTICLES.register("blemish_bubble", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BLEMISH_SPORE = PARTICLES.register("blemish_spore", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        ParticleEngine particles = Minecraft.getInstance().particleEngine;

        event.register(ReachParticles.LARGE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
        event.register(ReachParticles.BLEMISH_BUBBLE.get(), BlemishBlockParticle.BlemishBlockProvider1::new);
        event.register(ReachParticles.BLEMISH_SPORE.get(), ReachSuspendedParticle.BlemishSporeProvider::new);
    }
}
