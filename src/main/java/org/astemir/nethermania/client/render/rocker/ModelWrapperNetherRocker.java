package org.astemir.nethermania.client.render.rocker;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class ModelWrapperNetherRocker extends SkillsWrapperEntity<EntityNetherRocker> {

    public ModelNetherRocker MODEL = new ModelNetherRocker();

    @Override
    public SkillsModel<EntityNetherRocker, IDisplayArgument> getModel(EntityNetherRocker target) {
        return MODEL;
    }
}
