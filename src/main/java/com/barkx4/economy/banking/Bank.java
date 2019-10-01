package com.barkx4.economy.banking;

import com.barkx4.economy.Main;
import com.barkx4.economy.enums.TransactionType;
import com.barkx4.economy.init.ModItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Bank 
{
	public static void set(PlayerEntity playerEntity, CompoundTag bankData)
	{
		Main.BANK.get(playerEntity).set(bankData);
	}
	
	public static CompoundTag get(PlayerEntity playerEntity)
	{
		return Main.BANK.get(playerEntity).get();
	}
	
	public static void getBalance(PlayerEntity playerEntity, CompoundTag bankData)
    {    	
    	int iCopper = bankData.getInt("copper");
        int iSilver = bankData.getInt("silver");
        int iGold = bankData.getInt("gold");
        
        playerEntity.sendMessage(new LiteralText("Your balance is " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
    }
    
    public static CompoundTag deposit(PlayerEntity playerEntity, TransactionType transaction, CompoundTag bankData)
    {
    	int iCopper = 0;
    	int iSilver = 0;
    	int iGold = 0;
    	
        for(int i = 0; i < playerEntity.inventory.getInvSize(); ++i) 
        {
           ItemStack itemStack = playerEntity.inventory.getInvStack(i);
           
           if (transaction == TransactionType.COIN)
           {
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
           else if (transaction == TransactionType.BANKNOTE)
           {
        	   if (itemStack.getItem().equals(ModItems.BANK_NOTE))
        	   {
        		   CompoundTag bankNoteData = itemStack.getTag();
        		   
        		   iCopper += bankNoteData.getInt("copper");
        		   iSilver += bankNoteData.getInt("silver");
        		   iGold += bankNoteData.getInt("gold");
        		   
        		   itemStack.setCount(0);
        	   }
           }
        }
        
        iSilver += Math.floorDiv(iCopper, 64);
        iCopper = Math.floorMod(iCopper, 64);
        iGold += Math.floorDiv(iSilver, 64);
        iSilver = Math.floorMod(iSilver, 64);
        
        playerEntity.sendMessage(new LiteralText("Deposited " + String.valueOf(iGold) + " gold, " + String.valueOf(iSilver) + " silver, and " + String.valueOf(iCopper) + " copper."));
        
        iCopper += bankData.getInt("copper");
        iSilver += bankData.getInt("silver");
        iGold += bankData.getInt("gold");
        
        iSilver += Math.floorDiv(iCopper, 64);
        iCopper = Math.floorMod(iCopper, 64);
        iGold += Math.floorDiv(iSilver, 64);
        iSilver = Math.floorMod(iSilver, 64);
        
        bankData.putInt("copper", iCopper);
        bankData.putInt("silver", iSilver);
        bankData.putInt("gold", iGold);
    	
    	return bankData;
    }
    
    public static CompoundTag withdraw(PlayerEntity playerEntity, TransactionType transactionType, Transaction transaction, CompoundTag bankData)
    {
    	if (playerEntity.inventory.getEmptySlot() == -1)
		{
			playerEntity.sendMessage(new LiteralText("Your inventory is full! Withdrawal failed."));
			return bankData;
		}
    	
    	int iWithdrawGold = 0;
    	int iWithdrawSilver = 0;
    	int iWithdrawCopper = 0;
		
		// Debit the amounts from the player's bank component storage
		bankData.putInt("gold", (bankData.getInt("gold") - transaction.gold));
		bankData.putInt("silver", (bankData.getInt("silver") - transaction.silver));
		bankData.putInt("copper", (bankData.getInt("copper") - transaction.copper));
		
		if (transactionType == TransactionType.COIN)
		{
			while (transaction.gold > 0)
			{
				if (playerEntity.inventory.getEmptySlot() == -1)
				{
					playerEntity.sendMessage(new LiteralText("Your inventory is full! Stopping withdrawal."));
					
					// Refund unwithdrawn coins.
					bankData.putInt("gold", (bankData.getInt("gold") + transaction.gold));
					bankData.putInt("silver", (bankData.getInt("silver") + transaction.silver));
					bankData.putInt("copper", (bankData.getInt("copper") + transaction.copper));
					playerEntity.sendMessage(new LiteralText("Received " + String.valueOf(iWithdrawGold) + " gold, " + String.valueOf(iWithdrawSilver) + " silver, and " + String.valueOf(iWithdrawCopper) + " copper."));
					
					return bankData;
				}
				
				ItemStack is = null;
				
				if (transaction.gold >= 64)
				{
					is = new ItemStack(ModItems.GOLD_COINS, 64);
					
					iWithdrawGold += 64;
					transaction.gold -= 64;
				}
				else 
				{
					is = new ItemStack(ModItems.GOLD_COINS, transaction.gold);

					iWithdrawGold += transaction.gold;
					transaction.gold = 0;
				}
				
				playerEntity.inventory.insertStack(is);
			}
			
			while (transaction.silver > 0)
			{
				if (playerEntity.inventory.getEmptySlot() == -1)
				{
					playerEntity.sendMessage(new LiteralText("Your inventory is full! Stopping withdrawal."));
					
					// Refund unwithdrawn coins.
					bankData.putInt("silver", (bankData.getInt("silver") + transaction.silver));
					bankData.putInt("copper", (bankData.getInt("copper") + transaction.copper));
					playerEntity.sendMessage(new LiteralText("Received " + String.valueOf(iWithdrawGold) + " gold, " + String.valueOf(iWithdrawSilver) + " silver, and " + String.valueOf(iWithdrawCopper) + " copper."));
					
					
					return bankData;
				}
				
				ItemStack is = null;
				
				if (transaction.silver >= 64)
				{
					is = new ItemStack(ModItems.SILVER_COINS, 64);
					
					iWithdrawSilver += 64;
					transaction.silver -= 64;
				}
				else 
				{
					is = new ItemStack(ModItems.SILVER_COINS, transaction.silver);
					
					iWithdrawSilver += transaction.silver;
					transaction.silver = 0;
				}
				
				playerEntity.inventory.insertStack(is);
			}
			
			while (transaction.copper > 0)
			{
				if (playerEntity.inventory.getEmptySlot() == -1)
				{
					playerEntity.sendMessage(new LiteralText("Your inventory is full! Stopping withdrawal."));
					
					// Refund unwithdrawn coins.
					bankData.putInt("copper", (bankData.getInt("copper") + transaction.copper));
					playerEntity.sendMessage(new LiteralText("Received " + String.valueOf(iWithdrawGold) + " gold, " + String.valueOf(iWithdrawSilver) + " silver, and " + String.valueOf(iWithdrawCopper) + " copper."));
					
					return bankData;
				}
				
				ItemStack is = null;
				
				if (transaction.copper >= 64)
				{
					is = new ItemStack(ModItems.COPPER_COINS, 64);
					
					iWithdrawCopper += 64;
					transaction.copper -= 64;
				}
				else 
				{
					is = new ItemStack(ModItems.COPPER_COINS, transaction.copper);
					
					iWithdrawCopper += transaction.copper;
					transaction.copper = 0;
				}
				
				playerEntity.inventory.insertStack(is);
			}
			
			playerEntity.sendMessage(new LiteralText("Received " + String.valueOf(iWithdrawGold) + " gold, " + String.valueOf(iWithdrawSilver) + " silver, and " + String.valueOf(iWithdrawCopper) + " copper."));
		}
		else if (transactionType == TransactionType.BANKNOTE)
		{
			// Create the bank note
			CompoundTag bankNoteData = new CompoundTag();
			bankNoteData.putInt("gold", transaction.gold);
			bankNoteData.putInt("silver", transaction.silver);
			bankNoteData.putInt("copper", transaction.copper);
			
			ItemStack is = new ItemStack(ModItems.BANK_NOTE, 1);
			is.setTag(bankNoteData);
			
			playerEntity.inventory.insertStack(is);
			
			playerEntity.sendMessage(new LiteralText("Received bank note for " + String.valueOf(transaction.gold) + " gold, " + String.valueOf(transaction.silver) + " silver, and " + String.valueOf(transaction.copper) + " copper."));
		}
		
		return bankData;
    }
    
    public static void playSound(World world, BlockPos blockPos, SoundEvent soundEvent)
    {
    	world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
