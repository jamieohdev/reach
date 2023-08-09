package net.nullean.reach.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.nullean.reach.client.model.MooBooModel;
import net.nullean.reach.entity.animal.MooBoo;

public class MooBooTransLayer <T extends LivingEntity> extends RenderLayer<T, MooBooModel<T>> {
    private final EntityModel<T> model;

    public MooBooTransLayer(RenderLayerParent<T, MooBooModel<T>> p_174536_, EntityModelSet p_174537_) {
        super(p_174536_);
        this.model = new SlimeModel<>(p_174537_.bakeLayer(ModelLayers.SLIME_OUTER));
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (!p_117352_.isInvisible()) {
            VertexConsumer vertexconsumer = p_117350_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(p_117352_)));
            this.getParentModel().renderToBuffer(p_117349_, vertexconsumer, p_117351_, LivingEntityRenderer.getOverlayCoords(p_117352_, 0.0F), 1.0F, 1.0F, 1.0F, 0.15F);
        }
    }
}