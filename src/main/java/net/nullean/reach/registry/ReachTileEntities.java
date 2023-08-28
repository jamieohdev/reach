package net.nullean.reach.registry;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nullean.reach.Reach;
import net.nullean.reach.client.tile.SnagSignTile;

public class ReachTileEntities
{
    public static final DeferredRegister<BlockEntityType<?>> TILE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reach.MOD_ID);
    public static RegistryObject<BlockEntityType<SnagSignTile>> SIGN_TILE = TILE.register("snag_sign_tile", () -> new BlockEntityType<>(SnagSignTile::new, Sets.newHashSet(ReachBlocks.SNAG_SIGN.get(), ReachBlocks.SNAG_WALL_SIGN.get()), null));

    public String getName() {
        return Reach.MOD_ID + " - Tile Entities";
    }
}
