package org.astemir.nethermania.common.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.register.IRegistry;
import org.astemir.api.common.register.ItemRegistry;
import org.astemir.nethermania.NetherMania;

public class NMItems extends ItemRegistry {

    public static DeferredRegister<Item> ITEMS = IRegistry.create(NetherMania.MOD_ID, ForgeRegistries.ITEMS);
    public static RegistryObject<ItemFanSpawn> FAN_SPAWN = ITEMS.register("fan_spawn",ItemFanSpawn::new);

}
