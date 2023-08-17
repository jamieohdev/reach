package net.nullean.reach.client.model;// Made with Blockbench 4.6.4
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
import net.minecraft.world.entity.Entity;
import net.nullean.reach.Reach;

import java.util.Arrays;

public class SpreaderModel2<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Reach.MOD_ID, "shroom"), "main");
	private final ModelPart bb_main, bb_tentacles;
	//private ModelPart[] bb_tentacles = new ModelPart[4];

	public SpreaderModel2(ModelPart root) {
		this.bb_main = root;
		this.bb_tentacles = root;
		//Arrays.setAll(this.bb_tentacles, (p_170995_) -> {
			//return root.getChild(createTentacleName(p_170995_));
		//});
	}

	private static String createTentacleName(int p_170992_) {
		return "tentacle" + p_170992_;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-8.0F, -28.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-7.0F, -27.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-6.0F, -12.0F, 6.0F, 12.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 70).addBox(-6.0F, -12.0F, -7.0F, 12.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(73, 1).addBox(-1.0F, -12.0F, -1.0F, 3.0F, 22.0F, 3.0F);
		//CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);

		for(int k = 0; k < 4; ++k) {
			double d0 = (double)k * Math.PI * 1.0D / 2.0D;
			float f = (float)Math.cos(d0) * 5.0F;
			float f2 = (float)Math.sin(d0) * 5.0F;
			d0 = (double)k * Math.PI * -2.0D / 8.0D + (Math.PI / 2D);
			float f3 = (float)d0;

			//bb_main.addOrReplaceChild("tentacle", cubelistbuilder, PartPose.offsetAndRotation(
			//		f, 24.0F, f2, 0.0F, f3, 0.0F));
			bb_main.addOrReplaceChild(createTentacleName(k), CubeListBuilder.create()
					.texOffs(73, 1).addBox(3.0F, -36.0F, 3.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(-6.0F, -36.0F, 3.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(3.0F, -36.0F, -6.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(-6.0F, -36.0F, -5.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		}


			/**partdefinition.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(73, 1).addBox(3.0F, -12.0F, 3.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(-6.0F, -12.0F, 3.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(3.0F, -12.0F, -6.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
					.texOffs(73, 1).addBox(-6.0F, -12.0F, -5.0F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
**/
		PartDefinition sheath_4_r1 = bb_main.addOrReplaceChild("sheath_4_r1", CubeListBuilder.create().texOffs(0, 87).addBox(-6.0F, -12.0F, 6.0F, 12.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		PartDefinition sheath_3_r1 = bb_main.addOrReplaceChild("sheath_3_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-6.0F, -12.0F, 6.0F, 12.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//for(ModelPart modelpart : this.bb_tentacles) {
		//	//	modelpart.xRot = ageInTicks;
		//}
	}

	public ModelPart root() {
		return this.bb_main;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}