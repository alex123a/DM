CREATE TABLE driving_instructor(
    id serial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    work_phone CHAR(8) NOT NULL
);

CREATE TABLE customer(
    id serial PRIMARY KEY,
    CPR_number CHAR(10) NOT NULL,
    phone CHAR(8) NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE private_customer(
    id int REFERENCES customer(id) PRIMARY KEY
);

CREATE TABLE business_customer(
    id int REFERENCES customer(id) PRIMARY KEY
);

CREATE TABLE business(
    id serial PRIMARY KEY,
    CVR_number VARCHAR(30) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone CHAR(8) NOT NULL
);

CREATE TABLE business_customer_part_of(
    customer_id int REFERENCES business_customer(id),
    business_id int REFERENCES business(id),
    PRIMARY KEY (customer_id, business_id)

);

CREATE TABLE driving_licens(
    id serial PRIMARY KEY,
    price int NOT NULL
);

CREATE TABLE driving_licens_taking(
    id serial PRIMARY KEY,
    customer_id int REFERENCES customer(id) NOT NULL,
    license_type int REFERENCEs driving_licens(id) NOT NULL
);

CREATE TABLE driving_licens_finished(
    id serial PRIMARY KEY,
    customer_id int REFERENCES customer(id) NOT NULL,
    license_type int REFERENCES driving_licens(id) NOT NULL,
    date_for_finish DATE NOT NULL
);

CREATE TABLE tests(
    id serial PRIMARY KEY,
    date_for_test DATE NOT NULL,
    type_id int REFERENCES driving_licens(id) NOT NULL,
    customer_id REFERENCES customer(id) NOT NULL
);

CREATE TABLE driving_hours(
    id serial PRIMARY KEY,
    date_for_driving DATE NOT NULL,
    instructor_id int REFERENCES driving_instructor(id) NOT NULL,
    customer_id REFERENCES customer(id) NOT NULL
);

// Når ikke at putte data ind i databasen så laver queries i stedet

SELECT dh.date_for_driving, di.first_name, di.last_name
FROM driving_hours dh, driving_instructur di, business_customer bs, business_customer_part_of bcpo
WHERE dh.instructor_id = di.id AND di.id = 1
AND bs.id = dh.customer_id AND bcpo.customer_id = bs.id
AND bcpo = 1;

SELECT first_name, last_name, (SELECT COUNT(*) FROM driving_hours WHERE instructor_id = 1) 
FROM driving_instructur WHERE id = 1;


