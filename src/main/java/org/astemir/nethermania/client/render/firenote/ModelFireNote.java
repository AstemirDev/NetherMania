package org.astemir.nethermania.client.render.firenote;

import com.lowdragmc.shimmer.client.postprocessing.PostProcessing;
import com.lowdragmc.shimmer.client.shader.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.utils.ResourceUtils;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.EntityFireNote;

public class ModelFireNote extends SkillsAnimatedModel<EntityFireNote, IDisplayArgument> {

    public static final ResourceArray TEXTURE = new ResourceArray(NetherMania.MOD_ID,"entity/fire_note/fire_note_%s.png",3,1);
    public static final ResourceLocation MODEL = ResourceUtils.loadModel(NetherMania.MOD_ID,"entity/fire_note.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(NetherMania.MOD_ID,"entity/fire_note.animation.json");

    public ModelFireNote() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }


    @Override
    public ResourceLocation getTexture(EntityFireNote target) {
        return TEXTURE.getResourceLocation(target.tickCount);
    }
}
