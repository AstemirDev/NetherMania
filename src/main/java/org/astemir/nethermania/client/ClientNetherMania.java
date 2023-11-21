package org.astemir.nethermania.client;


import net.minecraft.client.renderer.entity.EntityRenderers;
import org.astemir.api.IClientLoader;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.nethermania.client.render.angrydemon.RendererAngryDemon;
import org.astemir.nethermania.client.render.chariot.RendererChariot;
import org.astemir.nethermania.client.render.firenote.RendererFireNote;
import org.astemir.nethermania.client.render.krampus.RendererKrampus;
import org.astemir.nethermania.client.render.punk.RendererPunk;
import org.astemir.nethermania.client.render.rocker.RendererNetherRocker;
import org.astemir.nethermania.client.render.rockerhead.RendererRockerHead;
import org.astemir.nethermania.common.entity.NMEntities;

public class ClientNetherMania implements IClientLoader {

    public static ClientNetherMania INSTANCE = new ClientNetherMania();

    @Override
    public void load() {
        EntityRenderers.register(NMEntities.KRAMPUS.get(), RendererKrampus::new);
        EntityRenderers.register(NMEntities.PUNK.get(), RendererPunk::new);
        EntityRenderers.register(NMEntities.NETHER_ROCKER.get(), RendererNetherRocker::new);
        EntityRenderers.register(NMEntities.CHARIOT.get(), RendererChariot::new);
        EntityRenderers.register(NMEntities.FIRE_NOTE.get(), RendererFireNote::new);
        EntityRenderers.register(NMEntities.ANGRY_DEMON.get(), RendererAngryDemon::new);
        EntityRenderers.register(NMEntities.ROCKER_HEAD.get(), RendererRockerHead::new);
        WorldEventHandler.INSTANCE.addClientListener("nethermania_client_events",new NMClientWorldEvents());
    }
}
