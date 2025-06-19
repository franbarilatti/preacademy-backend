package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.User;
import main.cineverse.models.enums.SubcriptionPlan;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void save(User user) throws SQLException{

        String sql = "INSERT INTO users (id, username, country, plan) VALUES (?,?,?,?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getCountry());
            stmt.setString(4, user.getPlan().name());

            stmt.executeUpdate();
        }

    }

    public List<User> findAll() throws SQLException{

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                users.add(new User()
                        .id(rs.getInt("id"))
                        .username("username")
                        .plan(SubcriptionPlan.valueOf(rs.getString("plan")))
                        .country(rs.getString("country")));
            }
        }

        return users;
    }

    public void printAllUsers() throws SQLException{
        List<User> users = findAll();
        for (User user : users){
            System.out.println("@" + user.getUsername() + " - " + user.getPlan() + " - " + user.getCountry());
        }
    }



}
