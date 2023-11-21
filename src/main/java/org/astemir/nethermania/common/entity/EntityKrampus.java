package org.astemir.nethermania.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.utils.EntityUtils;

public class EntityKrampus extends AbstractEntityRockerFan{


    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",4.16f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",2.08f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_RUN = new Animation("animation.model.run",0.84f).layer(0).loop().smoothness(1.5f);
    public static Animation ANIMATION_CONCERT1 = new Animation("animation.model.concert1",2.08f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_CONCERT2 = new Animation("animation.model.concert2",0.52f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.52f){
        @Override
        public void onTick(AnimationFactory factory, int tick) {
            if (tick == 10){
                EntityKrampus krampus = factory.getAnimated();
                if (EntityUtils.hasTarget(krampus)) {
                    if (krampus.distanceTo(krampus.getTarget()) < 3) {
                        EntityUtils.damageEntity(krampus, krampus.getTarget(), 1.0f);
                    }
                }
            }
        }
    }.layer(1).priority(1).smoothness(1.25f);
    public static Animation ANIMATION_TEASE = new Animation("animation.model.tease",1.08f).layer(1).priority(1).smoothness(1);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_TEASE,ANIMATION_CONCERT2,ANIMATION_RUN,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_ATTACK,ANIMATION_CONCERT1);

    private ActionStateMachine stateMachine = new ActionStateMachine(idleController);

    protected EntityKrampus(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setPersistenceRequired();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (idleController.is(ACTION_0)){
            animationFactory.play(ANIMATION_CONCERT1);
        }else
        if (idleController.is(ACTION_1)){
            animationFactory.play(ANIMATION_CONCERT2);
        }else {
            if (EntityUtils.isMoving(this, -0.1f, 0.1f)) {
                if (idleController.is(ACTION_RUN)) {
                    animationFactory.play(ANIMATION_RUN);
                }else{
                    animationFactory.play(ANIMATION_WALK);
                }
            } else {
                animationFactory.play(ANIMATION_IDLE);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (!animationFactory.isPlaying(ANIMATION_ATTACK)) {
            animationFactory.play(ANIMATION_ATTACK);
        }
        return false;
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
