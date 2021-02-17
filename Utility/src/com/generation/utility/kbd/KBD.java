package com.generation.utility.kbd;

import java.util.Scanner;

public class KBD implements IKBD
{

 

    //Questa classe è l'adapter, ovvero quello che mette in accordo
    //il vecchio componente con la nuova interfaccia
    //Nel nostro caso il vecchio Scanner e IKBD
    //Per farlo dobbiamo implementare l'interfaccia e inserire tra le proprietà
    //uno Scanner
    
    private Scanner s;
    
    public KBD()
    {
        this.s = new Scanner(System.in);
    }

 

    //Ora sfruttando il vecchio componente (Scanner) andiamo a scrivere
    //i seguenti metodi ereditati dalla nuova interfaccia
    
    @Override
    public String leggi() 
    {
        return s.nextLine();
    }

 

    @Override
    public String leggi(String messaggio) 
    {
        System.out.println(messaggio);
        return s.nextLine();
    }

 

    @Override
    public int leggiInt() 
    {
        return Integer.parseInt(s.nextLine());
    }

 

    @Override
    public int leggiInt(String messaggio) {
        System.out.println(messaggio);
        return leggiInt();
    }

 

    @Override
    public int leggiInt(String messaggio, int min, int max) 
    {
        int ris = 0;
        
        do
        {
            ris = leggiInt(messaggio);
        }while(ris < min || ris > max);
        
        return ris;
    }
    
}