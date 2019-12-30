## List Application

Spring Boot version of my list application with Angular for the frontend.

#### Technologies / Tools

<ul>
<li>Spring Boot</li>
<li>Hibernate</li>
<li>Swagger</li>
<li>Angular</li>
<li>MySQL</li>
<li>Liquibase</li>
</ul>

#### Swagger UI

Swagger UI is available at `http://127.0.0.1:8080/swagger-ui.html`

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

Execute a **liquibase-goal** (status, update et.al.) by:

```
mvn resources:resources liquibase:liquibase-goal
```