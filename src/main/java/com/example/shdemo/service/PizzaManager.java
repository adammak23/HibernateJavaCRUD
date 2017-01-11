package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Dodatek;
import com.example.shdemo.domain.Pizza;

public interface PizzaManager {

	void addPizza(Pizza pizza);
	List<Pizza> getAllPizza();
	void deletePizza(Pizza pizza);
	Pizza findPizzabyId(Long id);
	public Pizza findPizzabyName(String name);
	public boolean editPizza(Pizza pizza);
	
	Long addNewDodatek(Dodatek pizzacine);
	List<Dodatek> getAllDodatek();
	void deleteDodatek(Dodatek dodatek);
	Dodatek findDodatekById(Long id);
	public boolean editDodatek(Dodatek dodatek);
	
	List<Dodatek> getOwnedDodatek(Pizza pizza);
	void addDodatek(Long pizzaId, Long dodatekId);


}
