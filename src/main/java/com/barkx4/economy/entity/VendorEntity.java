package com.barkx4.economy.entity;

import java.awt.Component;
import java.util.Collections;
import java.util.UUID;

import com.barkx4.economy.Main;
import com.barkx4.economy.components.BaseVendingComponent;
import com.barkx4.economy.interfaces.VendingComponent;
import com.barkx4.economy.vending.Vending;

import nerdhub.cardinal.components.api.component.ComponentContainer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VendorEntity extends LivingEntity
{
	public VendorEntity(EntityType<? extends LivingEntity> entityType, World world) 
	{
		super(entityType, world);
	    this.setCustomName(new LiteralText("Egg Vendor"));
	    this.setCustomNameVisible(true);
	}

	@Override
	public void move(MovementType movementType_1, Vec3d vec3d_1) 
	{
		// Do nothing.
	}
	
	@Override
	protected void initDataTracker() 
	{
		super.initDataTracker();
	}

	@Override
	public Iterable<ItemStack> getArmorItems() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getEquippedStack(EquipmentSlot equipmentSlot) 
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setEquippedStack(EquipmentSlot var1, ItemStack var2) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Arm getMainArm() 
	{
		return Arm.RIGHT;
	}
	
	@Override 
	protected void applyDamage(DamageSource damageSource, float damage)
	{
		this.remove();
		return;
		/*
		if (damageSource.getAttacker() instanceof PlayerEntity)
		{
			CompoundTag vendingData = Vending.get(this);
			UUID UUIDowner = vendingData.getUuid("owner");
			PlayerEntity playerAttacker = (PlayerEntity) damageSource.getAttacker();
			
			if (playerAttacker.getUuid() == UUIDowner)
			{
		
			}
		}*/
	}
	
	@Override
	public Iterable<ItemStack> getItemsEquipped() 
	{
	      return Collections.emptyList();
	}
}
