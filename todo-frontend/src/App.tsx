import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

type TaskStatus = "PENDING" | "IN_PROGRESS" | "COMPLETED";

type Task = {
  id: string;
  title: string;
  description: string;
  status: TaskStatus;
  createdAt: string;
};

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  async function loadTasks() {
    const response = await api.get<Task[]>("/tasks");
    setTasks(response.data);
  }

  async function createTask(event: React.FormEvent) {
    event.preventDefault();

    if (!title.trim()) {
      alert("Informe o título da tarefa.");
      return;
    }

    await api.post("/tasks", {
      title,
      description,
    });

    setTitle("");
    setDescription("");
    await loadTasks();
  }

  useEffect(() => {
    loadTasks();
  }, []);

  return (
    <main className="container">
      <h1>Gerenciador de Tarefas</h1>

      <form onSubmit={createTask} className="task-form">
        <input
          type="text"
          placeholder="Título da tarefa"
          value={title}
          onChange={(event) => setTitle(event.target.value)}
        />

        <input
          type="text"
          placeholder="Descrição"
          value={description}
          onChange={(event) => setDescription(event.target.value)}
        />

        <button type="submit">Criar tarefa</button>
      </form>

      <section className="task-list">
        <h2>Tarefas</h2>

        {tasks.length === 0 && <p>Nenhuma tarefa cadastrada.</p>}

        {tasks.map((task) => (
          <article key={task.id} className="task-card">
            <strong>{task.title}</strong>
            <p>{task.description}</p>
            <small>Status: {task.status}</small>
          </article>
        ))}
      </section>
    </main>
  );
}

export default App;