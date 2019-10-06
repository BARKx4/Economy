package com.barkx4.economy.items;

import com.barkx4.economy.entity.VendorEntity;
import com.barkx4.economy.gui.VendorGui;
import com.barkx4.economy.gui.VendorPlacementGui;
import com.barkx4.economy.gui.VendorPlacementScreen;
import com.barkx4.economy.gui.VendorScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VendorContractItem extends Item 
{

	public VendorContractItem(Settings settings) 
	{
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) 
	{
		ItemStack itemStack = playerEntity.getStackInHand(hand);
		
		if (!world.isClient)
		{
			System.out.println("(VendorContract) Got from player: UUID = " + playerEntity.getUuidAsString());
			MinecraftClient.getInstance().openScreen(new VendorPlacementScreen(new VendorPlacementGui(playerEntity, world)));
		}
		
	    return new TypedActionResult<ItemStack>(ActionResult.PASS, itemStack);
	}

}
