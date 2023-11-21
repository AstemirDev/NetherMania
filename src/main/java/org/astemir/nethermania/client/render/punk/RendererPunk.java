package org.astemir.nethermania.client.render.punk;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.EntityPunk;

public class RendererPunk extends SkillsRendererLivingEntity<EntityPunk, ModelWrapperPunk> {

    public RendererPunk(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperPunk());
    }
}
