package fr.isen.java2.db.daos;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fr.isen.java2.db.entities.Film;
import fr.isen.java2.db.entities.Genre;

public class FilmDao {

	/**
	 * Method used to list films. To implement the bonus stage, the comment that surrounds the try needs to be 
	 * uncommented and the try that uses DataSourceFactory needs to be commented
	 * @return a list of films
	 */
	public List<Film> listFilms() {
		List<Film> listOfFilms = new ArrayList<>();
		
		String sqlQUERY = "Select * from film join genre on film.genre_id = genre.idgenre";
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection()){
			try(Statement statement = connection.createStatement()){
				try(ResultSet results = statement.executeQuery(sqlQUERY)){
					while(results.next()) {
						//Variable that holds the Genre of the film that is being extracted from results
						Genre genre = new Genre(results.getInt("genre_id"), results.getString("name"));
						
						Film film = new Film(results.getInt("idfilm"), results.getString("title"), results.getDate("release_date").toLocalDate(), genre, results.getInt("duration"), results.getString("director"), results.getString("summary"));
						listOfFilms.add(film);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return listOfFilms;
	}
	
	
	/**
	 * Method used to list all of the films of the same genre.To implement the bonus stage, the comment 
	 * that surrounds the try needs to be uncommented and the try that uses DataSourceFactory needs 
	 * to be commented.
	 * @param genreName: String with the name of the genre
	 * @return a list of films
	 */
	public List<Film> listFilmsByGenre(String genreName) {
		
		String sqlQUERY = "SELECT * FROM film JOIN genre ON film.genre_id = genre.idgenre WHERE genre.name = ?";
		
		List<Film> listOfFilms = new ArrayList<>();
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
	    try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        try (PreparedStatement statement = 
	                        connection.prepareStatement(sqlQUERY)) {
	            statement.setString(1, genreName);
	            try (ResultSet results = statement.executeQuery()) {
	                while (results.next()) {
	                	//Variable that holds the Genre of the film that is being extracted from results
	                	Genre genre = new Genre(results.getInt("genre_id"), results.getString("name"));
	                    Film film = new Film(results.getInt("idfilm"),
	                                            results.getString("title"),
	                                            results.getDate("release_date").toLocalDate(),
	                                            genre,
	                                            results.getInt("duration"), 
	                                            results.getString("director"),
	                                            results.getString("summary")
	                                            );
	                    listOfFilms.add(film);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listOfFilms;
	}
	
	/**
	 * Method used to add a film to a list.To implement the bonus stage, the comment that surrounds the try needs to be 
	 * uncommented and the try that uses DataSourceFactory needs to be commented
	 * @param film that we want to add
	 * @return The film that has been added
	 */
	public Film addFilm(Film film) {
		//BONUS STAGE
		//try (Connection connection = DataSourceFactory.getConnection()) {
		 try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
		        String sqlQuery = "INSERT INTO film(title,release_date,genre_id,duration,director,summary) VALUES(?,?,?,?,?,?)";
		        try (PreparedStatement statement = connection.prepareStatement(
		                        sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
		            statement.setString(1, film.getTitle());
		            statement.setDate(2, Date.valueOf(film.getReleaseDate()));
		            statement.setInt(3, film.getGenre().getId());
		            statement.setInt(4, film.getDuration());
		            statement.setString(5, film.getDirector());
		            statement.setString(6, film.getSummary());
		            
		            statement.executeUpdate();
		            ResultSet ids = statement.getGeneratedKeys();
		            if (ids.next()) {
		                return new Film(ids.getInt(1), film.getTitle(), film.getReleaseDate(), film.getGenre(), film.getDuration(), film.getDirector(), film.getSummary());
		            }
		        }
		    }catch (SQLException e) {
		        // Manage Exception
		        e.printStackTrace();
		    }
		    return null;
	}
}
