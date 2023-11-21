package org.astemir.nethermania.client.render.krampus;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityKrampus;

public class ModelWrapperKrampus extends SkillsWrapperEntity<EntityKrampus> {

    public ModelKrampus MODEL = new ModelKrampus();

    @Override
    public SkillsModel<EntityKrampus, IDisplayArgument> getModel(EntityKrampus target) {
        return MODEL;
    }
}
