CREATE DATABASE cineverse;
USE cineverse;

CREATE TABLE movies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    duration_minutes INT,
    genre VARCHAR(50),
    rating INT,
    origin VARCHAR(50),
    studio_name VARCHAR(255),
    licence_expiration DATE,
    director_name VARCHAR(255),
    production_year VARCHAR(10)
);

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    country VARCHAR(100),
    plan VARCHAR(50)
);

CREATE TABLE viewings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT,
    user_id INT,
    date DATE,
    minutes_watched INT,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);