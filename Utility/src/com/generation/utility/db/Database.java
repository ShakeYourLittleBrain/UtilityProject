package com.generation.utility.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database {

	private static final String FUSORARIO = "?" + "useUnicode=true&" + "useJDBCCompliantTimezoneShift=true&"
			+ "useLegacyDatetimeCode=false&" + "serverTimezone=UTC";

	// In questa classe utilizzeramo un pattern di nome singleton.
	// In informatica un pattern è una slouzione comune ad un problema ricorrente.
	// Il mio problema con questa classe è che voglio che di oggetti Database,
	// all'interno del mio progetto, non ve vengono creati più di 1.
	// Quindi voglio che venga creato un solo canale di connessione con mySQL
	// e che venga utilizzato solamente quello , senza mai chiuderlo e
	// riaprirne di nuovi, visto che richiederebbe risorse.

	// Per applicare il pattern Singleton si procede cosi :

	// Dichiarare un'istanza privata e statica di Database
	// questo sarà l'unico oggetto Database che tutto il mio programma
	// utilizzerà

	private static Database instance = null;
	private Connection con;

	// Dichiarare il costruttore privato per rendere impossibile la creazione
	// di oggetti Database dall'esterno
	private Database() {
	}

	// Creamo in metodo statico publico che quando richiamato crea l'istanza se non
	// esistente e la restituisce
	public static Database getInstance() {
		if (instance == null)
			instance = new Database();

		return instance;
	}

	public void apriConnessione(String nomeDb, String user, String pass) {
		if (con != null)
			return;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nomeDb + FUSORARIO, user, pass);
		} catch (Exception e) {
			System.out.println("Problema con database!!!");
			e.printStackTrace();
		}
	}

	public void chiudiConnessione() {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("Impossibile chiudere la connesisone");
			e.printStackTrace();
		}
	}

	public Map<String, String> row(String query) {
		try {
			return rows(query).get(0);
		} catch (Exception e) {
			return null;
		}

	}

	public Map<String, String> row(String query, String... params) {
		try {
			return rows(query, params).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, String>> rows(String query) {
		List<Map<String, String>> ris = new ArrayList<Map<String, String>>();

		try {
			PreparedStatement p = con.prepareStatement(query);
			ResultSet rs = p.executeQuery();

			while (rs.next()) {

				Map<String, String> riga = new LinkedHashMap<String, String>();

				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

					riga.put(rs.getMetaData().getColumnLabel(i).toLowerCase(), rs.getString(i));
				}

				ris.add(riga);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ris;
	}

	public List<Map<String, String>> rows(String query, String... params) {
		List<Map<String, String>> ris = new ArrayList<Map<String, String>>();

		try {
			PreparedStatement p = con.prepareStatement(query);

			for (int i = 0; i < params.length; i++)
				p.setString(i + 1, params[i]);

			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				Map<String, String> riga = new LinkedHashMap<String, String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					riga.put(rs.getMetaData().getColumnLabel(i).toLowerCase(), rs.getString(i));
				}

				ris.add(riga);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ris;
	}

	public boolean update(String query) {
		boolean ris = true;

		try {
			PreparedStatement p = con.prepareStatement(query);
			p.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			ris = false;
		}

		return ris;
	}

	public boolean update(String query, String... args) // ... varargs
	{
		boolean ris = true;

		try {
			PreparedStatement p = con.prepareStatement(query);

			for (int i = 0; i < args.length; i++)
				p.setString(i + 1, args[i]);

			p.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			ris = false;
		}

		return ris;
	}

}
