package org.astemir.nethermania.client.render.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.render.ISkillsRenderer;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.wrapper.IModelWrapper;
import org.astemir.api.common.misc.ICustomRendered;

public class TrailParticle<K extends Entity & ICustomRendered> extends Particle {

    private final K entity;
    private int life;
    private float opacityPower;
    private boolean bright;

    public TrailParticle(ClientLevel level, K entity,float opacityPower,boolean bright) {
        super(level, entity.getX(), entity.getY(), entity.getZ(), entity.getDeltaMovement().x, entity.getDeltaMovement().y, entity.getDeltaMovement().z);
        this.entity = entity;
        this.opacityPower = opacityPower;
        this.bright = bright;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @Override
    public void render(VertexConsumer p_107039_, Camera p_107040_, float p_107041_) {
        PoseStack stack = new PoseStack();
        MultiBufferSource.BufferSource multiBufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
        if (renderer != null) {
            IModelWrapper wrapperEntity = ((ISkillsRenderer) renderer).getModelWrapper();
            if (wrapperEntity != null) {
                ResourceLocation texture = wrapperEntity.getModel(entity).getTexture(entity);
                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(texture));
                if (bright) {
                    consumer = multiBufferSource.getBuffer(SkillsRenderTypes.eyesTransparentNoCull(texture));
                }
                stack.pushPose();
                float x1 = (float) (x - p_107040_.getPosition().x());
                float y1 = (float) (y - p_107040_.getPosition().y());
                float z1 = (float) (z - p_107040_.getPosition().z());
                stack.translate(x1, y1, z1);
                stack.scale(-1.0F, -1.0F, 1.0F);
                stack.translate(0.0D, (double) -1.501F, 0.0D);
                float f1 = ((float) (5 - life)) / opacityPower;

                float yaw = Mth.rotLerp(p_107041_, entity.yRotO, entity.getYRot());
                if (entity instanceof LivingEntity livingEntity){
                    yaw = Mth.rotLerp(p_107041_, livingEntity.yBodyRotO, livingEntity.yBodyRot);

                }
                stack.mulPose(Vector3f.YP.rotationDegrees(180.0F + yaw));
                wrapperEntity.getModel(entity).renderModel(stack, consumer, renderer.getPackedLightCoords(entity, p_107041_), OverlayTexture.NO_OVERLAY, 1, 1, 1, f1, RenderCall.MODEL, false);
            }
        }
        stack.popPose();
        multiBufferSource.endBatch();
    }

    public void tick() {
        life++;
        this.xd *= (double)this.friction;
        this.yd *= (double)this.friction;
        this.zd *= (double)this.friction;
        if (this.life > 5) {
            this.remove();
        }
    }
}
