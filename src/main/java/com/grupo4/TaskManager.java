package com.grupo4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Task> findById(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID de búsqueda no puede estar vacío.");

        List<Task> results = tasks.values().stream()
                .filter(task -> task.getId().contains(id.toUpperCase()))
                .collect(Collectors.toList());

        if (results.isEmpty())
            throw new IllegalArgumentException("No se encontraron tareas con ID que contenga: " + id);

        return results;
    }

    public int getTaskCount() { return tasks.size(); }
}
