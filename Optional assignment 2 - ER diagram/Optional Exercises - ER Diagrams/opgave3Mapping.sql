-- Er ikke 100% med på hvad SSN er, så laver den som en unique varchar
CREATE TABLE patients(
    id serial PRIMARY KEY,
    SSN VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    age int NOT NULL
);

CREATE TABLE speciality(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Tolker det som om en doktor kun kan have et speciale, derfor en til en kardanilitet.
CREATE TABLE doctors(
    id serial PRIMARY KEY,
    SSN VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    speciality_id int REFERENCES speciality(id) NOT NULL,
    years_of_exp int NOT NULL
);

CREATE TABLE patient_goes_to(
    id serial PRIMARY KEY,
    patient_id int REFERENCES patients(id) NOT NULL,
    doctor_id int REFERENCES doctors(id) NOT NULL
);

CREATE TABLE pharmaceutical_companies(
    id serial PRIMARY KEY,
    phone_number CHAR(8) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE pharmacies(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone_number CHAR(8) UNIQUE NOT NULL,
    address VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE supervisors(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE contracts(
    id serial PRIMARY KEY,
    pharmaceutical_id int REFERENCES pharmaceutical_companies(id) NOT NULL,
    pharmacy_id int REFERENCES pharmacies(id) NOT NULL,
    supervisor_id int REFERENCES supervisors(id) NOT NULL,
    text VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE drugs(
    id serial PRIMARY KEY,
    trade_name VARCHAR(50) UNIQUE NOT NULL,
    formula VARCHAR(300) NOT NULL
);

CREATE TABLE pharmacy_sells(
    id serial PRIMARY KEY,
    price int NOT NULL,
    pharmacy_id int REFERENCES pharmacies(id) NOT NULL,
    drug_id int REFERENCES drugs(id) NOT NULL
);

CREATE TABLE prescriped_drug(
    id serial PRIMARY KEY,
    doctor_id int REFERENCES doctors(id) NOT NULL,
    patient_id int REFERENCES patients(id) NOT NULL,
    drug_id int REFERENCES drugs(id) NOT NULL,
    date_given DATE NOT NULL,
    amount int NOT NULL
);

INSERT INTO patients(SSN, name, address, age)
VALUES ('SDDS330D', 'Some Dude', 'Dronningsgade 17', 23),
('FFBS330D', 'Some Girl', 'Dronningsgade 13', 20),
('GDDS330D', 'Karl Børge', 'Mussegade 3', 25);

INSERT INTO speciality(name) VALUES ('Børn og unge'),
('Ben specialist'),
('Øre specialist');

INSERT INTO doctors(SSN, name, speciality_id, years_of_exp)
VALUES ('DDDS899', 'Karl Johan', 1, 13),
('332SDDS', 'Julie Fine', 2, 15);

INSERT INTO patient_goes_to(doctor_id, patient_id)
VALUES (1, 1), (1, 2), (2, 3);

-- Er ikke 100% med på hvad SSN er, så laver den som en unique varchar
CREATE TABLE patients(
    id serial PRIMARY KEY,
    SSN VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    age int NOT NULL
);

CREATE TABLE speciality(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Tolker det som om en doktor kun kan have et speciale, derfor en til en kardanilitet.
CREATE TABLE doctors(
    id serial PRIMARY KEY,
    SSN VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    speciality_id int REFERENCES speciality(id) NOT NULL,
    years_of_exp int NOT NULL
);

-- Da en patient kun kan have en doctor, så burde der bare have været en reference
-- i patients til doktoren, men på grund af tidspres, så kan jeg ikke nå at ændre det.
CREATE TABLE patient_goes_to(
    id serial PRIMARY KEY,
    patient_id int REFERENCES patients(id) NOT NULL,
    doctor_id int REFERENCES doctors(id) NOT NULL
);

CREATE TABLE pharmaceutical_companies(
    id serial PRIMARY KEY,
    phone_number CHAR(8) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE pharmacies(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone_number CHAR(8) UNIQUE NOT NULL,
    address VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE supervisors(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE contracts(
    id serial PRIMARY KEY,
    pharmaceutical_id int REFERENCES pharmaceutical_companies(id) NOT NULL,
    pharmacy_id int REFERENCES pharmacies(id) NOT NULL,
    supervisor_id int REFERENCES supervisors(id) NOT NULL,
    text VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE drugs(
    id serial PRIMARY KEY,
    trade_name VARCHAR(50) UNIQUE NOT NULL,
    formula VARCHAR(300) NOT NULL
);

CREATE TABLE pharmacy_sells(
    id serial PRIMARY KEY,
    price int NOT NULL,
    pharmacy_id int REFERENCES pharmacies(id) NOT NULL,
    drug_id int REFERENCES drugs(id) NOT NULL
);

CREATE TABLE prescriped_drug(
    id serial PRIMARY KEY,
    doctor_id int REFERENCES doctors(id) NOT NULL,
    patient_id int REFERENCES patients(id) NOT NULL,
    drug_id int REFERENCES drugs(id) NOT NULL,
    date_given DATE NOT NULL,
    amount int NOT NULL
);

INSERT INTO patients(SSN, name, address, age)
VALUES ('SDDS330D', 'Some Dude', 'Dronningsgade 17', 23),
('FFBS330D', 'Some Girl', 'Dronningsgade 13', 20),
('GDDS330D', 'Karl Børge', 'Mussegade 3', 25);

INSERT INTO speciality(name) VALUES ('Børn og unge'),
('Ben specialist'),
('Øre specialist');

INSERT INTO doctors(SSN, name, speciality_id, years_of_exp)
VALUES ('DDDS899', 'Karl Johan', 1, 13),
('332SDDS', 'Julie Fine', 2, 15);

INSERT INTO patient_goes_to(doctor_id, patient_id)
VALUES (1, 1), (1, 2), (2, 3);
