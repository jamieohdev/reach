package net.nullean.reach.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.nullean.reach.client.ReachModelLayers;
import net.nullean.reach.client.model.BlemishModel;
import net.nullean.reach.entity.monster.Blemish;

public class BlemishRenderer extends MobRenderer<Blemish, BlemishModel<Blemish>> {
    private static final ResourceLocation BLEMISH_LOCATION = new ResourceLocation("reach:textures/entity/blemish.png");

    public BlemishRenderer(EntityRendererProvider.Context p_174340_) {
        super(p_174340_, new BlemishModel<>(p_174340_.bakeLayer(ReachModelLayers.BLEMISH)), 0.7F);
    }

    public ResourceLocation getTextureLocation(Blemish p_113771_) {
        return BLEMISH_LOCATION;
    }
}
