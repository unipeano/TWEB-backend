# TWEB (Web Technologies) Project - Backend

A Spring Boot backend application for managing recipes, ingredients, and recipe books. This project provides a RESTful API for user authentication, recipe management, and recipe collection organization.

## Features

- **User Management**: User registration and authentication with role-based access control (amateur, chef, admin)
- **Recipe Management**: Create, read, update, and delete recipes with descriptions and instructions
- **Ingredients**: Manage ingredients and their associations with recipes
- **Recipe Books**: Organize recipes into custom recipe books
- **Categories**: Categorize recipes for better organization
- **Session Management**: Secure cookie-based session handling

## Tech Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **ORM**: Spring Data JPA / Hibernate

## Project Structure

```
src/main/java/unito/tweb/projectbackend/
├── controllers/         # REST API endpoints
├── services/           # Business logic
├── persistence/        # JPA entities and repositories
├── dto/               # Data Transfer Objects
├── session/           # Session management
├── filter/            # Request filters (e.g., LoginFilter)
└── utils/             # Utility classes
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd project-backend
```

### 2. Configure Database

Create a PostgreSQL database:

```sql
CREATE DATABASE dbtweb;
```

### 3. Configure Application Properties

Copy the example configuration file:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `src/main/resources/application.properties` and set your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dbtweb
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

### 4. Initialize Database Schema

The database schema will be automatically created by Hibernate on first run (using `spring.jpa.hibernate.ddl-auto=update`). Alternatively, you can manually initialize using `db-init.sql`:

```bash
psql -U postgres -d dbtweb -f db-init.sql
```

### 5. Build and Run

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The server will start on `http://localhost:7777`

## API Endpoints

### Users
- `POST /users/register` - Register a new user
- `POST /login` - User login
- `GET /users/{id}` - Get user details

### Recipes
- `GET /recipes` - List all public recipes
- `POST /recipes` - Create a new recipe
- `GET /recipes/{id}` - Get recipe details
- `PUT /recipes/{id}` - Update a recipe
- `DELETE /recipes/{id}` - Delete a recipe

### Recipe Books
- `GET /recipe-books` - List user's recipe books
- `POST /recipe-books` - Create a new recipe book
- `GET /recipe-books/{id}` - Get recipe book details
- `POST /recipe-books/{id}/recipes` - Add recipe to book

### Ingredients
- `GET /ingredients` - List all ingredients
- `POST /ingredients` - Create a new ingredient

## Configuration

### Server Port
Default port is `7777`. Change it in `application.properties`:
```properties
server.port=8080
```

### Database DDL Strategy
- `create` - Drop tables and recreate on startup
- `create-drop` - Drop tables after application stops
- `update` - Update existing schema (default)
- `validate` - Only validate schema

## Security

- Session cookies are HTTP-only and secure
- Passwords are handled through the User entity
- Role-based access control is supported

## Development

### IDE Setup

**IntelliJ IDEA**: Open the project directly, Maven will auto-download dependencies.

**VS Code**: Install relevant extensions for Java/Spring Boot development.

### Running Tests

```bash
./mvnw test
```

## Database Schema

The application uses the following main entities:

- **Users**: User accounts with roles and profiles
- **Recipes**: Recipe information with instructions and user references
- **Ingredients**: Available ingredients database
- **RecipeIngredients**: Association between recipes and ingredients (with quantities)
- **RecipeBooks**: User-created recipe collections
- **RecipeBookRecipes**: Recipes contained in recipe books
- **Categories**: Recipe categories
- **RecipeCategories**: Association between recipes and categories

## Contributing

1. Create a feature branch (`git checkout -b feature/AmazingFeature`)
2. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
3. Push to the branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions, please open an issue in the repository.

## Authors

University of Turin - TWEB Project
