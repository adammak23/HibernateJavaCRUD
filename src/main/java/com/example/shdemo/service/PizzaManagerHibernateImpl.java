package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Dodatek;
import com.example.shdemo.domain.Pizza;

@Component
@Transactional
public class PizzaManagerHibernateImpl implements PizzaManager{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory _sessionFactory){
		this.sessionFactory = _sessionFactory;
	}

	
	// pizza
	public void addPizza(Pizza pizza) {
		sessionFactory.getCurrentSession().persist(pizza);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pizza> getAllPizza() {
		return sessionFactory.getCurrentSession().getNamedQuery("pizza.all").list();
	}

	public void deletePizza(Pizza pizza) {
		pizza = (Pizza) sessionFactory.getCurrentSession().get(Pizza.class, pizza.getId());
		for(Dodatek dodatek : pizza.getDodatek()){
			sessionFactory.getCurrentSession().delete(dodatek);
		}
		sessionFactory.getCurrentSession().delete(pizza);
	}

	public Pizza findPizzabyId(Long id) {
		return (Pizza) sessionFactory.getCurrentSession().get(Pizza.class, id);
	}
	
	public Pizza findPizzabyName(String name) {
		List<Pizza> pizzas =  sessionFactory.getCurrentSession().getNamedQuery("pizza.byName").setString("name", name).list();
		if(pizzas.size() == 0){
			return null;
		}else{
			return pizzas.get(0);
		}
	}
	
	public boolean editPizza(Pizza pizza){
		try{
			sessionFactory.getCurrentSession().update(pizza);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	//dodatki

	public Long addNewDodatek(Dodatek dodatek) {
		dodatek.setId(null);
		return (Long)sessionFactory.getCurrentSession().save(dodatek);
		
	}

	@SuppressWarnings("unchecked")
	public List<Dodatek> getAllDodatek() {
		return sessionFactory.getCurrentSession().getNamedQuery("dodatek.all").list();
	}

	public void deleteDodatek(Dodatek dodatek) {
		Dodatek _dodatek = (Dodatek) sessionFactory.getCurrentSession().get(Dodatek.class, dodatek.getId());
		
		List<Pizza> pizzas = getAllPizza();
		for(Pizza p : pizzas){
			for(Dodatek m : p.getDodatek()){
				if(m.getId() == _dodatek.getId()){
					p.getDodatek().remove(m);
					sessionFactory.getCurrentSession().update(p);
					break;
				}
			}
		}
		sessionFactory.getCurrentSession().delete(_dodatek);
	}

	public Dodatek findDodatekById(Long id) {
		return (Dodatek) sessionFactory.getCurrentSession().get(Dodatek.class, id);
	}

	public List<Dodatek> getOwnedDodatek(Pizza pizza) {
		pizza = (Pizza) sessionFactory.getCurrentSession().get(Pizza.class, pizza.getId());
		List<Dodatek> dodateks = new ArrayList<Dodatek>(pizza.getDodatek());
		return dodateks;
	}
	
	public boolean editDodatek(Dodatek dodatek){
		try{
			sessionFactory.getCurrentSession().update(dodatek);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public void addDodatek(Long pizzaId, Long dodatekId) {
		Pizza pizza = (Pizza) sessionFactory.getCurrentSession().get(Pizza.class, pizzaId);
		Dodatek dodatek = (Dodatek) sessionFactory.getCurrentSession().get(Dodatek.class, dodatekId);
		pizza.getDodatek().add(dodatek);
	}
}
