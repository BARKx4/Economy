package com.barkx4.economy.init;
 
import com.barkx4.economy.blocks.BankChest;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
 
public class ModBlocks
{
    public static final BankChest BANK_CHEST = new BankChest(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).hardness(2).build());
   
    public static void init()
    {
        Registry.register(Registry.BLOCK, new Identifier("economy", "bank_chest"), BANK_CHEST);
       
        Registry.register(
            Registry.ITEM, new Identifier("economy", "bank_chest"),
            new BlockItem(BANK_CHEST, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64))
        );
    }
}