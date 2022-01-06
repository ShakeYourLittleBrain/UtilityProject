package com.generation.utility.kbd;

    // merge test shekor-test branch

public interface IKBD 
{
	public String leggi();
    public String leggi(String messaggio);
    public int leggiInt();
    public int leggiInt(String messaggio);
    public int leggiInt(String messaggio, int min, int max);
}
