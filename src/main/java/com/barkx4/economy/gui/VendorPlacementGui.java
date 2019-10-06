package com.barkx4.economy.gui;

import com.barkx4.economy.banking.Bank;
import com.barkx4.economy.entity.VendorEntity;
import com.barkx4.economy.init.ModEntities;
import com.barkx4.economy.vending.Vending;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public class VendorPlacementGui extends LightweightGuiDescription 
{
    public VendorPlacementGui(PlayerEntity playerEntity, World world) 
    {  	
    	if (world.isClient) return;

    	WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(1, 1);
        
        WLabel lblTitle = new WLabel("Name your new vendor!");
        root.add(lblTitle, 0, 0, 1, 1);
        
        WTextField tfVendorName = new WTextField();
        root.add(tfVendorName, 0, 1, 8, 1);
        
        WButton btnOk = new WButton(new LiteralText("OK")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				if (world.isClient) return;
				
				VendorEntity vendor = new VendorEntity(ModEntities.VENDOR, world);
				vendor.copyPositionAndRotation(playerEntity);
				vendor.setCustomName(new LiteralText(tfVendorName.getText()));
				world.spawnEntity(vendor);
				
				CompoundTag vendorData = Vending.get(vendor);
				vendorData.putUuid("owner", playerEntity.getUuid());
				
				Vending.set(vendor, vendorData);
				System.out.println("(VendorPlacementGui) Adding to vending component: owner = " + playerEntity.getUuidAsString());
				
				playerEntity.inventory.getMainHandStack().setCount(0);
				
				MinecraftClient.getInstance().currentScreen.onClose();
			}
        };
        root.add(btnOk, 0, 2, 4, 1);
        
        WButton btnCancel = new WButton(new LiteralText("Cancel")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				if (world.isClient) return;
				MinecraftClient.getInstance().currentScreen.onClose();
			}
        };
        
        root.add(btnCancel, 4, 2, 4, 1);
        
        root.validate(this);
    }
}