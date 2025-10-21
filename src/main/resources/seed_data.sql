-- ==============================
-- INITIAL TEST DATA
-- ==============================

INSERT INTO customers (name, phone, mail, address, city)
VALUES
    ('Tolga Demir', '05321234567', 'tolga@example.com', 'Beylikdüzü, İstanbul', 'İstanbul'),
    ('Merve Aksoy', '05329876543', 'merve@example.com', 'Kadıköy, İstanbul', 'İstanbul');

INSERT INTO animals (name, species, breed, gender, colour, date_of_birth, customer_id)
VALUES
    ('Leo', 'Cat', 'British Shorthair', 'Male', 'Gray', '2022-01-15', 1),
    ('Mia', 'Dog', 'Golden Retriever', 'Female', 'Beige', '2020-05-10', 2);

INSERT INTO doctors (name, phone, mail, address, city)
VALUES
    ('Dr. Gökhan Kandemir', '02121234567', 'gokhan@vetclinic.com', 'Ataşehir, İstanbul', 'İstanbul'),
    ('Dr. Ayşe Yılmaz', '02129876543', 'ayse@vetclinic.com', 'Beşiktaş, İstanbul', 'İstanbul');

INSERT INTO available_dates (doctor_id, available_date)
VALUES
    (1, '2025-10-25'),
    (1, '2025-10-26'),
    (2, '2025-10-25');

INSERT INTO appointments (animal_id, doctor_id, appointment_date)
VALUES
    (1, 1, '2025-10-25 14:00:00'),
    (2, 2, '2025-10-26 10:00:00');

INSERT INTO vaccines (animal_id, name, code, protection_start_date, protection_finish_date)
VALUES
    (1, 'Rabies', 'RAB-01', '2024-01-01', '2025-01-01'),
    (2, 'Distemper', 'DIS-02', '2024-03-10', '2025-03-10');
