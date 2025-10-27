# 🗂️ Task Management System (Spring Boot + React)

A full-stack **Task Management System** that lets users create, organize, and track tasks using labels and deadlines — automatically sorted using efficient data structures. Built with **Spring Boot**, **React**, and **WebSockets (STOMP)** for real-time task updates.

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

* Java 17, Spring Boot 3
* Spring Web, Spring WebSocket, STOMP
* In-memory `TreeSet + HashMap` for DSA logic
* Optional: PostgreSQL / MongoDB persistence layer

**Frontend:**

* React 18 + Vite
* Axios for API calls
* WebSocket client for live updates
* Responsive design with Flexbox/Bootstrap

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

* JWT-based authentication
* Notification service for upcoming deadlines
* Task recurrence logic
* Archive & undo using stack-based history
* Database integration for persistence

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
