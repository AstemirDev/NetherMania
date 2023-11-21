package org.astemir.nethermania.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.entity.EntityProperties;
import org.astemir.api.common.register.EntityRegistry;
import org.astemir.api.common.register.IRegistry;
import org.astemir.nethermania.NetherMania;
import org.astemir.nethermania.common.entity.rocker.EntityNetherRocker;

public class NMEntities extends EntityRegistry {

    public static DeferredRegister<EntityType<?>> ENTITIES = IRegistry.create(NetherMania.MOD_ID, ForgeRegistries.ENTITY_TYPES);
    public static final RegistryObject<EntityType<EntityNetherRocker>> NETHER_ROCKER = register(ENTITIES,NetherMania.MOD_ID,"nether_rocker", new EntityProperties(EntityNetherRocker::new, MobCategory.MONSTER,1.5f,2.75f,()-> LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,300).add(Attributes.KNOCKBACK_RESISTANCE,1).add(Attributes.FOLLOW_RANGE,200).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.ATTACK_DAMAGE,2).add(Attributes.MOVEMENT_SPEED,0.5f).build()));
    public static final RegistryObject<EntityType<EntityKrampus>> KRAMPUS = register(ENTITIES,NetherMania.MOD_ID,"krampus", new EntityProperties(EntityKrampus::new, MobCategory.MONSTER,0.5f,1.5f,()-> LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.FOLLOW_RANGE,32).add(Attributes.ATTACK_DAMAGE,2).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.MOVEMENT_SPEED,0.45f).build()));
    public static final RegistryObject<EntityType<EntityPunk>> PUNK = register(ENTITIES,NetherMania.MOD_ID,"punk", new EntityProperties(EntityPunk::new, MobCategory.MONSTER,0.5f,1.5f,()-> LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.FOLLOW_RANGE,32).add(Attributes.ATTACK_DAMAGE,2).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.MOVEMENT_SPEED,0.5f).build()));
    public static final RegistryObject<EntityType<EntityChariot>> CHARIOT = register(ENTITIES,NetherMania.MOD_ID,"chariot", new EntityProperties(EntityChariot::new,MobCategory.AMBIENT,1,2,()->LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.FOLLOW_RANGE,32).add(Attributes.ATTACK_DAMAGE,2).add(Attributes.MOVEMENT_SPEED,0.5f).build()));
    public static final RegistryObject<EntityType<EntityAngryDemon>> ANGRY_DEMON = register(ENTITIES,NetherMania.MOD_ID,"angry_demon", new EntityProperties(EntityAngryDemon::new,MobCategory.MONSTER,1,2,()->LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,40).add(Attributes.FOLLOW_RANGE,32).add(Attributes.ATTACK_DAMAGE,2).add(Attributes.MOVEMENT_SPEED,0.5f).build()));
    public static final RegistryObject<EntityType<EntityFireNote>> FIRE_NOTE = ENTITIES.register("fire_note", ()->EntityType.Builder.<EntityFireNote>of(EntityFireNote::new, MobCategory.MISC).sized(1,1).clientTrackingRange(20).updateInterval(1).build("fire_note"));
    public static final RegistryObject<EntityType<EntityRockerHead>> ROCKER_HEAD = ENTITIES.register("rocker_head", ()->EntityType.Builder.<EntityRockerHead>of(EntityRockerHead::new, MobCategory.MISC).sized(1,1).clientTrackingRange(20).updateInterval(1).build("rocker_head"));

}
