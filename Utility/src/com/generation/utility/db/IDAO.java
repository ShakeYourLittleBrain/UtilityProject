package com.generation.utility.db;

import java.util.List;

import com.generation.utility.entities.Entity;

public interface IDAO {
	
	public List<Entity> elenco();	
	public boolean elimina(int id);
	public boolean modifica(Entity e);
	public boolean salva(Entity e);

}
