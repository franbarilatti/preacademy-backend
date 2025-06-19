package main.cineverse.models;

import main.cineverse.models.enums.SubcriptionPlan;

import java.util.List;

public class User {

    private int id;
    private String username;
    private SubcriptionPlan plan;
    private String country;
    private List<Viewing> viewings;

    public User() {
    }

    public User(int id, String username, SubcriptionPlan plan, String country, List<Viewing> viewings) {
        this.id = id;
        this.username = username;
        this.plan = plan;
        this.country = country;
        this.viewings = viewings;
    }

    public User(String username, SubcriptionPlan plan, String country, List<Viewing> viewings) {
        this.username = username;
        this.plan = plan;
        this.country = country;
        this.viewings = viewings;
    }

    public User id(int id){
        this.id = id;
        return this;
    }

    public User username(String username){
        this.username = username;
        return this;
    }

    public User plan(SubcriptionPlan plan){
        this.plan = plan;
        return this;
    }

    public User country(String country){
        this.country = country;
        return this;
    }

    public User viewings(List<Viewing> viewings){
        this.viewings = viewings;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SubcriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubcriptionPlan plan) {
        this.plan = plan;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Viewing> getViewings() {
        return viewings;
    }

    public void setViewings(List<Viewing> viewings) {
        this.viewings = viewings;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", plan=" + plan +
                ", country='" + country + '\'' +
                ", viewings=" + viewings +
                '}';
    }
}
