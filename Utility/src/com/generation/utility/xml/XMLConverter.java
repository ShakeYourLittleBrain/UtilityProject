package com.generation.utility.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class XMLConverter 
{
	
	//Cos'e XML ? XML (extensible markup language ) è un
	//linguaggio di markup , ovvero un linguaggio che definisce
	//delle regole ben precise di scrittura per far si che 
	//tutti i vari file rispettino delle regole standard
	//e di consentire una semplice lettura da parte di tutti
	
	//Un file XML per funzionare deve essere ben formato e valido.
	//Un file per essere ben formato deve rispettare 5 regole:
	//1) deve esserci un unico elemento root (radice)
	//2) Gli elimenti devono essere sempre chiusi
	//3) XML è case-sensetive, dunque se apro un tag in un modo
	//   per chiuderlo devo scriverlo esattamente uguale
	//4) devo rispetttare l'ordine di nidificazione , se apro il tag 
	//   <A> e poi il tag <B> prima di chiudere <A> devo chiudere <B>
	//5) i valori degli attribuiti devono essere racchiusi tra 
	//   apci singolo o doppi
	
	
	//Un file XML per essere considerato valido deve rispettare
	//un file di configurazione chiamato XSD or XML schema, questo
	//file non sempre è persente, qualora lo fosse cu fornisce delle
	//regole sui tag che devono essere peresenti , sui valori che devono avere
	//sugli attrubuti obbligatori ecc...
	
	
	public static List<Map<String, String>> leggiXML(String percorso, String element)
	{
		List<Map<String, String>> ris = new ArrayList<Map<String,String>>();
		
		try 
		{
			File file = new File(percorso);
			//servono a creare uno strumento Java per l'interpretazione dei Document,
            //cioè faremo in modo di avere l'intera struttura del file xml in java
            //esattamente come ResultSet era in grado di riportare tutta la 
            //struttura delle tabelle di SQL
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			 // doc contiene tutta la struttura ad albero dell'xml, quindi riconosce la radice
            // riconosce i vari sotto elementi, con eventuali figli, e riconosce gli attributi
			Document doc = dBuilder.parse(file);
			NodeList lista = doc.getElementsByTagName(element);
            
			for(int i = 0; i < lista.getLength(); i++)
            {
                //prendiamo l'elemento i-esimo della lista e lo castiamo a Element
                Element e = (Element)lista.item(i); 
                // corrisponde a get di List, serve per prendere un oggetto
                //Il metodo _getAttributes servirà ad inserire all'interno di una mappa
                //tutti gli attributi di un element
                Map<String, String> mappa = _getAttributies(e);
                //Se da quell'element non riesco ad estrapolare attributi
                //allora la mia mappa sarà vuota e chiamerò il metodo _getElements
                if(!mappa.isEmpty())
                    ris.add(mappa);
                else
                    ris.add(_getElements(e));
                //Il metodo getElements prende come parametro un singolo Element e restituisce
                //una mappa contenente tutti gli elementi figli
            }
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return ris;
	}
	
	
	private static Map<String, String> _getAttributies(Element e)
	{
		Map<String, String> ris = new HashMap<String, String>();
		
		//cosa cambia dal metodo precedente? L'unica differenza sta nel fatto
        //che non leggiamo più gli elementi figli di e ma leggiamo gli attributi di e
        NamedNodeMap listaattributi = e.getAttributes();
        
        for(int i = 0; i < listaattributi.getLength(); i++)
        {
            // mi restituisce il nome dell'attributo, che nel nostro caso sarà la chiave della mappa
            String chiave = listaattributi.item(i).getNodeName();
            // mi restituisce il valore dell'attributo, che nel nostro caso sarà la chiave della mappa
            String valore = listaattributi.item(i).getNodeValue(); 
            ris.put(chiave, valore);
        }
		
		
		return ris;
	}
	
	private static Map<String, String> _getElements(Element e)
	{
		Map<String, String> ris = new HashMap<String, String>();
		
		//listanodi conterrà tutti i nodi figli del nostro Element e
        NodeList listanodi = e.getChildNodes();
        
        for(int j = 0; j < listanodi.getLength(); j++)
        {
            
            String chiave = listanodi.item(j).getNodeName();
            String valore = listanodi.item(j).getTextContent();
            if(!chiave.equals("#text"))
                ris.put(chiave, valore);
        }
        
        return ris;
		
	}
	
	
	
	
	
	
}
