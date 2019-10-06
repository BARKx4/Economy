package com.barkx4.economy.gui;

import com.barkx4.economy.banking.Bank;
import com.barkx4.economy.entity.VendorEntity;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
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
    public VendorOwnerGui(PlayerEntity playerEntity, VendorEntity vendor, World world) 
    {  	
    	super(RecipeType.CRAFTING, WLabel.DEFAULT_DARKMODE_TEXT_COLOR, playerEntity.inventory);
    	if (world.isClient) return;
    	
    	CompoundTag bankData = Bank.get(playerEntity);
    	
    	WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(140, 140);
        
        WLabel lblTitle = new WLabel("Bank Balance:");
        root.add(lblTitle, 0, 0, 1, 1);
        
        WSprite icGold = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icGold, 0, 1, 1, 1);
        
        WPlayerInvPanel ipPlayer = new WPlayerInvPanel(playerEntity.inventory);
        
        root.add(ipPlayer, 0, 3);
        
        root.validate(this);
    }
}