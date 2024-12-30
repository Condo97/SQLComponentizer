# SQLComponentizer

Build SQL statements with simple components! SQLComponentizer provides a streamlined and modular approach to constructing SQL queries and managing database interactions in Java. By leveraging a component-based architecture and annotations for serialization, you can effortlessly create, execute, and manage SQL statements while ensuring resource safety and maintainability.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Building SQL Statements](#building-sql-statements)
    - [Select Statement Example](#select-statement-example)
    - [Insert Statement Example](#insert-statement-example)
    - [Update Statement Example](#update-statement-example)
    - [Delete Statement Example](#delete-statement-example)
  - [Serializing and Deserializing Objects](#serializing-and-deserializing-objects)
- [Database Client](#database-client)
- [Annotations](#annotations)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Component-Based SQL Construction**: Easily build complex SQL statements by combining simple, reusable components.
- **Automatic Resource Management**: Prepared statements are automatically closed after execution, preventing resource leaks.
- **Object Serialization**: Serialize and deserialize Java objects to and from database records using annotations.
- **Support for CRUD Operations**: Simplifies **Create**, **Read**, **Update**, and **Delete** operations with dedicated builders.
- **Flexible Query Building**: Supports various SQL clauses including SELECT, INSERT, UPDATE, DELETE, WHERE, ORDER BY, and more.
- **Enum Handling**: Custom annotations for handling enums during serialization and deserialization.

## Getting Started

### Prerequisites

- **Java 8 or higher**
- **A JDBC-compatible Database**: Ensure you have access to a database (e.g., MySQL, PostgreSQL).
- **Maven or Gradle**: For dependency management (optional, based on your setup).

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Condo97/sqlcomponentizer.git
   cd sqlcomponentizer
   ```

2. **Add to Your Project**

   **Not available through maven yet**
Please manually add this library to your project, or build for maven local and reference it in your build.gradle file.

## Usage

### Building SQL Statements

SQLComponentizer uses builders to create different types of SQL statements. Here's how you can utilize them:

#### Select Statement Example

```java
import sqlcomponentizer.preparedstatement.statement.SelectComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

public class SelectExample {
    public static void main(String[] args) {
        ComponentizedPreparedStatement selectStmt = SelectComponentizedPreparedStatementBuilder
            .forTable("users")
            .select("id", "name", "email")
            .where("age", SQLOperators.GREATER_THAN, 18)
            .orderBy(OrderByComponent.Direction.ASC, "name")
            .limit(10)
            .build();
        
        System.out.println(selectStmt);
        // Outputs: SELECT id, name, email FROM users WHERE age > ? ORDER BY name ASC;
    }
}
```

#### Insert Statement Example

```java
import sqlcomponentizer.preparedstatement.statement.InsertIntoComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

public class InsertExample {
    public static void main(String[] args) {
        ComponentizedPreparedStatement insertStmt = InsertIntoComponentizedPreparedStatementBuilder
            .forTable("users")
            .addColAndVal("name", "John Doe")
            .addColAndVal("email", "john.doe@example.com")
            .addColAndVal("age", 30)
            .build(true); // `true` to retrieve generated keys
        
        System.out.println(insertStmt);
        // Outputs: INSERT INTO users (name, email, age) VALUES (?, ?, ?);
    }
}
```

#### Update Statement Example

```java
import sqlcomponentizer.preparedstatement.statement.UpdateComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

public class UpdateExample {
    public static void main(String[] args) {
        ComponentizedPreparedStatement updateStmt = UpdateComponentizedPreparedStatementBuilder
            .forTable("users")
            .set("email", "new.email@example.com")
            .where("id", SQLOperators.EQUAL, 1)
            .build();
        
        System.out.println(updateStmt);
        // Outputs: UPDATE users SET email = ? WHERE id = ?;
    }
}
```

#### Delete Statement Example

```java
import sqlcomponentizer.preparedstatement.statement.DeleteComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

public class DeleteExample {
    public static void main(String[] args) {
        ComponentizedPreparedStatement deleteStmt = DeleteComponentizedPreparedStatementBuilder
            .forTable("users")
            .where("id", SQLOperators.EQUAL, 1)
            .build();
        
        System.out.println(deleteStmt);
        // Outputs: DELETE FROM users WHERE id = ?;
    }
}
```

### Serializing and Deserializing Objects

SQLComponentizer allows you to serialize Java objects to database records and deserialize database records back to Java objects using annotations.

#### Annotations

- **@DBSerializable**: Marks a class as serializable to a database table.
- **@DBColumn**: Marks a field as a database column.
- **@DBSubObject**: Marks a field as a sub-object that is also serializable.
- **@DBEnumGetter & @DBEnumSetter**: Handles custom enum serialization and deserialization.

#### Example

```java
import sqlcomponentizer.dbserializer.DBSerializable;
import sqlcomponentizer.dbserializer.DBColumn;
import sqlcomponentizer.dbserializer.DBSubObject;
import sqlcomponentizer.dbserializer.DBEnumGetter;
import sqlcomponentizer.dbserializer.DBEnumSetter;

@DBSerializable(tableName = "users")
public class User {
    
    @DBColumn(name = "id", primaryKey = true)
    private Integer id;
    
    @DBColumn(name = "name")
    private String name;
    
    @DBColumn(name = "email")
    private String email;
    
    @DBColumn(name = "status")
    private Status status;
    
    // Getters and Setters
    
    public enum Status {
        ACTIVE,
        INACTIVE;
        
        @DBEnumGetter
        public String getStatus() {
            return this.name();
        }
        
        @DBEnumSetter
        public static Status setStatus(String status) {
            return Status.valueOf(status);
        }
    }
}
```

#### Serialization Example

```java
import sqlcomponentizer.dbserializer.DBSerializer;

public class SerializeExample {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setStatus(User.Status.ACTIVE);
        
        Map<String, Object> tableMap = DBSerializer.getTableMap(user);
        System.out.println(tableMap);
        // Outputs: {id=1, name=Jane Doe, email=jane.doe@example.com, status=ACTIVE}
    }
}
```

#### Deserialization Example

```java
import sqlcomponentizer.dbserializer.DBDeserializer;

public class DeserializeExample {
    public static void main(String[] args) throws Exception {
        Map<String, Object> tableMap = Map.of(
            "id", 2,
            "name", "John Smith",
            "email", "john.smith@example.com",
            "status", "INACTIVE"
        );
        
        User user = DBDeserializer.createObjectFromMap(User.class, tableMap);
        System.out.println(user.getName()); // Outputs: John Smith
    }
}
```

## Database Client

SQLComponentizer provides a `DBClient` class to handle the execution of queries and updates seamlessly. It ensures that `PreparedStatement` objects are properly closed after operations, promoting efficient resource management.

### Query Example

```java
import sqlcomponentizer.DBClient;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

public class QueryExample {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:yourdb://localhost:3306/yourdb", "username", "password");
        
        ComponentizedPreparedStatement selectStmt = SelectComponentizedPreparedStatementBuilder
            .forTable("users")
            .select("id", "name", "email")
            .where("status", SQLOperators.EQUAL, "ACTIVE")
            .build();
        
        List<Map<String, Object>> results = DBClient.query(connection, selectStmt);
        
        results.forEach(row -> System.out.println(row));
    }
}
```

### Update Example with Generated Keys

```java
import sqlcomponentizer.DBClient;
import sqlcomponentizer.preparedstatement.statement.InsertIntoComponentizedPreparedStatementBuilder;
import sqlcomponentizer.preparedstatement.ComponentizedPreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

public class InsertWithKeysExample {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:yourdb://localhost:3306/yourdb", "username", "password");
        
        ComponentizedPreparedStatement insertStmt = InsertIntoComponentizedPreparedStatementBuilder
            .forTable("users")
            .addColAndVal("name", "Alice")
            .addColAndVal("email", "alice@example.com")
            .addColAndVal("status", "ACTIVE")
            .build(true); // Retrieve generated keys
        
        List<Map<String, Object>> generatedKeys = DBClient.updateReturnGeneratedKeys(connection, insertStmt);
        
        generatedKeys.forEach(key -> System.out.println(key));
    }
}
```

## Annotations

SQLComponentizer leverages Java annotations to facilitate the serialization and deserialization of objects to and from database records.

### `@DBSerializable`

Marks a class as serializable to a database table.

**Parameters:**

- `tableName`: Name of the database table.

**Example:**

```java
@DBSerializable(tableName = "users")
public class User {
    // Fields and methods
}
```

### `@DBColumn`

Marks a field as a database column.

**Parameters:**

- `name`: Name of the column in the database.
- `primaryKey`: (Optional) Indicates if the column is a primary key. Default is `false`.

**Example:**

```java
@DBColumn(name = "id", primaryKey = true)
private Integer id;
```

### `@DBSubObject`

Marks a field as a sub-object that is also serializable.

**Example:**

```java
@DBSubObject
private Address address;
```

### `@DBEnumGetter` & `@DBEnumSetter`

Handles custom enum serialization and deserialization.

**Example:**

```java
public enum Status {
    ACTIVE,
    INACTIVE;
    
    @DBEnumGetter
    public String getStatus() {
        return this.name();
    }
    
    @DBEnumSetter
    public static Status setStatus(String status) {
        return Status.valueOf(status);
    }
}
```

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
2. **Create a new branch**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Commit your changes**

   ```bash
   git commit -m "Add your feature"
   ```

4. **Push to the branch**

   ```bash
   git push origin feature/YourFeature
   ```

5. **Open a Pull Request**

Please ensure your code follows the project's coding standards and includes relevant tests.

## License

This project is licensed under the [MIT License](LICENSE).
