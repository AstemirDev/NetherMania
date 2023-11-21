package org.astemir.nethermania.client.render.angrydemon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.EntityAngryDemon;
import org.astemir.nethermania.common.entity.EntityChariot;

public class ModelAngryDemon extends SkillsAnimatedModel<EntityAngryDemon, IDisplayArgument> {

    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(NetherMania.MOD_ID,"entity/angry_demon.png");
    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/angry_demon.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(NetherMania.MOD_ID,"entity/angry_demon.animation.json");

    public ModelAngryDemon() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityAngryDemon animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        if (getRenderTarget().tickCount > 1) {
            super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        }
    }



    @Override
    public ResourceLocation getTexture(EntityAngryDemon target) {
        return TEXTURE;
    }
}
