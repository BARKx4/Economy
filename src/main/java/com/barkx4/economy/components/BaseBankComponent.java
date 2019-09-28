package com.barkx4.economy.components;

import com.barkx4.economy.interfaces.BankComponent;
import net.minecraft.nbt.CompoundTag;

public class BaseBankComponent implements BankComponent
{
	private CompoundTag value = new CompoundTag();
	
	@Override
	public void set(CompoundTag bankData) 
	{
		value = bankData;
	}

	@Override
	public CompoundTag get() 
	{
		return value;
	}

	@Override public void fromTag(CompoundTag tag) { this.value = tag.getCompound("bankData"); }
    @Override public CompoundTag toTag(CompoundTag tag) { tag.put("bankData", this.value); return tag; }
}