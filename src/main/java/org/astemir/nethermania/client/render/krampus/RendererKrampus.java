package org.astemir.nethermania.client.render.krampus;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.EntityKrampus;

public class RendererKrampus extends SkillsRendererLivingEntity<EntityKrampus,ModelWrapperKrampus> {

    public RendererKrampus(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperKrampus());
    }
}
