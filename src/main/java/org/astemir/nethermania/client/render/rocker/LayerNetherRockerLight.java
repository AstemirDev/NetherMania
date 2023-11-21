package org.astemir.nethermania.client.render.rocker;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class LayerNetherRockerLight extends SkillsModelLayer<EntityNetherRocker, IDisplayArgument,ModelNetherRocker> {

    public static ResourceArray TEXTURE = new ResourceArray(NetherMania.MOD_ID,"entity/rocker/rocker_layer_%s.png",8,0.5f);

    public LayerNetherRockerLight(ModelNetherRocker model) {
        super(model);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, EntityNetherRocker instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
        renderModel(pPoseStack,pBuffer, SkillsRenderTypes.eyesTransparentNoCull(getTexture(instance)),15728640,1,1,1,0.75f);
        ShimmerLib.postModelForce(pPoseStack,getModel(), SkillsRenderTypes.eyesTransparentNoCull(getTexture(instance)),15728640, OverlayTexture.NO_OVERLAY,1,1,1,1f);
        ShimmerLib.renderEntityPost();
    }

    @Override
    public RenderType getRenderType(EntityNetherRocker instance) {
        return SkillsRenderTypes.eyes(getTexture(instance));
    }

    @Override
    public ResourceLocation getTexture(EntityNetherRocker instance) {
        return TEXTURE.getResourceLocation(instance.tickCount);
    }
}
