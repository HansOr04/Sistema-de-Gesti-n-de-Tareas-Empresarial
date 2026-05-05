package com.grupo4;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private final Map<String, Task> tasks = new HashMap<>();

    // Valida que el ID tenga exactamente 2 letras + 3 dígitos (ej. AB123)
    private boolean isValidId(String id) {
        return id != null && id.matches("[A-Za-z]{2}\\d{3}");
    }

    // RF1 — Crear tarea
    public Task createTask(String id, String title, String description, String priority, String dueDate) {
        if (!isValidId(id))
            throw new IllegalArgumentException("ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");
        if (tasks.containsKey(id.toUpperCase()))
            throw new IllegalArgumentException("Ya existe una tarea con el ID: " + id);
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("El título es obligatorio.");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria.");
        if (dueDate == null || dueDate.isBlank())
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria.");
        if (!priority.equalsIgnoreCase("ALTA") &&
                !priority.equalsIgnoreCase("MEDIA") &&
                !priority.equalsIgnoreCase("BAJA"))
            throw new IllegalArgumentException("Prioridad inválida. Use: ALTA, MEDIA o BAJA.");

        Task task = new Task(id.toUpperCase(), title, description, priority.toUpperCase(), dueDate);
        tasks.put(id.toUpperCase(), task);
        return task;
    }

    // RF2 — Buscar tarea por ID
    public Task findById(String id) {
        if (!isValidId(id))
            throw new IllegalArgumentException("ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");
        Task task = tasks.get(id.toUpperCase());
        if (task == null)
            throw new IllegalArgumentException("Tarea no encontrada con ID: " + id);
        return task;
    }

    public int getTaskCount() { return tasks.size(); }
}
