package net.nullean.reach.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.nullean.reach.Reach;
import net.nullean.reach.client.ReachModelLayers;
import net.nullean.reach.client.model.MooBooModel;
import net.nullean.reach.entity.animal.MooBoo;

@OnlyIn(Dist.CLIENT)
public class MooBooRenderer extends MobRenderer<MooBoo, MooBooModel<MooBoo>>
{
    private static final ResourceLocation MOOBOO_LOCATION = new ResourceLocation(Reach.MOD_ID,"textures/entity/mooboo.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(MOOBOO_LOCATION);

    public MooBooRenderer(EntityRendererProvider.Context p_173956_) {
        super(p_173956_, new MooBooModel<>(p_173956_.bakeLayer(ReachModelLayers.MOOBOO)), 0.7F);
        this.addLayer(new MooBooTransLayer<>(this,  p_173956_.getModelSet()));
    }

    protected int getBlockLightLevel(ShulkerBullet p_115869_, BlockPos p_115870_) {
        return 15;
    }

   /** public void render(MooBoo p_115862_, float p_115863_, float p_115864_, PoseStack p_115865_, MultiBufferSource p_115866_, int p_115867_) {
        VertexConsumer vertexconsumer1 = p_115866_.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(p_115865_, vertexconsumer1, p_115867_, LivingEntityRenderer.getOverlayCoords(p_115862_, 0.0F), 1.0F, 1.0F, 1.0F, 0.15F);

        super.render(p_115862_, p_115863_, p_115864_, p_115865_, p_115866_, p_115867_);
    }**/

    public ResourceLocation getTextureLocation(MooBoo p_114029_) {
        return MOOBOO_LOCATION;
    }
}
