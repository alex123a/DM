-- Jeg vælger at lade være med at lave en Person tabel, dette vælger jeg for at undgå at skabe for mange joins når man skal query data ud.

CREATE TABLE employee(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    work_email VARCHAR(50) NOT NULL,
    work_phone VARCHAR(12) NOT NULL
);

-- work_phone er en VARCHAR(12) da der kan være forskel på telefon numre rundt om i verden, derfor ønsker jeg ikke at begrænse til et dansk nummer med CHAR(8) eller lignende

CREATE TABLE pilot(
    id PRIMARY KEY,
    ADD CONSTRAINT pilot_id FOREIGN KEY (id) REFERENCES employee (id) ON DELETE CASCADE;
);

CREATE TABLE stewardess(
    id PRIMARY KEY,
    ADD CONSTRAINT stewardess_id FOREIGN KEY (id) REFERENCES employee (id) ON DELETE CASCADE;
);

CREATE TABLE cook(
    id PRIMARY KEY,
    ADD CONSTRAINT cook_id FOREIGN KEY (id) REFERENCES employee (id) ON DELETE CASCADE;
);

CREATE TABLE customer(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(12) NOT NULL
);