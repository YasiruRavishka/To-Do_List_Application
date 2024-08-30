# Simple TO-DO List Application
### Installation
1. **Clone the Repository**
   ```bash
   git clone https://github.com/YasiruRavishka/To-Do_List_Application.git

2. **Set Up the Database**
   ```mysql
    CREATE DATABASE todo_list;
   
    USE todo_list;

    CREATE TABLE task(id int(5) PRIMARY KEY,
      title VARCHAR(100) NOT NULL,
      description VARCHAR(250),
      completion_date DATE NOT NULL);

    CREATE TABLE task_status(id int NOT NULL,
      is_completed BOOLEAN NOT NULL,
      CONSTRAINT FOREIGN KEY(id) REFERENCES task(id));
   
    CREATE TABLE task_next_index(pk int(1) PRIMARY KEY,
      next_index int NOT NULL);
   
    INSERT INTO task_next_index VALUES(1,1);

3. **Configure Database Connection**

Update the database connection details in the project to match your MySQL server configuration.
```
  ./src/main/java/database/DBConnection.java
