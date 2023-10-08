package net.nullean.reach.registry;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.nullean.reach.Reach;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.block.*;
import net.nullean.reach.block.grower.SnagTreeGrower;
import net.nullean.reach.fluid.ModFluids;
import net.nullean.reach.world.material.fluids.ReachFluids;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReachBlocks {
    Blocks block;
    Villager v;
    PowderSnowBlock ref;
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Reach.MOD_ID);
    // General Reach Blocks
    public static final RegistryObject<Block> SOULSTONE = register("soulstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 13.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> SOULSLATE= register("soulslate", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5.5F, 30.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_TILES)));
    public static final RegistryObject<Block> SOUL_GRASS_BLOCK = register("soulgrass", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS).lightLevel((p_50874_) -> {
        return 15;
    })));
    public static final RegistryObject<Block> SOUL_PATH = register("soulpath", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS).lightLevel((p_50874_) -> {
        return 7;
    })));

    public static final RegistryObject<Block> SHARD_PLASMA = register("shard_plasma", () -> new SculkBlock(BlockBehaviour.Properties.of(Material.SPONGE).strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SCULK)));
    public static final RegistryObject<Block> SHARD_STONE = register("shard_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SHARD_RED = register("shard_red", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> SHARD_BLUE = register("shard_blue", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> SHARD_GREEN = register("shard_green", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> SHARD_CYAN = register("shard_cyan", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> SHARD_MAGENTA = register("shard_magenta", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> SHARD_WHITE = register("shard_white", () ->  new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));

    // Blemish Blocks
    public static final RegistryObject<Block> BLEMISH = register("blemish", () -> new BlemishLayerBlock(BlockBehaviour.Properties.of(Material.TOP_SNOW).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.HONEY_BLOCK).isViewBlocking((p_187417_, p_187418_, p_187419_) -> {
        return p_187417_.getValue(BlemishLayerBlock.LAYERS) >= 8;})));

    public static final RegistryObject<Block> BLEMISH_BLOCK = register("blemish_block", () -> new BlemishBlock(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> BLEMISH_TEST = register("blemish_test", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> BLEMISH_STONE = register("blemish_stone", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> BLEMISH_ROOTED_BLOCK = register("blemish_rooted_block", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> RAFFLESIA = register("rafflesia", () -> new RafflesiaBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().noCollission().sound(SoundType.SPORE_BLOSSOM)));
    public static final RegistryObject<Block> BLEMISH_VINES = register("blemish_vines", () -> new BlemishVinesBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_CYAN).randomTicks().noCollission().instabreak().sound(SoundType.WEEPING_VINES)));
    public static final RegistryObject<Block> BLEMISH_VINES_PLANT = register("blemish_vines_plant", () -> new BlemishVinesPlantBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WEEPING_VINES)));
    public static final RegistryObject<Block> BLEMISH_ROOTS = register("blemish_roots", () -> new ReachRootsBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, MaterialColor.NETHER).noCollission().instabreak().sound(SoundType.ROOTS).offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final RegistryObject<Block> GOOP = register("goop", () -> new GoopBlock(BlockBehaviour.Properties.of(Material.MOSS).requiresCorrectToolForDrops().strength(0.2F).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().lightLevel((p_50884_) -> {
        return 9;
    })));// reach_brick reach:block/reach_bricks

    public static final RegistryObject<Block>  REACH_POLISHED = register("reach_polished_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  REACH_BRICKS = register("reach_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  REACH_BRICK_STAIRS = register("reach_brick_stairs", () -> new StairBlock(REACH_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(REACH_BRICKS.get())));
    public static final RegistryObject<Block>  REACH_BRICK_SLAB = register("reach_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(REACH_BRICKS.get())));
    public static final RegistryObject<Block>  REACH_BRICK_WALL = register("reach_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(REACH_BRICKS.get())));
    public static final RegistryObject<Block>  REACH_TILES = register("reach_tiles", () ->  new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  REACH_TILE_STAIRS = register("reach_tile_stairs", () -> new StairBlock(REACH_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(REACH_TILES.get())));
    public static final RegistryObject<Block>  REACH_TILE_SLAB = register("reach_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(REACH_TILES.get())));
    public static final RegistryObject<Block>  REACH_TILE_WALL = register("reach_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(REACH_TILES.get())));
    public static final RegistryObject<Block>  CRACKED_REACH_BRICKS = register("reach_cracked_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  CHISELED_REACH_BRICKS = register("reach_chiseled_bricks", () ->  new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> SNAG_PLANKS = register("snag_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SNAG_SAPLING = register("snag_sapling", () -> new ReachSaplingBlock(new SnagTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SNAG_LOG = register("snag_log", () ->  log(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN));
    public static final RegistryObject<Block> STRIPPED_SNAG_LOG = register("stripped_snag_log",() -> log(MaterialColor.PODZOL, MaterialColor.PODZOL));
    public static final RegistryObject<Block> SNAG_WOOD = register("snag_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_SNAG_WOOD = register("stripped_snag_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SNAG_LEAVES = register("snag_leaves", () -> leaves(SoundType.GRASS));
    public static final RegistryObject<Block> SNAG_SIGN = register("snag_sign", () -> new StandingSignReachBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_LOG.get().defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), ReachWoodTypes.SNAG));
    public static final RegistryObject<Block> SNAG_WALL_SIGN = register("snag_wall_sign", () -> new WallSignReachBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_LOG.get().defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(SNAG_SIGN.get()), ReachWoodTypes.SNAG));
    public static final RegistryObject<Block> SNAG_HANGING_SIGN = register("snag_hanging_sign", () -> new CeilingHangingSignReachBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_LOG.get().defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.HANGING_SIGN).requiredFeatures(FeatureFlags.UPDATE_1_20), ReachWoodTypes.SNAG));
    public static final RegistryObject<Block> SNAG_WALL_HANGING_SIGN = register("snag_wall_hanging_sign", () -> new WallHangingSignReachBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.HANGING_SIGN).dropsLike(SNAG_HANGING_SIGN.get()).requiredFeatures(FeatureFlags.UPDATE_1_20), ReachWoodTypes.SNAG));
    public static final RegistryObject<Block> SNAG_PRESSURE_PLATE = register("snag_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, SNAG_PLANKS.get().defaultMaterialColor()).noCollission().strength(0.5F).sound(SoundType.WOOD), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryObject<Block> SNAG_TRAPDOOR = register("snag_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ReachBlocks::never), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));
    public static final RegistryObject<Block> SNAG_STAIRS = register("snag_stairs", () -> new StairBlock(SNAG_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(SNAG_PLANKS.get())));
    public static final RegistryObject<Block> POTTED_SNAG_SAPLING = register("potted_snag_sapling", () -> new FlowerPotBlock(SNAG_SAPLING.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SNAG_BUTTON = register("snag_button", () -> woodenButton());
    public static final RegistryObject<Block> SNAG_SLAB = register("snag_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SNAG_FENCE_GATE = register("snag_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<Block> SNAG_FENCE = register("snag_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SNAG_DOOR = register("snag_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, SNAG_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));

    public static final RegistryObject<Block> SOUL_MOSS_CARPET = register("soul_moss_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS_CARPET)));
    public static final RegistryObject<Block> SOUL_MOSS_BLOCK = register("soul_moss_block", () -> new MossBlock(BlockBehaviour.Properties.of(Material.MOSS, MaterialColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS)));
    public static final RegistryObject<Block> BISMUTH = register("bismuth", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ANTIGRAVEL = register("antigravel", () -> new AntiGravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> SOUL_GRASS_PLANT_SMALL = register("soulgrass_plant_small", () ->  new SoulGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> SOUL_GRASS_PLANT = register("soulgrass_plant", () ->  new DoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final RegistryObject<Block> GHASTLY_PUMPKIN = register("ghastly_pumpkin", () -> new GhastlyPumpkinBlock(BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CARVED_GHASTLY_PUMPKIN = register("carved_ghastly_pumpkin", () ->  new CarvedPumpkinBlock(BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).isValidSpawn(ReachBlocks::always)));
    public static final RegistryObject<Block> JACK_O_LANTERN = register("jack_o_lantern", () ->  new CarvedPumpkinBlock(BlockBehaviour.Properties.of(Material.VEGETABLE, MaterialColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> {
        return 7;
    }).isValidSpawn(ReachBlocks::always)));
    public static final RegistryObject<Block> GHASTLY_ATTACHED_PUMPKIN_STEM = register("attached_pumpkin_stem", () ->  new AttachedStemBlock((StemGrownBlock)GHASTLY_PUMPKIN.get(), ReachItems.GHASTLY_PUMPKIN_SEEDS::get, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GHASTLY_PUMPKIN_STEM = register("ghastly_pumpkin_stem", () ->  new StemBlock((StemGrownBlock)GHASTLY_PUMPKIN.get(), ReachItems.GHASTLY_PUMPKIN_SEEDS::get, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.HARD_CROP)));

    public static final RegistryObject<Block> SOULCHEST = register("soulchest", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static Supplier<? extends FlowingFluid> SOUL_LAVA_SUPPLIER = Suppliers.memoize(() -> (FlowingFluid) ReachFluids.SOUL_LAVA.get());
    public static final RegistryObject<LiquidBlock> SOUL_LAVA = register("soul_lava", () -> new LiquidBlock(ModFluids.SOURCE_SOUL_LAVA, BlockBehaviour.Properties.of(Material.LAVA).noCollission().randomTicks().noLootTable().strength(100.0F).lightLevel((p_220867_) -> {
        return 15;
    }).noLootTable()));

    public static final RegistryObject<Block> CLOUD_WHITE = register("cloud_white", () -> new CloudBlock(BlockBehaviour.Properties.of(Material.SNOW).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));
    public static final RegistryObject<Block> CLOUD_PURPLE = register("cloud_purple", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).noOcclusion().isValidSpawn(ReachBlocks::never).isRedstoneConductor(ReachBlocks::never).isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never)));

    public static final RegistryObject<Block> CALMSTONE = register("calmstone", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> CALMGRASS_BLOCK = register("calmgrass_block", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> CALMGRASS_PLANT = register("calmgrass_plant", () -> new SoulGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> CALM_VINE = register("calm_vine", () -> new VineBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));
    public static final RegistryObject<Block> CALM_PILLAR = register("calm_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> WHITESTONE = register("whitestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  WHITESTONE_STAIRS = register("whitestone_stairs", () -> new StairBlock(WHITESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(WHITESTONE.get())));
    public static final RegistryObject<Block>  WHITESTONE_WALL = register("whitestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WHITESTONE.get())));
    public static final RegistryObject<Block>  WHITESTONE_SLAB = register("whitestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WHITESTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE = register("polished_whitestone", () -> new Block(BlockBehaviour.Properties.copy(WHITESTONE.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_BRICKS = register("polished_whitestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE.get()).strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  CRACKED_POLISHED_WHITESTONE_BRICKS = register("cracked_polished_whitestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE_BRICKS.get())));
    public static final RegistryObject<Block>  CHISELED_POLISHED_WHITESTONE = register("chiseled_polished_whitestone", () -> new Block(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE.get()).strength(1.5F, 6.0F)));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_BRICK_SLAB = register("polished_whitestone_brick_slab",() -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE_BRICKS.get()).strength(2.0F, 6.0F)));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_BRICK_STAIRS = register("polished_whitestone_brick_stairs", () -> new StairBlock(POLISHED_WHITESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_WHITESTONE_BRICKS.get())));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_BRICK_WALL = register("polished_whitestone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE_BRICKS.get())));
    public static final RegistryObject<Block>  GILDED_WHITESTONE = register("gilded_whitestone", () -> new Block(BlockBehaviour.Properties.copy(WHITESTONE.get()).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_STAIRS = register("polished_whitestone_stairs", () -> new StairBlock(POLISHED_WHITESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_WHITESTONE.get())));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_SLAB = register("polished_whitestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE.get())));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_PRESSURE_PLATE = register("polished_whitestone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().noCollission().strength(0.5F), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_BUTTON = register("polished_whitestone_button", () -> stoneButton());
    public static final RegistryObject<Block>  POLISHED_WHITESTONE_WALL = register("polished_whitestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_WHITESTONE.get())));

    public static final RegistryObject<Block> CALM_LANTERN = register("calm_lantern", () -> new LanternBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((p_152677_) -> {
        return 15;
    }).noOcclusion()));
    public static final RegistryObject<Block> CALM_TORCH = register("calm_torch", () -> new TorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((p_50755_) -> {
        return 14;
    }).sound(SoundType.WOOD), ParticleTypes.FLAME));


    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return (boolean)true;
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return (boolean)false;
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }



    private static RotatedPillarBlock log(MaterialColor p_50789_, MaterialColor p_50790_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_50789_ : p_50790_;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    private static LeavesBlock leaves(SoundType p_152615_) {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(p_152615_).noOcclusion().isSuffocating(ReachBlocks::never).isViewBlocking(ReachBlocks::never));
    }

    private static ButtonBlock woodenButton() {
        return woodenButton(SoundType.WOOD, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    private static ButtonBlock woodenButton(SoundType p_249282_, SoundEvent p_251988_, SoundEvent p_251887_) {
        return new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(p_249282_), 30, true, p_251988_, p_251887_);
    }
    private static ButtonBlock stoneButton() {
        return new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.STONE), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRenderTypes() {
        RenderType trans = RenderType.cutoutMipped();
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();

        ItemBlockRenderTypes.setRenderLayer(SNAG_DOOR.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(SNAG_TRAPDOOR.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(SOUL_GRASS_PLANT.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(SOUL_GRASS_PLANT_SMALL.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(CALM_LANTERN.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(CALMGRASS_PLANT.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(CALM_VINE.get(), cutout);

        ItemBlockRenderTypes.setRenderLayer(GHASTLY_PUMPKIN_STEM.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(GHASTLY_ATTACHED_PUMPKIN_STEM.get(), cutout);
        
        ItemBlockRenderTypes.setRenderLayer(CLOUD_WHITE.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(CLOUD_PURPLE.get(), translucent);

        ItemBlockRenderTypes.setRenderLayer(SHARD_BLUE.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(SHARD_GREEN.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(SHARD_RED.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(SHARD_CYAN.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(SHARD_MAGENTA.get(), translucent);
        ItemBlockRenderTypes.setRenderLayer(SHARD_WHITE.get(), translucent);
    }


    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        ReachItems.ITEMS.register(name, item.apply(register));
        Blocks blocks;
        return register;
    }

    private static <T extends Block> RegistryObject<T> noItemRegister(String name, Supplier<? extends T> block) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>) baseRegister(name, block, ReachBlocks::registerBlockItem);
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> {
            if (Objects.requireNonNull(block.get()) == SOULCHEST.get()) {
                return new BlockItem(SOULCHEST.get(), (new Item.Properties())) {
                    @Override
                    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
                        consumer.accept(new IClientItemExtensions() {
                            BlockEntityWithoutLevelRenderer myRenderer;


                            @Override
                            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                                Minecraft.getInstance().getEntityRenderDispatcher();
                                if (myRenderer == null) {
                                    myRenderer = new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {

                                        @Override
                                        public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
                                       }
                                    };
                                }

                                return myRenderer;
                            }
                        });
                    }
                };
            } else if (Objects.requireNonNull(block.get()) instanceof DoublePlantBlock || Objects.requireNonNull(block.get()) instanceof DoorBlock) {
                return new DoubleHighBlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
            } else {
                return new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
            }
        };
    }
}
