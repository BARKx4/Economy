package com.barkx4.economy.gui;

import com.barkx4.economy.banking.Bank;
import com.barkx4.economy.entity.VendorEntity;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class VendorOwnerGui extends CottonScreenController
{
    public VendorOwnerGui(int syncId, PlayerEntity playerEntity, VendorEntity vendor, World world) 
    {  	
    	super(RecipeType.CRAFTING, syncId, playerEntity.inventory);
    	if (world.isClient) return;
    	
    	CompoundTag bankData = Bank.get(playerEntity);
    	
    	WGridPanel root = (WGridPanel) getRootPanel();
		
		WItemSlot inputSlot = WItemSlot.of(playerEntity.inventory, 0);
		root.add(inputSlot, 4, 1);
        
        WPlayerInvPanel ipPlayer = new WPlayerInvPanel(playerEntity.inventory);
        
        root.add(ipPlayer, 0, 3);
        
        root.validate(this);
    }
}