package org.astemir.nethermania.mixin.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.astemir.nethermania.client.INMParticleEngine;
import org.astemir.nethermania.client.render.particle.NMParticleRenderTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Queue;

@Mixin({ParticleEngine.class})
public class ParticleEngineMixin implements INMParticleEngine {

    @Shadow @Final private Queue<Particle> particlesToAdd;

    @Mutable
    @Shadow @Final private Map<ParticleRenderType, Queue<Particle>> particles;

    public ParticleEngineMixin() {
        ParticleEngine.RENDER_ORDER = ImmutableList.of(ParticleRenderType.TERRAIN_SHEET, ParticleRenderType.PARTICLE_SHEET_OPAQUE, ParticleRenderType.PARTICLE_SHEET_LIT, ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT, NMParticleRenderTypes.VERY_BRIGHT_FLAME,ParticleRenderType.CUSTOM);
        particles = Maps.newTreeMap(net.minecraftforge.client.ForgeHooksClient.makeParticleRenderTypeComparator(ParticleEngine.RENDER_ORDER));
    }

    @Override
    public void addUnlimited(Particle particle) {
        particlesToAdd.add(particle);
    }
}
