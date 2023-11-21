package org.astemir.nethermania.client.render.rockerhead;

import net.minecraft.client.renderer.RenderType;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityRockerHead;

public class ModelWrapperRockerHead extends SkillsWrapperEntity<EntityRockerHead> {

    public static ModelRockerHead MODEL = new ModelRockerHead();

    @Override
    public SkillsModel<EntityRockerHead, IDisplayArgument> getModel(EntityRockerHead target) {
        return MODEL;
    }

    @Override
    public RenderType getDefaultRenderType() {
        return SkillsRenderTypes.entityCutout(getModel(getRenderTarget()).getTexture(getRenderTarget()));
    }
}
