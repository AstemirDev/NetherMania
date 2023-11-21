package org.astemir.nethermania.client.render.rocker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.math.MathUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class LayerNetherRockerFire extends SkillsModelLayer<EntityNetherRocker, IDisplayArgument,ModelNetherRocker> {

    public ResourceArray TEXTURE = new ResourceArray(NetherMania.MOD_ID,"entity/fire/fire_shield_%s.png",14,0.5f);

    public LayerNetherRockerFire(ModelNetherRocker model) {
        super(model);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, EntityNetherRocker instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
        if (instance.FIRE_SHIELD.get(instance)) {
            float ticks = ((float) (instance.tickCount)) + pPartialTick;
            int count = 8;
            for (int i = 0; i < count; i++) {
                float scale = MathUtils.abs(MathUtils.sin(ticks / 20f) * 0.25f) + 0.75f;
                float speed = 10;
                float f0 = instance.tickCount + (i * 40);
                float f1 = (ticks * speed / 2f) % 360;
                float f3 = (i % 2 == 0) ? 1 : -1;
                pPoseStack.pushPose();
                pPoseStack.scale(scale - (i / 30f), 1f, scale - (i / 30f));
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees(f3 * f1));
                ShimmerLib.postModelForce(pPoseStack,getModel(), SkillsRenderTypes.entityTranslucentEmissive(TEXTURE.getResourceLocation((int) f0)),ShimmerLib.LIGHT_UNSHADED, OverlayTexture.NO_OVERLAY,1,1,1,(float)i / 20.0F);
                pPoseStack.popPose();
            }
            ShimmerLib.renderEntityPost();
        }
    }


    @Override
    public ResourceLocation getTexture(EntityNetherRocker instance) {
        return TEXTURE.getResourceLocation(instance.tickCount);
    }
}
