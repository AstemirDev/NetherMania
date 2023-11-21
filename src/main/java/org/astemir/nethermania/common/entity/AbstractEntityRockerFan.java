package org.astemir.nethermania.common.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.IActionListener;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.math.random.RandomUtils;

import java.util.Arrays;

public abstract class AbstractEntityRockerFan extends Monster implements ICustomRendered, IAnimatedEntity, IActionListener {

    public ActionController idleController = new ActionController(this,"idleController",ACTION_0,ACTION_1,ACTION_RUN);

    public static Action ACTION_0 = new Action(0,"action_0",-1);
    public static Action ACTION_1 = new Action(1,"action_1",-1);
    public static Action ACTION_RUN = new Action(2,"run",-1);

    protected AbstractEntityRockerFan(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new MoveControl(this){
            @Override
            public void tick() {
                if (!isStandingStill()) {
                    super.tick();
                }
            }
        };
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new MeleeAttackGoal(this,0.6f,true){
            @Override
            public void start() {
                super.start();
                startRun();
            }

            @Override
            public void stop() {
                super.stop();
                stopRun();
            }
        });
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AbstractEntityRockerFan randomFan(Level level){
        return RandomUtils.randomElement(level.random, Arrays.asList(NMEntities.KRAMPUS,NMEntities.PUNK)).get().create(level);
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.isFire()){
            return false;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    public void startRun(){
        idleController.playAction(ACTION_RUN);
    }

    public void stopRun(){
        if (idleController.is(ACTION_RUN)) {
            idleController.setNoState();
        }
    }

    public boolean isStandingStill(){
        return getCurrentAction() == ACTION_0 || getCurrentAction() == ACTION_1;
    }

    public Action getCurrentAction(){
        return idleController.getActionState();
    }
}
