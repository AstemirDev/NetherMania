package org.astemir.nethermania.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.math.vector.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.nethermania.client.NMClientWorldEvents;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

import java.util.Arrays;

public class EntityFireNote extends Projectile implements IAnimatedEntity, ICustomRendered, IEventEntity {

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE);
    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",0.96f).loop();
    public long ticks = 0;

    private int randomTime = 50;

    private AttackType attackType = AttackType.FIRST;

    private EntityNetherRocker rocker;

    private LivingEntity target;
    private Vec3 pos;

    protected EntityFireNote(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
        randomTime = RandomUtils.randomElement(Arrays.asList(50,55,60));
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setRocker(EntityNetherRocker rocker) {
        this.rocker = rocker;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    @Override
    public void tick() {
        super.tick();
        ticks++;
        animationFactory.play(ANIMATION_IDLE);
        if (rocker != null) {
            Vec3 view = rocker.getViewVector(0);
            float rot = MathUtils.atan2((float)view.x,(float)view.z);
            if (ticks < randomTime) {
                pos = null;
                setDeltaMovement(attackType.direction(rocker,tickCount,rot).mul(0.25f).toVec3());
            } else {
                if (target != null) {
                    if (pos == null) {
                        pos = target.position().add(0, 0.5f, 0);
                        playClientEvent(0);
                    }
                }
                if (pos != null) {
                    for (Player entity : EntityUtils.getEntities(Player.class, level, blockPosition(), 1)) {
                        entity.hurt(DamageSource.IN_FIRE,4f);
                    }
                    if (distanceToSqr(pos) > 1f) {
                        Vector3 dir = Vector3.from(position()).direction(Vector3.from(pos));
                        setDeltaMovement(Vector3.from(getDeltaMovement()).lerp(dir, 0.5f).toVec3());
                    } else {
                        discard();
                    }
                }
            }
        }
        if (ticks > 120) {
            discard();
        }
        setPos(getX()+getDeltaMovement().x,getY()+getDeltaMovement().y,getZ()+getDeltaMovement().z);
        NMClientWorldEvents.spawnPostParticle(this, level, blockPosition(),10,true);
    }



    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            ParticleEmitter.emit(ParticleTypes.LAVA,level,Vector3.from(position()),new Vector3(0.25f,0.25f,0.25f),Vector3.from(getDeltaMovement()),4);
        }
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public interface AttackType{

        AttackType FIRST = (rocker, tickCount, rotation) -> {
            return Vector3.from(
                    MathUtils.cos(tickCount / 4f)*MathUtils.sin(rocker.tickCount/4f),
                    MathUtils.sin(tickCount / 4f) * 4f,
                    -MathUtils.sin(tickCount / 4f)*2f).
                    rotateAroundY(rotation);
        };

        AttackType SECOND = (rocker, tickCount, rotation) -> {
            return Vector3.from(
                    MathUtils.cos(tickCount / 4f) * 5,
                    MathUtils.sin(tickCount / 4f) * 4f,
                    -MathUtils.sin(tickCount / 4f)*2f).
                    rotateAroundY(rotation);
        };

        AttackType THIRD = (rocker, tickCount, rotation) -> {
            return Vector3.from(
                    -MathUtils.cos(tickCount/4f)*5f,
                    MathUtils.abs(MathUtils.sin(rocker.tickCount/4f))/2f,
                    MathUtils.sin(tickCount/4f)*2f).
                    rotateAroundY(rotation);
        };

        Vector3 direction(EntityNetherRocker rocker,int tickCount,float rotation);
    }
}
