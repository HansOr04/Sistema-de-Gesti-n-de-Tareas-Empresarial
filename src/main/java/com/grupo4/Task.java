package com.grupo4;

public class Task {
    private final String id;
    private String title;
    private String description;
    private String priority;   // "ALTA", "MEDIA", "BAJA"
    private String dueDate;    // formato: yyyy-MM-dd

    public Task(String id, String title, String description, String priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return "ID: " + id + " | Título: " + title + " | Prioridad: " + priority +
                " | Vence: " + dueDate + "\nDescripción: " + description;
    }
}