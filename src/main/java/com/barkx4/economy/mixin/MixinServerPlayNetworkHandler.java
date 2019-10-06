package com.barkx4.economy.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.barkx4.economy.entity.VendorEntity;
import com.barkx4.economy.gui.VendorGui;
import com.barkx4.economy.gui.VendorOwnerGui;
import com.barkx4.economy.gui.VendorOwnerScreen;
import com.barkx4.economy.gui.VendorScreen;
import com.barkx4.economy.vending.Vending;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.PlayerInteractEntityC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler 
{
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interactAt(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), cancellable = true)
	public void onPlayerInteractEntity(PlayerInteractEntityC2SPacket packet, CallbackInfo info) {
		World world = player.getEntityWorld();
		Entity entity = packet.getEntity(world);
		if (entity != null) {
			EntityHitResult hitResult = new EntityHitResult(entity, packet.getHitPosition().add(entity.x, entity.y, entity.z));
			
			// Custom code for handling vendors
			if (entity instanceof VendorEntity)
			{
				if (!world.isClient)
				{
					CompoundTag vendingData = Vending.get((VendorEntity)entity);
					UUID UUIDowner = vendingData.getUuid("owner");
					
					System.out.println("Got from player: UUID = " + player.getUuidAsString());
					System.out.println("Got from vending component: owner = " + UUIDowner.toString());
					
					if (player.getUuid().compareTo(UUIDowner) == 0)
					{
						System.out.println("Owner Menu");
						MinecraftClient.getInstance().openScreen(new VendorOwnerScreen(new VendorOwnerGui(entity.getEntityId(), player, (VendorEntity)entity, world), player));
					}
					else
					{
						System.out.println("Customer Menu");
						MinecraftClient.getInstance().openScreen(new VendorScreen(new VendorGui(player, (VendorEntity)entity, world)));
					}
				}
			}

			ActionResult result = UseEntityCallback.EVENT.invoker().interact(player, world, packet.getHand(), entity, hitResult);
			if (result != ActionResult.PASS) {
				info.cancel();
			}
		}
	}
}