package com.barkx4.economy.init;

import com.barkx4.economy.banking.Transaction;
import com.barkx4.economy.config.Config;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.loot.ConstantLootTableRange;
import net.minecraft.world.loot.UniformLootTableRange;
import net.minecraft.world.loot.entry.ItemEntry;

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

                	if (entityType != null)
                	{
                		if (Config.mobDropVariables.containsKey(EntityType.getId(entityType)))
                		{
		                	Transaction tMobLoot = Config.mobDropVariables.get(EntityType.getId(entityType));
		                	
		                	FabricLootPoolBuilder poolGold = FabricLootPoolBuilder.builder()
		                            .withRolls(UniformLootTableRange.between(0, tMobLoot.gold))
		                            .withEntry(ItemEntry.builder(ModItems.GOLD_COINS));
		                    
		                	FabricLootPoolBuilder poolSilver = FabricLootPoolBuilder.builder()
		                            .withRolls(UniformLootTableRange.between(0, tMobLoot.silver))
		                            .withEntry(ItemEntry.builder(ModItems.SILVER_COINS));
		
		                	FabricLootPoolBuilder poolCopper = FabricLootPoolBuilder.builder()
		                            .withRolls(UniformLootTableRange.between(0, tMobLoot.copper))
		                            .withEntry(ItemEntry.builder(ModItems.COPPER_COINS));
		                	
		                    supplier.withPool(poolGold).withPool(poolSilver).withPool(poolCopper);
                		}
                	}
                });
            }
        });
    }
}
