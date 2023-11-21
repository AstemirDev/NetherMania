package org.astemir.nethermania.client.render.chariot;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.EntityChariot;
import org.astemir.nethermania.common.entity.EntityPunk;

public class RendererChariot extends SkillsRendererLivingEntity<EntityChariot, ModelWrapperChariot> {

    public RendererChariot(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperChariot());
    }
}
