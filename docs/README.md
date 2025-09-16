# Barcelona Chatbot

Barcelona is a simple interactive task management chatbot that runs in your terminal or GUI.  

It allows users to add, update, search, and delete tasks, while saving them persistently to a storage file.

---

## Features

- Maintain a personal **task list** with file persistence.
- Supports multiple task types:
    - **Todo** – simple tasks.
    - **Deadline** – tasks with a due date.
    - **Event** – tasks scheduled between two times.
- Mark and unmark tasks as completed.
- Delete tasks when they are no longer needed.
- Search tasks by keyword.
- Change the storage location and load tasks from a different file.

---

## Supported Commands

| Command    | Format                                    | Description                                              |
|------------|-------------------------------------------|----------------------------------------------------------|
| **BYE**    | `bye`                                     | Exit the application.                                    |
| **LIST**   | `list`                                    | Display all tasks currently stored.                      |
| **MARK**   | `mark <index>`                            | Mark the task at the given index as completed.           |
| **UNMARK** | `unmark <index>`                          | Mark the task at the given index as not done.            |
| **TODO**   | `todo <description>`                      | Add a Todo task.                                         |
| **DEADLINE** | `deadline <desc> /by <due_date>`        | Add a Deadline task. <br/>(due_date format: DD/MM/YYYY HHmm)  |
| **EVENT**  | `event <desc> /from <start> /to <end>`    | Add an Event task. <br/>(start & end format: DD/MM/YYYY HHmm) |
| **DELETE** | `delete <index>`                          | Remove the task at a specific index.                     |
| **FIND**   | `find <keyword>`                          | Search for tasks containing a keyword.                   |

---

## Installation

Download .jar file and run with
`java -jar barcelona.jar`