package net.nullean.reach.client.model;// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports



import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.nullean.reach.Reach;

public class ModelSpreader<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Reach.MOD_ID, "spreader"), "main");
	private final ModelPart HEAD;
	private final ModelPart JAW;
	private final ModelPart EAR_LEFT;
	private final ModelPart EAR_RIGHT;
	private final ModelPart BODY;
	private final ModelPart HAIR_LEFT_TOP;
	private final ModelPart HAIR_LEFT_MIDDLE;
	private final ModelPart HAIR_LEFT_BOTTOM;
	private final ModelPart HAIR_RIGHT_TOP;
	private final ModelPart HAIR_RIGHT_MIDDLE;
	private final ModelPart HAIR_RIGHT_BOTTOM;

	public ModelSpreader(ModelPart root) {
		this.HEAD = root.getChild("HEAD");
		this.JAW = root.getChild("JAW");
		this.EAR_LEFT = root.getChild("EAR_LEFT");
		this.EAR_RIGHT = root.getChild("EAR_RIGHT");
		this.BODY = root.getChild("BODY");
		this.HAIR_LEFT_TOP = root.getChild("HAIR_LEFT_TOP");
		this.HAIR_LEFT_MIDDLE = root.getChild("HAIR_LEFT_MIDDLE");
		this.HAIR_LEFT_BOTTOM = root.getChild("HAIR_LEFT_BOTTOM");
		this.HAIR_RIGHT_TOP = root.getChild("HAIR_RIGHT_TOP");
		this.HAIR_RIGHT_MIDDLE = root.getChild("HAIR_RIGHT_MIDDLE");
		this.HAIR_RIGHT_BOTTOM = root.getChild("HAIR_RIGHT_BOTTOM");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("HEAD", CubeListBuilder.create().texOffs(0, 26).addBox(-8.0F, -18.0F, -7.5F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("JAW", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -13.0F, -8.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("EAR_LEFT", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -15.0F, -2.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("EAR_RIGHT", CubeListBuilder.create().texOffs(0, 26).addBox(8.0F, -15.0F, -2.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("BODY", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
 		partdefinition.addOrReplaceChild("HAIR_LEFT_TOP", CubeListBuilder.create().texOffs(46, 36).addBox(-22.0F, -13.0F, 0.0F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("HAIR_LEFT_MIDDLE", CubeListBuilder.create().texOffs(46, 32).addBox(-22.0F, -11.0F, 1.5F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("HAIR_LEFT_BOTTOM", CubeListBuilder.create().texOffs(46, 34).addBox(-22.0F, -11.0F, -1.5F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("HAIR_RIGHT_TOP", CubeListBuilder.create().texOffs(46, 30).addBox(-22.0F, -13.0F, 0.0F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("HAIR_RIGHT_MIDDLE", CubeListBuilder.create().texOffs(46, 28).addBox(-22.0F, -11.0F, 1.5F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 24.0F, 0.0F));
		partdefinition.addOrReplaceChild("HAIR_RIGHT_BOTTOM", CubeListBuilder.create().texOffs(46, 26).addBox(-22.0F, -11.0F, -1.5F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(32.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		/**this.HAIR_RIGHT_BOTTOM.zRot = -1.2217305F;
		this.HAIR_RIGHT_MIDDLE.zRot = -1.134464F;
		this.HAIR_RIGHT_TOP.zRot = -0.87266463F;
		
		this.HAIR_LEFT_TOP.zRot = 0.87266463F;
		this.HAIR_LEFT_MIDDLE.zRot = 1.134464F;
		
		this.HAIR_LEFT_BOTTOM.zRot = 1.2217305F;
		float f1 = Mth.cos(limbSwing * 1.5F + (float)Math.PI) * limbSwingAmount;
		this.HAIR_RIGHT_BOTTOM.zRot += f1 * 1.3F;
		this.HAIR_RIGHT_MIDDLE.zRot += f1 * 1.2F;
		this.HAIR_RIGHT_TOP.zRot += f1 * 0.6F;
		this.HAIR_LEFT_TOP.zRot += f1 * 0.6F;
		this.HAIR_LEFT_MIDDLE.zRot += f1 * 1.2F;
		this.HAIR_LEFT_BOTTOM.zRot += f1 * 1.3F;
		float f2 = 1.0F;
		float f3 = 1.0F;
		this.HAIR_RIGHT_BOTTOM.zRot += 0.05F * Mth.sin(ageInTicks * 1.0F * -0.4F);
		this.HAIR_RIGHT_MIDDLE.zRot += 0.1F * Mth.sin(ageInTicks * 1.0F * 0.2F);
		this.HAIR_RIGHT_TOP.zRot += 0.1F * Mth.sin(ageInTicks * 1.0F * 0.4F);
		this.HAIR_LEFT_TOP.zRot += 0.1F * Mth.sin(ageInTicks * 1.0F * 0.4F);
		this.HAIR_LEFT_MIDDLE.zRot += 0.1F * Mth.sin(ageInTicks * 1.0F * 0.2F);
		this.HAIR_LEFT_BOTTOM.zRot += 0.05F * Mth.sin(ageInTicks * 1.0F * -0.4F);**/
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		HEAD.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		JAW.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		EAR_LEFT.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		EAR_RIGHT.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		BODY.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_LEFT_TOP.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_LEFT_MIDDLE.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_LEFT_BOTTOM.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_RIGHT_TOP.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_RIGHT_MIDDLE.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		HAIR_RIGHT_BOTTOM.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}