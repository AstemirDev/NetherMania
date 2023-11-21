package org.astemir.nethermania.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.nethermania.common.entity.AbstractEntityRockerFan;
import org.astemir.nethermania.common.entity.NMEntities;


public class ItemFanSpawn extends Item {


    public ItemFanSpawn() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64));
    }


    @Override
    public boolean hurtEnemy(ItemStack p_41395_, LivingEntity p_41396_, LivingEntity p_41397_) {
        if (p_41396_ instanceof AbstractEntityRockerFan rockerFan){
            rockerFan.discard();
        }
        return super.hurtEnemy(p_41395_, p_41396_, p_41397_);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_40510_) {
        Direction direction = p_40510_.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = p_40510_.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(p_40510_);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = p_40510_.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
            AABB aabb = NMEntities.KRAMPUS.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if (level.noCollision((Entity)null, aabb) && level.getEntities((Entity)null, aabb).isEmpty()) {
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)level;
                    AbstractEntityRockerFan fan = AbstractEntityRockerFan.randomFan(level);
                    fan.setPos(vec3.x,vec3.y,vec3.z);
                    if (fan == null) {
                        return InteractionResult.FAIL;
                    }
                    float f = (float) Mth.floor((Mth.wrapDegrees(p_40510_.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    fan.moveTo(fan.getX(), fan.getY(), fan.getZ(), f, 0.0F);
                    fan.setYRot(f);
                    fan.setYBodyRot(f);
                    fan.yBodyRot = f;
                    fan.yRotO = f;
                    fan.yHeadRot = f;
                    fan.setNoAi(true);
                    if (RandomUtils.doWithChance(50)) {
                        fan.idleController.playAction(AbstractEntityRockerFan.ACTION_0);
                    }else{
                        fan.idleController.playAction(AbstractEntityRockerFan.ACTION_1);
                    }
                    serverlevel.addFreshEntityWithPassengers(fan);
                }
                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

}
