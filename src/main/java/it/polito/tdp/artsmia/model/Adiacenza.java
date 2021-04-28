package it.polito.tdp.artsmia.model;

public class Adiacenza
{
	private Integer id1;
	private Integer id2;
	private int peso;
	
	
	public Adiacenza(Integer id1, Integer id2, int peso)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}

	public Integer getId1()
	{
		return id1;
	}

	public Integer getId2()
	{
		return id2;
	}


	public int getPeso()
	{
		return peso;
	}
		
}
