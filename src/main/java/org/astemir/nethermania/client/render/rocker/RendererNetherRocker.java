package org.astemir.nethermania.client.render.rocker;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class RendererNetherRocker extends SkillsRendererLivingEntity<EntityNetherRocker,ModelWrapperNetherRocker> {

    public RendererNetherRocker(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperNetherRocker());
    }


    @Override
    protected int getBlockLightLevel(EntityNetherRocker p_113910_, BlockPos p_113911_) {
        return 15;
    }
}
