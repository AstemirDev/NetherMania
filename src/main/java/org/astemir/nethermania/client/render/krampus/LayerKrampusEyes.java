package org.astemir.nethermania.client.render.krampus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.EntityKrampus;

public class LayerKrampusEyes extends SkillsModelLayer<EntityKrampus, IDisplayArgument, ModelKrampus> {

    public ResourceLocation TEXTURE = ResourceUtils.loadTexture(NetherMania.MOD_ID,"entity/krampus/krampus_eyes.png");

    public LayerKrampusEyes(ModelKrampus model) {
        super(model);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, EntityKrampus instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
        renderModel(pPoseStack,pBuffer, SkillsRenderTypes.eyes(getTexture(instance)),15728640,1,1,1,1);
    }


    @Override
    public ResourceLocation getTexture(EntityKrampus instance) {
        return TEXTURE;
    }
}
