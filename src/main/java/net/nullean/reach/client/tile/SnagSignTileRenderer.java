package net.nullean.reach.client.tile;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.nullean.reach.block.ReachWoodTypes;
import net.nullean.reach.block.SignReachBlock;
import net.nullean.reach.block.StandingSignReachBlock;
import net.nullean.reach.block.WallSignReachBlock;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;


public class SnagSignTileRenderer  implements BlockEntityRenderer<SnagSignTile> {
    public static final int MAX_LINE_WIDTH = 90;
    private static final int LINE_HEIGHT = 10;
    private static final String STICK = "stick";
    private static final int BLACK_TEXT_OUTLINE_COLOR = -988212;
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private final Map<WoodType, SnagSignTileRenderer.SignModel> signModels;
    private final Font font;

    public SnagSignTileRenderer(BlockEntityRendererProvider.Context context) {
        this.signModels = WoodType.values().collect(ImmutableMap.toImmutableMap((p_173645_) -> {
            return p_173645_;
        }, (p_173651_) -> {
            return new SnagSignTileRenderer.SignModel(context.bakeLayer(ModelLayers.createSignModelName(p_173651_)));
        }));
        this.font = context.getFont();
    }

    @Override
    public void render(SnagSignTile p_112497_, float p_112498_, PoseStack p_112499_, MultiBufferSource p_112500_, int p_112501_, int p_112502_) {
        BlockState blockstate = p_112497_.getBlockState();
        p_112499_.pushPose();
        float f = 0.6666667F;
        ReachWoodTypes woodtype = getWoodType(blockstate.getBlock());
        SnagSignTileRenderer.SignModel signrenderer$signmodel = this.signModels.get(woodtype);
        if (blockstate.getBlock() instanceof StandingSignReachBlock) {
            p_112499_.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float)(blockstate.getValue(StandingSignReachBlock.ROTATION) * 360) / 16.0F);
            p_112499_.mulPose(Axis.YP.rotationDegrees(f1));
            signrenderer$signmodel.stick.visible = true;
        } else {
            p_112499_.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.getValue(WallSignReachBlock.FACING).toYRot();
            p_112499_.mulPose(Axis.YP.rotationDegrees(f4));
            p_112499_.translate(0.0D, -0.3125D, -0.4375D);
            signrenderer$signmodel.stick.visible = false;
        }

        p_112499_.pushPose();
        p_112499_.scale(0.6666667F, -0.6666667F, -0.6666667F);
        Material material = Sheets.getSignMaterial(woodtype);
        VertexConsumer vertexconsumer = material.buffer(p_112500_, signrenderer$signmodel::renderType);
        signrenderer$signmodel.root.render(p_112499_, vertexconsumer, p_112501_, p_112502_);
        p_112499_.popPose();
        float f2 = 0.010416667F;
        p_112499_.translate(0.0D, (double)0.33333334F, (double)0.046666667F);
        p_112499_.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = getDarkColor(p_112497_);
        int j = 20;
        FormattedCharSequence[] aformattedcharsequence = p_112497_.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (p_173653_) -> {
            List<FormattedCharSequence> list = this.font.split(p_173653_, 90);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean flag;
        int l;
        if (p_112497_.hasGlowingText()) {
            k = p_112497_.getColor().getTextColor();
            flag = isOutlineVisible(p_112497_, k);
            l = 15728880;
        } else {
            k = i;
            flag = false;
            l = p_112501_;
        }

        for(int i1 = 0; i1 < 4; ++i1) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f3 = (float)(-this.font.width(formattedcharsequence) / 2);
            if (flag) {
                this.font.drawInBatch8xOutline(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, i, p_112499_.last().pose(), p_112500_, l);
            } else {
                this.font.drawInBatch(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, false, p_112499_.last().pose(), p_112500_, false, 0, l);
            }
        }

        p_112499_.popPose();
    }

    private static boolean isOutlineVisible(SnagSignTile p_173642_, int p_173643_) {
        if (p_173643_ == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping()) {
                return true;
            } else {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(p_173642_.getBlockPos())) < (double)OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    private static int getDarkColor(SnagSignTile p_173640_) {
        int i = p_173640_.getColor().getTextColor();
        double d0 = 0.4D;
        int j = (int)((double) NativeImage.getR(i) * 0.4D);
        int k = (int)((double)NativeImage.getG(i) * 0.4D);
        int l = (int)((double)NativeImage.getB(i) * 0.4D);
        return i == DyeColor.BLACK.getTextColor() && p_173640_.hasGlowingText() ? -988212 : NativeImage.combine(0, l, k, j);
    }

    public static ReachWoodTypes getWoodType(Block block) {
        ReachWoodTypes woodtype;
        if (block instanceof SignReachBlock) {
            woodtype = ((SignReachBlock)block).type();
        } else {
            woodtype = ReachWoodTypes.SNAG;
        }

        return woodtype;
    }

    public static SnagSignTileRenderer.SignModel createSignModel(EntityModelSet p_173647_, WoodType p_173648_) {
        return new SnagSignTileRenderer.SignModel(p_173647_.bakeLayer(ModelLayers.createSignModelName(p_173648_)));
    }

    @OnlyIn(Dist.CLIENT)
    public static final class SignModel extends Model {
        public final ModelPart root;
        public final ModelPart stick;

        public SignModel(ModelPart p_173657_) {
            super(RenderType::entityCutoutNoCull);
            this.root = p_173657_;
            this.stick = p_173657_.getChild("stick");
        }

        public void renderToBuffer(PoseStack p_112510_, VertexConsumer p_112511_, int p_112512_, int p_112513_, float p_112514_, float p_112515_, float p_112516_, float p_112517_) {
            this.root.render(p_112510_, p_112511_, p_112512_, p_112513_, p_112514_, p_112515_, p_112516_, p_112517_);
        }
    }
}