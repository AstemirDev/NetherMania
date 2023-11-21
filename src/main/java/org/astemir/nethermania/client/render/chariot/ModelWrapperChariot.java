package org.astemir.nethermania.client.render.chariot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityChariot;

public class ModelWrapperChariot extends SkillsWrapperEntity<EntityChariot> {

    public ModelChariot MODEL = new ModelChariot();

    @Override
    public void renderWrapper(PoseStack poseStack, VertexConsumer bufferSource, int packedLight, int packedOverlay, float r, float g, float b, float a, RenderCall renderCall, boolean resetBuffer) {
        super.renderWrapper(poseStack, bufferSource, packedLight, packedOverlay, r, g, b, a, renderCall, resetBuffer);
    }



    @Override
    public RenderType getDefaultRenderType() {
        return SkillsRenderTypes.entityTranslucent(getModel(getRenderTarget()).getTexture(getRenderTarget()));
    }

    @Override
    public SkillsModel<EntityChariot, IDisplayArgument> getModel(EntityChariot target) {
        return MODEL;
    }
}
