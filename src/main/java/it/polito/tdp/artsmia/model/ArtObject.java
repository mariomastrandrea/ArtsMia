package it.polito.tdp.artsmia.model;

public class ArtObject 
{
	private int id;
	private String classification;
	private String continent;
	private String country;
	private int curatorApproved;
	private String dated;
	private String department;
	private String medium;
	private String nationality;
	private String name;
	private int restricted;
	private String rightsType;
	private String role;
	private String room;
	private String style;
	private String title;

	
	public ArtObject(int objectId, String classification, String continent, 
			String country, int curatorApproved, String dated, String department, 
			String medium, String nationality, String objectName, int restricted, 
			String rightsType, String role, String room, String style, String title) 
	{
		this.id = objectId;
		this.classification = classification;
		this.continent = continent;
		this.country = country;
		this.curatorApproved = curatorApproved;
		this.dated = dated;
		this.department = department;
		this.medium = medium;
		this.nationality = nationality;
		this.name = objectName;
		this.restricted = restricted;
		this.rightsType = rightsType;
		this.role = role;
		this.room = room;
		this.style = style;
		this.title = title;
	}

	public int getId() 
	{
		return this.id;
	}

	public String getClassification() 
	{
		return this.classification;
	}

	public String getContinent() 
	{
		return this.continent;
	}

	public String getCountry() 
	{
		return this.country;
	}

	public int getCuratorApproved() 
	{
		return this.curatorApproved;
	}


	public String getDated() 
	{
		return this.dated;
	}

	public String getDepartment() 
	{
		return this.department;
	}

	public String getMedium() 
	{
		return this.medium;
	}
	
	public String getNationality() 
	{
		return this.nationality;
	}

	public String getName() 
	{
		return this.name;
	}

	public int getRestricted() 
	{
		return this.restricted;
	}

	public String getRightsType() 
	{
		return this.rightsType;
	}

	public String getRole() 
	{
		return this.role;
	}

	public String getRoom() 
	{
		return this.room;
	}

	public String getStyle() 
	{
		return this.style;
	}

	public String getTitle() 
	{
		return this.title;
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
		ArtObject other = (ArtObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() 
	{
		return String.format("objectId: %d, title: %s", this.id, this.title);
	}
}
