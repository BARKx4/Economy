package com.barkx4.economy.items;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class BankNoteItem extends Item
{

	public BankNoteItem(Settings settings) 
	{
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) 
	{
		CompoundTag bankNoteData = itemStack.getTag();
		
		if (bankNoteData != null)
		{
			tooltip.add(new LiteralText("Value: §l§e" + String.valueOf(bankNoteData.getInt("gold")) + "g §f" 
				+ String.valueOf(bankNoteData.getInt("silver")) + "s §6" 
				+ String.valueOf(bankNoteData.getInt("copper")) + "c")
			);
		}
	}
}
