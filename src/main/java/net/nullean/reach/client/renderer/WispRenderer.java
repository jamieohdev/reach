package net.nullean.reach.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.nullean.reach.Reach;
import net.nullean.reach.client.ReachModelLayers;
import net.nullean.reach.client.model.WispModel;
import net.nullean.reach.entity.animal.Wisp;

public class WispRenderer <T extends Wisp> extends MobRenderer<T, WispModel<T>> {
    private static final ResourceLocation WISP_LOCATION = new ResourceLocation(Reach.MOD_ID,"textures/entity/wisp.png");
    SlimeRenderer ref;

    public WispRenderer(EntityRendererProvider.Context p_174129_) {
        super(p_174129_, new WispModel<>(p_174129_.bakeLayer(ReachModelLayers.WISP)), 0.5F);
    }

    protected int getBlockLightLevel(Wisp p_113910_, BlockPos p_113911_) {
        return 15;
    }

    public ResourceLocation getTextureLocation(Wisp p_114755_) {
        return WISP_LOCATION;
    }
}
