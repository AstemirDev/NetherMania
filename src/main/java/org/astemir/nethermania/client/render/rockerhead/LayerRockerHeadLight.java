package org.astemir.nethermania.client.render.rockerhead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.nethermania.client.render.rocker.LayerNetherRockerLight;
import org.astemir.nethermania.common.entity.EntityRockerHead;

public class LayerRockerHeadLight extends SkillsModelLayer<EntityRockerHead, IDisplayArgument, ModelRockerHead> {

    public LayerRockerHeadLight(ModelRockerHead model) {
        super(model);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, EntityRockerHead instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(SkillsRenderTypes.eyesTransparentNoCull((getTexture(instance))));
        getModel().renderModel(pPoseStack, vertexconsumer, 15728640, 0, r, g, b, 0.75f, RenderCall.LAYER,false);
        ShimmerLib.postModelForce(pPoseStack,getModel(),SkillsRenderTypes.eyesTransparentNoCull((getTexture(instance))),15728640, OverlayTexture.NO_OVERLAY,1,1,1,1);
        ShimmerLib.renderEntityPost();
    }


    @Override
    public ResourceLocation getTexture(EntityRockerHead instance) {
        return LayerNetherRockerLight.TEXTURE.getResourceLocation(instance.tickCount);
    }
}
