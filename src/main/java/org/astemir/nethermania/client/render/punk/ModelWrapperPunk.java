package org.astemir.nethermania.client.render.punk;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityPunk;

public class ModelWrapperPunk extends SkillsWrapperEntity<EntityPunk> {

    public ModelPunk MODEL = new ModelPunk();

    @Override
    public SkillsModel<EntityPunk, IDisplayArgument> getModel(EntityPunk target) {
        return MODEL;
    }
}
