package net.nullean.reach.registry;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.nullean.reach.Reach;
import net.nullean.reach.client.tile.SnagSignTileRenderer;

public class ReachTileEntityRenderers {

    public static void init() {
        BlockEntityRenderers.register(ReachTileEntities.SIGN_TILE.get(), SnagSignTileRenderer::new);

    }

    public String getName() {
        return Reach.MOD_ID + " - Tile Renders";
    }
}
