package com.barkx4.economy.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups 
{
	public static final ItemGroup ECONOMY_GROUP = FabricItemGroupBuilder.create(
		new Identifier("economy", "economy_group"))
		.icon(() -> new ItemStack(ModItems.GOLD_COINS))
		.appendItems(stacks ->
		{
			stacks.add(new ItemStack(ModBlocks.BANK_CHEST));
			stacks.add(new ItemStack(ModItems.COPPER_COINS));
			stacks.add(new ItemStack(ModItems.SILVER_COINS));
			stacks.add(new ItemStack(ModItems.GOLD_COINS));
		}).build();
	
	public static void init()
	{
		
	}
}
