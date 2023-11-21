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

public class EntityPunk extends AbstractEntityRockerFan{


    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",0.96f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",0.96f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_RUN = new Animation("animation.model.run",0.64f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_CONCERT1 = new Animation("animation.model.concert1",2.08f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_CONCERT2 = new Animation("animation.model.concert2",0.52f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.84f){
        @Override
        public void onTick(AnimationFactory factory, int tick) {
            if (tick == 10){
                EntityPunk punk = (EntityPunk) factory.getAnimated();
                if (EntityUtils.hasTarget(punk)) {
                    if (punk.distanceTo(punk.getTarget()) < 3) {
                        EntityUtils.damageEntity(punk, punk.getTarget(), 1.0f);
                    }
                }
            }
        }
    }.layer(1).priority(1).smoothness(2);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_ATTACK,ANIMATION_CONCERT1,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_CONCERT2,ANIMATION_RUN);

    private ActionStateMachine stateMachine = new ActionStateMachine(idleController);


    protected EntityPunk(EntityType<? extends Monster> p_33002_, Level p_33003_) {
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
