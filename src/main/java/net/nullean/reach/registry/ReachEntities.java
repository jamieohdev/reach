package net.nullean.reach.registry;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.entity.animal.MooBoo;
import net.nullean.reach.entity.monster.Blemish;
import net.nullean.reach.entity.monster.Spreader;
import net.nullean.reach.entity.proejctile.SpreaderBlast;
import org.apache.commons.lang3.NotImplementedException;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReachEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reach.MOD_ID);
    
    public static final RegistryObject<EntityType<MooBoo>> MOOBOO = ENTITIES.register("mooboo", () -> EntityType.Builder.of(MooBoo::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build(prefix("mooboo")));
    public static final RegistryObject<EntityType<Blemish>> BLEMISH = ENTITIES.register("blemish", () -> EntityType.Builder.of(Blemish::new, MobCategory.MONSTER).sized(0.9F, 1.4F).clientTrackingRange(10).build(prefix("blemish")));
    public static final RegistryObject<EntityType<Spreader>> SPREADER = ENTITIES.register("spreader", () -> EntityType.Builder.of(Spreader::new, MobCategory.MONSTER).sized(1.0F, 1.0F).clientTrackingRange(10).build(prefix("spreader")));
    public static final RegistryObject<EntityType<SpreaderBlast>> SPREADER_BLAST = ENTITIES.register("spreader_blast", () -> EntityType.Builder.<SpreaderBlast>of(SpreaderBlast::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("spreader_blast")));


    private static String prefix(String path) {
        return Reach.MOD_ID + "." + path;
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {

        event.put(MOOBOO.get(), MooBoo.createAttributes().build());
        event.put(BLEMISH.get(), Blemish.createAttributes().build());
        event.put(SPREADER.get(), Spreader.createAttributes().build());

        SpawnPlacements.register(MOOBOO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MooBoo::checkMooBooSpawnRules);
        SpawnPlacements.register(BLEMISH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blemish::checkBlemishSpawnRules);
        SpawnPlacements.register(SPREADER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spreader::checkSpreaderSpawnRules);
    }
}
