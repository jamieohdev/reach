package net.nullean.reach;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.nullean.reach.block.ReachWoodTypes;
import net.nullean.reach.registry.*;
import net.nullean.reach.block.entity.ModBlockEntities;
import net.nullean.reach.entity.ModEntityTypes;
import net.nullean.reach.fluid.ModFluidTypes;
import net.nullean.reach.fluid.ModFluids;
import net.nullean.reach.loot.ModLootModifiers;
import net.nullean.reach.networking.ModMessages;
import net.nullean.reach.recipe.ModRecipes;
import net.nullean.reach.screen.ModMenuTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Locale;


@Mod(Reach.MOD_ID)
public class Reach
{
    /**
     * TODO LIST:
     *
     * PORT TO 1.19.4
     * PORT TO 1.20.1 BEFORE 1.21 COMES OUT?
     *
     * BUGS:
     * FLUIDS DO NOT WORK
     * CEILING CRYSTALS TANK PERFORMANCE
     *
     */
    public static final String MOD_ID = "reach";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Reach() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ReachItems.ITEMS.register(modEventBus);
        ReachBlocks.BLOCKS.register(modEventBus);
        ReachFeatures.FEATURES.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ReachEntities.ENTITIES.register(modEventBus);
        ReachFluidsReg.FLUID_REGISTER.register(modEventBus);
        ReachFluidsReg.FORGE_FLUID_REGISTER.register(modEventBus);
        ReachSounds.SOUND_EVENTS.register(modEventBus);
        ReachParticles.PARTICLES.register(modEventBus);
        ReachTileEntities.TILE.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ReachFluidsReg.setup();
        ReachTags.setup();

        ReachStructureTypes.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
        ReachStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(modEventBus);

        // ReachPieces.STRUCTURE_PIECE_TYPES.register(modEventBus);
    }

    public static ResourceLocation makeID(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
        ReachBiomes.addBiomeTypes();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(
                    ReachBlocks::setRenderTypes);
            ReachTileEntityRenderers.init();
            registerWoodTypes(event);
        }
    }

    private static void registerWoodTypes(FMLClientSetupEvent event) {
        //Add each of your custom wood types here. This is for textures.
        event.enqueueWork(() -> {
            Sheets.addWoodType(ReachWoodTypes.SNAG);
        });
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(Reach.MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static String prefixOnString(String name) {
        return Reach.MOD_ID + ":" + name.toLowerCase(Locale.ROOT);
    }

    private void registerCommands(RegisterCommandsEvent evt) {
      //  ReachExampleCommand.register(evt.getDispatcher());
    }
}
