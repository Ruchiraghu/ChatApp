Here's an updated `README.md` based on the information that you don't have a `GroupController`:

---

# Console-Based Chat Application

## Overview

This is a **console-based chat application** that allows users to create accounts, send messages, and chat in real-time. The application supports both **one-on-one messaging** and **group chats** with a simple text-based interface. Users only need to create an account or add new users to begin chatting—no login is required.

## Features

- **User Creation**: Add users to the chat system without any login or authentication process.
- **Real-time Messaging**: Users can send and receive messages instantly.
- **Private Messaging**: Direct communication between two users.
- **Group Chat**: Users can chat in groups with multiple participants (managed manually in the `ChatRoomController`).
- **Message History**: View messages exchanged in private chats or group discussions.

## Technologies Used

- **Java 17**: Core language for building the application.
- **Hibernate (JPA)**: For handling database interactions and entity management.
- **MySQL**: Database to store user and message data.
- **Maven**: For project build management and dependency management.

## Project Structure

```
/src
  /controllers
    - UserController.java        # Handles user creation and management
    - MessageController.java     # Manages message operations between users
    - GroupController.java    # Manages group chats and chat rooms
  /services
    - UserService.java           # Logic related to user operations
    - MessageService.java        # Logic for sending and receiving messages
    - GroupService.java       # Logic for sending and receiving group messages
  /dao
    - UserDao.java               # Database operations for users
    - MessageDao.java            # Database operations for messages
    - groupDao.java           # Database operations for chat rooms
 /daoImpl
    - UserDaoImpl.java           # Implementation of UserDao
    - MessageDaoImpl.java        # Implementation of MessageDao
    - GroupDaoImpl.java       # Implementation of ChatRoomDao
  /entities
    - User.java                  # Entity representing a user
    - Message.java               # Entity representing a message
    - ChatRoom.java              # Entity representing a chat room
/exception_handling
    - UserNotFoundException.java  # Custom exception for user not found
    - MessageNotFoundException.java # Custom exception for message not found
    - ChatRoomNotFoundException.java # Custom exception for chat room not found
  /util
    - ScannerUtil.java         # Helper class for setting up Hibernate
/main
    -App.java                  # Entry point for the application
```

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Ruchiraghu/ChatApp.git
   cd ChatApp
   ```

2. **Set up the database**:
   - Install MySQL and create a database for the chat application.
   - Update the `hibernate.cfg.xml` with your MySQL connection details (URL, username, and password).
   - Run the provided SQL scripts to set up the necessary tables.

3. **Configure Maven**:
   - Build the project using Maven. Ensure you have Maven installed:
     ```bash
     mvn clean install
     ```

4. **Run the application**:
   ```bash
   java -jar target/chattingApp-1.0-SNAPSHOT.jar
   ```

## Usage

After running the application, you can interact with it through the console. Here's a guide to the available operations.

### Main Menu

```plaintext
1. User Management
2. Chat between two Users
3. Group chat
4. Exit
```

### User Management

```plaintext
1. Register a new user
2. Get user by ID
3. List all users
4. Update user
5. Delete user
6. Back to the main menu
```

### Chat Between Two Users

```plaintext
Enter user ID 1:
User ID 1: 
1
Enter user ID 2:
User ID 2: 
2
Chat started between ruchi and priya.
Type 'exit' to end the chat.
ruchi: hi
priya: hello
ruchi: exit
Chat ended. 
```

### Group Chat

```plaintext
1. Create group
2. Add Users to Group
3. View Messages by Group
4. Group Chat
5. View All Groups
6. Exit
```

## Database Configuration

- **MySQL Database**: Update your `persistence.xml` to include your database connection details:
  - `hibernate.connection.url`
  - `hibernate.connection.username`
  - `hibernate.connection.password`

The database schema will contain:
- **Users Table**: Stores user information.
- **Messages Table**: Stores messages sent between users and groups.
- **Groups Table**: Stores group information.

## Dependencies

The project uses Maven to manage dependencies. Key dependencies include:
- **Hibernate Core 5.6.15**: For ORM and database management.
- **MySQL Connector 8.0.33**: To connect to the MySQL database.

You can find these dependencies in the `pom.xml` file:
```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

## Future Enhancements

- **Message Encryption**: Add end-to-end encryption to secure messages.
- **User Search**: Allow users to search for other users by name or ID.
- **User Status**: Implement online/offline status indicators for users.

---

