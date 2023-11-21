package org.astemir.nethermania.common.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.register.IRegistry;
import org.astemir.nethermania.NetherMania;

public class NMParticles implements IRegistry<ParticleType> {

    public static DeferredRegister<ParticleType<?>> PARTICLES = IRegistry.create(NetherMania.MOD_ID, ForgeRegistries.PARTICLE_TYPES);

    public static RegistryObject<SimpleParticleType> FIRE_PARTICLE = PARTICLES.register("fire_particle",()->new SimpleParticleType(false));

}
