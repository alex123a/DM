CREATE TABLE genres(
    id serial PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL
);

CREATE TABLE movies(
    id serial PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    release_year DATE NOT NULL
);

CREATE TABLE actors(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE directors(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE box_office_date(
    id serial PRIMARY KEY,
    movie_id int REFERENCES movies(id) NOT NULL,
    the_date DATE NOT NULL
);

CREATE TABLE location(
    id serial PRIMARY KEY,
    location VARCHAR(50) NOT NULL
);

CREATE TABLE release_location(
    movie_id int REFERENCES movies(id) NOT NULL,
    location_id int REFERENCES location(id) NOT NULL
);

CREATE TABLE cast_for_movie(
    movie_id int REFERENCES movies(id) NOT NULL,
    actor_id int REFERENCES actors(id) NOT NULL,
    PRIMARY KEY (movie_id, actor_id)
);

CREATE TABLE directors_for_movie(
    movie_id int REFERENCES movies(id) NOT NULL,
    director_id int REFERENCES directors(id) NOT NULL,
    PRIMARY KEY (movie_id, director_id)
);

CREATE TABLE genre_for_movies(
    movie_id int REFERENCES movies(id) NOT NULL,
    genre_id int REFERENCES genres(id) NOT NULL,
    PRIMARY KEY (movie_id, genre_id)
);