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

INSERT INTO customer(CPR_number, phone, email, first_name, last_name)
VALUES ('3425233435', '23456789','some.email@gmail.com',
	   'someFirstName', 'someLastName'),
	   ('2423233436', '24456789','some2.email@gmail.com',
	   'some2FirstName', 'some2LastName'),
	   ('1425233435', '53456789','some3.email@gmail.com',
	   'some3FirstName', 'some3LastName');

INSERT INTO driving_instructor(first_name, last_name, work_phone)
VALUES ('someInstructor', 'someInstructorLastName', '45362324'),
('some2Instructor', 'some2InstructorLastName', '75362324');

INSERT INTO driving_hours(date_for_driving, instructor_id, customer_id)
VALUES (NOW(), 1, 2), (NOW(), 2, 2), ('04-06-2021', 1, 1);


SELECT dh.date_for_driving, di.first_name, di.last_name
FROM driving_hours dh, driving_instructur di, business_customer bs, business_customer_part_of bcpo
WHERE dh.instructor_id = di.id AND di.id = 1
AND bs.id = dh.customer_id AND bcpo.customer_id = bs.id
AND bcpo = 1;

SELECT first_name, last_name, (SELECT COUNT(*) FROM driving_hours WHERE instructor_id = 1) 
FROM driving_instructor WHERE id = 1;


