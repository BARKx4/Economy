package com.barkx4.economy.init;

import com.barkx4.economy.items.BankNoteItem;
import com.barkx4.economy.items.VendorContractItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems 
{
    public static final Item GOLD_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    public static final Item SILVER_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    public static final Item COPPER_COINS = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64));
    public static final BankNoteItem BANK_NOTE = new BankNoteItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
    public static final VendorContractItem VENDOR_CONTRACT = new VendorContractItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
    
    public static void init()
    {
	    Registry.register(Registry.ITEM, new Identifier("economy", "gold_coins"), GOLD_COINS);
	    Registry.register(Registry.ITEM, new Identifier("economy", "silver_coins"), SILVER_COINS);
	    Registry.register(Registry.ITEM, new Identifier("economy", "copper_coins"), COPPER_COINS);
	    Registry.register(Registry.ITEM, new Identifier("economy", "bank_note"), BANK_NOTE);
	    Registry.register(Registry.ITEM, new Identifier("economy", "vendor_contract"), VENDOR_CONTRACT);
    }
}
