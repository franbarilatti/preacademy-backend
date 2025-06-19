package main.cineverse.repositories;

import main.cineverse.configs.DatabaseConnection;
import main.cineverse.models.User;
import main.cineverse.models.enums.SubcriptionPlan;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (username, country, plan) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getCountry());
            stmt.setString(3, user.getPlan().name());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));  // Seteamos el ID generado
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
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

    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String country = rs.getString("country");
                    SubcriptionPlan plan = SubcriptionPlan.valueOf(rs.getString("plan"));

                    return new User(id, username, plan, country, new ArrayList<>());
                }
            }
        }
        return null; // si no encontró usuario con ese id
    }

}
