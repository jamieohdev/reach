package net.nullean.reach.util;

import net.nullean.reach.Reach;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_CUSTOM_TOOL
                = tag("needs_custom_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Reach.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}
