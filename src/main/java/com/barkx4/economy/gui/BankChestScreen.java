package com.barkx4.economy.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.ClientCottonScreen;

/** This subclass doesn't need to do anything, just be a distinct
 *  class so that anyone making edits or adding buttons can find us
 *  with an instanceof check */
public class BankChestScreen extends ClientCottonScreen {
    public BankChestScreen(GuiDescription description) {
        super(description);
    }
}
