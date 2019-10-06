package com.barkx4.economy;

import com.barkx4.economy.client.render.VendorRenderer;
import com.barkx4.economy.entity.VendorEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class Client implements ClientModInitializer
{
	@Override
	public void onInitializeClient() 
	{
		EntityRendererRegistry.INSTANCE.register(VendorEntity.class, (entityRenderDispatcher, context) -> new VendorRenderer(entityRenderDispatcher));
	}
}
