MapperProject

A small Spring Boot practice project for learning MapStruct — mapping JPA entities to DTOs without writing conversion code by hand.
---
Stack
Java 26 · Spring Boot 4.0.7 · MapStruct 1.6.3 · Lombok · MySQL · Maven
---
What it does
A basic Employee CRUD API. Employees have a name and a department (DEVELOPERS, HR, OPERATIONS), stored in MySQL and exposed as DTOs through a MapStruct mapper.
---
Method	Path
GET	/api/employees
GET	/api/employees/{id}
POST	/api/employees
---
MapStruct concepts used
Entity → DTO and DTO → Entity mapping
Combining fields (firstName + lastName → fullName)
List mapping
Partial updates with @MappingTarget — a PATCH only changes the fields you send
