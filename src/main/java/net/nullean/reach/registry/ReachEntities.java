package net.nullean.reach.registry;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.entity.animal.MooBoo;
import net.nullean.reach.entity.animal.Wisp;
import net.nullean.reach.entity.block.RisingBlockEntity;
import net.nullean.reach.entity.monster.Blemish;
import net.nullean.reach.entity.monster.Spreader;
import net.nullean.reach.entity.projectile.SpreaderBlast;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReachEntities
{
    EntityType types;
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reach.MOD_ID);
    
    public static final RegistryObject<EntityType<MooBoo>> MOOBOO = ENTITIES.register("mooboo", () -> EntityType.Builder.of(MooBoo::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build(prefix("mooboo")));
    public static final RegistryObject<EntityType<Blemish>> BLEMISH = ENTITIES.register("blemish", () -> EntityType.Builder.of(Blemish::new, MobCategory.MONSTER).sized(0.9F, 1.4F).clientTrackingRange(10).build(prefix("blemish")));
    public static final RegistryObject<EntityType<Spreader>> SPREADER = ENTITIES.register("spreader", () -> EntityType.Builder.of(Spreader::new, MobCategory.MONSTER).sized(1.0F, 1.0F).clientTrackingRange(10).build(prefix("spreader")));
    public static final RegistryObject<EntityType<SpreaderBlast>> SPREADER_BLAST = ENTITIES.register("spreader_blast", () -> EntityType.Builder.<SpreaderBlast>of(SpreaderBlast::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("spreader_blast")));
    public static final RegistryObject<EntityType<Wisp>> WISP = ENTITIES.register("wisp", () -> EntityType.Builder.of(Wisp::new, MobCategory.CREATURE).sized(0.35F, 0.6F).clientTrackingRange(8).updateInterval(2).build(prefix("wisp")));

    public static final RegistryObject<EntityType<RisingBlockEntity>> RISING_BLOCK = ENTITIES.register("rising_block", () -> EntityType.Builder.of(RisingBlockEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20).build(prefix("spreader")));


    private static String prefix(String path) {
        return Reach.MOD_ID + "." + path;
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {

        event.put(MOOBOO.get(), MooBoo.createAttributes().build());
        event.put(BLEMISH.get(), Blemish.createAttributes().build());
        event.put(SPREADER.get(), Spreader.createAttributes().build());
        event.put(WISP.get(), Wisp.createAttributes().build());

        SpawnPlacements.register(WISP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(MOOBOO.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MooBoo::checkMooBooSpawnRules);
        SpawnPlacements.register(BLEMISH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blemish::checkBlemishSpawnRules);
        SpawnPlacements.register(SPREADER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spreader::checkSpreaderSpawnRules);
    }
}
