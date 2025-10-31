-- =====================================================
-- 🧩 INITIAL TEST DATA // Toplu silmek istersen
-- This script resets all database tables and inserts
-- sample records for testing and development purposes.
-- Use this only in development environments.
-- =====================================================

TRUNCATE TABLE appointments,available_dates,vaccines,animals,doctors,customers RESTART IDENTITY CASCADE;

-- ==============================
-- Clean tables before inserting // tek tek silmek istersen
-- ==============================

TRUNCATE TABLE customers RESTART IDENTITY CASCADE;
TRUNCATE TABLE animals RESTART IDENTITY CASCADE;
TRUNCATE TABLE doctors RESTART IDENTITY CASCADE;
TRUNCATE TABLE available_dates RESTART IDENTITY CASCADE;
TRUNCATE TABLE appointments RESTART IDENTITY CASCADE;
TRUNCATE TABLE vaccines RESTART IDENTITY CASCADE;

-- ==============================
-- INITIAL TEST DATA
-- ==============================

-- 🧍 Customers
INSERT INTO customers (name, phone, mail, address, city)
VALUES ('Tolga Demir', '05321234567', 'tolga@example.com', 'Beylikdüzü, İstanbul', 'İstanbul'),
       ('Serra Demir', '05551231234', 'ayse@example.com', 'Ataşehir, İstanbul', 'İstanbul'),
       ('Mehmet Kara', '05443334455', 'mehmet@example.com', 'Konak, İzmir', 'İzmir'),
       ('Zeynep Öz', '05071234567', 'zeynep@example.com', 'Nilüfer, Bursa', 'Bursa'),
       ('Ali Can', '05391112233', 'ali@example.com', 'Çankaya, Ankara', 'Ankara');


-- 🐾 Animals
INSERT INTO animals (name, species, breed, gender, colour, date_of_birth, customer_id)
VALUES ('Leo', 'Cat', 'British Shorthair', 'Male', 'Gray', '2022-01-15', 1),
       ('Jarvis', 'Dog', 'Golden Retriever', 'Female', 'Cream', '2020-06-10', 2),
       ('Kuki', 'Parrot', 'African Grey', 'Male', 'Silver', '2021-02-05', 3),
       ('Gölge', 'Cat', 'Siamese', 'Male', 'White', '2019-09-09', 4),
       ('Karaman', 'Dog', 'Poodle', 'Female', 'Black', '2023-03-22', 5);


-- 🩺 Doctors
INSERT INTO doctors (name, phone, mail, address, city)
VALUES ('Dr. Güldane Demir', '02121234567', 'gokhan.kandemir@vetclinic.com', 'Ataşehir, İstanbul', 'İstanbul'),
       ('Dr. İsmail Demir', '02129998877', 'elif.aydin@vetclinic.com', 'Bornova, İzmir', 'İzmir'),
       ('Dr. Arzu Leblebici', '03122223344', 'mehmet.ozkan@vetclinic.com', 'Çankaya, Ankara', 'Ankara'),
       ('Dr. Selin Demir', '02243332211', 'selin.demir@vetclinic.com', 'Nilüfer, Bursa', 'Bursa'),
       ('Dr. Can Yılmaz', '02128887766', 'can.yilmaz@vetclinic.com', 'Kadıköy, İstanbul', 'İstanbul');


-- 📅 Available Dates
INSERT INTO available_dates (doctor_id, available_date)
VALUES (1, '2025-10-28'),
       (1, '2025-10-29'),
       (2, '2025-10-30'),
       (3, '2025-10-31'),
       (4, '2025-11-01');


-- 🕒 Appointments
INSERT INTO appointments (doctor_id, animal_id, appointment_date)
VALUES (1, 1, '2025-10-28T14:00:00'),
       (1, 2, '2025-10-29T10:00:00'),
       (2, 3, '2025-10-30T16:00:00'),
       (3, 4, '2025-10-31T11:00:00'),
       (4, 5, '2025-11-01T13:00:00');


-- 💉 Vaccines
INSERT INTO vaccines (animal_id, name, code, protection_start_date, protection_finish_date)
VALUES (1, 'Rabies', 'RAB-01', '2024-01-01', '2025-01-01'),
       (1, 'Feline Distemper', 'CAT-02', '2024-03-01', '2025-03-01'),
       (2, 'Canine Parvo', 'DOG-01', '2023-11-01', '2024-11-01'),
       (3, 'Avian Influenza', 'BIRD-01', '2024-05-01', '2025-05-01'),
       (5, 'Rabies', 'RAB-01', '2024-02-01', '2025-02-01');

-- =====================================================
-- ✅ DATABASE SEED COMPLETED SUCCESSFULLY
-- =====================================================

