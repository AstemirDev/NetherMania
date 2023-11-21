package org.astemir.nethermania.client.render.rockerhead;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.nethermania.common.entity.EntityRockerHead;

public class RendererRockerHead extends SkillsRendererEntity<EntityRockerHead, ModelWrapperRockerHead> {

    @Override
    protected int getBlockLightLevel(EntityRockerHead p_114496_, BlockPos p_114497_) {
        return 15;
    }

    public RendererRockerHead(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperRockerHead());
    }
}
