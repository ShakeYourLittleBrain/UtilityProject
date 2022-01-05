package com.generation.utility.entities;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Entity implements Cloneable {

	private int id;
	
	public Entity clone()
	{
		try
		{
			return (Entity) super.clone();
		
		} 
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String toString()
	{
		return "Id : " + id + "\n" ;
	}

	
	
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}


	
	public String toXML()
	{
		
		String ris = "    <"+ this.getClass().getSimpleName().toLowerCase() +">\n";
		
		Map<String, String> dati = this.toMap();
		
		for(String key : dati.keySet())
			ris+= "         <" + key +">" + dati.get(key) + "</" + key + ">\n";
		
		ris += "    </" + this.getClass().getSimpleName().toLowerCase() + ">\n";
		
		return ris;
	}


	//Questa metodo lo scriveremo con l'aiuto di java refelection 
	//Una serie di metodi che ci aiutono a ragionare sugli elementi che
	//compongono il nostro oggetto, quindi potremo manipolare le sue 
	//propriet� e i suoi metodi senza conoscerle
	public Map<String, String> toMap()
	{
		Map<String, String> ris = new LinkedHashMap<String , String>();	

		//Ciclo tutti i metodi dellamia classe

		for(Method m : this.getClass().getMethods())
		{
			if(m.getName().startsWith("get") || m.getName().startsWith("is"))
			{

				try
				{	String chiave ="";

				if(m.getName().startsWith("get"))
					chiave = m.getName().substring(3).toLowerCase();
				else
					chiave = m.getName().substring(2).toLowerCase();

				if(chiave.equals("class")) continue;

				String valore = m.invoke(this).toString();

				ris.put(chiave,valore);

				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		}

		return ris;
	}
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION
	//CONVENTION OVER CONFIGURATION

	public void fromMap(Map<String , String> mappa)
	{
		for(Method m : this.getClass().getMethods())
		{
			if(m.getName().startsWith("set") && m.getParameterCount() == 1)
			{

				String nome = m.getName().substring(3).toLowerCase();

				//Controllo che nella mia mappa esista il campo che si
				// chiama come il setter e che non sia null
				if(mappa.containsKey(nome) && mappa.get(nome) != null)
				{
					//Risaliamo al tipo si paramentro richiesto dal setter

					String tipo = m.getParameters()[0].getType().getSimpleName().toLowerCase();

					try
					{

						switch(tipo)
						{
						case "string" : 
							m.invoke(this, mappa.get(nome));
							break;
						case "int" : 
							m.invoke(this, Integer.parseInt(mappa.get(nome)));
							break;
						case "double" : 
							m.invoke(this, Double.parseDouble(mappa.get(nome)));
							break;
						case "boolean" : 
							m.invoke(this, mappa.get(nome).equalsIgnoreCase("true"));
							break;
						}

					}
					catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}




}
