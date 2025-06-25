Этот проект представляет собой REST-сервис на базе Spring Boot для управления банковскими картами. Для удобного запуска используется Docker Compose c двумя контейнерами:

* **db** – PostgreSQL база данных
* **app** – само Spring Boot приложение

## Быстрый старт

1. Убедитесь, что на машине установлены `docker` и `docker-compose`.
2. Выполните скрипт, который создаст необходимые секреты и запустит контейнеры:

```bash
bash scripts/start-docker.sh
```

Скрипт создаст каталог `secrets` со стандартными значениями паролей, соберёт приложение и поднимет сервисы в фоне.

После запуска приложение будет доступно по адресу [http://localhost:8080](http://localhost:8080).

## Остановка контейнеров

Для остановки и удаления контейнеров выполните:

```bash
docker-compose down
```

## Запуск вручную

Если необходимо изменить значения секретов, создайте файлы в каталоге `secrets` перед запуском:

```
secrets/
├── admin_password
├── admin_user
├── db_password
└── jwt_secret
```

После этого можно запустить контейнеры командой:

```bash
docker-compose up -d --build
```

## Структура проекта

Код приложения находится в каталоге `src/main/java/com/example/bankcards` и разделён на пакеты по зонам ответственности:

- `BankcardsApplication` – точка входа в Spring Boot приложение.
- `controller` – REST-контроллеры.
    - `AuthController` выдаёт JWT токены и регистрирует пользователей.
    - `CardController` содержит операции над картами.
    - `UserController` управляет пользователями.
- `service` – бизнес-слой.
    - `UserService` сохраняет и ищет пользователей.
    - `CardService` управляет картами и балансом.
    - `CardExpiryService` помечает просроченные карты.
- `repository` – интерфейсы JPA.
    - `UserRepository` и `CardRepository` обращаются к БД.
- `entity` – JPA-сущности (`User`, `Card`, `Role`, `CardStatus`).
- `dto` – объекты передачи данных для API.
- `security` – конфигурация безопасности.
    - `SecurityConfig` настраивает фильтры и CORS.
    - `JwtAuthenticationFilter` извлекает пользователя из токена.
    - `JwtTokenProvider` создаёт и разбирает JWT.
    - `AppUserDetailsService` адаптирует `UserService` для Spring Security.
- `converter` – вспомогательные конвертеры JPA (`CardNumberEncryptConverter` шифрует номера карт).
- `config` – прочие конфигурации (`CorsConfig`, `AdminInitializer`, `OpenApiConfig`).
- `aspect` – аспект логирования (`LoggingAspect`).
- `exception` – пользовательские исключения и `GlobalExceptionHandler`.
- `util` – утилитарные классы (`CardNumberGenerator`, `CardMaskUtil`).

Дополнительные каталоги проекта:

- `src/main/resources` – конфигурационные файлы приложения и миграции Liquibase.
- `scripts` – утилиты для подготовки окружения и запуска контейнеров.
- `docs` – описание API в формате OpenAPI.
- `src/test` – модульные тесты.

### Создание карт пользователем

Рядовой пользователь может отправить запрос `POST /api/cards/createCard` для генерации новой
банковской карты. Созданная карта появится в списке `/api/cards/my` и будет
доступна во всех операциях пополнения и переводов.