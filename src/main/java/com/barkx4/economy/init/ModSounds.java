package com.barkx4.economy.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds 
{
	public static final Identifier COIN_SFX = new Identifier("economy:coin_sound");
	public static SoundEvent COIN_SOUND_EVENT = new SoundEvent(COIN_SFX);
	
	public static final Identifier CASH_REGISTER_SFX = new Identifier("economy:register_sound");
	public static SoundEvent CASH_REGISTER_SOUND_EVENT = new SoundEvent(CASH_REGISTER_SFX);
	
	public static void init()
	{
		Registry.register(Registry.SOUND_EVENT, COIN_SFX, COIN_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, CASH_REGISTER_SFX, CASH_REGISTER_SOUND_EVENT);
	}
}
