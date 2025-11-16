# 🗂️ Task Management System (Spring Boot + React)

A full-stack **Task Management System** designed to showcase clean architecture, backend sorting logic, and modern frontend state management. Tasks can be created, updated, deleted, filtered, and are always kept in automatically sorted order based on deadline, priority label, and insertion time.

Built with **Spring Boot, React, Redux Toolkit, and JWT authentication**.

---

## 🚀 Features

* **Create, Update, Delete Tasks**
* **Auto-sorted order** based on:

  1. Nearest **deadline** (ascending)
  2. **Label priority** (Important → Medium → Low)
  3. **Insertion order** (FIFO among equals)
* **Label-based filtering** via sidebar
* **Real-time updates** using Spring WebSockets + STOMP
* **Per-user task visibility**
* **Clean, minimal UI** with modal-based task creation
* **Three-dot menu** for task deletion
* **Future-ready extensions** (planned):

  * Snooze/Postpone deadlines
  * Recurring tasks
  * Server-sent reminders before deadlines
  * Completed tasks archive (stack-based undo)

### 🔐 Authentication

* User registration & login
* JWT-based authentication
* Protected CRUD routes
* User-specific task visibility

### 📌 Task Operations

* Create / Edit / Delete tasks
* Auto-sorted tasks based on:
* Nearest deadline first
* Label priority (Important → Medium → Low → None)
* Insertion order when equal

### 🎨 UI & UX

* Clean, minimal UI
* Add/Edit task modal
* Three-dot menu for deletion
* Sidebar label filters
* Global state managed via Redux



---

## 🧠 DSA Design

| Concept                                   | Implementation                                                                                                                                                                                                                                                 |
| ----------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Primary Structure**                     | `TreeSet<Task>` with custom comparator for natural sorting                                                                                                                                                                                                     |
| **Fast Lookup & CRUD**                    | `HashMap<Long, Task>` → enables O(1) access by ID                                                                                                                                                                                                              |
| **Reason for TreeSet over PriorityQueue** | `PriorityQueue` doesn’t maintain sorted order after modifications or allow efficient arbitrary deletions. `TreeSet` (with a comparator) keeps elements sorted *at all times* and supports log(n) insertion/removal — ideal for dynamically updated task lists. |
| **Comparator Order**                      | `(deadline ASC) → (label priority DESC) → (insertionTime ASC)`                                                                                                                                                                                                 |

---

## ⚙️ Tech Stack

**Backend:**

* Java 8
* Spring Boot 2.7.x
* Spring Web
* Spring Security + JWT
* Spring Data JPA
* PostgreSQL

**Backend Layers: **

* Controller: REST endpoints
* Service: sorting logic, validation, security
* Repository: JPA interfaces
* Entity: Task + User
* Security: JWT filters, authentication manager


Responsive UI with Flexbox / CSS

**Frontend:**

* React 18 + Vite
* Axios for API calls
* Redux Toolkit
* React Router

---

## 📡 API Endpoints (Core)

| Method   | Endpoint          | Description                      |
| -------- | ----------------- | -------------------------------- |
| `POST`   | `/api/tasks`      | Create new task                  |
| `GET`    | `/api/tasks`      | Get all tasks (auto-sorted)      |
| `PUT`    | `/api/tasks/{id}` | Update task                      |
| `DELETE` | `/api/tasks/{id}` | Delete task                      |
| `WS`     | `/topic/tasks`    | WebSocket topic for live updates |

---

## 🧩 UI Overview

* **Main Page:** Displays all tasks in sorted order.
* **Add Task (+)** button: Opens modal or full-screen form for task creation.
* **Task Card:** Click to edit; top-right menu (`⋮`) → delete.
* **Sidebar:** Filters tasks by label (`Important`, `Medium`, `Low`, `None`).

---

## 🔒 Future Enhancements

* Notification service for upcoming deadlines
* Task recurrence logic
* Archive & undo using stack-based history
* Drag-and-drop ordering

---

## 🧰 How to Run

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Access frontend at: `http://localhost:5173`

---

## 🧑‍💻 Author

**Sagar Janjoted**
Built to demonstrate applied DSA in full-stack systems — bridging algorithms, concurrency, and modern UI design.
