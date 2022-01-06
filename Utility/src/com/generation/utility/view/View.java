package com.generation.utility.view;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.generation.utility.entities.Entity;

	//merge test kanta-test branch
 
public class View 
{
	private String percorsoCartella;

	public View(String percorsoCartella)
	{	
		this.percorsoCartella = percorsoCartella;
	}
	
	public String leggi(String nomeFile)
	{
		String ris = "";
		
		Scanner dati = null;
		try
		{
			dati = new Scanner(new File(percorsoCartella + "/" + nomeFile));
			
			while(dati.hasNextLine())
					ris += dati.nextLine();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			ris = "Problemi nella lettura del file" + nomeFile;
		}
		finally 
		{
			dati.close();
		}
		
		return ris;
	}
	
	
	
	
	public String grafica(String nomeFile, Entity e)
	{
		String ris = leggi(nomeFile);
		
		Map<String, String> campi = e.toMap();
		
		for(String chiave : campi.keySet())
			ris = ris.replace("[" + chiave + "]", campi.get(chiave));
		
		return ris;
	}
	
	
	public String grafica(String nomeFile, List<Entity> entities)
	{
		String temp = leggi(nomeFile);
		String ris = "";
		
		for(Entity e : entities)
		{
			ris += temp;
			Map<String, String> campi = e.toMap();
			for(String chiave : campi.keySet())
				ris = ris.replace("[" + chiave + "]", campi.get(chiave));	
			
		}
		
		return ris;
	}
	
}
