package com.barkx4.economy;

import java.io.IOException;

import com.barkx4.economy.components.BaseBankComponent;
import com.barkx4.economy.components.BaseVendingComponent;
import com.barkx4.economy.config.Config;
import com.barkx4.economy.entity.VendorEntity;
import com.barkx4.economy.gui.VendorOwnerGui;
import com.barkx4.economy.init.ModBlocks;
import com.barkx4.economy.init.ModEntities;
import com.barkx4.economy.init.ModItemGroups;
import com.barkx4.economy.init.ModItems;
import com.barkx4.economy.init.ModLootTables;
import com.barkx4.economy.init.ModSounds;
import com.barkx4.economy.interfaces.BankComponent;
import com.barkx4.economy.interfaces.VendingComponent;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer 
{
	public static final String MOD_ID = "economy";
	
	public static final ComponentType<BankComponent> BANK = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("economy:bank_component"), BankComponent.class);
	
	public static final ComponentType<VendingComponent> VENDING = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("economy:vending_component"), VendingComponent.class);
	
	@Override
    public void onInitialize()
    {       
    	ModItems.init();
    	ModBlocks.init();
    	ModLootTables.init();
    	ModItemGroups.init();
    	ModSounds.init();
    	ModEntities.init();
    	
    	try 
    	{
			Config.init();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Add vending component to our vendor entities
    	EntityComponentCallback.event(VendorEntity.class).register((entity, components) -> components.put(VENDING, new BaseVendingComponent()));    	
    	// Add the component to every instance of PlayerEntity()
    	EntityComponentCallback.event(PlayerEntity.class).register((player, components) -> components.put(BANK, new BaseBankComponent()));
    	// Ensure the component's data is copied when the player respawns
    	EntityComponents.setRespawnCopyStrategy(BANK, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
