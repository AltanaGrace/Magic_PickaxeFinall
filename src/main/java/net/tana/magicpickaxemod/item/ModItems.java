package net.tana.magicpickaxemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tana.magicpickaxemod.MagicPickaxeMod;
import net.tana.magicpickaxemod.item.custom.MagicPickaxe;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicPickaxeMod.MOD_ID);

    public static final RegistryObject<Item> MAGICPICKAXE = ITEMS.register("magicpickaxe",
            () -> new MagicPickaxe(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}