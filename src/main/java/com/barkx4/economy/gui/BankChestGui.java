package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.barkx4.economy.Main;
import com.barkx4.economy.init.ModItems;
import com.barkx4.economy.init.ModSounds;
import com.google.common.primitives.Ints;

public class BankChestGui extends LightweightGuiDescription 
{
    public BankChestGui(PlayerEntity playerEntity, World world, BlockPos blockPos) 
    {
    	getBalance(playerEntity, world, blockPos);
    	
    	CompoundTag nbtData = Main.BANK.get(playerEntity).get();
        
        WButton withdrawButton;
        WButton bankNoteButton;
        
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(140, 140);
        
        WLabel lblTitle = new WLabel("Bank Balance");
        root.add(lblTitle, 4, 0, 1, 1);
        
        WSprite icGold = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icGold, 0, 1, 1, 1);
        
        WLabel lblGold = new WLabel(String.valueOf(nbtData.getInt("gold")));
        root.add(lblGold, 1, 1, 2, 1);
        
        WSprite icSilver = new WSprite(new Identifier("economy:textures/item/silver_coins.png"));
        root.add(icSilver, 4, 1, 1, 1);
        
        WLabel lblSilver = new WLabel(String.valueOf(nbtData.getInt("silver")));
        root.add(lblSilver, 5, 1, 2, 1);
        
        WSprite icCopper = new WSprite(new Identifier("economy:textures/item/copper_coins.png"));
        root.add(icCopper, 8, 1, 1, 1);
        
        WLabel lblCopper = new WLabel(String.valueOf(String.valueOf(nbtData.getInt("copper"))));
        root.add(lblCopper, 9, 1, 2, 1);
        
        WLabel lblWithdraw = new WLabel("Withdrawal Amount");
        root.add(lblWithdraw, 4, 2, 1, 1);
        
        WSprite icGold2 = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icGold2, 0, 3, 1, 1);
        
        WTextField tfGold = new WTextField() {
        	@Override
			public void onFocusLost() 
        	{
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > nbtData.getInt("gold")) iVal = nbtData.getInt("gold");
        			
        			setText(String.valueOf(iVal));
    			}
        	}
        };
        tfGold.setText("0");
        root.add(tfGold, 1, 3, 2, 1);
        
        WSprite icSilver2 = new WSprite(new Identifier("economy:textures/item/silver_coins.png"));
        root.add(icSilver2, 4, 3, 1, 1);
        
        WTextField tfSilver = new WTextField() {
        	@Override
			public void onFocusLost() 
        	{
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > nbtData.getInt("silver")) iVal = nbtData.getInt("silver");
        			
        			setText(String.valueOf(iVal));
    			}
        	}
        };
        tfSilver.setText("0");
        root.add(tfSilver, 5, 3, 2, 1);
        
        WSprite icCopper2 = new WSprite(new Identifier("economy:textures/item/copper_coins.png"));
        root.add(icCopper2, 8, 3, 1, 1);
        
        WTextField tfCopper = new WTextField() {
        	@Override
			public void onFocusLost() 
        	{
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > nbtData.getInt("copper")) iVal = nbtData.getInt("copper");
        			
        			setText(String.valueOf(iVal));
    			}
        	}
        };
        tfCopper.setText("0");
        root.add(tfCopper, 9, 3, 2, 1);
        
        WButton depositButton = new WButton(new LiteralText("Deposit All Inventory Coins")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				depositAll(playerEntity, world, blockPos);
			}
		};
        root.add(depositButton, 0, 5, 11, 1);
        
        WButton cashBankNoteButton = new WButton(new LiteralText("Deposit All Bank Notes")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				
			}
		};
        root.add(cashBankNoteButton, 0, 6, 11, 1);
        
        withdrawButton = new WButton(new LiteralText("Withdraw Coins to Inventory")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				
			}
		};
        root.add(withdrawButton, 0, 7, 11, 1);
        
        bankNoteButton = new WButton(new LiteralText("Create Bank Note for Amount")) {
			@Override
			public void onClick(int x, int y, int button) 
			{	
				// validate the data in the text fields
				int iWithdrawGold = Optional.ofNullable(tfGold.getText().trim())
   					 .map(Ints::tryParse)
   					 .orElse(0);
   			
				if (iWithdrawGold > nbtData.getInt("gold")) iWithdrawGold = nbtData.getInt("gold");
				
				int iWithdrawSilver = Optional.ofNullable(tfSilver.getText().trim())
	   					 .map(Ints::tryParse)
	   					 .orElse(0);
	   			
				if (iWithdrawSilver > nbtData.getInt("silver")) iWithdrawSilver = nbtData.getInt("silver");
				
				int iWithdrawCopper = Optional.ofNullable(tfCopper.getText().trim())
	   					 .map(Ints::tryParse)
	   					 .orElse(0);
	   			
				if (iWithdrawCopper > nbtData.getInt("copper")) iWithdrawCopper = nbtData.getInt("copper");
				
				// Debit the amounts from the player's bank component storage
				
				int iBankGold = nbtData.getInt("gold");
				int iBankSilver = nbtData.getInt("silver");
				int iBankCopper = nbtData.getInt("copper");
				
				iBankGold -= iWithdrawGold;
				iBankSilver -= iWithdrawSilver;
				iBankCopper -= iWithdrawCopper;
				
				nbtData.putInt("gold", iBankGold);
				nbtData.putInt("silver", iBankSilver);
				nbtData.putInt("copper", iBankCopper);
				
				Main.BANK.get(playerEntity).set(nbtData);
				
				// Update GUI balance
				
				tfGold.setText(String.valueOf(nbtData.getInt("gold")));
				tfSilver.setText(String.valueOf(nbtData.getInt("silver")));
				tfCopper.setText(String.valueOf(nbtData.getInt("copper")));
				
				// Create the bank note
				CompoundTag bankNoteData = new CompoundTag();
				bankNoteData.putInt("gold", iWithdrawGold);
				bankNoteData.putInt("silver", iWithdrawSilver);
				bankNoteData.putInt("copper", iWithdrawCopper);
				
				ItemStack is = new ItemStack(ModItems.BANK_NOTE, 1);
				is.setTag(bankNoteData);
				
				List<Text> tooltip = new ArrayList<Text>();
				tooltip.add(new LiteralText("Value: " + String.valueOf(bankNoteData.getInt("gold")) + "g " + String.valueOf(bankNoteData.getInt("silver")) + "s " + String.valueOf(bankNoteData.getInt("copper")) + "c"));
				is.getItem().appendTooltip(is, world, tooltip, TooltipContext.Default.NORMAL);
				
				playerEntity.inventory.insertStack(is);
				
				getBalance(playerEntity, world, blockPos);
			}
		};
        root.add(bankNoteButton, 0, 8, 11, 1);
        
        root.validate(this);
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