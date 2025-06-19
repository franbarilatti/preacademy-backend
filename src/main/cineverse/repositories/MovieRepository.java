package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.ExternalMovie;
import main.cineverse.models.InternalMovie;
import main.cineverse.models.Movie;
import main.cineverse.models.enums.Genre;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {


    public void save(Movie movie) throws SQLException{

        String sql = "INSERT INTO movies (title, duration_minutes, genre, rating, origin, studio_name, licence_expiration, director_name, production_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, movie.getTitle());
            stmt.setInt(2,movie.getDurationMinutes());
            stmt.setString(3, movie.getGenre().name());
            stmt.setInt(4, movie.getRating());

            if(movie instanceof InternalMovie internal){
                stmt.setString(5, "INTERNAL");
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.DATE);
                stmt.setString(8, internal.getDirectorName());
                stmt.setString(9, internal.getProductionYear());
            }else if (movie instanceof ExternalMovie external) {
                stmt.setString(5, "EXTERNAL");
                stmt.setString(6, external.getStudioName());
                stmt.setDate(7, Date.valueOf(external.getLicenceExpirationDate()));
                stmt.setNull(8, Types.VARCHAR);
                stmt.setNull(9, Types.VARCHAR);
            }

            stmt.executeUpdate();

        }

    }

    public List<Movie> findAll() throws SQLException{
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movies";

        try(Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                String origin = rs.getString("origin");
                if("INTERNAL".equals(origin)){
                    movies.add(new InternalMovie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("duration_minutes"),
                            Genre.valueOf(rs.getString("genre")),
                            rs.getInt("rating"),
                            rs.getString("director_name"),
                            rs.getString("production_year")
                    ));
                }else {
                    movies.add(new ExternalMovie(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("duration_minutes"),
                            Genre.valueOf(rs.getString("genre")),
                            rs.getInt("rating"),
                            rs.getString("studio_name"),
                            rs.getDate("licence_expiration").toLocalDate()
                    ));
                }
            }

        }
        return movies;
    }

    public void printAllMovies() throws SQLException{
        List<Movie> movies = findAll();
        for (Movie m : movies){
            System.out.println(m.getTitle() + " - " + m.getGenre() + " - " + m.getDurationMinutes() + " mins");

        }
    }


}
