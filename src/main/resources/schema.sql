-- ===================================
-- 🧩 VETERINARY MANAGEMENT SYSTEM SCHEMA
-- ===================================

-- Drop existing tables safely // Toplu silmek istersen
DROP TABLE IF EXISTS animals,doctors,available_dates,appointments,vaccines CASCADE;

-- Clean tables before inserting // tek tek silmek istersen
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS animals CASCADE;
DROP TABLE IF EXISTS doctors CASCADE;
DROP TABLE IF EXISTS available_dates CASCADE;
DROP TABLE IF EXISTS appointments CASCADE;
DROP TABLE IF EXISTS vaccines CASCADE;

-- 2️⃣ Sequence resetle (gerekirse)
ALTER SEQUENCE animals_id_seq RESTART WITH 1;
ALTER SEQUENCE appointments_id_seq RESTART WITH 1;
ALTER SEQUENCE available_dates_id_seq RESTART WITH 1;
ALTER SEQUENCE customers_id_seq RESTART WITH 1;
ALTER SEQUENCE doctors_id_seq RESTART WITH 1;
ALTER SEQUENCE vaccines_id_seq RESTART WITH 1;


-- 🧍 CUSTOMERS
CREATE TABLE customers
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    phone   VARCHAR(20),
    mail    VARCHAR(100),
    address TEXT,
    city    VARCHAR(50)
);

-- 🐾 ANIMALS
CREATE TABLE animals
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100),
    species       VARCHAR(50),
    breed         VARCHAR(50),
    gender        VARCHAR(20),
    colour        VARCHAR(30),
    date_of_birth DATE,
    customer_id   INT REFERENCES customers (id) ON DELETE CASCADE
);

-- 🩺 DOCTORS
CREATE TABLE doctors
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100),
    phone   VARCHAR(20),
    mail    VARCHAR(100),
    address TEXT,
    city    VARCHAR(50)
);

-- 📅 AVAILABLE DATES
CREATE TABLE available_dates
(
    id             SERIAL PRIMARY KEY,
    doctor_id      INT REFERENCES doctors (id) ON DELETE CASCADE,
    available_date DATE NOT NULL
);

-- 🕒 APPOINTMENTS
CREATE TABLE appointments
(
    id               SERIAL PRIMARY KEY,
    animal_id        INT REFERENCES animals (id) ON DELETE CASCADE,
    doctor_id        INT REFERENCES doctors (id) ON DELETE CASCADE,
    appointment_date TIMESTAMP NOT NULL
);

-- 💉 VACCINES
CREATE TABLE vaccines
(
    id                     SERIAL PRIMARY KEY,
    animal_id              INT REFERENCES animals (id) ON DELETE CASCADE,
    name                   VARCHAR(100),
    code                   VARCHAR(50),
    protection_start_date  DATE,
    protection_finish_date DATE
);

-- =========================
-- ⚡ INDEXES (AFTER CREATION)
-- =========================
CREATE INDEX idx_customer_name ON customers (name);
CREATE INDEX idx_animal_name ON animals (name);
CREATE INDEX idx_vaccine_code ON vaccines (code);
CREATE INDEX idx_doctor_name ON doctors (name);

