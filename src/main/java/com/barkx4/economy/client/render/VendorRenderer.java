package com.barkx4.economy.client.render;

import com.barkx4.economy.entity.VendorEntity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class VendorRenderer extends LivingEntityRenderer<VendorEntity, PlayerEntityModel<VendorEntity>> 
{

	public VendorRenderer(EntityRenderDispatcher entityRenderDispatcher) 
	{
		super(entityRenderDispatcher, new PlayerEntityModel<VendorEntity>(1.0F, true), 1);
	}

	@Override
	protected Identifier getTexture(VendorEntity var1) 
	{
		return new Identifier("economy:textures/entity/vendor/default.png");
	}

}