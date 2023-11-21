package org.astemir.nethermania.client.render.firenote;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.EntityFireNote;
import org.astemir.nethermania.common.entity.EntityPunk;

public class RendererFireNote extends SkillsRendererEntity<EntityFireNote, ModelWrapperFireNote> {

    public RendererFireNote(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperFireNote());
    }
}
