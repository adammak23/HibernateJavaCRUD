package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "pizza.all", query = "Select p from Pizza p"),
	@NamedQuery(name = "pizza.byId", query = "Select p from Pizza p where p.id = :id"),
	@NamedQuery(name = "pizza.byName", query = "Select p from Pizza p where p.name = :name"),
	@NamedQuery(name = "pizza.byCost", query = "Select p from Pizza p where p.cost < :cost")
})
public class Pizza {
	private Long id;
	private String name = "";
	private int cost;
	private List<Dodatek> dodatki = new ArrayList<Dodatek>();
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Dodatek> getDodatek() {
		return dodatki;
	}
	public void setDodatek(List<Dodatek> dodatki) {
		this.dodatki = dodatki;
	}
}
