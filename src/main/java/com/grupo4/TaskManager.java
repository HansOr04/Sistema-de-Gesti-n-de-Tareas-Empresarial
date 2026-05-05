package com.grupo4;

import java.time.LocalDate;
import java.util.Comparator;
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

    // RF1 — Crear tarea
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
        if (dueDate == null || dueDate.isBlank())
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria.");
        if (!isValidDate(dueDate))
            throw new IllegalArgumentException("Fecha inválida. Formato requerido: yyyy-MM-dd (ej. 2025-12-31).");

        Task task = new Task(id.toUpperCase(), title, description, priority.toUpperCase(), dueDate);
        tasks.put(id.toUpperCase(), task);
        return task;
    }

    // RF2 — Buscar tarea por ID (tipo LIKE)
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

    // RF3 — Actualizar estado (solo hacia adelante, un paso a la vez)
    public Task updateStatus(String id, String newStatus) {
        if (!isValidId(id))
            throw new IllegalArgumentException("ID inválido. Formato requerido: 2 letras y 3 dígitos (ej. AB123).");

        Task task = tasks.get(id.toUpperCase());
        if (task == null)
            throw new IllegalArgumentException("Tarea no encontrada con ID: " + id.toUpperCase());

        if (!newStatus.equalsIgnoreCase("pendiente") &&
                !newStatus.equalsIgnoreCase("en progreso") &&
                !newStatus.equalsIgnoreCase("completada"))
            throw new IllegalArgumentException("Estado inválido. Use: pendiente, en progreso o completada.");

        String current = task.getStatus();
        String next = newStatus.toLowerCase();

        // Validar que sea exactamente un paso hacia adelante
        boolean valid = (current.equals("pendiente") && next.equals("en progreso")) ||
                (current.equals("en progreso") && next.equals("completada"));

        if (!valid)
            throw new IllegalArgumentException(
                    "Transición inválida. El estado '" + current + "' solo puede avanzar a '" + nextStatus(current) + "'."
            );

        task.setStatus(next);
        return task;
    }

    // Ayuda a mostrar cuál es el siguiente estado válido
    private String nextStatus(String current) {
        return switch (current) {
            case "pendiente" -> "en progreso";
            case "en progreso" -> "completada";
            default -> "ninguno (ya está completada)";
        };
    }

    // RF4 — Listar tareas por prioridad (descendente por fecha)
    public List<Task> listByPriority(String priority) {
        if (!priority.equalsIgnoreCase("ALTA") &&
                !priority.equalsIgnoreCase("MEDIA") &&
                !priority.equalsIgnoreCase("BAJA"))
            throw new IllegalArgumentException("Prioridad inválida. Use: ALTA, MEDIA o BAJA.");

        List<Task> results = tasks.values().stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .collect(Collectors.toList());

        if (results.isEmpty())
            throw new IllegalArgumentException("No hay tareas con prioridad: " + priority.toUpperCase());

        return results;
    }

    // RF5 — Listar tareas próximas a vencer (<= 7 días)
    public List<Task> listUpcoming() {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(7);

        List<Task> results = tasks.values().stream()
                .filter(task -> {
                    LocalDate due = LocalDate.parse(task.getDueDate());
                    return !due.isBefore(today) && !due.isAfter(limit);
                })
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());

        if (results.isEmpty())
            throw new IllegalArgumentException("No hay tareas próximas a vencer en los próximos 7 días.");

        return results;
    }

    public int getTaskCount() { return tasks.size(); }
}