-- ===================================
-- VETERINARY MANAGEMENT SYSTEM SCHEMA
-- ===================================

CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           phone VARCHAR(20),
                           mail VARCHAR(100),
                           address TEXT,
                           city VARCHAR(50)
);

CREATE TABLE animals (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100),
                         species VARCHAR(50),
                         breed VARCHAR(50),
                         gender VARCHAR(20),
                         colour VARCHAR(30),
                         date_of_birth DATE,
                         customer_id INT REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE doctors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100),
                         phone VARCHAR(20),
                         mail VARCHAR(100),
                         address TEXT,
                         city VARCHAR(50)
);

CREATE TABLE available_dates (
                                 id SERIAL PRIMARY KEY,
                                 doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                                 available_date DATE NOT NULL
);

CREATE TABLE appointments (
                              id SERIAL PRIMARY KEY,
                              animal_id INT REFERENCES animals(id) ON DELETE CASCADE,
                              doctor_id INT REFERENCES doctors(id) ON DELETE CASCADE,
                              appointment_date TIMESTAMP NOT NULL
);

CREATE TABLE vaccines (
                          id SERIAL PRIMARY KEY,
                          animal_id INT REFERENCES animals(id) ON DELETE CASCADE,
                          name VARCHAR(100),
                          code VARCHAR(50),
                          protection_start_date DATE,
                          protection_finish_date DATE
);