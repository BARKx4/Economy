package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

import com.barkx4.economy.banking.Bank;
import com.barkx4.economy.banking.Transaction;
import com.barkx4.economy.enums.TransactionType;
import com.barkx4.economy.init.ModSounds;
import com.google.common.primitives.Ints;

public class BankChestGui extends LightweightGuiDescription 
{
    public BankChestGui(PlayerEntity playerEntity, World world, BlockPos blockPos) 
    {  	
    	if (world.isClient) return;
    	
    	CompoundTag nbtData = Bank.get(playerEntity);
    	Bank.getBalance(playerEntity, nbtData);
    	Bank.playSound(world, blockPos, ModSounds.CASH_REGISTER_SOUND_EVENT);
        
        WButton withdrawButton;
        WButton bankNoteButton;
        
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(140, 140);
        
        WLabel lblTitle = new WLabel("Bank Balance:");
        root.add(lblTitle, 0, 0, 1, 1);
        
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
        
        WLabel lblWithdraw = new WLabel("Withdrawal Amount:");
        root.add(lblWithdraw, 0, 2, 1, 1);
        
        WSprite icGold2 = new WSprite(new Identifier("economy:textures/item/gold_coins.png"));
        root.add(icGold2, 0, 3, 1, 1);
        
        WTextField tfGold = new WTextField() {
        	@Override
			public void onFocusLost() 
        	{
        		CompoundTag bankData = Bank.get(playerEntity);
        		
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > bankData.getInt("gold")) iVal = bankData.getInt("gold");
        			
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
        		CompoundTag bankData = Bank.get(playerEntity);
        		
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > bankData.getInt("silver")) iVal = bankData.getInt("silver");
        			
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
        		CompoundTag bankData = Bank.get(playerEntity);
        		
    			if (getText() != null)
    			{
        			int iVal = Optional.ofNullable(getText().trim())
        					 .map(Ints::tryParse)
        					 .orElse(0);
        			
        			if (iVal > bankData.getInt("copper")) iVal = bankData.getInt("copper");
        			
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
				if (world.isClient) return;
				
				CompoundTag bankData = Bank.get(playerEntity);
				
				bankData = Bank.deposit(playerEntity, TransactionType.COIN, bankData);
				lblGold.setText(new LiteralText(String.valueOf(bankData.getInt("gold"))));
				lblSilver.setText(new LiteralText(String.valueOf(bankData.getInt("silver"))));
				lblCopper.setText(new LiteralText(String.valueOf(bankData.getInt("copper"))));
				
				Bank.set(playerEntity, bankData);
				Bank.playSound(world, blockPos, ModSounds.COIN_SOUND_EVENT);
				Bank.getBalance(playerEntity, bankData);
			}
		};
        root.add(depositButton, 0, 5, 11, 1);
        
        WButton cashBankNoteButton = new WButton(new LiteralText("Deposit All Bank Notes")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				if (world.isClient) return;
				
				CompoundTag bankData = Bank.get(playerEntity);
				
				bankData = Bank.deposit(playerEntity, TransactionType.BANKNOTE, bankData);
				lblGold.setText(new LiteralText(String.valueOf(bankData.getInt("gold"))));
				lblSilver.setText(new LiteralText(String.valueOf(bankData.getInt("silver"))));
				lblCopper.setText(new LiteralText(String.valueOf(bankData.getInt("copper"))));
				
				Bank.set(playerEntity, bankData);
				Bank.playSound(world, blockPos, ModSounds.COIN_SOUND_EVENT);
				Bank.getBalance(playerEntity, bankData);
			}
		};
        root.add(cashBankNoteButton, 0, 6, 11, 1);
        
        withdrawButton = new WButton(new LiteralText("Withdraw Coins to Inventory")) {
			@Override
			public void onClick(int x, int y, int button) 
			{
				if (world.isClient) return;
				
				CompoundTag bankData = Bank.get(playerEntity);
				
				// validate the data in the text fields
				int iWithdrawGold = Optional.ofNullable(tfGold.getText().trim())
						 .map(Ints::tryParse)
						 .orElse(0);
				
				if (iWithdrawGold > bankData.getInt("gold")) iWithdrawGold = bankData.getInt("gold");
				
				int iWithdrawSilver = Optional.ofNullable(tfSilver.getText().trim())
							 .map(Ints::tryParse)
							 .orElse(0);
					
				if (iWithdrawSilver > bankData.getInt("silver")) iWithdrawSilver = bankData.getInt("silver");
				
				int iWithdrawCopper = Optional.ofNullable(tfCopper.getText().trim())
							 .map(Ints::tryParse)
							 .orElse(0);
					
				if (iWithdrawCopper > bankData.getInt("copper")) iWithdrawCopper = bankData.getInt("copper");
				
				Transaction transaction = new Transaction(iWithdrawGold, iWithdrawSilver, iWithdrawCopper);
				bankData = Bank.withdraw(playerEntity, TransactionType.COIN, transaction, bankData);
				lblGold.setText(new LiteralText(String.valueOf(bankData.getInt("gold"))));
				lblSilver.setText(new LiteralText(String.valueOf(bankData.getInt("silver"))));
				lblCopper.setText(new LiteralText(String.valueOf(bankData.getInt("copper"))));
				
				Bank.set(playerEntity, bankData);
				Bank.playSound(world, blockPos, ModSounds.CASH_REGISTER_SOUND_EVENT);
				Bank.getBalance(playerEntity, bankData);
			}
		};
        root.add(withdrawButton, 0, 7, 11, 1);
        
        bankNoteButton = new WButton(new LiteralText("Create Bank Note for Amount")) {
			@Override
			public void onClick(int x, int y, int button) 
			{	
				if (world.isClient) return;
				
				CompoundTag bankData = Bank.get(playerEntity);
				
				// validate the data in the text fields
				int iWithdrawGold = Optional.ofNullable(tfGold.getText().trim())
						 .map(Ints::tryParse)
						 .orElse(0);
				
				if (iWithdrawGold > bankData.getInt("gold")) iWithdrawGold = bankData.getInt("gold");
				
				int iWithdrawSilver = Optional.ofNullable(tfSilver.getText().trim())
							 .map(Ints::tryParse)
							 .orElse(0);
					
				if (iWithdrawSilver > bankData.getInt("silver")) iWithdrawSilver = bankData.getInt("silver");
				
				int iWithdrawCopper = Optional.ofNullable(tfCopper.getText().trim())
							 .map(Ints::tryParse)
							 .orElse(0);
					
				if (iWithdrawCopper > bankData.getInt("copper")) iWithdrawCopper = bankData.getInt("copper");
				
				Transaction transaction = new Transaction(iWithdrawGold, iWithdrawSilver, iWithdrawCopper);
				bankData = Bank.withdraw(playerEntity, TransactionType.BANKNOTE, transaction, bankData);
				lblGold.setText(new LiteralText(String.valueOf(bankData.getInt("gold"))));
				lblSilver.setText(new LiteralText(String.valueOf(bankData.getInt("silver"))));
				lblCopper.setText(new LiteralText(String.valueOf(bankData.getInt("copper"))));
				
				Bank.set(playerEntity, bankData);
				Bank.playSound(world, blockPos, ModSounds.CASH_REGISTER_SOUND_EVENT);
				Bank.getBalance(playerEntity, bankData);
			}
		};
        root.add(bankNoteButton, 0, 8, 11, 1);
        
        root.validate(this);
    }
    
    @Override
    public void addPainters() {
        getRootPanel().setBackgroundPainter(BackgroundPainter.VANILLA); //This is done automatically though
    }
}