# Employee Information

A small full-stack app for managing employee information.

- **Backend** — Java 17, Spring Boot 3.2, Maven (`backend/`)
- **Frontend** — Angular 16 (`frontend/`)

The first slice is a **login page** wired end to end.

## Prerequisites

| Tool | Version used |
|------|--------------|
| JDK  | 17           |
| Node | 18+ (20 LTS recommended) |
| npm  | 9+           |

Maven is **not** required globally — the backend ships with the Maven Wrapper
(`mvnw` / `mvnw.cmd`).

## Run the backend

```bash
cd backend
./mvnw spring-boot:run        # Windows: mvnw spring-boot:run
```

API starts on <http://localhost:8080>.

### Login endpoint

```
POST /api/auth/login
Content-Type: application/json

{ "username": "admin", "password": "admin123" }
```

Success returns `200` with `{ token, username, fullName }`; bad credentials
return `401` with `{ message }`.

Seed users (in-memory, see `AuthService`):

| Username | Password  | Name               |
|----------|-----------|--------------------|
| `admin`  | `admin123`| Site Administrator |
| `jdoe`   | `password`| Jane Doe           |

### Employee endpoints

```
GET  /api/employees      -> 200 [ { id, firstName, lastName, email, department, jobTitle }, ... ]
POST /api/employees      -> 201 { id, ... }   (400 if any field is blank / email invalid)
```

Employees are held in memory (`EmployeeStore`) and sorted by last name.

## Run the frontend

```bash
cd frontend
npm install        # first time only
npm start
```

App starts on <http://localhost:4200> and calls the API at
`http://localhost:8080/api` (configured in `src/environments/environment.ts`).
CORS for the dev server is allowed in `backend/.../config/WebConfig.java`.

## Project layout

```
backend/
  src/main/java/com/example/employeeinfo/
    EmployeeInfoApplication.java
    config/WebConfig.java          # CORS for the Angular dev server
    auth/                          # login request/response, service, controller
    employee/                      # Employee, EmployeeRequest, EmployeeStore, EmployeeController
  src/test/java/...                # AuthService + EmployeeStore unit tests
frontend/
  src/app/
    login/                         # login page (reactive form)
    dashboard/                     # add-employee form + employee list
    core/                          # AuthService, EmployeeService, route guard
```

## Tests

```bash
cd backend  && ./mvnw test
cd frontend && npm test
```

## Next steps

- Replace in-memory auth with a real user store + password hashing
- Issue a real JWT and validate it on protected endpoints
- Persist employees to a database (JPA entity + repository)
- Add edit / delete for employees
