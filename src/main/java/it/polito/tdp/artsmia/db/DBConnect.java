package it.polito.tdp.artsmia.db;
import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBConnect 
{
	private static final String jdbcURL = "jdbc:mariadb://localhost/artsmia";
	private static final String username = "root";
	private static final String password = "root";
	private static final HikariDataSource ds;

	static
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setUsername(username);
		config.setPassword(password);
		
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("preprStmtChacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}
	
	
	public static Connection getConnection() 
	{
		try 
		{
			return ds.getConnection();
		} 
		catch (SQLException sqle) 
		{
			System.err.println("DB Connection error at: "+ jdbcURL);
			throw new RuntimeException("DB Connection error at: "+ jdbcURL, sqle);
		}
	}
	
	public static void close(AutoCloseable... resources)
	{
		for(var r : resources)
			try
			{
				r.close();
			}
			catch(Exception e)
			{
				System.err.println("Error in closing resource "+ r);
				throw new RuntimeException("Error in closing resource "+r.toString(), e);
			}
	}

}
