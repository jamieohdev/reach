package net.nullean.reach.registry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.nullean.reach.Reach;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.block.*;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReachBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Reach.MOD_ID);
    // General Reach Blocks
    public static final RegistryObject<Block> SOULSTONE = register("soulstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 13.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> SOULSLATE= register("soulslate", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5.5F, 30.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_TILES)));
    public static final RegistryObject<Block> SOUL_GRASS_BLOCK = register("soulgrass", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS)));

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
    })));

    public static final RegistryObject<Block> SOULCHEST = register("soulchest", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

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
