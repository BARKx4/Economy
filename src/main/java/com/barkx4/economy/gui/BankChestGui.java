package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WTextField;
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
    	getBalance(playerEntity, world, blockPos);
    	
    	CompoundTag nbtData = Main.BANK.get(playerEntity).get();
    	
    	int iCopper = nbtData.getInt("copper");
        int iSilver = nbtData.getInt("silver");
        int iGold = nbtData.getInt("gold");
        
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(140, 140);
        
        WLabel lblTitle = new WLabel("Bank Register");
        root.add(lblTitle, 4, 0, 1, 1);
        
        WSprite icGold = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icGold, 0, 1, 1, 1);
        
        WTextField tfGold = new WTextField();
        tfGold.setText(String.valueOf(iGold));
        root.add(tfGold, 1, 1, 2, 1);
        
        WSprite icSilver = new WSprite(new Identifier("economy:textures/item/silver_coins.png"));
        root.add(icSilver, 4, 1, 1, 1);
        
        WTextField tfSilver = new WTextField();
        tfSilver.setText(String.valueOf(iSilver));
        root.add(tfSilver, 5, 1, 2, 1);
        
        WSprite icCopper = new WSprite(new Identifier("economy:textures/item/copper_coins.png"));
        root.add(icCopper, 8, 1, 1, 1);
        
        WTextField tfCopper = new WTextField();
        tfCopper.setText(String.valueOf(iCopper));
        root.add(tfCopper, 9, 1, 2, 1);
        
        WButton depositButton = new WButton(new LiteralText("Deposit All")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				depositAll(playerEntity, world, blockPos);
			}
		};
        root.add(depositButton, 0, 3, 4, 1);
    }
    
    public void getBalance(PlayerEntity playerEntity, World world, BlockPos blockPos)
    {
    	if (world.isClient) return;
    	
    	CompoundTag nbtData = Main.BANK.get(playerEntity).get();
    	
    	int iCopper = nbtData.getInt("copper");
        int iSilver = nbtData.getInt("silver");
        int iGold = nbtData.getInt("gold");
        
        playerEntity.sendMessage(new LiteralText("Your balance is " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
    	
    	world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.CASH_REGISTER_SOUND_EVENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
    
    public void depositAll(PlayerEntity playerEntity, World world, BlockPos blockPos)
    {
    	if (world.isClient) return;
    	
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
    	
    	world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.COIN_SOUND_EVENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
    
    @Override
    public void addPainters() {
        getRootPanel().setBackgroundPainter(BackgroundPainter.VANILLA); //This is done automatically though
    }
}