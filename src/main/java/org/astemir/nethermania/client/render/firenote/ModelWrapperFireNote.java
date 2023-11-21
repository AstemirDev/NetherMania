package org.astemir.nethermania.client.render.firenote;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityFireNote;

public class ModelWrapperFireNote extends SkillsWrapperEntity<EntityFireNote> {

    public static ModelFireNote MODEL = new ModelFireNote();

    @Override
    public SkillsModel<EntityFireNote, IDisplayArgument> getModel(EntityFireNote target) {
        return MODEL;
    }

    @Override
    public void renderWrapper(PoseStack poseStack, VertexConsumer bufferSource, int packedLight, int packedOverlay, float r, float g, float b, float a, RenderCall renderCall, boolean resetBuffer) {
        SkillsModel<EntityFireNote,IDisplayArgument> model = getModel(renderTarget);
        model.modelWrapper = this;
        model.renderWithLayers(poseStack,bufferSource,packedLight, packedOverlay, r, g, b, a,renderCall,resetBuffer);
    }

    @Override
    public RenderType getDefaultRenderType() {
        return SkillsRenderTypes.eyesTransparentNoCull(MODEL.getTexture(getRenderTarget()));
    }
}
