package org.astemir.nethermania.client.render.angrydemon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.EntityAngryDemon;
import org.astemir.nethermania.common.entity.EntityChariot;

public class RendererAngryDemon extends SkillsRendererLivingEntity<EntityAngryDemon, ModelWrapperAngryDemon> {

    public RendererAngryDemon(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperAngryDemon());
    }

    @Override
    protected void scale(EntityAngryDemon p_115314_, PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(1.5f,1.5f,1.5f);
    }
}
