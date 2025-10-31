# 🐾 Veterinary Management System

A RESTful **Veterinary Management System** built with **Spring Boot (Java 21)** and **PostgreSQL**.  
The project follows **layered architecture** principles and provides CRUD operations for managing  
customers, animals, doctors, available dates, vaccines, and appointments.

---


## 🚀 Technologies

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL 17**
- **Lombok**
- **ModelMapper**
- **Postman** (for API testing)
- **Maven**

---

## 📂 Project Structure
```
VeterinaryManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/com/tolgademir/veterinarymanagementsystem/
│   │   │   ├── controller/        # REST API endpoints
│   │   │   ├── service/           # Business logic layer
│   │   │   ├── repository/        # Data access layer (DAO)
│   │   │   ├── model/             # Entities (Animal, Customer, Doctor, etc.)
│   │   │   └── exception/         # Custom exceptions
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql         # Database structure
│   │       ├── seed_data.sql      # Initial test data
│   │       └── postman/           # Postman collection (API tests)
│   └── test/                      # Unit tests (optional)
│
├── pom.xml
└── README.md
```
---

## 🧩 Entities & Relationships

| Entity            | Description                        | Relationships                                    |
| ----------------- | ---------------------------------- | ------------------------------------------------ |
| **Customer**      | Pet owner information              | One-to-Many → Animals                            |
| **Animal**        | Pet details                        | Many-to-One → Customer<br>One-to-Many → Vaccines |
| **Vaccine**       | Vaccination records                | Many-to-One → Animal                             |
| **Doctor**        | Veterinarian info                  | One-to-Many → AvailableDates                     |
| **AvailableDate** | Doctor’s available working dates   | Many-to-One → Doctor                             |
| **Appointment**   | Animal’s appointment with a doctor | Many-to-One → Doctor<br>Many-to-One → Animal     |


---

## 🧱 Database Schema

📄 **File:** [`src/main/resources/schema.sql`](./src/main/resources/schema.sql)

```sql
CREATE TABLE customers
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100),
    phone   VARCHAR(20),
    mail    VARCHAR(100),
    address VARCHAR(255),
    city    VARCHAR(100)
);
```

---

## 🌱 Seed Data

**File:** [`src/main/resources/seed_data.sql`](./src/main/resources/seed_data.sql)

#### The file includes at least 5 sample records for each table:

- 5 Customers

- 5 Animals

- 5 Doctors

- 5 Available Dates

- 5 Appointments

- 5 Vaccines

---

## 🔁 Database Reset Script

If you want to clear all data and start fresh before re-importing **seed_data.sql:**

```sql
TRUNCATE TABLE
    appointments,
    available_dates,
    vaccines,
    animals,
    doctors,
    customers
RESTART IDENTITY CASCADE;
```

---

## 📫 API Endpoints (Overview)

| Module            | Method | Endpoint                        | Description                     |
| ----------------- | ------ | ------------------------------- | ------------------------------- |
| **Customer**      | POST   | `/api/customers`                | Create customer                 |
|                   | GET    | `/api/customers`                | Get all customers               |
|                   | GET    | `/api/customers/{id}`           | Get by ID                       |
|                   | PUT    | `/api/customers/{id}`           | Update customer                 |
|                   | DELETE | `/api/customers/{id}`           | Delete customer                 |
| **Animal**        | POST   | `/api/animals`                  | Create animal                   |
|                   | GET    | `/api/animals`                  | Get all animals                 |
|                   | GET    | `/api/animals/name/{name}`      | Filter by name                  |
|                   | GET    | `/api/animals/customer/{id}`    | Filter by owner                 |
| **Doctor**        | POST   | `/api/doctors`                  | Add doctor                      |
|                   | GET    | `/api/doctors`                  | List doctors                    |
| **AvailableDate** | POST   | `/api/available-dates`          | Add available day               |
| **Appointment**   | POST   | `/api/appointments`             | Create appointment              |
|                   | GET    | `/api/appointments`             | Get all appointments            |
|                   | GET    | `/api/appointments/doctor/{id}` | Filter by doctor & date range   |
|                   | GET    | `/api/appointments/animal/{id}` | Filter by animal & date range   |
| **Vaccine**       | POST   | `/api/vaccines`                 | Add vaccine                     |
|                   | GET    | `/api/vaccines`                 | List all vaccines               |
|                   | GET    | `/api/vaccines/animal/{id}`     | List by animal                  |
|                   | GET    | `/api/vaccines/protection`      | Filter by protection date range |

---

## ⚙️ Business Rules

### ✅ Appointment Rules
- A doctor cannot take appointments on a day they are not available.
- A doctor cannot have more than one appointment at the same date & time.
- Throws: `BusinessRuleException`, `ResourceNotFoundException`

### ✅ Vaccine Rules
- A new vaccine cannot be added if another active vaccine with the same code exists for the same animal.
- Throws: `DuplicateRecordException`

### ✅ Cascade Rules
- When a Customer is deleted → all related Animals, Vaccines, and Appointments are deleted automatically (Cascade).

### ✅ Global Exception Handling
- Custom exceptions:
    - `ResourceNotFoundException`
    - `DuplicateRecordException`
    - `BusinessRuleException`

---

## 🧾 Postman Collection

All endpoints are documented and tested in Postman.
- Collection file: **VeterinaryManagementSystem.postman_collection.json**

Import this file into Postman to test all CRUD operations directly.

---

## ▶️ Run Instructions

1️⃣ Create Database

```sql
CREATE DATABASE veterinary_db;
```

2️⃣ Configure Connection
```
Update your application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/veterinary_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```
3️⃣ Run Schema & Seed
```sql
\i 'src/main/resources/schema.sql';
\i 'src/main/resources/seed_data.sql';
```
4️⃣ Start Spring Boot App
```
In IntelliJ:

Run → VeterinaryManagementSystemApplication
```
Server starts at:
👉 http://localhost:8080

---

## 🧪 Postman API Test Guide (Short Version)

Bu rehber, projenin temel API uç noktalarını hızlıca test etmek için hazırlanmıştır.  
Tüm isteklerin Base URL’si: http://localhost:8080/api

---

### 🐾 1️⃣ Customer Testleri

**Yeni müşteri ekle (POST)**

`POST /api/customers`
```json
{
  "name": "Tolga Demir",
  "phone": "05321234567",
  "mail": "tolga@example.com",
  "address": "Beylikdüzü, İstanbul",
  "city": "İstanbul"
}
```
**Tüm müşterileri listele (GET)**

`GET /api/customers`

---

### 🐶 2️⃣ Animal Testi

**Yeni hayvan ekle (POST)**

`GET /api/animals`
```json
{
"name": "Leo",
"species": "Cat",
"breed": "British Shorthair",
"gender": "Male",
"colour": "Gray",
"dateOfBirth": "2020-05-15",
"customerId": 1
}
```

---

### 🩺 3️⃣ Doctor & AvailableDate

**Yeni doktor ekle (POST)**

`POST /api/doctors`
```json
{
"name": "Dr. Selin Yılmaz",
"phone": "05321230000",
"mail": "selin.yilmaz@vetclinic.com",
"address": "Kadıköy, İstanbul",
"city": "İstanbul"
}
```
**Doktora uygun gün ekle (POST)**

`POST /api/available-dates`
```json
{
"availableDate": "2025-11-02",
"doctorId": 1
}
```

---

### 📅 4️⃣ Appointment Testleri

**Yeni randevu oluştur (POST)**

`POST /api/appointments`
```json
{
"appointmentDate": "2025-11-05T10:00:00",
"doctorId": 1,
"animalId": 1
}
```
**Randevu çakışma testi (aynı tarih–saat)**
```json
{
"appointmentDate": "2025-11-05T10:00:00",
"doctorId": 1,
"animalId": 2
}
```

### 🧠 Beklenen sonuç:

- 400 Bad Request
- **"Bu doktorun bu tarih ve saatte başka bir randevusu bulunmaktadır!"**

---

### 💉 5️⃣ Vaccine Testleri

**Yeni aşı ekle (POST)**

`POST /api/vaccines`
```json
{
"code": "KDZ-001",
"name": "Kuduz Aşısı",
"protectionStartDate": "2025-10-01",
"protectionFinishDate": "2026-04-01",
"animalId": 1
}
```
**Aynı isimli aşı ekleme (koruyuculuk kontrolü)**
```json
{
"code": "KDZ-002",
"name": "Kuduz Aşısı",
"protectionStartDate": "2025-12-01",
"protectionFinishDate": "2026-06-01",
"animalId": 1
}
```
### 🧠 Beklenen sonuç:
- 400 Bad Request
- **"Bu hayvan için aynı isimli aşının koruyuculuk süresi hâlâ devam ediyor!"**

---

| Test        | Amaç                        | Beklenen Durum                  |
| ----------- | --------------------------- | ------------------------------- |
| Appointment | Çakışma kontrolü            | 400 – BusinessRuleException     |
| Vaccine     | Koruyuculuk süresi kontrolü | 400 – BusinessRuleException     |
| Vaccine     | Aynı kod                    | 409 – DuplicateRecordException  |
| Resource    | Bulunmayan ID               | 404 – ResourceNotFoundException |

---

### 📌 Not:
Tüm GET istekleri başarılı dönüyor ve hata durumlarında
**timestamp**, **status**, **error**, **message**, **path** alanları görünüyorsa proje sorunsuz çalışıyor demektir ✅

---

## 🧠 UML Diagram
![UML Diagram](./uml-diagram.png)

---

## ⚠️ Error Handling & Business Rules

Bu projede hatalar, özel **Exception sınıfları** ve **Global Exception Handler** aracılığıyla merkezi olarak yönetilir.  
Tüm hatalar API kullanıcılarına standart JSON formatında döner.

### 🔧 Exception Yapısı
| Exception | Açıklama | HTTP Kodu |
|------------|-----------|-----------|
| `ResourceNotFoundException` | İstenilen kayıt bulunamadığında | `404 Not Found` |
| `DuplicateRecordException` | Aynı verinin tekrar eklenmeye çalışılması durumunda | `409 Conflict` |
| `BusinessRuleException` | İş kuralı ihlali durumlarında (örnek: randevu çakışması, aşı koruyuculuk kontrolü) | `400 Bad Request` |

---

### 🧠 Örnek JSON Hata Yanıtı

```json
{
  "timestamp": "2025-10-31T22:14:10.992",
  "status": 400,
  "error": "Business Rule Violation",
  "message": "Bu doktorun bu tarih ve saatte başka bir randevusu bulunmaktadır!",
  "path": "/api/appointments"
}
```

---

## 💉 Örnek İş Kuralları

#### 🩺 AppointmentService (Randevu Çakışması)
- Aynı doktora aynı tarih-saatte ikinci randevu oluşturulamaz.
- Kural ihlali durumunda BusinessRuleException fırlatılır.

#### 💊 VaccineService (Aşı Koruyuculuk Süresi)
- Aynı hayvana, koruyuculuk bitmeden aynı isimli aşı eklenemez.
- Aynı kodlu aşı var ise DuplicateRecordException fırlatılır.

---

## 🧾 Global Exception Handler

Tüm hatalar **@RestControllerAdvice** üzerinden yönetilir.

#### Her hata yanıtı şu bilgileri içerir:
- **timestamp** → hata zamanı
- **status** → HTTP durum kodu
- **error** → hata tipi
- **message** → hata mesajı
- **path** → istek yapılan endpoint

---

## 🧩 Örnek Başarılı Yanıt
```json
{
"id": 1,
"appointmentDate": "2025-11-05T10:00:00",
"doctorId": 3,
"animalId": 5
}
```
---

## ✅ Özet

#### Bu yapı sayesinde proje:

- Katmanlı mimariyi korur,
- İş kurallarını uygular,
- Tüm hataları REST standartlarına uygun şekilde döndürür,
- Kullanıcıya açık, tutarlı API çıktısı sağlar.

---

## 🧑‍💻 Author
**Tolga Demir**

**Back-End Developer | Java | Spring Boot | PostgreSQL**
- [GitHub](https://github.com/tolgademir-co)
- [LinkedIn](https://www.linkedin.com/in/tolgademir-co/)

---

## 📜 License

- This project is licensed under the MIT License.
- You are free to use, modify, and distribute it with attribution.