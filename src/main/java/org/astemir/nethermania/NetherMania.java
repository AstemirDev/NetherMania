package org.astemir.nethermania;


import net.minecraftforge.fml.common.Mod;
import org.astemir.api.IClientLoader;
import org.astemir.api.SkillsForgeMod;
import org.astemir.api.common.register.IRegistry;
import org.astemir.nethermania.client.ClientNetherMania;
import org.astemir.nethermania.common.entity.NMEntities;
import org.astemir.nethermania.common.item.NMItems;
import org.astemir.nethermania.common.particle.NMParticles;

import static org.astemir.nethermania.NetherMania.MOD_ID;

@Mod(MOD_ID)
public class NetherMania extends SkillsForgeMod {

    public static final String MOD_ID = "nethermania";

    public NetherMania() {
        IRegistry.register(NMEntities.ENTITIES);
        IRegistry.register(NMItems.ITEMS);
        IRegistry.register(NMParticles.PARTICLES);
    }

    @Override
    public IClientLoader getClientLoader() {
        return ClientNetherMania.INSTANCE;
    }
}
