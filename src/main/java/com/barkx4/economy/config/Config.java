package com.barkx4.economy.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.barkx4.economy.banking.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Config 
{
	public static Map<Identifier, Transaction> mobDropVariables = new HashMap<Identifier, Transaction>();
		
	public static void init() throws IOException 
	{
		Registry.ENTITY_TYPE.forEach((t) -> { 
			if (t.getCategory() == EntityCategory.MONSTER)
			{
				mobDropVariables.put(EntityType.getId(t), new Transaction(0,0,0));
			}
		});
		
		
		
		File file = new File("config/mmoverhaul_economy.json");
		
		if (!file.exists())
		{
	        Writer writer = new FileWriter(file);
	
	        Gson gson = new GsonBuilder().registerTypeAdapter(Identifier.class, new Identifier.Serializer()).setPrettyPrinting().create();
	        gson.toJson(mobDropVariables, writer);
	
	        writer.close();
		}
		else
		{
			Reader reader = new FileReader(file);

            Gson gson = new GsonBuilder().registerTypeAdapter(Identifier.class, new Identifier.Serializer()).create();
            mobDropVariables = gson.fromJson(reader, new TypeToken<Map<Identifier, Transaction>>(){}.getType());
            
            reader.close();
		}
    }
}
