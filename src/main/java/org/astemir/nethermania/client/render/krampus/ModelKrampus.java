package org.astemir.nethermania.client.render.krampus;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.math.MathUtils;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.EntityKrampus;

public class ModelKrampus extends SkillsAnimatedModel<EntityKrampus, IDisplayArgument> {

    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(NetherMania.MOD_ID,"entity/krampus/krampus.png");
    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/krampus.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(NetherMania.MOD_ID,"entity/krampus.animation.json");

    public ModelKrampus() {
        super(MODEL, ANIMATIONS);
        addLayer(new LayerKrampusEyes(this));
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityKrampus animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        if (getModelElement("head") != null){
            lookAt(getModelElement("head"),headPitch,headYaw);
        }
    }

    @Override
    public void renderWithLayers(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        if (getRenderTarget().tickCount < 10){
            float f1 = (((float)getRenderTarget().tickCount)+Minecraft.getInstance().getPartialTick()) /10f;
            stack.translate(0,1-f1,0);
        }
        super.renderWithLayers(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public ResourceLocation getTexture(EntityKrampus target) {
        return TEXTURE;
    }
}
