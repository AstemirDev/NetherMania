package org.astemir.nethermania.client.render.rockerhead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.animation.AnimationUtils;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.vector.Vector3;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.client.render.rocker.ModelNetherRocker;
import org.astemir.nethermania.common.entity.EntityFireNote;
import org.astemir.nethermania.common.entity.EntityRockerHead;

public class ModelRockerHead extends SkillsModel<EntityRockerHead, IDisplayArgument> {

    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/rocker_head.json");

    public ModelRockerHead() {
        super(MODEL);
        addLayer(new LayerRockerHeadLight(this));
    }

    @Override
    public void customAnimate(EntityRockerHead animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement element = getModelElement("head");
        ModelElement tongue = getModelElement("tongue1");
        float partialTicks = Minecraft.getInstance().getPartialTick();
        float f1 = ((float)(animated.tickCount))+partialTicks;
        float f2 = f1%360f;
        if (element != null){
            element.setRotation(new Vector3(f2/2f,0,MathUtils.sin(f1/4f)/2f));
        }
        if (tongue != null){
            tongue.showModel = false;
        }
    }


    @Override
    public void renderWithLayers(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderWithLayers(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public ResourceLocation getTexture(EntityRockerHead target) {
        return ModelNetherRocker.TEXTURE;
    }
}
