package com.barkx4.economy.vending;

import com.barkx4.economy.Main;
import com.barkx4.economy.entity.VendorEntity;

import net.minecraft.nbt.CompoundTag;

public class Vending 
{
	public static void set(VendorEntity vendor, CompoundTag vendorData)
	{
		Main.VENDING.get(vendor).set(vendorData);
	}
	
	public static CompoundTag get(VendorEntity vendor)
	{
		return Main.VENDING.get(vendor).get();
	}
}
