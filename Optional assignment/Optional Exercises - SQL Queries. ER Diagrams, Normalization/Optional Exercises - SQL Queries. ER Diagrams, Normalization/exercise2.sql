-- Jeg vælger at lade være med at lave en Person tabel, dette vælger jeg for at undgå at skabe for mange joins når man skal query data ud.
-- work_phone er en VARCHAR(12) da der kan være forskel på telefon numre rundt om i verden, derfor ønsker jeg ikke at begrænse til et dansk nummer med CHAR(8) eller lignende
CREATE TABLE employees(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    work_email VARCHAR(50) NOT NULL,
    work_phone VARCHAR(12) NOT NULL
);

CREATE TABLE roles(
    id serial PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE work_as(
    id serial PRIMARY KEY,
    employee_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT employee_id_constraint FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    CONSTRAINT role_id_constraint FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
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
    CONSTRAINT flight_ticket FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

-- Kunder skal kunne købe flere end en billet, hvis det f.eks. er en far som køber billetter til hele familien.
CREATE TABLE tickets_bought(
    id serial PRIMARY KEY,
    ticket_id INT NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT tickets_bought_constraint FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
    CONSTRAINT customer_on_tickets_bought FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Her kan customer kun forekomme en gang. Selvom en customer kan købe billetter til hele familien, så tolker jeg det som
-- om at hele familien skal registeres i customers, således fly firmaet har informationer på dem som flyver med dem. Dog kan man risikere
-- at en person har brug for to sæder, hvis personer f.eks. er handicappet eller lignende.
CREATE TABLE passengers_on(
    id serial PRIMARY KEY,
    customer_id INT NOT NULL,
    flight_id INT NOT NULL,
    CONSTRAINT passengers_on_plane FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    CONSTRAINT flight_for_passenger FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

-- En employee kan kun være på en flight en gang
CREATE TABLE crew_on(
    id serial PRIMARY KEY,
    employee_id INT NOT NULL,
    flight_id INT NOT NULL,
    CONSTRAINT employees_on_flight FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    CONSTRAINT flight_for_crew FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE
);

-- Data
INSERT INTO employees(first_name, last_name, work_email, work_phone)
VALUES ('Børge', 'Søndergaard', 'sej@mail.com', '12345678');
INSERT INTO employees(first_name, last_name, work_email, work_phone)
VALUES ('Buk', 'Reje', 'sejeReje@mail.com', '12385678');
INSERT INTO employees(first_name, last_name, work_email, work_phone)
VALUES ('Sejt', 'Klap', 'sejKlap@mail.com', '32345679');
INSERT INTO employees(first_name, last_name, work_email, work_phone)
VALUES ('Nej', 'Ja', 'sejNejJa@mail.com', '12347678');

INSERT INTO roles(role) VALUES ('cook');
INSERT INTO roles(role) VALUES ('pilot');
INSERT INTO roles(role) VALUES ('stewardess');

INSERT INTO work_as(employee_id, role_id) VALUES (1, 2);
INSERT INTO work_as(employee_id, role_id) VALUES (2, 1);
INSERT INTO work_as(employee_id, role_id) VALUES (2, 3);
INSERT INTO work_as(employee_id, role_id) VALUES (3, 1);
INSERT INTO work_as(employee_id, role_id) VALUES (4, 3);

INSERT INTO customers(first_name, last_name, email, phone_number) VALUES ('fuck', 'fucker', 'fuck@gmail.com', '12345668');
INSERT INTO customers(first_name, last_name, email, phone_number) VALUES ('ingen', 'ide', 'noidea@sejt.com', '12345578');
INSERT INTO customers(first_name, last_name, email, phone_number) VALUES ('fuck2', 'fucker2', 'fuck2@gmail.com', '22345678');
INSERT INTO customers(first_name, last_name, email, phone_number) VALUES ('fuck3', 'fucker3', 'fuck2@gmail.com', '32345678');

INSERT INTO airports(airport_name, city_name) VALUES ('Singapore', 'Singapore Airport');
INSERT INTO airports(airport_name, city_name) VALUES ('Billund', 'Billund Airport');
INSERT INTO airports(airport_name, city_name) VALUES ('Amsterdam', 'Amsterdam Airport');

INSERT INTO flights(origin, destination, departure_time, arrival_time, number_of_seats)
VALUES (2, 3, '05-06-2020', '06-06-2020', 300);
INSERT INTO flights(origin, destination, departure_time, arrival_time, number_of_seats)
VALUES (1, 3, '07-06-2020', '07-06-2020', 330);
INSERT INTO flights(origin, destination, departure_time, arrival_time, number_of_seats)
VALUES (2, 1, '05-06-2020', '06-06-2020', 300);
INSERT INTO flights(origin, destination, departure_time, arrival_time, number_of_seats)
VALUES (2, 1, '05-07-2020', '06-07-2020', 300);
INSERT INTO flights(origin, destination, departure_time, arrival_time, number_of_seats)
VALUES (1, 3, '07-06-2020', '07-06-2020', 330);

INSERT INTO tickets(price, flight_id) VALUES (400, 1);
INSERT INTO tickets(price, flight_id) VALUES (500, 2);
INSERT INTO tickets(price, flight_id) VALUES (300, 3);
INSERT INTO tickets(price, flight_id) VALUES (400, 3);

INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (1, 1);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (1, 1);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (1, 2);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (2, 3);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (3, 4);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (3, 2);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (4, 1);
INSERT INTO tickets_bought(ticket_id, customer_id) VALUES (4, 3);

INSERT INTO passengers_on(customer_id, flight_id) VALUES (1, 1);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (3, 1);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (2, 1);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (3, 2);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (4, 3);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (2, 3);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (1, 3);
INSERT INTO passengers_on(customer_id, flight_id) VALUES (3, 3);

INSERT INTO crew_on(employee_id, flight_id) VALUES (1, 1);
INSERT INTO crew_on(employee_id, flight_id) VALUES (2, 1);
INSERT INTO crew_on(employee_id, flight_id) VALUES (1, 2);
INSERT INTO crew_on(employee_id, flight_id) VALUES (2, 2);
INSERT INTO crew_on(employee_id, flight_id) VALUES (3, 2);
INSERT INTO crew_on(employee_id, flight_id) VALUES (1, 3);

-- Queries
SELECT * FROM employees WHERE work_phone = '12345678';

SELECT * FROM customers WHERE id = 1;

SELECT * FROM employees WHERE work_email = 'sejeReje@mail.com';

SELECT * FROM customers WHERE email LIKE '%@gmail.com';

SELECT * FROM customers cu INNER JOIN passengers_on pa ON cu.id = pa.customer_id, flights f
WHERE f.id = pa.flight_id AND f.id = 1;

SELECT * FROM flights WHERE destination = 3 AND arrival_time > '01-06-2020' AND arrival_time < '10-06-2020';


-- Gider ikke til at lave sidste query for at få all passengers på et fly til paris på en
-- bestemt dato. Det er bare at kombinere de to ovenstående queries.


-- Index
-- Laver index på ticket price, da det kunne forestilles at folk søger efter prisniveau.
CREATE INDEX ON tickets(price);

-- Tilføjer lige en kolonne til pladser tilbage på flyet for at lave triggeren under
ALTER TABLE flights ADD COLUMN seats_left INT DEFAULT 300;

-- Function and trigger
CREATE OR REPLACE FUNCTION update_seats_left_on_flight()
	RETURNS TRIGGER
AS $$
DECLARE
	passengers_on_board integer = 0;
	flights_id CURSOR FOR SELECT DISTINCT(flights.id) AS id FROM flights;
BEGIN
	FOR the_flight_id IN flights_id LOOP
		SELECT COUNT(*) INTO passengers_on_board FROM passengers_on pa WHERE pa.flight_id = the_flight_id.id;
		UPDATE flights SET seats_left = number_of_seats - passengers_on_board WHERE the_flight_id.id = flights.id;
	END LOOP;
	RETURN NEW;
END; $$
LANGUAGE plpgsql;

CREATE TRIGGER update_number_of_seats_left_trigger
	AFTER INSERT OR DELETE ON passengers_on
	EXECUTE PROCEDURE update_seats_left_on_flight();