package org.astemir.nethermania.common.entity.rocker;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.ai.AITaskSystem;
import org.astemir.api.common.entity.ai.ICustomAIEntity;
import org.astemir.api.common.entity.ai.tasks.AITask;
import org.astemir.api.common.entity.ai.tasks.AITaskTimer;
import org.astemir.api.common.entity.ai.triggers.TaskMalus;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.gfx.GFXBlackOut;
import org.astemir.api.common.gfx.PlayerGFXEffectManager;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.lib.shimmer.DynamicEntityLight;
import org.astemir.api.lib.shimmer.IDynamicLightEntity;
import org.astemir.api.math.Color;
import org.astemir.api.math.FDG;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.math.vector.Vector2;
import org.astemir.api.math.vector.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.nethermania.common.entity.*;
import org.astemir.nethermania.common.particle.NMParticles;

import java.util.Arrays;


public class EntityNetherRocker extends Monster implements ISkillsMob, ICustomAIEntity, IDynamicLightEntity {


    public static EntityData<Boolean> FIRE_SHIELD = new EntityData<>(EntityNetherRocker.class,"FireShield", EntityDataSerializers.BOOLEAN,false);
    public static EntityData<Boolean> IS_HEAD_HIDDEN = new EntityData<>(EntityNetherRocker.class,"IsHeadHidden", EntityDataSerializers.BOOLEAN,false);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_TRANSITION,ANIMATION_PLAYING6,ANIMATION_FAN,ANIMATION_CALL,ANIMATION_PLAYING4,ANIMATION_PLAYING7,ANIMATION_STATIC,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_PLAYING1,ANIMATION_PLAYING3,ANIMATION_PLAYING5,ANIMATION_ATTACK1,ANIMATION_HEAD,ANIMATION_LAUGHTER,ANIMATION_TONGUE,ANIMATION_PLAYING2);
    public static Animation ANIMATION_TRANSITION = new Animation("animation.model.transition",0.52f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME).smoothness(1);
    public static Animation ANIMATION_FAN = new Animation("animation.model.fan",0.48f).loop().smoothness(1);
    public static Animation ANIMATION_CALL = new Animation("animation.model.call",2.76f).loop(Animation.Loop.HOLD_ON_LAST_FRAME).smoothness(1);
    public static Animation ANIMATION_PLAYING1 = new Animation("animation.model.playing1",1.6f).layer(0).loop().smoothness(2f);
    public static Animation ANIMATION_PLAYING2 = new Animation("animation.model.playing2",0.64f).layer(0).loop().smoothness(1.5f);
    public static Animation ANIMATION_PLAYING3 = new Animation("animation.model.playing3",1.76f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME).smoothness(1.5f);
    public static Animation ANIMATION_PLAYING4 = new Animation("animation.model.playing4",2.48f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_PLAYING5 = new Animation("animation.model.playing5",4.56f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_PLAYING6 = new Animation("animation.model.playing6",6.4f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_PLAYING7 = new Animation("animation.model.playing7",0.64f).layer(0).loop().smoothness(2);
    public static Animation ANIMATION_STATIC = new Animation("animation.model.static",0.72f).loop().smoothness(1);
    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",2.08f).layer(0).loop().smoothness(1);
    public static Animation ANIMATION_ATTACK1 = new Animation("animation.model.attack1",1.12f).smoothness(1);
    public static Animation ANIMATION_HEAD = new Animation("animation.model.head",1.8f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME).smoothness(1);
    public static Animation ANIMATION_LAUGHTER = new Animation("animation.model.laughter",0.72f).loop().smoothness(1);
    public static Animation ANIMATION_TONGUE = new Animation("animation.model.tongue",5.64f).loop(Animation.Loop.HOLD_ON_LAST_FRAME).smoothness(1);

    public ActionController actionController = new ActionController(this,"actionController",ACTION_START,ACTION_FIRE_ATTACK,ACTION_HEAD_ATTACK);
    public static Action ACTION_START = new Action(0,"start",0.52f);
    public static Action ACTION_FIRE_ATTACK = new Action(1,"fireAttack",1.76f);
    public static Action ACTION_HEAD_ATTACK = new Action(2,"headAttack",1.8f);


    public ActionController stageController = new ActionController(this,"stageController",ACTION_SLOW_PLAY,ACTION_FAST_PLAY,ACTION_AGGRESSIVE_PLAY);
    public static Action ACTION_SLOW_PLAY = new Action(0,"slowPlay",-1);
    public static Action ACTION_FAST_PLAY = new Action(1,"fastPlay",-1);
    public static Action ACTION_AGGRESSIVE_PLAY = new Action(2,"aggressivePlay",-1);

    private ActionStateMachine stateMachine = new ActionStateMachine(actionController,stageController);

    private AITaskSystem aiTaskSystem;

    private int cooldown = 0;

    @OnlyIn(Dist.CLIENT)
    private DynamicEntityLight entityLight;


    public EntityNetherRocker(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new MoveControl(this){
            @Override
            public void tick() {
            }
        };
    }

    @Override
    public void onClientRemoval() {
        entityLight.remove();
    }

    @Override
    public DynamicEntityLight getOrCreateLight() {
        if (entityLight == null){
            Color color = Color.YELLOW;
            color.a = 1f;
            entityLight = new DynamicEntityLight(color,4).create();
        }
        return entityLight;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        FIRE_SHIELD.register(this);
        IS_HEAD_HIDDEN.register(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        FIRE_SHIELD.save(this,p_21484_);
        IS_HEAD_HIDDEN.save(this,p_21484_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        FIRE_SHIELD.load(this,p_21450_);
        IS_HEAD_HIDDEN.load(this,p_21450_);
    }

    @Override
    protected void registerGoals() {
        targetSelector.addGoal(1,new NearestAttackableTargetGoal(this, Player.class,false));
        targetSelector.addGoal(2,new NearestAttackableTargetGoal(this, ArmorStand.class,false));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (cooldown > 0){
            cooldown--;
        }
        if (getTarget() != null){
            EntityUtils.rotate(this,Vector3.from(position()).direction(Vector3.from(getTarget().position())));
        }
        if (actionController.isNoAction()) {
            if (stageController.is(ACTION_SLOW_PLAY)) {
                animationFactory.play(ANIMATION_PLAYING1);
            }else if (stageController.is(ACTION_FAST_PLAY)) {
                animationFactory.play(ANIMATION_PLAYING2);
            }else if (stageController.is(ACTION_AGGRESSIVE_PLAY)) {
                animationFactory.play(ANIMATION_PLAYING7);
            }
            else {
                animationFactory.play(ANIMATION_IDLE);
            }
        }
        if (stageController.is(ACTION_SLOW_PLAY) && RandomUtils.doWithChance(random,1)){
            stageController.playAction(ACTION_FAST_PLAY);
            FIRE_SHIELD.set(this,true);
        }
        if (FIRE_SHIELD.get(this)) {
            for (Player entity : EntityUtils.getEntities(Player.class, level, blockPosition(), 2)) {
                if (!entity.isCreative() && !entity.isSpectator()) {
                    entity.hurt(DamageSource.IN_FIRE, 4f);
                    entity.setSecondsOnFire(4);
                    Vector3 vec = Vector3.from(entity.getViewVector(0)).mul(-1, 0, -1);
                    vec.setY(0.25f);
                    entity.setDeltaMovement(vec.toVec3());
                }
            }
            float pi = (float) (Math.PI * 2);
            if (tickCount % 2 == 0) {
                for (double d = 0; d < pi; d += pi / 8) {
                    float y = RandomUtils.randomFloat(0.1f, 0.25f);
                    float speed = 4;
                    if (stageController.is(ACTION_AGGRESSIVE_PLAY)) {
                        y = RandomUtils.randomFloat(0.5f, 0.85f);
                        speed = 2;
                    }
                    float radius = (MathUtils.clamp(MathUtils.abs(MathUtils.sin(tickCount)), 0.5f, 1) * 3);
                    Vector3 rot = new Vector3((float) Math.cos(d) * radius, MathUtils.abs(MathUtils.sin(tickCount) / speed) * 2 + 0.75f, (float) Math.sin(d) * radius).rotateAroundY(MathUtils.sin(tickCount));
                    ParticleEmitter.emit(RandomUtils.randomElement(random, Arrays.asList(ParticleTypes.LARGE_SMOKE, ParticleTypes.LARGE_SMOKE, ParticleTypes.FLAME, ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.LAVA)), level, Vector3.from(position()).add(rot), new Vector3(0, 0, 0), new Vector3(RandomUtils.randomFloat(random, -0.1f, 0.1f), y, RandomUtils.randomFloat(random, -0.1f, 0.1f)), 1);
                }
            }
        }
    }

    @Override
    public void setupAI() {
        aiTaskSystem = new AITaskSystem(this);
        aiTaskSystem.registerTask(new AITaskBattle(0).layer(0));
        aiTaskSystem.registerTask(new AITaskFireAttack(1,200).layer(1).malus(TaskMalus.ATTACK));
        aiTaskSystem.registerTask(new AITaskNoteAttack(2,300).layer(1).malus(TaskMalus.ATTACK));
        aiTaskSystem.registerTask(new AITaskHeadAttack(3,400).layer(1).malus(TaskMalus.ATTACK));
        aiTaskSystem.registerTask(new AITaskSpawnMonsters(4,100).layer(2).malus(TaskMalus.ATTACK));
    }


    @Override
    public void onAnimationTick(Animation animation, int tick) {
        if (animation == ANIMATION_PLAYING3 && tick == 10){
            cooldown = 40;
            playClientEvent(0);
            for (Player entity : EntityUtils.getEntities(Player.class, level, blockPosition(), 5)) {
                if (!entity.isCreative() && !entity.isSpectator()) {
                    entity.hurt(DamageSource.IN_FIRE, 20f);
                    entity.setSecondsOnFire(4);
                    Vector3 vec = Vector3.from(entity.getViewVector(0)).mul(-2, 0, -2);
                    vec.setY(1);
                    entity.setDeltaMovement(vec.toVec3());
                }
            }
            for (ServerPlayer serverPlayer : EntityUtils.getEntities(ServerPlayer.class, level, blockPosition(), 10)) {
                PlayerGFXEffectManager.addGFXEffect(serverPlayer,new GFXBlackOut(Color.ORANGE,0.25f),false);
            }
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == ANIMATION_PLAYING3){
            animationFactory.play(ANIMATION_PLAYING1);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onActionBegin(Action state) {
        if (state == ACTION_FIRE_ATTACK){
            animationFactory.play(ANIMATION_PLAYING3);
        }else
        if (state == ACTION_HEAD_ATTACK){
            animationFactory.play(ANIMATION_HEAD);
        }
    }

    @Override
    public void onActionEnd(Action state) {
        if (state == ACTION_START){
            stageController.playAction(ACTION_SLOW_PLAY);
        }else
        if (state == ACTION_FIRE_ATTACK) {
            animationFactory.stop(ANIMATION_PLAYING3);
        }else
        if (state == ACTION_HEAD_ATTACK){
            animationFactory.stop(ANIMATION_HEAD);
        }
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            new FDG(){
                @Override
                public void onExecute(Vector3 point) {
                    ParticleEmitter.emit(NMParticles.FIRE_PARTICLE.get(),level,Vector3.from(position().add(0,3,0).add(point.toVec3())),Vector3.zero(),point.mul(0.65f),1);
                }
            }.sphere(1f,0.1f,new Vector3(0,0,0));
        }
    }

    @Override
    public void onActionTick(Action state, int ticks) {
        if (state == ACTION_HEAD_ATTACK){
            if (ticks == 11) {
                EntityRockerHead head = NMEntities.ROCKER_HEAD.get().create(level);
                head.setRocker(EntityNetherRocker.this);
                head.setPos(position().add(getViewVector(0).x*2, 1.5f, getViewVector(0).z*2));
                head.setTarget(getTarget());
                level.addFreshEntity(head);
            }
            if (ticks < 5){
                EntityNetherRocker.IS_HEAD_HIDDEN.set(this,true);
            }
        }
    }


    public class AITaskBattle extends AITask{


        public AITaskBattle(int id) {
            super(id);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && getTarget() != null && !getTarget().isRemoved();
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && getTarget() != null && !getTarget().isRemoved();
        }

        @Override
        public void onStart() {
            actionController.playAction(ACTION_START);
        }

        @Override
        public void onStop() {
            stageController.setNoState();
            actionController.setNoState();
        }
    }

    public class AITaskFireAttack extends AITaskTimer{


        public AITaskFireAttack(int id, int delay) {
            super(id, delay);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && getAISystem().getRunningTask(AITaskBattle.class) != null && random.nextInt(10) == 0 && !stageController.is(ACTION_AGGRESSIVE_PLAY) && actionController.isNoAction() && cooldown ==0;
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && getAISystem().getRunningTask(AITaskBattle.class) != null  && !stageController.is(ACTION_AGGRESSIVE_PLAY);
        }

        @Override
        public void onRun() {
            cooldown = 40;
            actionController.playAction(ACTION_FIRE_ATTACK);
            stop();
        }
    }

    public class AITaskNoteAttack extends AITaskTimer{

        private int notesCount = 0;
        private EntityFireNote.AttackType attackType = EntityFireNote.AttackType.FIRST;

        public AITaskNoteAttack(int id, int delay) {
            super(id, delay);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && getAISystem().getRunningTask(AITaskBattle.class) != null && random.nextInt(10) == 0 && cooldown ==0;
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && getAISystem().getRunningTask(AITaskBattle.class) != null;
        }

        @Override
        public void onRun() {
            cooldown = 40;
            stageController.playAction(ACTION_AGGRESSIVE_PLAY);
            attackType = RandomUtils.randomElement(Arrays.asList(EntityFireNote.AttackType.FIRST,EntityFireNote.AttackType.SECOND,EntityFireNote.AttackType.THIRD));
        }

        @Override
        public void onStop() {
            super.onStop();
            notesCount = 0;
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            if (animationFactory.isPlaying(ANIMATION_PLAYING7)){
                if (getTicks() % 5 == 0){
                    EntityFireNote fireNote = NMEntities.FIRE_NOTE.get().create(level);
                    fireNote.setRocker(EntityNetherRocker.this);
                    fireNote.setAttackType(attackType);
                    fireNote.setPos(position().add(getViewVector(0).x,1.5f,getViewVector(0).z));
                    fireNote.setTarget(getTarget());
                    level.addFreshEntity(fireNote);
                    notesCount++;
                }
            }
            if (notesCount > 10 && random.nextInt(50) == 0){
                stageController.playAction(ACTION_FAST_PLAY);
                stop();
            }
        }
    }


    public class AITaskHeadAttack extends AITaskTimer{


        public AITaskHeadAttack(int id, int delay) {
            super(id, delay);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && getAISystem().getRunningTask(AITaskBattle.class) != null && random.nextInt(50) == 0 && cooldown ==0;
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && getAISystem().getRunningTask(AITaskBattle.class) != null;
        }

        @Override
        public void onRun() {
            cooldown = 40;
            actionController.playAction(ACTION_HEAD_ATTACK);
        }
    }

    public class AITaskSpawnMonsters extends AITaskTimer{

        public AITaskSpawnMonsters(int id, int delay) {
            super(id, delay);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && getAISystem().getRunningTask(AITaskBattle.class) != null && random.nextInt(10) == 0 && cooldown ==0;
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && getAISystem().getRunningTask(AITaskBattle.class) != null;
        }

        @Override
        public void onRun() {
            cooldown = 40;
            Vector3 randomPos = new Vector3(getX(),getY(),getZ()).add(Vector3.from(getViewVector(0)).mul(4,0,4));
            randomPos = new Vector3(randomPos.x + RandomUtils.randomInt(4, 8)*RandomUtils.randomElement(Arrays.asList(-1,1)), randomPos.y, randomPos.z + RandomUtils.randomInt(4, 8)*RandomUtils.randomElement(Arrays.asList(-1,1)));
            if (RandomUtils.doWithChance(random,75f)) {
                int size = EntityUtils.getEntities(AbstractEntityRockerFan.class, level, blockPosition(), 20,(fan)->!fan.isStandingStill()).size();
                if (size < 3) {
                    AbstractEntityRockerFan fan = AbstractEntityRockerFan.randomFan(level);
                    Vector3 direction = randomPos.direction(Vector3.from(getTarget().position()));
                    Vector2 rot = direction.mul(1,0,1).yawPitchDeg();
                    EntityUtils.setPositionRotation(fan,randomPos,-rot.x,0);
                    EntityUtils.rotate(fan,direction);
                    fan.setTarget(getTarget());
                    level.addFreshEntity(fan);
                }
            }else{
                if (RandomUtils.doWithChance(random,50)) {
                    EntityAngryDemon demon = NMEntities.ANGRY_DEMON.get().create(level);
                    demon.setPos(randomPos.toVec3());
                    level.addFreshEntity(demon);
                }else{
                    EntityChariot chariot = NMEntities.CHARIOT.get().create(level);
                    int lineLength = RandomUtils.randomElement(random, new Integer[]{-1,1})*RandomUtils.randomInt(20,40);
                    Vec3 view = getViewVector(0);
                    float myRot = MathUtils.atan2((float)view.x,(float)view.z);
                    Vector3 line = new Vector3(lineLength,0,-view.z*2).rotateAroundY(MathUtils.rad(myRot));
                    Vector3 pos = Vector3.from(position()).add(line);
                    pos.setY((float) getTarget().position().y);
                    Vector3 direction = pos.direction(Vector3.from(getTarget().position()));
                    chariot.setTargetPos(direction.toVec3());
                    chariot.setPos(pos.toVec3());
                    EntityUtils.rotate(chariot,direction);
                    level.addFreshEntity(chariot);
                }
            }
        }

    }


    @Override
    public boolean isPushable() {
        return false;
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
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

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    public float getVoicePitch() {
        return 0.75f;
    }

    @Override
    public AITaskSystem getAISystem() {
        return aiTaskSystem;
    }

}
