package com.barkx4.economy.components;

import com.barkx4.economy.interfaces.VendingComponent;

import net.minecraft.nbt.CompoundTag;

public class BaseVendingComponent implements VendingComponent
{
	private CompoundTag value = new CompoundTag();
	
	@Override
	public void set(CompoundTag vendingData) 
	{
		value = vendingData;
	}

	@Override
	public CompoundTag get() 
	{
		return value;
	}

	@Override public void fromTag(CompoundTag tag) { this.value = tag.getCompound("vendingData"); }
    @Override public CompoundTag toTag(CompoundTag tag) { tag.put("vendingData", this.value); return tag; }
}