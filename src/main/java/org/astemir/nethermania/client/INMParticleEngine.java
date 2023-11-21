package org.astemir.nethermania.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public interface INMParticleEngine {

    void addUnlimited(Particle particle);


    static INMParticleEngine getInstance(){
        return (INMParticleEngine)Minecraft.getInstance().particleEngine;
    }
}
