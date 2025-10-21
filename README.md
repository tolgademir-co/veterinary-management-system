# 🩺 Veterinary Management System

A RESTful backend application built with **Java 21**, **Spring Boot**, and **PostgreSQL**.  
This project was developed as a final project to demonstrate layered architecture, business rules, and REST API design for a veterinary clinic management system.

---

## 🚀 Project Overview
This project is developed as part of the **Patika+ Back-End Developer Bootcamp** Final Assignment.  
It allows a veterinary clinic to:
- Register customers (pet owners) and their animals
- Record vaccines for animals with protection period checks
- Register doctors and define their available working dates
- Create appointments by checking doctor availability and schedule conflicts


---

## 🚀 Technologies
- Java 21
- Spring Boot 3.5+
- Spring Data JPA
- PostgreSQL 16+
- Maven
- Lombok
- Postman (for API testing)

---

## 📂 Project Structure

`src/main/java/com/tolgademir/veterinarymanagementsystem`

├── **model** → Entity classes (`Animal`, `Customer`, `Doctor`, `Appointment`, `AvailableDate`, `Vaccine`)  
├── **repository** → Data access layer (JPA interfaces)  
├── **service** → Business logic & validation rules  
├── **controller** → REST API endpoints  
├── **exception** → Custom exceptions & global error handling  
└── **config** → Database and CORS configuration

---

## 👤 User Roles
- **Veterinary Employee**
    - Add / Update / Delete / List Customers
    - Add / Update / Delete / List Animals
    - Add / Update / Delete / List Doctors
    - Add / Delete Available Dates for Doctors
    - Create / Update / Delete Appointments
    - Record / Update / Delete Vaccines
    - Filter by name, doctor, animal, and date ranges

---

## 📌 Business Rules
- A doctor can only take appointments **on available dates**.
- A doctor **cannot have overlapping appointments** at the same time.
- A vaccine cannot be added if **an active vaccine of the same type exists** for the animal.
- Deleting a customer also deletes **all animals and related records**.
- All endpoints return **meaningful HTTP status codes** and **custom error messages**.
- Request validation and exception handling are properly implemented.

---

## 🗄️ Database Schema

**customers**
- id (PK)
- name
- phone
- mail
- address
- city

**animals**
- id (PK)
- name
- species
- breed
- gender
- colour
- date_of_birth
- customer_id (FK → customers.id)

**doctors**
- id (PK)
- name
- phone
- mail
- address
- city

**available_dates**
- id (PK)
- doctor_id (FK → doctors.id)
- available_date

**appointments**
- id (PK)
- doctor_id (FK → doctors.id)
- animal_id (FK → animals.id)
- appointment_date

**vaccines**
- id (PK)
- animal_id (FK → animals.id)
- name
- code
- protection_start_date
- protection_finish_date

---

## 🌱 Seed Data

Example test data from `seed_data.sql`:

### Customers
- Tolga Demir — Beylikdüzü, İstanbul
- Merve Aksoy — Kadıköy, İstanbul

### Animals
- Leo 🐈 — British Shorthair
- Mia 🐕 — Golden Retriever

### Doctors
- Dr. Gökhan Kandemir — Ataşehir
- Dr. Ayşe Yılmaz — Beşiktaş

### Appointments
- Leo → Dr. Gökhan (2025-10-25 14:00)
- Mia → Dr. Ayşe (2025-10-26 10:00)

### Vaccines
- Rabies → valid until 2025-01-01
- Distemper → valid until 2025-03-10

---

## ▶️ Run Instructions

1. Create PostgreSQL database:
   ```sql
   CREATE DATABASE veterinary_db;
   
2. Run schema.sql to create tables.

3. Run seed_data.sql to insert sample data.

4. Configure your database credentials in:

    ##### src/main/resources/application-vetdb.properties

5. Run the application:

    #### VeterinaryManagementSystemApplication

---

## 📖 Example API Flow
Create Appointment Example

POST /api/appointments

Content-Type: application/json

{

"doctor": { "id": 1 },

"animal": { "id": 1 },

"appointmentDate": "2025-10-25T14:00:00"

}

## ✅ Returns:

{

"id": 1,

"appointmentDate": "2025-10-25T14:00:00",

"doctor": { "id": 1, "name": "Dr. Gökhan Kandemir" },

"animal": { "id": 1, "name": "Leo" }

}

---

## 🧩 UML Diagram

Entity relationship diagram (ERD) illustrating the project structure is included:

### 📄 uml_diagram.png

---

## 📜 License

This project is part of Patika+ Back-End Developer Bootcamp and distributed under the MIT License.
You may use or modify it for educational purposes with proper credit.

---

## 👤 Author

Tolga Demir

Back-End Developer | Java | Spring Boot | PostgreSQL

[GitHub](https://github.com/tolgademir-co) · [LinkedIn](https://www.linkedin.com/in/tolgademir-co/)



