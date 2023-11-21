package org.astemir.nethermania.client.render.chariot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.math.MathUtils;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.EntityChariot;
import org.astemir.nethermania.common.entity.EntityPunk;

public class ModelChariot extends SkillsAnimatedModel<EntityChariot, IDisplayArgument> {

    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(NetherMania.MOD_ID,"entity/chariot.png");
    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/chariot.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(NetherMania.MOD_ID,"entity/chariot.animation.json");

    public ModelChariot() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityChariot animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
    }

    @Override
    public ResourceLocation getTexture(EntityChariot target) {
        return TEXTURE;
    }
}
