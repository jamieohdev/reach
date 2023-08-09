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
import net.nullean.reach.client.model.SpreaderModel;
import net.nullean.reach.entity.monster.Spreader;

public class SpreaderRenderer<T extends Spreader> extends MobRenderer<T, SpreaderModel<T>> {
    private static final ResourceLocation SPREADER_LOCATION = new ResourceLocation(Reach.MOD_ID,"textures/entity/shroom.png");
    private static final ResourceLocation SPREADER_SHOOTING_LOCATION = new ResourceLocation(Reach.MOD_ID,"textures/entity/shroom.png");

    public SpreaderRenderer(EntityRendererProvider.Context p_174129_) {
        super(p_174129_, new SpreaderModel<>(p_174129_.bakeLayer(ReachModelLayers.SPREADER)), 1.5F);
    }

    public ResourceLocation getTextureLocation(Spreader p_114755_) {
        return p_114755_.isCharging() ? SPREADER_SHOOTING_LOCATION : SPREADER_LOCATION;
    }

    protected void scale(Spreader p_114757_, PoseStack p_114758_, float p_114759_) {
        p_114758_.scale(4.5F, 4.5F, 4.5F);
    }

    protected void setupRotations(T p_116035_, PoseStack p_116036_, float p_116037_, float p_116038_, float p_116039_) {
        float f = Mth.lerp(p_116039_, p_116035_.xBodyRotO, p_116035_.xBodyRot);
        float f1 = Mth.lerp(p_116039_, p_116035_.zBodyRotO, p_116035_.zBodyRot);
        p_116036_.translate(0.0F, 0.5F, 0.0F);
        p_116036_.mulPose(Axis.YP.rotationDegrees(180.0F - p_116038_));
        p_116036_.mulPose(Axis.XP.rotationDegrees(f));
        p_116036_.mulPose(Axis.YP.rotationDegrees(f1));
        p_116036_.translate(0.0F, -1.2F, 0.0F);
    }

    protected float getBob(T p_116032_, float p_116033_) {
        return Mth.lerp(p_116033_, p_116032_.oldTentacleAngle, p_116032_.tentacleAngle);
    }
}