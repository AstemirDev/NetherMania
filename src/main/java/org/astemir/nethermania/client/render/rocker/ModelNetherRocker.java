package org.astemir.nethermania.client.render.rocker;



import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.lib.shimmer.DynamicEntityLight;
import org.astemir.api.math.Color;
import org.astemir.api.math.MathUtils;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;


public class ModelNetherRocker extends SkillsAnimatedModel<EntityNetherRocker, IDisplayArgument> {

    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(NetherMania.MOD_ID,"entity/rocker/rocker.png");
    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/rocker.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(NetherMania.MOD_ID,"entity/rocker.animation.json");

    public ModelNetherRocker() {
        super(MODEL, ANIMATIONS);
        addLayer(new LayerNetherRockerLight(this));
        addLayer(new LayerNetherRockerFire(this));
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityNetherRocker animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement head = getModelElement("head");
        if (head != null){
            head.showModel = !(EntityNetherRocker.IS_HEAD_HIDDEN.get(animated));
        }
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        if (getRenderTarget() != null) {
            float ticks = ((float) getRenderTarget().tickCount) + Minecraft.getInstance().getPartialTick();
            DynamicEntityLight dynamicEntityLight = getRenderTarget().getOrCreateLight();
            dynamicEntityLight.setColor(Color.YELLOW.interpolate(Color.ORANGE,MathUtils.abs(MathUtils.sin(ticks/20f))));
            dynamicEntityLight.setRadius(MathUtils.abs(MathUtils.sin(ticks/40f)*2)+3);
            dynamicEntityLight.tick(getRenderTarget());
        }
    }

    @Override
    public ResourceLocation getTexture(EntityNetherRocker target) {
        return TEXTURE;
    }
}
