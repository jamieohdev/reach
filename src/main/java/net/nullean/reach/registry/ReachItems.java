package net.nullean.reach.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Blocks;
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
import net.nullean.reach.item.entity.SnagSignItem;

import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Reach.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReachItems {
    public static CreativeModeTab REACHITEM;
    public static CreativeModeTab REACHBLOCK;

    Items items;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reach.MOD_ID);
    public static final RegistryObject<Item> MOOBOO_SPAWNEGG = ITEMS.register("mooboo_spawn_egg", () -> new ForgeSpawnEggItem(ReachEntities.MOOBOO, 0xB18346, 0x9B6B2D, (new Item.Properties())));

   // public static final RegistryObject<Item>  SNAG_BOAT = ITEMS.register("snag_boat", () -> new BoatItem(false, Boat.Type.SNAG, (new Item.Properties()).stacksTo(1)));
   // public static final RegistryObject<Item>  SNAG_CHEST_BOAT = ITEMS.register("snag_chest_boat", () -> new BoatItem(true, Boat.Type.SNAG, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item>  SNAG_SIGN = ITEMS.register("snag_sign_item", () -> new SnagSignItem((new Item.Properties()).stacksTo(16), ReachBlocks.SNAG_SIGN.get(), ReachBlocks.SNAG_WALL_SIGN.get()));

    public static final RegistryObject<Item> GHASTLY_PUMPKIN_SEEDS = ITEMS.register("ghastly_pumpkin_seeds", () -> new ItemNameBlockItem(ReachBlocks.GHASTLY_PUMPKIN_STEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> CLOUD_BUCKET = ITEMS.register("cloud_bucket", () -> new SolidBucketItem(ReachBlocks.CLOUD_WHITE.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, (new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> SOUL_LAVA_BUCKET = ITEMS.register("soul_lava_bucket", () -> new SolidBucketItem(ReachBlocks.SOUL_LAVA.get(), SoundEvents.BUCKET_EMPTY_LAVA, (new Item.Properties()).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Reach.MOD_ID, "item")
                , (builder) -> {
                    REACHITEM = builder.icon(() -> new ItemStack(ReachItems.MOOBOO_SPAWNEGG.get())).title(Component.translatable("itemGroup." + Reach.MOD_ID + ".item" + ".main_tab")).displayItems((features, output, hasPermissions) ->
                            output.acceptAll(Stream.of(
                                            MOOBOO_SPAWNEGG, SNAG_SIGN, GHASTLY_PUMPKIN_SEEDS, CLOUD_BUCKET,
                                            SOUL_LAVA_BUCKET)
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
                                            ReachBlocks.CRACKED_REACH_BRICKS,
                                            ReachBlocks.CHISELED_REACH_BRICKS,
                                            ReachBlocks.CRACKED_REACH_BRICKS,
                                            ReachBlocks.SHARD_BLUE,
                                            ReachBlocks.SHARD_RED,
                                            ReachBlocks.SHARD_GREEN,
                                            ReachBlocks.SHARD_CYAN,
                                            ReachBlocks.SHARD_MAGENTA,
                                            ReachBlocks.SHARD_WHITE,
                                            ReachBlocks.SHARD_PLASMA,
                                            ReachBlocks.SOUL_PATH,

                                            ReachBlocks.SNAG_BUTTON,
                                            ReachBlocks.SNAG_WALL_SIGN,
                                            ReachBlocks.SNAG_DOOR,
                                            ReachBlocks.SNAG_FENCE,
                                            ReachBlocks.SNAG_FENCE_GATE,
                                            ReachBlocks.SNAG_LEAVES,
                                            ReachBlocks.SNAG_LOG,
                                            ReachBlocks.SNAG_PLANKS,
                                            ReachBlocks.SNAG_PRESSURE_PLATE,
                                            ReachBlocks.SNAG_SAPLING,
                                            ReachBlocks.SNAG_SLAB,
                                            ReachBlocks.SNAG_STAIRS,
                                            ReachBlocks.SNAG_TRAPDOOR,
                                            ReachBlocks.SNAG_WOOD,
                                            ReachBlocks.STRIPPED_SNAG_LOG,
                                            ReachBlocks.STRIPPED_SNAG_WOOD,

                                            ReachBlocks.SOUL_MOSS_CARPET,
                                            ReachBlocks.SOUL_MOSS_BLOCK,
                                            ReachBlocks.BISMUTH,
                                            ReachBlocks.ANTIGRAVEL,
                                            ReachBlocks.SOUL_GRASS_PLANT_SMALL,
                                            ReachBlocks.SOUL_GRASS_PLANT,
                                            ReachBlocks.GHASTLY_PUMPKIN,
                                            ReachBlocks.CARVED_GHASTLY_PUMPKIN,
                                            ReachBlocks.JACK_O_LANTERN,

                                            ReachBlocks.CALMGRASS_BLOCK,
                                            ReachBlocks.CALMGRASS_PLANT,
                                            ReachBlocks.CALM_LANTERN,
                                            ReachBlocks.CALM_PILLAR,
                                            ReachBlocks.CALM_TORCH,
                                            ReachBlocks.CALM_VINE,
                                            ReachBlocks.CALMSTONE,
                                            ReachBlocks.CLOUD_PURPLE,
                                            ReachBlocks.CLOUD_WHITE,
                                            ReachBlocks.WHITESTONE,
                                            ReachBlocks.WHITESTONE_SLAB,
                                            ReachBlocks.WHITESTONE_STAIRS,
                                            ReachBlocks.WHITESTONE_WALL,
                                            ReachBlocks.GILDED_WHITESTONE,
                                            ReachBlocks.POLISHED_WHITESTONE,
                                            ReachBlocks.CRACKED_POLISHED_WHITESTONE_BRICKS,
                                            ReachBlocks.CHISELED_POLISHED_WHITESTONE,
                                            ReachBlocks.POLISHED_WHITESTONE_BRICKS)
                                    .map(block -> block.get().asItem().getDefaultInstance())
                                    .toList())).build();
                });
    }

}
