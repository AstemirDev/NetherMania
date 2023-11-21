package org.astemir.nethermania.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.math.vector.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.nethermania.client.NMClientWorldEvents;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;
import org.astemir.nethermania.common.particle.NMParticles;

public class EntityRockerHead extends Projectile implements ICustomRendered, IEventEntity {


    private EntityNetherRocker rocker;

    private LivingEntity target;
    private Vec3 pos;
    private boolean returnBack = false;

    protected EntityRockerHead(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setRocker(EntityNetherRocker rocker) {
        this.rocker = rocker;
    }

    @Override
    public void tick() {
        super.tick();
        if (rocker != null) {
            if (!returnBack) {
                if (target != null) {
                    if (pos == null) {
                        pos = target.position().add(0, 0.5f, 0);
                    }
                } else {
                    if (pos == null) {
                        pos = Vector3.from(rocker.getViewVector(0)).mul(20,0,20).add(Vector3.from(position())).toVec3();
                    }
                }
            }else{
                if (rocker != null) {
                    pos = rocker.getEyePosition().add(0,1.75f,0);
                }
            }
            if (pos != null) {
                for (Player entity : EntityUtils.getEntities(Player.class, level, blockPosition(), 1)) {
                    entity.hurt(DamageSource.IN_FIRE,4f);
                }
                if (distanceToSqr(pos) > 0.5f) {
                    Vector3 dir = Vector3.from(position()).direction(Vector3.from(pos));
                    setDeltaMovement(Vector3.from(getDeltaMovement()).lerp(dir, 0.1f).toVec3());
                    EntityUtils.rotate(this,dir);
                }else {
                    if (returnBack) {
                        discard();
                        EntityNetherRocker.IS_HEAD_HIDDEN.set(rocker,false);
                    }else{
                        returnBack = true;
                    }
                }
            }
        }
        if (tickCount > 200) {
            returnBack = true;
        }
        setPos(getX()+getDeltaMovement().x,getY()+getDeltaMovement().y,getZ()+getDeltaMovement().z);
        float f1 = RandomUtils.randomFloat(-0.5f,0.5f);
        float f2 = RandomUtils.randomFloat(-0.25f,0.25f);
        float f3 = RandomUtils.randomFloat(-0.5f,0.5f);
        ParticleEmitter.emit(NMParticles.FIRE_PARTICLE.get(), level, Vector3.from(position().add(0,0.5f,0)),new Vector3(f1,f2,f3),Vector3.from(getDeltaMovement().multiply(-0.5f,-0.5f,-0.5f)),10);
        ParticleEmitter.emit(ParticleTypes.FLAME, level, Vector3.from(position().add(0,0.5f,0)),new Vector3(f1,f2,f3),Vector3.from(getDeltaMovement().multiply(-0.5f,-0.5f,-0.5f)),2);
        NMClientWorldEvents.spawnPostParticle(this, level, blockPosition(),20,false);
    }



    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

}
