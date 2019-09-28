package com.barkx4.economy.init;

import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModLootTables 
{
	private static final String PREFIX = "entities/";
    private static final int PREFIX_LENGTH = PREFIX.length();

    public static void init() 
    {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, supplier, setter) -> {
            if(identifier.getPath().startsWith(PREFIX)) 
            {
                Identifier entityTypeName = new Identifier(identifier.getNamespace(), identifier.getPath().substring(PREFIX_LENGTH));
                Registry.ENTITY_TYPE.getOrEmpty(entityTypeName).ifPresent(entityType -> {
                	/*stuff here*/
                });
            }
        });
    }
}
