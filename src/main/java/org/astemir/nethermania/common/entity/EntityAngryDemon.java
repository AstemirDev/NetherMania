package org.astemir.nethermania.common.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class EntityAngryDemon extends Monster implements ICustomRendered, IAnimatedEntity {


    public static Animation ANIMATION_ATTACK = new Animation("animation.model.attack",1.56f).layer(0);
    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_ATTACK);

    protected EntityAngryDemon(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setPersistenceRequired();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        animationFactory.play(ANIMATION_ATTACK);
    }

    @Override
    public void onAnimationTick(Animation animation, int tick) {
        if (tick == 10){
            for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), 2,(living)->!(living instanceof EntityNetherRocker))) {
                entity.hurt(DamageSource.mobAttack(this),20f);
            }
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == ANIMATION_ATTACK){
            discard();
        }
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.getEntity() != null) {
            return false;
        }
        return super.hurt(p_21016_,p_21017_);
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
