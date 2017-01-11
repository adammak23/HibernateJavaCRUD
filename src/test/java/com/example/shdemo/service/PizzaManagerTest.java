package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Dodatek;
import com.example.shdemo.domain.Pizza;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PizzaManagerTest {
	
	@Autowired
	PizzaManager pizzaManager;
	
	private final String PIZZA = "JakasPizza";
	private final String PIZZA_2 = "Margheritta";
	private final String DODATEK = "Oliwki";
	private final int COST_1 = 20;
	private final int COST_2 = 2;
	
	@Before
	public void setup(){
		if(pizzaManager.findPizzabyName(PIZZA) == null){
			Dodatek dodatek = new Dodatek();
			dodatek.setName(DODATEK);
			dodatek.setCost(COST_2);
			
			Pizza pizza = new Pizza();
			pizza.setName(PIZZA);
			pizza.getDodatek().add(dodatek);
			pizza.setCost(COST_1);
			
			pizzaManager.addPizza(pizza);
		}
	}
	
	// pizza
	@Test
	public void add_pizza_noDodatek(){
		Pizza pizza = new Pizza();
		pizza.setName(PIZZA_2);
		pizza.setCost(COST_1);
		
		pizzaManager.addPizza(pizza);
		Pizza recievedPizza = pizzaManager.findPizzabyName(PIZZA_2);
		assertEquals(PIZZA_2, recievedPizza.getName());
	}
	
	@Test
	public void edit_pizza(){
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		pizza.setName("Change");
		pizza.setCost(COST_1);
		Long PizzaId = pizza.getId();
		pizzaManager.editPizza(pizza);
		
		Pizza pizza2 = pizzaManager.findPizzabyId(PizzaId);
		
		assertEquals("Change", pizza2.getName());
	}

	@Test
	public void delete_pizza(){
		int PizzaCount = pizzaManager.getAllPizza().size();
		int DodatekCount = pizzaManager.getAllDodatek().size();
		
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		Long phId = pizza.getId();
		pizzaManager.deletePizza(pizza);
		
		assertEquals(null, pizzaManager.findPizzabyId(phId));
		assertEquals(PizzaCount - 1, pizzaManager.getAllPizza().size());
		assertEquals(DodatekCount - 1, pizzaManager.getAllDodatek().size());
	}
	
	@Test
	public void add_dodatek(){
		Pizza recievedPizza = pizzaManager.findPizzabyName(PIZZA);
		int prev = recievedPizza.getDodatek().size();
		Dodatek dodatek = new Dodatek();
		dodatek.setName(DODATEK);
		dodatek.setCost(COST_2);
		
		Long dodatekId = pizzaManager.addNewDodatek(dodatek);
	
		pizzaManager.addDodatek(recievedPizza.getId(), dodatekId);
		

		List<Dodatek> ownedDodatek = pizzaManager.getOwnedDodatek(recievedPizza);
		
		assertEquals(prev+1, ownedDodatek.size());
		assertEquals(DODATEK, ownedDodatek.get(0).getName());
		assertEquals(COST_2, ownedDodatek.get(0).getCost());
	}
	
	@Test
	public void getPizzabyId(){
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		
		Pizza byId = pizzaManager.findPizzabyId(pizza.getId());
		
		assertEquals(pizza.getId(), byId.getId());
	}
	
	@Test
	public void getOwnedDodatek(){
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		List<Dodatek> ownedDodatek = pizzaManager.getOwnedDodatek(pizza);
		
		assertEquals(DODATEK, ownedDodatek.get(0).getName());
		assertEquals(COST_2, ownedDodatek.get(0).getCost());
		
	}
	
	//dodatki
	
	@Test
	public void new_dodatek(){
		Dodatek dodatek = new Dodatek();
		dodatek.setName(DODATEK);
		dodatek.setCost(COST_2);
		
		Long dodatekId = pizzaManager.addNewDodatek(dodatek);
		Dodatek retrievedDodatek = pizzaManager.findDodatekById(dodatekId);
		
		assertEquals(DODATEK, retrievedDodatek.getName());
		assertEquals(COST_2, retrievedDodatek.getCost());
	
	}

	@Test
	public void getDodatekbyId(){
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		Dodatek med = pizza.getDodatek().get(0);
		Dodatek byId = pizzaManager.findDodatekById(pizza.getDodatek().get(0).getId());
		
		assertEquals(med.getId(), byId.getId());
	}
	
	@Test
	public void edit_dodatek(){
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		Dodatek dodatek = pizza.getDodatek().get(0);
		dodatek.setName("Change");
		dodatek.setCost(999);
		Long PizzaId = pizza.getId();
		pizzaManager.editDodatek(dodatek);
		
		Pizza pizza2 = pizzaManager.findPizzabyId(PizzaId);
		
		assertEquals("Change", pizza2.getDodatek().get(0).getName());
		assertEquals(999, pizza2.getDodatek().get(0).getCost());
	}
	
	@Test
	public void delete_dodatek(){
		int PizzaCount = pizzaManager.getAllPizza().size();
		int DodatekCount = pizzaManager.getAllDodatek().size();
		
		Pizza pizza = pizzaManager.findPizzabyName(PIZZA);
		int DodatekinPizza = pizza.getDodatek().size();
		Dodatek dodatek = pizza.getDodatek().get(0);
		pizzaManager.deleteDodatek(dodatek);
		
		pizza = pizzaManager.findPizzabyName(PIZZA);
		
		assertEquals(PizzaCount, pizzaManager.getAllPizza().size());
		assertEquals(DodatekCount - 1, pizzaManager.getAllDodatek().size());
		assertEquals(DodatekinPizza - 1, pizza.getDodatek().size());
	}
}
