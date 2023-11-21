package org.astemir.nethermania.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.network.PacketArgument;
import org.astemir.nethermania.client.render.particle.TrailParticle;

public class NMClientWorldEvents implements WorldEventHandler.IClientWorldEventListener {

    public static int EVENT_PARTICLE = 214141713;

    @Override
    public void onHandleEvent(ClientLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        if (event == EVENT_PARTICLE) {
            Entity note = level.getEntity(arguments[0].asInt());
            float power = arguments[1].asFloat();
            boolean bright = arguments[2].asBoolean();
            if (bright) {
                if (note != null) {
                    ShimmerLib.spawnParticle(new TrailParticle(level, note, power, true));
                }
            }else{
                Minecraft.getInstance().particleEngine.add(new TrailParticle(level, note, power, false));
            }
        }
    }

    public static void spawnPostParticle(Entity entity, Level level, BlockPos pos,float power,boolean bright){
        WorldEventHandler.playClientEvent(level,pos,EVENT_PARTICLE,PacketArgument.integer(entity.getId()),new PacketArgument(PacketArgument.ArgumentType.FLOAT,power),PacketArgument.bool(bright));
    }
}
