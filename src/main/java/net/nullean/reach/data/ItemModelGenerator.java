package net.nullean.reach.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.nullean.reach.Reach;
import net.nullean.reach.registry.ReachBlocks;

public class ItemModelGenerator extends ItemModelProvider
{
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Reach.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        this.toBlock(ReachBlocks.SOUL_GRASS_BLOCK);
        this.toBlock(ReachBlocks.SOULSTONE);
        this.toBlock(ReachBlocks.SOULSLATE);
        this.toBlock(ReachBlocks.BLEMISH_BLOCK);
        this.toBlock(ReachBlocks.BLEMISH);
        this.toBlock(ReachBlocks.BLEMISH_ROOTED_BLOCK);
        this.toBlock(ReachBlocks.BLEMISH_TEST);
        this.toBlock(ReachBlocks.BLEMISH_STONE);
        this.toBlock(ReachBlocks.RAFFLESIA);
        this.toBlock(ReachBlocks.BLEMISH_VINES_PLANT);
        this.toBlock(ReachBlocks.BLEMISH_VINES);
        this.toBlock(ReachBlocks.BLEMISH_ROOTS);
        this.toBlock(ReachBlocks.GOOP);

        this.toBlock(ReachBlocks.REACH_BRICKS);
        this.toBlock(ReachBlocks.REACH_BRICK_STAIRS);
        this.toBlock(ReachBlocks.REACH_BRICK_SLAB);
        this.toBlock(ReachBlocks.REACH_BRICK_WALL);
        this.toBlock(ReachBlocks.REACH_TILES);
        this.toBlock(ReachBlocks.REACH_TILE_STAIRS);
        this.toBlock(ReachBlocks.REACH_TILE_SLAB);
        this.toBlock(ReachBlocks.REACH_TILE_WALL);
        this.toBlock(ReachBlocks.CRACKED_REACH_BRICKS);
        this.toBlock(ReachBlocks.CHISELED_REACH_BRICKS);
    }

    public ItemModelBuilder torchItem(Block item) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(item).getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + ForgeRegistries.BLOCKS.getKey(item).getPath()));
    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder singleTexTool(RegistryObject<Item> item) {
        return tool(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), Reach.prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder tool(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), Reach.prefix("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder bowItem(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/bow");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder bowTex(RegistryObject<Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
        return bowItem(item.getId().getPath(), Reach.prefix("item/" + item.getId().getPath()))
                .override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
    }

    private void woodenButton(Block button, String variant) {
        getBuilder(ForgeRegistries.BLOCKS.getKey(button).getPath())
                .parent(getExistingFile(mcLoc("block/button_inventory")))
                .texture("texture", "block/wood/planks_" + variant + "_0");
    }

    private void woodenFence(RegistryObject<? extends Block> fence, RegistryObject<? extends Block> block) {
        getBuilder(ForgeRegistries.BLOCKS.getKey(fence.get()).getPath())
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath());
    }

    private void woodenFence(Block fence, String texture) {
        getBuilder(ForgeRegistries.BLOCKS.getKey(fence).getPath())
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/" + texture);
    }

    private void toBlock(RegistryObject<? extends Block> b) {
        toBlockModel(b.get(), ForgeRegistries.BLOCKS.getKey(b.get()).getPath());
    }

    private void toBlockModel(Block b, String model) {
        toBlockModel(b, Reach.prefix("block/" + model));
    }

    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(ForgeRegistries.BLOCKS.getKey(b).getPath(), model);
    }

    public ItemModelBuilder itemBlockFlat(RegistryObject<? extends Block> block) {
        return itemBlockFlat(block.get(), blockName(block.get()));
    }

    public ItemModelBuilder itemBlockFlat(Block block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    public ItemModelBuilder egg(RegistryObject<Item> item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }

    public String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    @Override
    public String getName() {
        return "reach item and itemblock models";
    }
}
