package net.nullean.reach.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.nullean.reach.Reach;
import net.nullean.reach.client.ReachModelLayers;
import net.nullean.reach.client.model.ModelSpreader;
import net.nullean.reach.entity.monster.Spreader;

public class SpreaderRenderer<T extends Spreader> extends MobRenderer<T, ModelSpreader<T>> {
    private static final ResourceLocation SPREADER_LOCATION = new ResourceLocation(Reach.MOD_ID,"textures/entity/spreader.png");

    public SpreaderRenderer(EntityRendererProvider.Context p_174129_) {
        super(p_174129_, new ModelSpreader<>(p_174129_.bakeLayer(ReachModelLayers.SPREADER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(Spreader p_114755_) {
        return SPREADER_LOCATION;
    }
}