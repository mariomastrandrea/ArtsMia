package it.polito.tdp.artsmia.model;

public class Exhibition 
{
	private int id;
	private String department;
	private String title;
	private int beginYear;
	private int endYear;

	
	public Exhibition(int id, String department, String title, int beginYear, int endYear) 
	{
		this.id = id;
		this.department = department;
		this.title = title;
		this.beginYear = beginYear;
		this.endYear = endYear;
	}

	public int getId() 
	{
		return this.id;
	}

	public String getDepartment() 
	{
		return this.department;
	}

	public String getTitle() 
	{
		return this.title;
	}

	public int getBeginYear() 
	{
		return this.beginYear;
	}

	public int getEndYear() 
	{
		return this.endYear;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exhibition other = (Exhibition) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() 
	{
		return String.format("exhibitionId: %d, title: %s", this.id, this.title);
	}
}
