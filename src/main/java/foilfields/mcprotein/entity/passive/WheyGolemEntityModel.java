package foilfields.mcprotein.entity.passive;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

/** Whey golem model class.
 * @author woukie
 */
public class WheyGolemEntityModel<T extends WheyGolemEntity> extends SnowGolemEntityModel<T> {
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart root;
	private final ModelPart upperBody;

	public WheyGolemEntityModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.root = root.getChild("root");
		this.upperBody = root.getChild("upper_body");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, 5.25F, 0.0F));

		ModelPartData left_arm_rotation = left_arm.addChild("left_arm_rotation", ModelPartBuilder.create().uv(32, 0).cuboid(-0.5F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new Dilation(-0.5F)), ModelTransform.of(-0.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.9948F));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(-14.5F, 5.25F, 0.0F));

		ModelPartData right_arm_flip = right_arm.addChild("right_arm_flip", ModelPartBuilder.create(), ModelTransform.of(5.0F, 1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData right_arm_rotation = right_arm_flip.addChild("right_arm_rotation", ModelPartBuilder.create().uv(32, 0).cuboid(-0.5F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new Dilation(-0.5F)), ModelTransform.of(-5.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9948F));

		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 20).cuboid(-5.0F, 1.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

		ModelPartData upper_body = modelPartData.addChild("upper_body", ModelPartBuilder.create().uv(0, 40).cuboid(-6.0F, -21.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
		this.upperBody.yaw = headYaw * ((float)Math.PI / 180) * 0.25f;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		upperBody.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}