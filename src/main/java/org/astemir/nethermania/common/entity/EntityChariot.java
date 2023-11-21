package org.astemir.nethermania.common.entity;

import com.lowdragmc.shimmer.client.postprocessing.PostProcessing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.GlobalTaskHandler;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.math.vector.Vector3;
import org.astemir.nethermania.client.NMClientWorldEvents;
import org.astemir.nethermania.common.particle.NMParticles;
import org.checkerframework.checker.units.qual.K;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;


public class EntityChariot extends Monster implements ISkillsMob {


    public static Animation ANIMATION_IDLE = new Animation("animation.model.ride",0.52f).layer(0).loop().smoothness(2);
    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE);

    private ActionStateMachine stateMachine = new ActionStateMachine();
    private Vec3 direction;

    protected EntityChariot(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setPersistenceRequired();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        animationFactory.play(ANIMATION_IDLE);
        if (direction != null){
            setDeltaMovement(direction.multiply(1f,0,1f));
            EntityUtils.rotate(this,Vector3.from(direction));
            if (tickCount < 150) {
                for (int i = 1; i < 4; i++) {
                    BlockPos pos = new BlockPos(position().add(direction.multiply(-i, 0, -i)));
                    level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 19);
                    GlobalTaskHandler.getInstance().runTaskLater(this, () -> {
                        removeFire(pos);
                    }, 20);
                }
            }
        }
        if (tickCount > 100){
            discard();
        }
        for (Player entity : EntityUtils.getEntities(Player.class, level, blockPosition(), 1)) {
            if (!entity.isCreative() && !entity.isSpectator()) {
                entity.hurt(DamageSource.mobAttack(this), 20f);
                Vector3 vec = Vector3.from(getDeltaMovement().multiply(2, 1, 2));
                vec.x += RandomUtils.randomFloat(-0.5f, 0.5f);
                vec.z += RandomUtils.randomFloat(-0.5f, 0.5f);
                vec.y = RandomUtils.randomFloat(0.5f, 1f);
                entity.setDeltaMovement(vec.toVec3());
            }
        }

        Vector3 viewVector = Vector3.from(getViewVector(0)).rotateAroundY(MathUtils.sin(tickCount));
        Vector3 vecPos = Vector3.from(position()).add(viewVector.mul(-0.75f,1,-0.75f));
        Vector3 vecSpeed = new Vector3(-viewVector.x/4,0.2f,-viewVector.z/4);
        PostProcessing.BLOOM_UNREAL.postParticle(NMParticles.FIRE_PARTICLE.get(),vecPos.x,vecPos.y,vecPos.z,vecSpeed.x,vecSpeed.y,vecSpeed.z);
        NMClientWorldEvents.spawnPostParticle(this,level,blockPosition(),30f,false);
    }


    public void removeFire(BlockPos pos){
        if (level.getBlockState(pos).is(Blocks.FIRE)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 19);
        }
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.isFire()){
            return false;
        }
        if (p_21016_.getEntity() != null) {
            return false;
        }
        return super.hurt(p_21016_,p_21017_);
    }

    public void setTargetPos(Vec3 pos) {
        this.direction = pos;
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
