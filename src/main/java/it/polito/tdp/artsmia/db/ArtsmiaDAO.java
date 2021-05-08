package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO 
{
	public void listObjects(Map<Integer, ArtObject> idMap) 
	{	
		String sql = "SELECT * from objects";

		try 
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet queryResult = statement.executeQuery();
			
			while (queryResult.next()) 
			{
				ArtObject artObj;
				int id = queryResult.getInt("object_id");
				if(!idMap.containsKey(id))
				{
					artObj = new ArtObject(id, queryResult.getString("classification"), queryResult.getString("continent"), 
							queryResult.getString("country"), queryResult.getInt("curator_approved"), queryResult.getString("dated"), queryResult.getString("department"), 
							queryResult.getString("medium"), queryResult.getString("nationality"), queryResult.getString("object_name"), queryResult.getInt("restricted"), 
							queryResult.getString("rights_type"), queryResult.getString("role"), queryResult.getString("room"), queryResult.getString("style"), queryResult.getString("title"));
					
					idMap.put(artObj.getId(), artObj);
				}	
			}
			connection.close();
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in listObjects()", sqle);
		}
	}
	
	public int getPesoArcoTra(ArtObject a1, ArtObject a2)
	{
		String sqlQuery = String.format("%s %s %s %s",
							"SELECT COUNT(*) AS peso",
							"FROM exhibition_objects e1, exhibition_objects e2",
							"WHERE e1.exhibition_id = e2.exhibition_id",
								   	"AND e1.object_id = ? AND e2.object_id = ?");
		int peso = 0;

		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, a1.getId());
			statement.setInt(2, a2.getId());
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				peso = result.getInt("peso");
			
			DBConnect.close(result, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getPesoArcoTra()", sqle);
		}
		return peso;
	}
	
	public Collection<Adiacenza> getAdiacenze()
	{
		String sqlQuery = String.format("%s %s %s %s",
				"SELECT e1.object_id AS id1, e2.object_id AS id2, COUNT(*) AS peso",
				"FROM exhibition_objects e1, exhibition_objects e2",
				"WHERE e1.exhibition_id = e2.exhibition_id AND e1.object_id < e2.object_id",
				"GROUP BY e1.object_id, e2.object_id");
		
		Collection<Adiacenza> adiacenze = new HashSet<>();
		
		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next())
			{
				Adiacenza newAdiacenza = new Adiacenza(queryResult.getInt("id1"), 
														queryResult.getInt("id2"), 
														queryResult.getInt("peso"));
				adiacenze.add(newAdiacenza);
			}
						
			DBConnect.close(queryResult, statement, connection);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Dao error in getAdiacenze()", sqle);
		}
		
		return adiacenze;
	}	
}
