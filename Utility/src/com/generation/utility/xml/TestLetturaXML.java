package com.generation.utility.xml;

public class TestLetturaXML {

	public static void main(String[] args) 
	{
		
		String percorso = "src/com/generation/utility/xml/prova.xml";
		String elementoCercato = "studente";
		
		System.out.println(XMLConverter.leggiXML(percorso, elementoCercato));
		
		

	}

}
