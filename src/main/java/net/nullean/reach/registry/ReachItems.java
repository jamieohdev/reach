package net.nullean.reach.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReachItems {
    public static CreativeModeTab REACHITEM;
    public static CreativeModeTab REACHBLOCK;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reach.MOD_ID);
    public static final RegistryObject<Item> MOOBOO_SPAWNEGG = ITEMS.register("mooboo_spawn_egg", () -> new ForgeSpawnEggItem(ReachEntities.MOOBOO, 0xB18346, 0x9B6B2D, (new Item.Properties())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Reach.MOD_ID, "item")
                , (builder) -> {
                    REACHITEM = builder.icon(() -> new ItemStack(ReachItems.MOOBOO_SPAWNEGG.get())).title(Component.translatable("itemGroup." + Reach.MOD_ID + ".item" + ".main_tab")).displayItems((features, output, hasPermissions) ->
                            output.acceptAll(Stream.of(
                                            MOOBOO_SPAWNEGG)
                                    .map(item -> item.get().getDefaultInstance())
                                    .toList())).build();
                });
        event.registerCreativeModeTab(new ResourceLocation(Reach.MOD_ID, "block")
                , (builder) -> {
                    REACHBLOCK = builder.icon(() -> new ItemStack(ReachBlocks.SOUL_GRASS_BLOCK.get())).title(Component.translatable("itemGroup." + Reach.MOD_ID + ".block" + ".main_tab")).displayItems((features, output, hasPermissions) ->
                            output.acceptAll(Stream.of(
                                            ReachBlocks.SOUL_GRASS_BLOCK,
                                            ReachBlocks.SOULSTONE,
                                            ReachBlocks.SOULSLATE,
                                            ReachBlocks.BLEMISH_BLOCK,
                                            ReachBlocks.BLEMISH_TEST,
                                            ReachBlocks.BLEMISH_STONE,
                                            ReachBlocks.BLEMISH,
                                            ReachBlocks.BLEMISH_ROOTED_BLOCK,
                                            ReachBlocks.RAFFLESIA,
                                            ReachBlocks.BLEMISH_VINES_PLANT,
                                            ReachBlocks.BLEMISH_VINES,
                                            ReachBlocks.BLEMISH_ROOTS,
                                            ReachBlocks.REACH_BRICKS,
                                            ReachBlocks.REACH_BRICK_STAIRS,
                                            ReachBlocks.REACH_BRICK_SLAB,
                                            ReachBlocks.REACH_BRICK_WALL,
                                            ReachBlocks.REACH_TILES,
                                            ReachBlocks.REACH_TILE_STAIRS,
                                            ReachBlocks.REACH_TILE_SLAB,
                                            ReachBlocks.REACH_TILE_WALL,
                                            ReachBlocks.CRACKED_REACH_BRICKS ,
                                            ReachBlocks.CHISELED_REACH_BRICKS)
                                    .map(block -> block.get().asItem().getDefaultInstance())
                                    .toList())).build();
                });
    }

}
