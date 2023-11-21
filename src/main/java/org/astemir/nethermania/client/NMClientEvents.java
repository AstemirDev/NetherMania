package org.astemir.nethermania.client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.client.render.particle.FireParticle;
import org.astemir.nethermania.common.particle.NMParticles;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = NetherMania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NMClientEvents {

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent e){
        e.register(NMParticles.FIRE_PARTICLE.get(), FireParticle.Provider::new);
    }
}
