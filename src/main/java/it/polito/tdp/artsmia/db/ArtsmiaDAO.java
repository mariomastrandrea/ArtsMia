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
		Connection conn = DBConnect.getConnection();

		try 
		{
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) 
			{
				ArtObject artObj;
				if(!idMap.containsKey(res.getInt("object_id")))
				{
					artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
							res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
							res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
							res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
					idMap.put(artObj.getId(), artObj);
				}	
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("SQL error in listObjects()", e);
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
			
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			peso = 0;
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
			ResultSet result = statement.executeQuery();
			
			while(result.next())
			{
				adiacenze.add(new Adiacenza(result.getInt("id1"), result.getInt("id2"), result.getInt("peso")));
			}
						
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return adiacenze;
	}
	
	
	
}
