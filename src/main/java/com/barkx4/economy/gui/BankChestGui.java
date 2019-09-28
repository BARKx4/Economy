package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.barkx4.economy.Main;
import com.barkx4.economy.init.ModItems;
import com.barkx4.economy.init.ModSounds;

public class BankChestGui extends LightweightGuiDescription 
{
    public BankChestGui(PlayerEntity playerEntity, World world, BlockPos blockPos) 
    {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(140, 140);
        
        WSprite icon = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icon, 3, 0, 1, 1);
        
        WButton depositButton = new WButton(new LiteralText("Deposit All")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				depositAll(playerEntity, world, blockPos);
			}
		};
        root.add(depositButton, 0, 3, 4, 1);
        
        WButton balanceButton = new WButton(new LiteralText("Check Balance")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				getBalance(playerEntity, world, blockPos);
			}
		};
        root.add(balanceButton, 0, 5, 4, 1);
        
        WLabel label = new WLabel(new LiteralText("Bank Chest"), 0xFFFFFF);
        root.add(label, 0, 0, 2, 1);
    }
    
    public void getBalance(PlayerEntity playerEntity, World world, BlockPos blockPos)
    {
    	CompoundTag nbtData = Main.BANK.get(playerEntity).get();
    	
    	int iCopper = nbtData.getInt("copper");
        int iSilver = nbtData.getInt("silver");
        int iGold = nbtData.getInt("gold");
        
        playerEntity.sendMessage(new LiteralText("Your balance is " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
    	
    	world.playSound(playerEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.CASH_REGISTER_SOUND_EVENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
    
    public void depositAll(PlayerEntity playerEntity, World world, BlockPos blockPos)
    {
    	//if (!world.isClient) return;
    	
    	CompoundTag nbtData = Main.BANK.get(playerEntity).get();
    	
    	int iCopper = 0;
    	int iSilver = 0;
    	int iGold = 0;
    	
        for(int i = 0; i < playerEntity.inventory.getInvSize(); ++i) 
        {
           ItemStack itemStack = playerEntity.inventory.getInvStack(i);
           
           if (itemStack.getItem().equals(ModItems.COPPER_COINS)) 
           {
        	   iCopper += itemStack.getCount(); 
        	   itemStack.setCount(0);
           }
           else if (itemStack.getItem().equals(ModItems.SILVER_COINS)) 
           {
        	   iSilver += itemStack.getCount(); 
        	   itemStack.setCount(0);
           }
           else if (itemStack.getItem().equals(ModItems.GOLD_COINS)) 
           {
        	   iGold += itemStack.getCount(); 
        	   itemStack.setCount(0);
           }
        }
        
        iSilver += Math.floorDiv(iCopper, 64);
        iCopper = Math.floorMod(iCopper, 64);
        iGold += Math.floorDiv(iSilver, 64);
        iSilver = Math.floorMod(iSilver, 64);
        
        playerEntity.sendMessage(new LiteralText("Deposited " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
        
        iCopper += nbtData.getInt("copper");
        iSilver += nbtData.getInt("silver");
        iGold += nbtData.getInt("gold");
        
        iSilver += Math.floorDiv(iCopper, 64);
        iCopper = Math.floorMod(iCopper, 64);
        iGold += Math.floorDiv(iSilver, 64);
        iSilver = Math.floorMod(iSilver, 64);
        
        nbtData.putInt("copper", iCopper);
        nbtData.putInt("silver", iSilver);
        nbtData.putInt("gold", iGold);
        
        Main.BANK.get(playerEntity).set(nbtData);
        
        playerEntity.sendMessage(new LiteralText("Your new balance is " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
    	
    	world.playSound(playerEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.COIN_SOUND_EVENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
    
    @Override
    public void addPainters() {
        getRootPanel().setBackgroundPainter(BackgroundPainter.VANILLA); //This is done automatically though
    }
}