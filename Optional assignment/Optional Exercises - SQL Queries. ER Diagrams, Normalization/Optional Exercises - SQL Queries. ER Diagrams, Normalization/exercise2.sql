-- Jeg vælger at lade være med at lave en Person tabel, dette vælger jeg for at undgå at skabe for mange joins når man skal query data ud.

CREATE TABLE employees(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    work_email VARCHAR(50) NOT NULL,
    work_phone VARCHAR(12) NOT NULL
);

-- work_phone er en VARCHAR(12) da der kan være forskel på telefon numre rundt om i verden, derfor ønsker jeg ikke at begrænse til et dansk nummer med CHAR(8) eller lignende

CREATE TABLE pilots(
    id INT PRIMARY KEY,
    ADD CONSTRAINT pilot_id FOREIGN KEY (id) REFERENCES employees (id) ON DELETE CASCADE;
);

CREATE TABLE stewardess(
    id INT PRIMARY KEY,
    ADD CONSTRAINT stewardess_id FOREIGN KEY (id) REFERENCES employees (id) ON DELETE CASCADE;
);

CREATE TABLE cooks(
    id INT PRIMARY KEY,
    ADD CONSTRAINT cook_id FOREIGN KEY (id) REFERENCES employees (id) ON DELETE CASCADE;
);

CREATE TABLE customers(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12) NOT NULL
);

-- Da origin og destination omtaler en lufthavn, så laver jeg
-- en tabel kaldet airport.
CREATE TABLE airports(
    id serial PRIMARY KEY,
    airport_name VARCHAR(50) NOT NULL,
    city_name VARCHAR(50)
);

CREATE TABLE flights(
    id serial PRIMARY KEY,
    origin INT REFERENCES airports(id) NOT NULL,
    destination INT REFERENCES airports(id) NOT NULL,
    departure_time DATE NOT NULL,
    arrival_time DATE NOT NULL,
    number_of_seats INT
);

CREATE TABLE tickets(
    id serial PRIMARY KEY,
    price INT NOT NULL,
    flight_id INT NOT NULL,
    ADD CONSTRAINT flight_ticket FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE;
);

-- Kunder skal kunne købe flere end en billet, hvis det f.eks. er en far som køber billetter til hele familien.
CREATE TABLE tickets_bought(
    id serial PRIMARY KEY,
    ticket_id INT NOT NULL,
    customer_id INT NOT NULL,
    ADD CONSTRAINT tickets_bought_constraint FOREIGN KEY (ticket_id) REFERENCES tickets (id) ON DELETE CASCADE,
    ADD CONSTRAINT customer_on_tickets_bought FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);

-- Her kan customer kun forekomme en gang. Selvom en customer kan købe billetter til hele familien, så tolker jeg det som
-- om at hele familien skal registeres i customers, således fly firmaet har informationer på dem som flyver med dem. Dog kan man risikere
-- at en person har brug for to sæder, hvis personer f.eks. er handicappet eller lignende.
CREATE TABLE passengers_on(
    id serial PRIMARY KEY,
    customer_id INT NOT NULL,
    flight_id INT NOT NULL,
    ADD CONSTRAINT passengers_on_plane FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
    ADD CONSTRAINT flight_for_passenger FOREIGN KEY (flight_id) REFERENCES flights (id) ON DELETE CASCADE
);

-- En employee kan kun være på en flight en gang
CREATE TABLE crew_on(
    id serial PRIMARY KEY,
    employee_id INT NOT NULL,
    flight_id INT NOT NULL,
    ADD CONSTRAINT employees_on_flight FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE,
    ADD CONSTRAINT flight_for_crew FOREIGN KEY (flight_id) REFERENCES flights (id) ON DELETE CASCADE
);