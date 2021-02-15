## List Application

Spring Boot version of my list application with Angular for the frontend.

#### Technologies / Tools

- Spring Boot
- Hibernate
- Swagger
- Angular
- MySQL
- Liquibase

#### Swagger UI

Swagger UI is available at `http://127.0.0.1:8080/swagger-ui.html`

#### Development

The application is available on port ``8080`` after executing ``mvn spring-boot:run`` for the list-server module.

#### E2E testing

Run the integration tests by building without the **skip-it** profile:

```
mvn clean install -P-skip-it
```

Generate Serenity reports by executing:

```
mvn serenity:aggregate
```

#### Liquibase

Execute a **liquibase-goal** (status, update et.al.) for list-server module by:

```
mvn resources:resources liquibase:liquibase-goal
```

The main changelog files (``changelog-ddl.xml``, ``changelog-dml.xml``) contain the scripts needed to create the database
schema along with some needed data. ``changelog-it.xml`` contains data needed for the integration tests.