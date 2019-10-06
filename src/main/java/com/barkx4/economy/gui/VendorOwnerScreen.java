package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.client.CottonScreen;
import net.minecraft.entity.player.PlayerEntity;

/** This subclass doesn't need to do anything, just be a distinct
 *  class so that anyone making edits or adding buttons can find us
 *  with an instanceof check */
public class VendorOwnerScreen extends CottonScreen<VendorOwnerGui> {
    public VendorOwnerScreen(VendorOwnerGui container, PlayerEntity player) {
        super(container, player);
    }
}
