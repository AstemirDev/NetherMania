package org.astemir.nethermania.client.render.angrydemon;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.EntityAngryDemon;
import org.astemir.nethermania.common.entity.EntityChariot;

public class ModelWrapperAngryDemon extends SkillsWrapperEntity<EntityAngryDemon> {

    public ModelAngryDemon MODEL = new ModelAngryDemon();



    @Override
    public SkillsModel<EntityAngryDemon, IDisplayArgument> getModel(EntityAngryDemon target) {
        return MODEL;
    }
}
