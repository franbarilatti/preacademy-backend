package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRepository {

    public void printAllMovies(){
        String sql = "SELECT id, title, duration_minutes, genre, rating FROM movies";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration_minutes");
                String genre = rs.getString("genre");
                int rating = rs.getInt("rating");
                System.out.println("Movie: " + title + " (" + genre + ") - " + duration + " min, rating: " + rating);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }


}
