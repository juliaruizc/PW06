package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Genre;

public class GenreDao {

	/**
	 * Method used to list different Genres. To implement the bonus stage, the comment that surrounds the try 
	 * needs to be uncommented and the try that uses DataSourceFactory needs to be commented
	 * @return list of genres
	 */
	public List<Genre> listGenres() {
		String sqlQUERY = "SELECT * FROM genre";
		List<Genre> listOfGenre = new ArrayList<>();
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection()){
			try(Statement statement = connection.createStatement()){
				try(ResultSet results = statement.executeQuery(sqlQUERY)){
					while(results.next()) {
						Genre genre = new Genre(results.getInt("idgenre"), results.getString("name"));
						listOfGenre.add(genre);
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfGenre;
	}

	/**
	 * Method used to get the Genre based on the name of the genre. To implement the bonus stage, the comment
	 * that surrounds the try needs to be uncommented and the try that uses DataSourceFactory needs to be commented
	 * @param name of the genre
	 * @return Genre associated with the name
	 */
	public Genre getGenre(String name) {
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        try (PreparedStatement statement = connection.prepareStatement(
	                    "select * from genre where name=?")) {
	            statement.setString(1, name);
	            try (ResultSet results = statement.executeQuery()) {
	                if (results.next()) {
	                    return new Genre(results.getInt("idgenre"),
	                                    results.getString("name"));
	                }
	            }
	        }
	    } catch (SQLException e) {
	        // Manage Exception
	        e.printStackTrace();
	    }
		return null;
	}

	/**
	 * Method used to add a genre. To implement the bonus stage, the comment that surrounds the try needs to be 
	 * uncommented and the try that uses DataSourceFactory needs to be commented
	 * @param name of the genre
	 */
	public void addGenre(String name) {
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        String sqlQuery = "insert into genre(name) "+"VALUES(?)";
	        try (PreparedStatement statement = connection.prepareStatement(
	                        sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, name);	            
	            statement.executeUpdate();
	            ResultSet ids = statement.getGeneratedKeys();
	            if (ids.next()) {
	                new Genre(ids.getInt(1), name);
	            }
	        }
	    }catch (SQLException e) {
	        // Manage Exception
	        e.printStackTrace();
	    }
	}
}
