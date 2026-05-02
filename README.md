# GREATER EVENTS - REST API

## Grupo: Arthur T. V. — Rodrigo Ruiz Benavides

---

### OVERVIEW

Backend de clase mundial para gestion de eventos musicales y artistas. Construido con las tecnologias mas avanzadas del ecosistema Java. PDyC 2026 - UNNOBA.

---

### TECH STACK

| Componente | Tecnologia | Version |
|------------|------------|---------|
| Language | Java | 17 LTS |
| Framework | Spring Boot | 3.5.14 |
| Persistencia | Spring Data JPA | - |
| Database | PostgreSQL | 16 |
| ORM | Hibernate | 6.6.49 |
| Build Tool | Maven | - |

---

### FEATURES

- CRUD operations para Artistas y Eventos
- Relacion Many-to-Many entre entidades
- Estado machine para eventos: tentative -> confirmed -> rescheduled -> cancelled
- Date validations (no past events)
- Business rules por estado
- Endpoints under /admin/ namespace
- Soft delete para artistas con historial

---

### QUICK START

#### Prerrequisitos

- JDK 17+
- PostgreSQL 16 running

#### Setup

```bash
# Clone the repository
git clone https://github.com/44ArThur44/pdyc2026-junin.git
cd pdyc2026-junin

# Create database
sudo -u postgres psql -c "CREATE DATABASE pdyc2026;"

# Configure credentials in src/main/resources/application.properties

# Run the application
./mvnw spring-boot:run
