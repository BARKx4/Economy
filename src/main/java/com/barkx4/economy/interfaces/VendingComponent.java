package com.barkx4.economy.interfaces;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;

public interface VendingComponent extends Component
{
	void set(CompoundTag vendingData);
	CompoundTag get();
}
