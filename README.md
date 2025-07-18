# CourseModel — Тестовое задание


Полноценный микросервис на **Java (Spring Boot)** с фронтендом на **React**, реализующий модель курсов, преподавателей и студентов. Включает REST API, генерацию отчёта в Excel и веб-интерфейс для управления преподавателями.

---
# CourseModel

Приложение «CourseModel» сочетает в себе Java-микросервис на Spring Boot и простую панель на React для работы с моделями курсов, преподавателей и студентов. Включает генерацию отчёта в формате XLSX.

## Технологии

- Backend: Java 17, Spring Boot, Spring Data JPA, H2, Apache POI  
- Frontend: React, Axios, Bootstrap  
- Сборка: Maven, npm  

## Быстрый старт
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
Запустить фронтенд в режиме разработки  
   ```bash
   cd frontend
   npm install
   npm start
   ```
При необходимости собрать фронтенд для продакшена и встроить в бэкенд  
   ```bash
   cd frontend
   npm run build
   ──> скопировать build/ в backend/src/main/resources/static/
   cd ../backend
   mvn spring-boot:run
   ```

## Авторы

Бондарь Павел Сергеевич
Email: a159177716@gmail.com


Лисейчиков Роман Олегович
Email: farthouse12doge@gmail.com
