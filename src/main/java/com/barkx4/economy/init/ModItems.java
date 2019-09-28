package com.barkx4.economy.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems 
{
    public static final Item GOLD_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    public static final Item SILVER_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    public static final Item COPPER_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    
    public static void init()
    {
	    Registry.register(Registry.ITEM, new Identifier("economy", "gold_coins"), GOLD_COINS);
	    Registry.register(Registry.ITEM, new Identifier("economy", "silver_coins"), SILVER_COINS);
	    Registry.register(Registry.ITEM, new Identifier("economy", "copper_coins"), COPPER_COINS);
    }
}
