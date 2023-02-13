package fr.isen.java2.db.daos;



import java.sql.SQLException;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

//LIBRARIES USED FOR THE BONUS STAGE
//import java.sql.Connection;
//import java.sql.DriverManager;


public class DataSourceFactory {

	private static SQLiteDataSource dataSource;
	
	//BONUS STAGE
	//private static final String DRIVER = "org.sqlite.JDBC";
    //private static final String URL = "jdbc:sqlite:sqlite.db";

	private DataSourceFactory() {
		// This is a static class that should not be instantiated.
		// Here's a way to remember it when this class will have 2K lines and you come
		// back to it in 2 years
		throw new IllegalStateException("This is a static class that should not be instantiated");
	}

	/**
	 * @return a connection to the SQLite Database
	 * @throws SQLException 
	 * 
	 */
	public static DataSource getDataSource() throws SQLException {
		if (dataSource == null) {
			dataSource = new SQLiteDataSource();
			dataSource.setUrl("jdbc:sqlite:sqlite.db");
			
			
		}
		return dataSource;
	}
	
	//BONUS STAGE
	
	/*public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL);
    }*/
}