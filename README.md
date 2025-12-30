# Задание 1
- В файле TASK1_BUGS.md отображены баги, которые были найдены в задании номер 1.
# Задание 2.1
- В файлах TESTCASES.MD и BUGS.MD отображены тест-кейсы и баги задания 2.1 
- Автотесты для задания 2.1 находятся: src/test/java/api
# API Tests for Avito QA Internship  
Автоматизированные тесты для сервиса объявлений  
Host: https://qa-internship.avito.com

---

## Описание проекта

Проект содержит автоматизированные тесты для проверки четырёх API-методов микросервиса:

1. **Создать объявление** — `POST /api/1/item`
2. **Получить объявление по ID** — `GET /api/1/item/{id}`
3. **Получить все объявления по sellerId** — `GET /api/1/{sellerId}/item`
4. **Получить статистику по объявлению** — `GET /api/1/statistic/{id}`

Сервис хранит объявления, каждое имеет собственный уникальный ID.  
Тестовая система написана на **Java 17 + Maven + JUnit5 + Rest Assured**.

---

## Требования

Перед запуском убедитесь, что установлено:

- **Java 17+**
- **Maven 3.8+**
- Интернет-доступ к хосту `qa-internship.avito.com`

## Проверка:

```bash
java -version
mvn -version

## Установка проекта

Склонируйте репозиторий:

git clone <URL репозитория>
cd avito

Обновите зависимости:

mvn clean install

Запуск тестов
Запустить все тесты:
mvn clean test

Запуск конкретного класса:
mvn -Dtest=ItemApiTest test

Запуск конкретного теста:
mvn -Dtest=GetSellerItemsNegativeTests#TC17_massItems test
