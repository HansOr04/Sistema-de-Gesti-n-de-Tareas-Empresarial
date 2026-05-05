package com.grupo4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskManager {
    private final Map<String, Task> tasks = new HashMap<>();

    private boolean isValidId(String id) {
        return id != null && id.matches("[A-Za-z]{2}\\d{3}");
    }

    private boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public Task createTask(String id, String title, String description, String priority, String dueDate) {
        if (!isValidId(id))
            throw new IllegalArgumentException("ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");
        if (tasks.containsKey(id.toUpperCase()))
            throw new IllegalArgumentException("Ya existe una tarea con el ID: " + id.toUpperCase());
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("El título es obligatorio.");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria.");
        if (priority == null || priority.isBlank())
            throw new IllegalArgumentException("La prioridad es obligatoria.");
        if (!priority.equalsIgnoreCase("ALTA") &&
                !priority.equalsIgnoreCase("MEDIA") &&
                !priority.equalsIgnoreCase("BAJA"))
            throw new IllegalArgumentException("Prioridad inválida. Use: ALTA, MEDIA o BAJA.");
        if (!isValidDate(dueDate))
            throw new IllegalArgumentException("Fecha inválida. Formato requerido: yyyy-MM-dd (ej. 2025-12-31).");

        Task task = new Task(id.toUpperCase(), title, description, priority.toUpperCase(), dueDate);
        tasks.put(id.toUpperCase(), task);
        return task;
    }

    public List<Task> findById(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El ID de búsqueda no puede estar vacío.");

        List<Task> results = tasks.values().stream()
                .filter(task -> task.getId().contains(id.toUpperCase()))
                .collect(Collectors.toList());

        if (results.isEmpty())
            throw new IllegalArgumentException("No se encontraron tareas con ID que contenga: " + id.toUpperCase());

        return results;
    }

    public int getTaskCount() { return tasks.size(); }
}